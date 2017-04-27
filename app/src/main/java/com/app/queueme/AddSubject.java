package com.app.queueme;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddSubject extends AppCompatActivity implements View.OnClickListener{
    private EditText ekode;
    private EditText enavn;
    private Button btnesave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);
        //needed to connect to firebase
        FirebaseApp.initializeApp(this);
        //finds buttons ect and sets onclicklistenes
        ekode= (EditText) findViewById(R.id.ekode);
        enavn= (EditText) findViewById(R.id.enavn);
        btnesave=(Button) findViewById(R.id.btnesave);
        btnesave.setOnClickListener(this);

    }
//writes subject to databse
    public void onSaveClicked2(){
        Subject subject = new Subject();

        subject.setEmnekode(ekode.getText().toString());
        subject.setEmnenavn(enavn.getText().toString());

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        myRef.child("Subject").child(subject.getEmnekode()).setValue(subject);
    }

    @Override
    public void onClick(View v) {
        if (v==btnesave){
            onSaveClicked2();
            Toast.makeText(AddSubject.this, "Saved",Toast.LENGTH_SHORT).show();
        }
    }
}
