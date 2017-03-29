package com.example.queueme.MySessionSwipeFunction;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

import static com.example.queueme.R.id.pager;


public class ScreenSlidePagerActivity extends FragmentActivity {
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private int NUM_PAGES = 1;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;
    private TextView nr;
    private TextView person;
    Button end;
    private ArrayList<Person> students = new ArrayList<Person>();
    private String emnenavn;
    private String emnekode;
    private String uid;
    private DatabaseReference ref;
//notifikasjon
    NotificationCompat.Builder notification;
    private static final int uniqueID = 45612;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screenslide);
//notifikasjon
        notification = new NotificationCompat.Builder(this);
        notification.setAutoCancel(true);

        Intent intent = getIntent();
        emnenavn = intent.getStringExtra("emnenavn");
        emnekode = intent.getStringExtra("emnekode");

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.addOnPageChangeListener(viewPagerPageChangeListener);

        person=(TextView) findViewById(R.id.person);
        nr=(TextView) findViewById(R.id.nr);
        person.setText("There are no person in line");
        nr.setText("0");
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref = database.getReference("Subject");

        DatabaseReference myRef = database.getReference("Subject");
        final DatabaseReference myRef2 = database.getReference("Subject");

        end =(Button) findViewById(R.id.end);
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final AlertDialog.Builder mbuilder = new AlertDialog.Builder(ScreenSlidePagerActivity.this);
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
                        dialog.dismiss();
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

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            uid = user.getUid();
        }

        final ArrayList<Person> students = new ArrayList<Person>();
        this.students=students;

        //lager funskjoner når endring under denne referansen skjer
        myRef.child(emnekode).child("StudAssList").child(uid).child("Queue").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                NUM_PAGES+=1;
                mPagerAdapter.notifyDataSetChanged();




                //henter data og legger til personen som addes til listen over
                fetchData(dataSnapshot);
                //setter tekst i textviewene
                //int studentsnr= students.size();
                nr.setText(String.valueOf(linecount()));

                if (!students.isEmpty()){
                    String uid= students.get(0).getUid();
                    Person person = dataSnapshot.getValue(Person.class);
                    TextView firstperson = (TextView) findViewById(R.id.person);
                    firstperson.setText(person.getName()+ " are next in line");
                    //finner navnet på første i kø i persondata i persondatabasen
                   /* DatabaseReference personRef = database.getReference("Person");
                    personRef.child(uid).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Person person = dataSnapshot.getValue(Person.class);
                            String firstInLineName = person.getName();
                            //oppdaterer texviewen
                            TextView firstperson = (TextView) findViewById(R.id.person);
                            firstperson.setText(firstInLineName + " are next in line");
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });*/
                }else{
                    TextView firstperson = (TextView) findViewById(R.id.person);
                    firstperson.setText("no one in line");
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {


            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                NUM_PAGES-=1;
                mPagerAdapter.notifyDataSetChanged();
                //henter ut personene som slettes og sletter han fra listen
                Person person = dataSnapshot.getValue(Person.class);
                for (Person personinlist:students){
                    if (person.getUid()==personinlist.getUid()){
                        //nr.setText(String.valueOf(students.indexOf(personinlist)));
                        students.remove(students.indexOf(personinlist));
                    }else{

                    }
                }
                nr.setText(String.valueOf(linecount()));


                //fetchDataDelete(dataSnapshot);
                //oppdatere texviewene


                if (!students.isEmpty()) {
                    TextView firstperson = (TextView) findViewById(R.id.person);
                    firstperson.setText(students.get(0).getName() + " are next in line");
                }else {
                    TextView firstperson = (TextView) findViewById(R.id.person);
                    firstperson.setText("no one in line");
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
    private void removePerson(DatabaseReference ref,String puid){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        ref.child(emnekode).child("StudAssList").child(user.getUid()).child("Queue").child(puid).removeValue();

    }
    void updatePageCount(ViewPager viewPager) {
        int currentItem = mPager.getCurrentItem();
        mPager.setAdapter(mPager.getAdapter());
        mPager.setCurrentItem(currentItem);
    }
    private void removeQueue(DatabaseReference ref){

        startActivity(new Intent(ScreenSlidePagerActivity.this, StudOrAss.class));
        ref.child(emnekode).child("StudAssList").child(uid).removeValue();
        finish();
    }
    private void fetchData(DataSnapshot dataSnapshot)
    {
        //students.clear();
        Person person = dataSnapshot.getValue(Person.class);
        students.add(person);
    }
    private void fetchDataDelete(DataSnapshot dataSnapshot)
    {
        //students.clear();
        Person person = dataSnapshot.getValue(Person.class);
        students.remove(0);


    }

    private int linecount() {
        return students.size();
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            Toast.makeText(ScreenSlidePagerActivity.this, "NEXT",Toast.LENGTH_SHORT).show();

            if (!students.isEmpty()) {
                removePerson(ref,students.get(0).getUid());
                students.remove(0);
                if(!students.isEmpty()) {
                    person.setText(students.get(0).getName() + " is next in line");
                }else{
                    person.setText("There are no one in your line");
                }
                nr.setText(String.valueOf(linecount()));
            }else{

            }

            //kan override getcount()???
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new ScreenSlidePageFragment();
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}