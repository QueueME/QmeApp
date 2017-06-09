package com.example.queueme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StartSession extends AppCompatActivity implements View.OnClickListener {

    private String emnekode;
    //private int queuenr;
    private String emnenavn;
    //private TextView antall;
    private Button queue;
    private EditText time;
    private  TextView subject;

    private String myUID;

    private ArrayList<Person> persons = new ArrayList<Person>();
    private Person Me;
    private Button meny;
    private Button home;
    private String myName;
    private SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startsession);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        //sets toolbar
        meny = (Button) findViewById(R.id.meny);
        meny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartSession.this, MenyActivity.class));

            }
        });
        home = (Button) findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartSession.this, ChooseSubjectAss.class));
                finish();
            }
        });
        //gets user ingo
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        myUID=user.getUid();
        //gets info from last activity

        Intent intent = getIntent();
        emnenavn = intent.getStringExtra("emnenavn");
        emnekode = intent.getStringExtra("emnekode");
        //finds buttons
        queue = (Button) findViewById(R.id.queue);
        queue.setOnClickListener(this);
        //finds text
        time = (EditText) findViewById(R.id.time_up_to);
        subject= (TextView) findViewById(R.id.subject);
        subject.setText(emnekode +" "+ emnenavn);


        //retrieves  list of persons in firebase
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference myRef = database.getReference();
            myRef.child("Person").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //get all of the children of this level.
                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                    //shake hands with each of them
                    for (DataSnapshot child: children){
                        Person person = child.getValue(Person.class);
                        persons.add(person);



                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
        });
        //retrives users name
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



    private void QueueMe(){
        //lager en komling til databasen
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //henter ut info om meg
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //legger til timestamp på meg siden jeg skal være studass
        DatabaseReference myRef = database.getReference("Person");
        myRef.child(user.getUid()).child("time_to_stop").setValue( time.getText().toString());

        //lager et duplikat av meg selv som studass som jeg skal legge inn under et fag slik at
        //studenter kan se meg og legge meg til
        Person person = new Person();
        person.setTime_to_stop(time.getText().toString());
        person.setName(myName);
        person.setEmail(user.getEmail());
        person.setUid(user.getUid());
        Boolean gender = prefs.getBoolean("gender", false);
        person.setMale(gender);
        //legger jeg meg inn under det valgte faget.
        DatabaseReference myRef2 = database.getReference("Subject");
        myRef2.child(emnekode).child("StudAssList").child(user.getUid()).setValue(person);

        //tar med info om faget inn i neste aktivitet for å vite hviket fag jeg er i
        Intent moveToDetailIntent = new Intent(StartSession.this,com.example.queueme.Studassqueue.StudassQueue.class);
        moveToDetailIntent.putExtra("emnekode",emnekode);
        moveToDetailIntent.putExtra("emnenavn",emnenavn);
        startActivity(moveToDetailIntent);
        finish();

    }


    @Override
    public void onClick(View v) {
        if (v==queue){
            QueueMe();
        }
    }
}
