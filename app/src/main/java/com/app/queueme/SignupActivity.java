package com.app.queueme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword,inputName;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private CheckBox male;
    private CheckBox female;
    private SharedPreferences prefs;

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(SignupActivity.this, Beginning.class));
        finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        FirebaseApp.initializeApp(this);
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        male = (CheckBox) findViewById(R.id.male);
        female = (CheckBox) findViewById(R.id.female);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        inputName = (EditText) findViewById(R.id.fullname);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);


        //onclikk for reseting password
        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, ResetPasswordActivity.class));
            }
        });
        //onclick for signing in
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //onclick for registrering to the app
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = inputName.getText().toString();
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();


                int blank = name.indexOf(' ');

                if (blank == -1){
                    Toast.makeText(getApplicationContext(), "Your fullname must contain at least two words", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!email.toLowerCase().contains("stud.ntnu.no") ){
                    Toast.makeText(getApplicationContext(), "Make sure your email contains 'stud.ntnu.no', try again!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ((male.isChecked()&&female.isChecked())|| (!male.isChecked()&&!female.isChecked())) {
                    Toast.makeText(getApplicationContext(), "You have to choose ONE gender!", Toast.LENGTH_SHORT).show();
                    return;
                }


                    progressBar.setVisibility(View.VISIBLE);

                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(SignupActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignupActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    createPersonFromUser(name);
                                    startActivity(new Intent(SignupActivity.this, StudOrAss.class));
                                    finish();
                                }
                            }
                        });

            }


        });
    }
    //creates a person object in the databse connected to the users UID
    private void createPersonFromUser(String fullname){
        String useruid="";
        String useremail="";

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            // Name, email address, and profile photo Url
            String username = user.getDisplayName();
            useremail = user.getEmail();
            useruid = user.getUid();
        }
        //spesify person attributes
        Person person =new Person();
        person.setName(fullname);
        person.setEmail(useremail);
        person.setUid(useruid);
        person.setTime_to_stop("0");

        if (male.isChecked()){
            prefs.edit().putBoolean("gender", true).apply();
            person.setMale(true);
        }
        if (female.isChecked()){
            prefs.edit().putBoolean("gender", false).apply();
            person.setMale(false);
        }

        //setting value
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef= database.getReference("Person");
        myRef.child(useruid).setValue(person);

    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}
