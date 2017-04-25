package com.app.queueme;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.queueme.FeedAdapters.FeedAdapter_ChooseSubject_Ass;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ChooseSubjectAss extends Activity {
    // Search EditText
    //private EditText inputSearch;
    ArrayAdapter feedAdapter;
    private Button popup;
    private Button meny;
    private Button home;
    private String uid;
    private ArrayList<Subject> subjects;
    private TextView no_added_subject;
    private ImageView arrow_down;

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(ChooseSubjectAss.this, StudOrAss.class));
        finish();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alternative_choose_subject_stud);

        FirebaseApp.initializeApp(this);


        meny = (Button) findViewById(R.id.meny);
        meny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChooseSubjectAss.this, MenyActivity.class));

            }
        });
        home = (Button) findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChooseSubjectAss.this, StudOrAss.class));
                finish();
            }
        });

        //activity_coose_subject_ass

        //finer listview og setter som variabel
        final ListView l=(ListView) findViewById(R.id.listview);
        no_added_subject = (TextView) findViewById(R.id.no_added_subject);

        arrow_down = (ImageView) findViewById(R.id. arrow_down);



        //finner buttons
        popup = (Button) findViewById(R.id.popup);


        //lager listen alle fagene skal legger i
       subjects = new ArrayList<Subject>();

        //inputSearch = (EditText) findViewById(R.id.inputSearch);

        no_added_subject.setVisibility(View.VISIBLE);

        //henter ut alle subjects som ligger i databasen og legger i liste
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference();
        // brukes til dialogen:
        final DatabaseReference myRefdialog = database.getReference();
        final DatabaseReference myRefdialog2 = database.getReference();
        //
        //henter info om bruker
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        this.uid=user.getUid();

        myRef.child("Person").child(user.getUid()).child("FavoriteAssSubject").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                no_added_subject.setVisibility(View.INVISIBLE);

                subjects.clear();
                //get all of the children of this level.
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                //shake hands with each of them
                for (DataSnapshot child: children){
                    Subject subject = child.getValue(Subject.class);
                    subjects.add(subject);



                }

                if(subjects.isEmpty()){
                    no_added_subject.setVisibility(View.VISIBLE);

                    no_added_subject.setText("You haven't added any subject yet. Press ADD SUBJECT below to do so");
                    arrow_down.setVisibility(View.VISIBLE);
                }

                FeedAdapter_ChooseSubject_Ass Adapter = new FeedAdapter_ChooseSubject_Ass(ChooseSubjectAss.this, R.layout.list_subjectitem_ass, subjects);
                l.setAdapter(Adapter);

                l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Subject subject = (Subject) subjects.get(position);
                        Intent moveToDetailIntent = new Intent(ChooseSubjectAss.this, StartSession.class);
                        // moveToDetailIntent.putExtra("bkjb", );
                        String emnekode= subject.getEmnekode();
                        String emnenavn = subject.getEmnenavn();


                        moveToDetailIntent.putExtra("emnekode",emnekode);
                        moveToDetailIntent.putExtra("emnenavn",emnenavn);


                        //startActivityForResult(moveToDetailIntent,position);
                        //Person Anders = new Person();
                        //Anders.setName("nonneanders");
                        //person.getPersons().add(Anders);
                        startActivity(moveToDetailIntent);


                    }
                });


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder mbuilder = new AlertDialog.Builder(ChooseSubjectAss.this);
                View mView= getLayoutInflater().inflate(R.layout.dialog_subject,null);
                mbuilder.setView(mView);
                final AlertDialog dialog = mbuilder.create();
                dialog.show();
                final EditText inputSearch = (EditText) mView.findViewById(R.id.inputSearch);
                Button finish = (Button) mView.findViewById(R.id.finish);
                final ListView listView=(ListView) mView.findViewById(R.id.listview);

                //lager listen alle fagene skal legger i
                final ArrayList<Subject> subjects = new ArrayList<Subject>();

                //
                myRef.child("Subject").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //get all of the children of this level.
                        Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                        //shake hands with each of them
                        for (DataSnapshot child: children){
                            Subject subject = child.getValue(Subject.class);
                            subjects.add(subject);



                        }

                        //lager arrayadapter som viser listene

                        feedAdapter = new ArrayAdapter(ChooseSubjectAss.this, android.R.layout.simple_list_item_1,subjects);
                        listView.setAdapter(feedAdapter);
                        //definerer hva som skjer når man trykker på searchknappen
                        inputSearch.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                ChooseSubjectAss.this.feedAdapter.getFilter().filter(s);
                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                            }
                        });
                        //lager funkjsonen når man trykker på en av knappene i listviewen
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Subject subject = (Subject) subjects.get(position);
                                String emnekode= subject.getEmnekode();
                                String emnenavn = subject.getEmnenavn();



                                //henter brukerdata
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                myRefdialog2.child("Person").child(user.getUid()).child("FavoriteAssSubject").child(emnekode).setValue(subject);
                                Toast.makeText(ChooseSubjectAss.this,"Subject added to favorites",Toast.LENGTH_SHORT).show();
                                no_added_subject.setVisibility(View.INVISIBLE);
                                arrow_down.setVisibility(View.INVISIBLE);





                            }
                        });






                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                //
                finish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            }

        });

        l.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ChooseSubjectAss.this, "LONG TOUCH",
                        Toast.LENGTH_SHORT).show();
                final Subject subject = (Subject) subjects.get(position);

                final AlertDialog.Builder mbuilder = new AlertDialog.Builder(ChooseSubjectAss.this);
                View mView= getLayoutInflater().inflate(R.layout.delete_subject_popup,null);
                mbuilder.setView(mView);
                final AlertDialog dialog = mbuilder.create();
                dialog.show();
                Button yes = (Button) mView.findViewById(R.id.yes);
                Button no = (Button) mView.findViewById(R.id.no);

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        myRefdialog.child("Person").child(uid).child("FavoriteAssSubject").child(subject.getEmnekode()).removeValue();
                        subjects.remove(subject);
                        dialog.dismiss();
                    }
                });

                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                Log.v("long clicked","pos: " + position);



                return true;
            }

        });
        }
public ArrayList getSubjectss(){
    return subjects;
}

}

