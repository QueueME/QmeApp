package com.example.queueme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.queueme.Infoscreens.WelcomeActivityStudass;

public class StudOrAss extends AppCompatActivity implements View.OnClickListener{

    private Button btnass;
    private Button btnstud;
    private Button meny;
    private Button home;
    private Intent intent;



    //overrider tilbakeknappen på androiden
    @Override
    public void onBackPressed()
    {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alternative_home);
        //definerer knapper
        btnass=(Button) findViewById(R.id.btnass);
        btnstud=(Button) findViewById(R.id.btnstud);
        //swipe=(Button) findViewById(R.id.swipe);

        btnass.setOnClickListener(this);
        btnstud.setOnClickListener(this);
        //swipe.setOnClickListener(this);

        meny = (Button) findViewById(R.id.meny);
        meny.setOnClickListener(this);
        home = (Button) findViewById(R.id.home);
        home.setOnClickListener(this);
        intent=getIntent();
    }

    @Override
    public void onClick(View v) {
        //velger studass
        if (v==btnass){
            btnass();
        }
        //velger student
        if (v==btnstud){
            btnstud();
        }


        if(v==meny){
            meny();

        }

    }

    public void meny(){

        this.intent = new Intent(StudOrAss.this, MenyActivity.class);

        startActivity(intent);
    }

    public void btnstud(){
        startActivity(new Intent(StudOrAss.this, com.example.queueme.Infoscreens.WelcomeActivityStudent.class));
    }
    public void btnass(){
            startActivity(new Intent(StudOrAss.this, WelcomeActivityStudass.class));
    }


}
