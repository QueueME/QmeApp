package com.example.queueme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DetailedActivity extends AppCompatActivity implements View.OnClickListener{
    private String email;
    private String personuid;
    private String emnekode;
    private String emnenavn;
    private int nrInLine;
    private TextView name;
    private TextView subjectinfo;
    private TextView availible_until;
    private TextView count;
    private TextView subnavn;
    private ArrayList<Person> persons = new ArrayList<Person>();
    private ArrayList<Person> studasses = new ArrayList<Person>();
    private String myName;
    private Button queue;
    private Button meny;
    private Button home;

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alternative_queueinfo);

        FirebaseApp.initializeApp(this);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        //gets infor from last page
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        personuid=intent.getStringExtra("uid");
        emnenavn =intent.getStringExtra("emnenavn");
        emnekode = intent.getStringExtra("emnekode");
        //sets toolbar
        meny = (Button) findViewById(R.id.meny);
        meny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailedActivity.this, MenyActivity.class));

            }
        });
        home = (Button) findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailedActivity.this, ChoosePerson.class);
                intent.putExtra("emnekode",emnekode);
                intent.putExtra("emnenavn",emnenavn);
                startActivity(intent);
                finish();
            }
        });
        //fins
        queue = (Button) findViewById(R.id.queue);
        queue.setOnClickListener(this);
        name = (TextView) findViewById(R.id.name);
        subjectinfo = (TextView) findViewById(R.id.name);
        subnavn=(TextView) findViewById(R.id.subnavn);
        subnavn.setText(emnenavn+" " +emnekode);
        availible_until = (TextView) findViewById(R.id.avilible_until);
        count= (TextView) findViewById(R.id.count);



        //establish connection to firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Subject");
        final DatabaseReference myRef2 = database.getReference("Subject").child(emnekode).child("StudAssList");
        //retireves a list of persons from database
        myRef.child(emnekode).child("StudAssList").child(personuid).child("Queue").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                persons.clear();
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child: children){
                    Person person = child.getValue(Person.class);
                    persons.add(person);
                }

                count.setText(""+ linecount()+"");

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //retrieves list of studassess from firebase
        Query queryRef =myRef2.orderByChild("uid").equalTo(personuid);
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                //shake hands with each of them
                for (DataSnapshot child: children){
                    Person person = child.getValue(Person.class);
                    studasses.add(person);
                }

                name.setText(studasses.get(0).getName());
                availible_until.setText(studasses.get(0).getTime_to_stop());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //retriebes user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //retrieves users name
        DatabaseReference personRef = database.getReference("Person");
        personRef.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Person person = dataSnapshot.getValue(Person.class);
                String firstInLineName = person.getName();
                myName=firstInLineName;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void fetchData(DataSnapshot dataSnapshot) {
        Person person = dataSnapshot.getValue(Person.class);
        persons.add(person);
    }

    private void fetchDataDelete(DataSnapshot dataSnapshot) {
        Person person = dataSnapshot.getValue(Person.class);
        persons.remove(person);
    }

    private int linecount() {
        return persons.size();
    }



    private void QueueMe(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email=user.getEmail();
        String uid=user.getUid();

        Person person =new Person();
        person.setEmail(user.getEmail());
        person.setName(myName);
        person.setUid(user.getUid());
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        person.setTimestamp(ts);
        Boolean gender = prefs.getBoolean("gender", false);
        person.setMale(gender);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        myRef.child("Subject").child(emnekode).child("StudAssList").child(personuid).child("Queue").child(uid).setValue(person);

    }


    @Override
    public void onClick(View v) {
        if (v==queue){

            QueueMe();
           //bring info to next activity
            Intent moveToDetailIntent = new Intent(DetailedActivity.this, InQueue.class);
            moveToDetailIntent.putExtra("email",email);
            moveToDetailIntent.putExtra("uid",personuid);
            moveToDetailIntent.putExtra("emnekode",emnekode);
            moveToDetailIntent.putExtra("emnenavn",emnenavn);
            //begin activity
            startActivity(moveToDetailIntent);
            finish();

        }
    }
}
