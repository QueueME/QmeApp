package com.example.queueme;


import android.app.Activity;
import android.content.Intent;
import android.widget.EditText;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowHandler;
import org.robolectric.shadows.ShadowToast;

import static junit.framework.Assert.assertTrue;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertEquals;


/**
 * Created by magnusknaedal on 03.04.2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class ResetPasswordActivityTest {

    EditText email;


    @Before
    public void setUp() throws Exception {
        Activity activity = Robolectric.setupActivity(LoginActivity.class);
        email = (EditText) activity.findViewById(R.id.email);

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

        //EditText email = (EditText) mMainActivity.findViewById(R.id.email);
        //email.setText("joakimjohansenerkul@stud.ntnu.no");

        ShadowActivity shadowActivity = Shadows.shadowOf(mMainActivity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();

        assertTrue(actualIntent.filterEquals(expectedIntent));
    }

    @Test
    public void onCreate1() throws Exception {
        email.setText("hei");
        assertThat(email.getText().toString()).isEqualTo("hei");

    }
    @Test
    public void checkingEmail() {
        ResetPasswordActivity mMainActivity = Robolectric.setupActivity(ResetPasswordActivity.class);

        email.setText("joakimjohansenerkul@stud.ntnu.no");

        assertEquals(email.getText().toString(), "joakimjohansenerkul@stud.ntnu.no");


    }
    @Test
    public void assertValidationFailureWithNullInput() {
        ResetPasswordActivity mMainActivity = Robolectric.setupActivity(ResetPasswordActivity.class);

        email.setText(null);
        mMainActivity.findViewById(R.id.btn_reset_password).performClick();

        ShadowHandler.idleMainLooper();
        assertThat(ShadowToast.getTextOfLatestToast()).isEqualTo("Enter your registered email id");

    }


    @Test
    public void clickingBack_shouldStartLoginActivity(){
        ResetPasswordActivity mMainActivity = Robolectric.setupActivity(ResetPasswordActivity.class);
        mMainActivity.findViewById(R.id.btn_back).performClick();
        assertTrue(mMainActivity.isFinishing());
    }
    @Test
    public void setText_and_click_back(){
        ResetPasswordActivity mMainActivity = Robolectric.setupActivity(ResetPasswordActivity.class);
        email.setText("hei");
        mMainActivity.findViewById(R.id.btn_back).performClick();
        assertTrue(mMainActivity.isFinishing());
    }
    @Test
    public void sdfassertValidationFailureWithNullInput() {
        ResetPasswordActivity mMainActivity = Robolectric.setupActivity(ResetPasswordActivity.class);

        email.setText("jokkefitte@stud.ntnu.no");
        mMainActivity.findViewById(R.id.btn_reset_password).performClick();

        ShadowHandler.idleMainLooper();
        assertThat(ShadowToast.getTextOfLatestToast()).isEqualTo("id");

    }

}