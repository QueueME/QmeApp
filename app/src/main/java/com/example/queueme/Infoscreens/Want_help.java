package com.example.queueme.Infoscreens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.queueme.ChooseSubjectAss;
import com.example.queueme.R;


public class Want_help extends AppCompatActivity {

    private com.example.queueme.Infoscreens.PrefManager prefManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.want_help);

        /*prefManager = new PrefManager(this);
        if (!prefManager.isFirstTimeLaunch()) {
            launchChooseSubjectAss();
            finish();
        }
        */
    }
    private void launchChooseSubjectAss() {
        prefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(Want_help.this, ChooseSubjectAss.class));
        finish();
    }
}
