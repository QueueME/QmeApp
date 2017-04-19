package com.example.queueme;

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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.queueme.FeedAdapters.FeedAdapter_ChooseSubject_Ass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ChooseSubjectStud extends Activity {
    ArrayAdapter feedAdapter;
    private Button popup;
    private Button meny;
    private Button home;
    private String uid;
    private TextView no_added_subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alternative_choose_subject_stud);
        //sets toolbar
        meny = (Button) findViewById(R.id.meny);
        meny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChooseSubjectStud.this, MenyActivity.class));

            }
        });
        home = (Button) findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChooseSubjectStud.this, StudOrAss.class));

            }
        });
        //finds buttons ect
        final ListView l=(ListView) findViewById(R.id.listview);
        //finner buttons
        popup = (Button) findViewById(R.id.popup);
        no_added_subject = (TextView) findViewById(R.id.no_added_subject);
        no_added_subject.setVisibility(View.VISIBLE);
        //instanciates list of subjects
        final ArrayList<Subject> subjects = new ArrayList<Subject>();

        //connects to database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference();
        final DatabaseReference myRefdialog = database.getReference();
        final DatabaseReference myRefdialog2 = database.getReference();

        //gets current user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        this.uid = user.getUid();
        myRef.child("Person").child(user.getUid()).child("FavoriteStudSubject").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
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
                }else{
                    no_added_subject.setVisibility(View.INVISIBLE);

                }

                //creates arrayadapter to display list
                feedAdapter = new FeedAdapter_ChooseSubject_Ass(ChooseSubjectStud.this, R.layout.list_subjectitem_ass, subjects);
                //sets adapter
                l.setAdapter(feedAdapter);

                //sets onclick listeners for each item in adapter
                l.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Subject subject = (Subject) subjects.get(position);
                        //creates intent
                        Intent moveToDetailIntent = new Intent(ChooseSubjectStud.this, ChoosePerson.class);
                      //brings info to next page
                        String emnekode= subject.getEmnekode();
                        String emnenavn = subject.getEmnenavn();
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
        //onclick for popup
        popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder mbuilder = new AlertDialog.Builder(ChooseSubjectStud.this);
                View mView= getLayoutInflater().inflate(R.layout.dialog_subject,null);
                mbuilder.setView(mView);
                final AlertDialog dialog = mbuilder.create();
                dialog.show();
                final EditText inputSearch = (EditText) mView.findViewById(R.id.inputSearch);
                Button finish = (Button) mView.findViewById(R.id.finish);
                final ListView listView=(ListView) mView.findViewById(R.id.listview);

               //list for search array
                final ArrayList<Subject> subjects = new ArrayList<Subject>();

                //fills list from database
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

                        //creates new arraylist
                        feedAdapter = new ArrayAdapter(ChooseSubjectStud.this, android.R.layout.simple_list_item_1,subjects);
                        listView.setAdapter(feedAdapter);
                        //defines search criterias
                        inputSearch.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                ChooseSubjectStud.this.feedAdapter.getFilter().filter(s);
                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                            }
                        });
                        //creates onclick listener for each item
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Subject subject = (Subject) subjects.get(position);
                                String emnekode= subject.getEmnekode();
                                String emnenavn = subject.getEmnenavn();
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                myRefdialog2.child("Person").child(user.getUid()).child("FavoriteStudSubject").child(subject.getEmnekode()).setValue(subject);
                                Toast.makeText(ChooseSubjectStud.this,"Subject added to favorites",Toast.LENGTH_SHORT).show();
                                no_added_subject.setVisibility(View.INVISIBLE);


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
        //onclick for long touch. Creates a dialog allowin for deletion of item
        l.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ChooseSubjectStud.this, "LONG TOUCH",
                        Toast.LENGTH_SHORT).show();
                final Subject subject = (Subject) subjects.get(position);

                final AlertDialog.Builder mbuilder = new AlertDialog.Builder(ChooseSubjectStud.this);
                View mView= getLayoutInflater().inflate(R.layout.delete_subject_popup,null);
                mbuilder.setView(mView);
                final AlertDialog dialog = mbuilder.create();
                dialog.show();
                Button yes = (Button) mView.findViewById(R.id.yes);
                Button no = (Button) mView.findViewById(R.id.no);

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        myRefdialog.child("Person").child(uid).child("FavoriteStudSubject").child(subject.getEmnekode()).removeValue();
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



}
