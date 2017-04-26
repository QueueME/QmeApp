package com.example.queueme.Studassqueue;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.queueme.Person;
import com.example.queueme.R;
import com.example.queueme.StudOrAss;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class StudassQueue extends AppCompatActivity {

    private TextView nr;
    private TextView person;
    private TextView next_in_line;
    Button end;
    private Button next;
    private ArrayList<Person> students = new ArrayList<Person>();
    private String emnenavn;
    private String emnekode;
    private String uid;
    private DatabaseReference ref;
    private ImageView image;
    private boolean first=true;
    private ImageView im;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studass_queue);
        //gets infor fram last page
        Intent intent = getIntent();
        emnenavn = intent.getStringExtra("emnenavn");
        emnekode = intent.getStringExtra("emnekode");
        //finds buttons ect
        person=(TextView) findViewById(R.id.person);
        nr=(TextView) findViewById(R.id.nr);
        next_in_line= (TextView) findViewById(R.id.next_in_line);
        next_in_line.setVisibility(View.INVISIBLE);
        person.setVisibility(View.INVISIBLE);
        nr.setText("0");
        im = (ImageView) findViewById(R.id.im);
        //connects to firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref = database.getReference("Subject");
        DatabaseReference myRef = database.getReference("Subject");
        final DatabaseReference myRef2 = database.getReference("Subject");
        //sets onclick for endig queueu.
        end =(Button) findViewById(R.id.end);
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //shows dialog asking you if you are sure you want to quit
                final AlertDialog.Builder mbuilder = new AlertDialog.Builder(StudassQueue.this);
                View mView= getLayoutInflater().inflate(R.layout.popup_warning,null);
                mbuilder.setView(mView);
                final AlertDialog dialog = mbuilder.create();
                dialog.show();
                Button yes = (Button) mView.findViewById(R.id.yes);
                Button no = (Button) mView.findViewById(R.id.no);
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //quit
                        removeQueue(myRef2);
                        dialog.dismiss();
                    }
                });
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //close dialog
                        dialog.dismiss();
                    }
                });

            }

        });
        next =(Button ) findViewById(R.id.next);
        next.setVisibility(View.INVISIBLE);
        //onclick deletes first perosn in line and sends you the next
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removePerson(ref,students.get(0).getUid());
                students.remove(0);

                Next();

            }
        });


        //get user uid
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            uid = user.getUid();
        }

        final ArrayList<Person> students = new ArrayList<Person>();
        this.students=students;

        //defines what happends when someone is added and removed
        myRef.child(emnekode).child("StudAssList").child(uid).child("Queue").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //henter data og legger til personen som addes til listen over
                if (students.isEmpty()){
                    next.setVisibility(View.VISIBLE);
                }
                fetchData(dataSnapshot);
                Next();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {


            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

                //deletes the person from local list
                Person person1 = dataSnapshot.getValue(Person.class);
                for (Person personinlist:students){
                    if (person1.getUid()==personinlist.getUid()){
                        //nr.setText(String.valueOf(students.indexOf(personinlist)));
                        students.remove(students.indexOf(personinlist));
                    }else{

                }
            }
                nr.setText(String.valueOf(linecount()));
                if (!students.isEmpty()) {

                    person.setText(students.get(0).getName() + " are next in line");
                }else {
                    person.setVisibility(View.INVISIBLE);
                    next.setVisibility(View.INVISIBLE);
                    im.setImageResource(R.drawable.coffeeeeeeeeeeee);

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
    private void Next(){

        nr.setText(String.valueOf(linecount()));
            if(!students.isEmpty()) {
                next_in_line.setVisibility(View.VISIBLE);

                person.setText(students.get(0).getName() + " is next in line");
                person.setVisibility(View.VISIBLE);
                //setting imageview based on gender
                if (students.get(0).isMale()){
                    im.setImageResource(R.drawable.astudfull);

                }
                else if (!students.get(0).isMale()){
                    im.setImageResource(R.drawable.girlstud);

                }


            }else{
                person.setVisibility(View.INVISIBLE);
                im.setImageResource(R.drawable.coffeeeeeeeeeeee);
                next_in_line.setVisibility(View.INVISIBLE);

            }


    }
    private void removePerson(DatabaseReference ref,String puid){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        ref.child(emnekode).child("StudAssList").child(user.getUid()).child("Queue").child(puid).removeValue();

    }

    private void removeQueue(DatabaseReference ref){
        startActivity(new Intent(StudassQueue.this, StudOrAss.class));
        ref.child(emnekode).child("StudAssList").child(uid).removeValue();
        finish();
    }
    private void fetchData(DataSnapshot dataSnapshot){
        Person person = dataSnapshot.getValue(Person.class);
        students.add(person);
    }

    private int linecount() {
        return students.size();
    }

    @Override
    public void onBackPressed() {

    }



}
