package com.example.queueme;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class InQueue extends AppCompatActivity {
    private String email;
    private String uid;
    private String personuid;
    private String emnenavn;
    private String emnekode;
    private TextView count;
    private TextView nrinline;
    private TextView emneinfo;
    private ArrayList<Person> students = new ArrayList<Person>();
    private Button end;
    private boolean first=false;
    private SharedPreferences prefs;


    //notification
    NotificationCompat.Builder notification;
    private static final int uniqueID = 45612;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inqueue);

        //finds imageviews and set them depending on gender
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        ImageView female=(ImageView) findViewById(R.id.girl);
        ImageView male=(ImageView) findViewById(R.id.man);
        Boolean gender = prefs.getBoolean("gender", false);
        if (!gender){
            female.setVisibility(View.VISIBLE);
            male.setVisibility(View.INVISIBLE);

        }
        //sets notification
        notification = new NotificationCompat.Builder(this);
        notification.setAutoCancel(true);

        //gets info from last activity
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        personuid = intent.getStringExtra("uid");
        emnenavn = intent.getStringExtra("emnenavn");
        emnekode = intent.getStringExtra("emnekode");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        uid=user.getUid();

        //creates link to firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Subject");
        final DatabaseReference myRef2 = database.getReference("Subject");






        //finds buttons ect
        count = (TextView) findViewById(R.id.count);
        nrinline = (TextView) findViewById(R.id.nrInLine);
        emneinfo=(TextView)findViewById(R.id.emneinfo);
        emneinfo.setText(emnekode + " " + emnenavn);
        end = (Button) findViewById(R.id.step_out);
        //onclick for ending queue. Open allert dialog
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final AlertDialog.Builder mbuilder = new AlertDialog.Builder(InQueue.this);
                View mView= getLayoutInflater().inflate(R.layout.popup_warning,null);
                mbuilder.setView(mView);
                final AlertDialog dialog = mbuilder.create();
                dialog.show();
                Button yes = (Button) mView.findViewById(R.id.yes);
                Button no = (Button) mView.findViewById(R.id.no);
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        removeQueue(myRef2);

                    }
                });
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            }

        });



        //gets everybody in the queue
        myRef.child(emnekode).child("StudAssList").child(personuid).child("Queue").orderByChild("timestamp").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                students.clear();
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child: children){
                    Person person = child.getValue(Person.class);
                    students.add(person);



                }
                //sets text in TextView
                count.setText(""+ linecount()+"");
                //ifsetning
                nrinline.setText("" + nrInline() + "");
                if (!students.isEmpty()) {
                    if ((students.get(0).getUid() == uid)&& !first) {
                        first=true;
                        //builds the actual notification
                        notification.setSmallIcon(R.drawable.astudass);
                        notification.setTicker("Dette er ticker");
                        notification.setWhen(System.currentTimeMillis());
                        notification.setContentTitle("It's your turn");
                        notification.setContentText("Seek out your student assistant and get your help :)");

                        Uri alarmSound = RingtoneManager.getActualDefaultRingtoneUri(InQueue.this, RingtoneManager.TYPE_NOTIFICATION);
                        notification.setSound(alarmSound);

                        Intent intent = new Intent(InQueue.this, InQueue.class);
                        PendingIntent pendingIntent = PendingIntent.getActivity(InQueue.this, 0, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT);
                        notification.setContentIntent(pendingIntent);

                        // sending notification
                        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                        nm.notify(uniqueID, notification.build());
                        //
                        Toast.makeText(InQueue.this, "You have been notified",
                                Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //defining action on people added and removed form queue
        myRef.child(emnekode).child("StudAssList").child(personuid).child("Queue").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                //fetchDataDelete(dataSnapshot);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Person person = dataSnapshot.getValue(Person.class);

                if (person.getUid() == uid) {
                    startActivity(new Intent(InQueue.this, StudOrAss.class));
                    finish();

                } else{

                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    private void removeQueue(DatabaseReference ref){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        ref.child(emnekode).child("StudAssList").child(personuid).child("Queue").child(user.getUid()).removeValue();
        startActivity(new Intent(InQueue.this, StudOrAss.class));
        finish();
    }


    private int linecount() {
        return students.size();
    }

    private int nrInline() {
        int index=0;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        for (Person person : students) {
            if (person.getUid() == user.getUid()) {
                index = students.indexOf(person);

            }
        }
        return index +1;


    }
    @Override
    public void onBackPressed() {

    }
}
