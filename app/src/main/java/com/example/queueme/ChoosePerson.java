package com.example.queueme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChoosePerson extends AppCompatActivity {

    private String emnekode;
    private String emnenavn;
    private Button meny;
    private Button home;
    private ArrayList<Person> persons = new ArrayList<Person>();
    private ListView l;



    public String getEmnekode() {
        return emnekode;
    }

    public void setEmnekode(String emnekode) {
        this.emnekode = emnekode;
    }

    public String getEmnenavn() {
        return emnenavn;
    }

    public void setEmnenavn(String emnenavn) {
        this.emnenavn = emnenavn;
    }

    public Button getMeny() {
        return meny;
    }

    public void setMeny(Button meny) {
        this.meny = meny;
    }

    public Button getHome() {
        return home;
    }

    public void setHome(Button home) {
        this.home = home;
    }


    ListView v;

    public ListView getListView(){
        return v;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_person);

        FirebaseApp.initializeApp(this);


        meny = (Button) findViewById(R.id.meny);

        meny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChoosePerson.this, MenyActivity.class));

            }
        });
        home = (Button) findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChoosePerson.this, StudOrAss.class));

            }
        });
        //finner listview

        l=(ListView) findViewById(R.id.listview);


        //henter ut info fra forrige side
        Intent intent = getIntent();
        emnenavn = intent.getStringExtra("emnenavn");
        emnekode  = intent.getStringExtra("emnekode");

        //Henter ut perosnene fra databasen og legger dem i persons listen
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        everything(myRef,emnekode);



    }

public void everything(DatabaseReference myRef, final String emnekode){
    myRef.child("Subject").child(emnekode).child("StudAssList").addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            persons.clear();
            //get all of the children of this level.
            Iterable<DataSnapshot> children = dataSnapshot.getChildren();

            //shake hands with each of them
            for (DataSnapshot child: children){
                Person person = child.getValue(Person.class);
                persons.add(person);



            }
            //lager et adapter

            FeedAdapter_ChoosePerson feedAdapter = new FeedAdapter_ChoosePerson(ChoosePerson.this, R.layout.list_subjectitem_person, persons);
            l.setAdapter(feedAdapter);
            //definerer hva som skal skje når man trykker på en person
            l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Person person = (Person) persons.get(position);
                    Intent moveToDetailIntent = new Intent(ChoosePerson.this, DetailedActivity.class);


                    String email = person.getEmail().toString();
                    String uid = person.getUid().toString();



                    moveToDetailIntent.putExtra("email",email);
                    moveToDetailIntent.putExtra("uid",uid);
                    moveToDetailIntent.putExtra("emnekode",emnekode);
                    moveToDetailIntent.putExtra("emnenavn",emnenavn);

                    startActivity(moveToDetailIntent);




                }
            });



        }


        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });
}
public ArrayList getPersons(){
        return persons;
    }
    public void setEmnekode(String input){
     this.emnekode=input;
    }
    public void setEmnenavn(String input){
        this.emnenavn=input;
    }
}
