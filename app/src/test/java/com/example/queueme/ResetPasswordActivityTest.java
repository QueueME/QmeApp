package com.example.queueme;




import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import static junit.framework.Assert.assertTrue;

import static org.junit.Assert.*;


/**
 * Created by magnusknaedal on 03.04.2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class ResetPasswordActivityTest {



    @Before
    public void setUp() throws Exception {


    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void onCreate() throws Exception {

    }

    @Test
    public void clickingResetPassword_shouldResetPassword(){
        ResetPasswordActivity mMainActivity = Robolectric.setupActivity(ResetPasswordActivity.class);
        mMainActivity.findViewById(R.id.btn_back).performClick();

        Intent expectedIntent = new Intent(mMainActivity, LoginActivity.class);

        EditText email = (EditText) mMainActivity.findViewById(R.id.email);
        email.setText("joakimjohansenerkul@stud.ntnu.no");

        ShadowActivity shadowActivity = Shadows.shadowOf(mMainActivity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();

        assertTrue(actualIntent.filterEquals(expectedIntent));
    }

    @Test
    public void checkingEmail() {
        ResetPasswordActivity mMainActivity = Robolectric.setupActivity(ResetPasswordActivity.class);

        EditText email = (EditText) mMainActivity.findViewById(R.id.email);
        email.setText("joakimjohansenerkul@stud.ntnu.no");

        assertEquals(email.getText().toString(), "joakimjohansenerkul@stud.ntnu.no");


    }


    @Test
    public void clickingBack_shouldStartLoginActivity(){
        ResetPasswordActivity mMainActivity = Robolectric.setupActivity(ResetPasswordActivity.class);
        mMainActivity.findViewById(R.id.btn_back).performClick();
        assertTrue(mMainActivity.isFinishing());
    }


}