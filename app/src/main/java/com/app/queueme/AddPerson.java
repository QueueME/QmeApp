package com.app.queueme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddPerson extends AppCompatActivity implements View.OnClickListener{

    private EditText email;
    private EditText name;
    private Button save;
    private Button btnsubject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addperson);

        //finds buttons ect
        email=(EditText) findViewById(R.id.email);
        name =(EditText) findViewById(R.id.name);
        save=(Button) findViewById(R.id.save);
        btnsubject=(Button) findViewById(R.id.btnsubject);
        //setts onclicklisteners
        save.setOnClickListener(this);
        btnsubject.setOnClickListener(this);


    }
//writes personobject to database
    public void onSaveClicked(){
        Person person = new Person();
        person.setEmail(email.getText().toString());
        person.setName(name.getText().toString());


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Person");
        myRef.child(email.getText().toString()).setValue(person);

    }
    private void Switch(){
        startActivity(new Intent(AddPerson.this, AddSubject.class));

    }

    @Override
    public void onClick(View v) {
        if(v==save){
            onSaveClicked();
            Toast.makeText(AddPerson.this, "Saved",Toast.LENGTH_SHORT).show();
        }
        if(v==btnsubject){
            Switch();
        }
    }
}
