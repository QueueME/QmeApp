package com.example.queueme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class StudOrAss extends AppCompatActivity implements View.OnClickListener{

    private Button btnass;
    private Button btnstud;
    private Button swipe;
    private ImageButton meny;
    private ImageButton home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        btnass=(Button) findViewById(R.id.btnass);
        btnstud=(Button) findViewById(R.id.btnstud);
        //swipe=(Button) findViewById(R.id.swipe);

        btnass.setOnClickListener(this);
        btnstud.setOnClickListener(this);
        //swipe.setOnClickListener(this);

        meny = (ImageButton) findViewById(R.id.meny);
        meny.setOnClickListener(this);
        home = (ImageButton) findViewById(R.id.homee);
        home.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        //velger studass
        if (v==btnass){
            startActivity(new Intent(StudOrAss.this, com.example.queueme.Infoscreens.WelcomeActivityStudass.class));
        }
        //velger student
        if (v==btnstud){
            startActivity(new Intent(StudOrAss.this, com.example.queueme.Infoscreens.WelcomeActivityStudent.class));
        }
        if (v==swipe){
            startActivity(new Intent(StudOrAss.this, com.example.queueme.Infoscreens.WelcomeActivityStudent.class));
        }
        if (v==home){

        }
        if(v==meny){
            startActivity(new Intent(StudOrAss.this, MenyActivity.class));

        }

    }
}
