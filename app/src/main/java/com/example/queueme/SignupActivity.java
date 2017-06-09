package com.example.queueme;

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
        //definerer checkboxer. Er en egen funksjon som man senere kan sjekke om er avkrysset eller ikke :)
        male = (CheckBox) findViewById(R.id.male);
        female = (CheckBox) findViewById(R.id.female);
        //pref manager er appens interne minne. Dvs ikke tilknyttet appen. Her kan vi lagre enkle verdier
        //slik at vi ikke trenger å hente den fra databasen hver gang
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
        //onclick for signing in-- aner ikke hvilken kanpp dette er hehe
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
                //henter ut variablene fra edittekstene
                final String name = inputName.getText().toString();
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                //validerer. Vi har definert nye validereinger så trenger IKKE SE PÅ DISSE
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

                //synligjør progressbar for å vise at den jobber
                    progressBar.setVisibility(View.VISIBLE);

                //create user- INNEBYG FIREBASE FUNKJSON
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
                                    //HVIS ALT ER OK
                                    //createPersonFromUser lager en person utifra edittekstene som legges i databasen
                                    //dette er fordi vi må ha kontroll over personene i databasen, ikke bare brukere
                                    //SPESIFISERER UID FOR Å VITE HVOR PERSONEN LAGRES SLIK AT VI KAN GÅ INN PÅ HAN IGJEN
                                    createPersonFromUser(name);
                                    //sender brukeren viere til hjemskjerm. er nå logget inn
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
        //trenger å finne den genererte UID. Henter da ut all info fra brukeren via standarisert funksjon:
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //får da all info

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
        //SPESIFISERER UID SLIK AT VI VET HVOR I DATABASEN PERSONEN LIGGE. denne kommer fra FIREBASE user
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

        //Standar måte å connecte til firebase database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //sier at jeg skal legge til noe under noden "Person"
        DatabaseReference myRef= database.getReference("Person");
        //lager jeg en ny node under "Person" som heter useruid (child(useruid) og setter inn personen.
        myRef.child(useruid).setValue(person);

    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}
