package com.example.queueme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class Beginning extends AppCompatActivity {
    private Button login;
    private Button reg;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);

        FirebaseApp.initializeApp(this);
        auth = FirebaseAuth.getInstance();
        //if user has allready logged in return to home
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(Beginning.this, StudOrAss.class));
            finish();
        }

        login=(Button) findViewById(R.id.login);
        reg=(Button) findViewById(R.id.reg);

        //sends you to loginactivity
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Beginning.this, LoginActivity.class));

            }
        });
        //sends you yo signupactivity
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Beginning.this, SignupActivity.class));

            }
        });

    }


}
