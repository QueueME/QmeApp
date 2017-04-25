package com.app.queueme;

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


/**
 * Created by anders on 03.04.2017.
 */


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class LoginActivityTest  {
    LoginActivity s;
    EditText email;
    EditText password;

    @Before
    public void setUp() throws Exception {

        Activity activity = Robolectric.setupActivity(LoginActivity.class);
        email = (EditText) activity.findViewById(R.id.email);
        password = (EditText) activity.findViewById(R.id.password);



    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void onCreate() throws Exception {
        email.setText("hei");
        assertThat(email.getText().toString()).isEqualTo("hei");
        password.setText("hei");
        assertThat(password.getText().toString()).isEqualTo("hei");

    }

    @Test
    public void jkdasfhg() throws Exception {
        LoginActivity mMainActivity = Robolectric.setupActivity(LoginActivity.class);
        email = (EditText) mMainActivity.findViewById(R.id.email);
        password = (EditText) mMainActivity.findViewById(R.id.password);
        email.setText("pugjengen@stud.ntnu.no");
        password.setText("pugjengenpåtur");
        mMainActivity.findViewById(R.id.btn_login).performClick();
        assertThat(password.getText().toString()).isEqualTo("pugjengenpåtur");
        assertThat(email.getText().toString()).isEqualTo("pugjengen@stud.ntnu.no");

    }
    @Test
    public void assertValidationFailureWithNullInput() {
        LoginActivity mMainActivity = Robolectric.setupActivity(LoginActivity.class);

        email.setText(null);
        mMainActivity.findViewById(R.id.btn_login).performClick();

        ShadowHandler.idleMainLooper();
        assertThat(ShadowToast.getTextOfLatestToast()).isEqualTo("Enter email address!");

    }

    @Test
    public void assertValidationFailure() {
        LoginActivity mMainActivity = Robolectric.setupActivity(LoginActivity.class);

        email.setText("wefg@stud.ntnu.no");
        password.setText(null);
        mMainActivity.findViewById(R.id.btn_login).performClick();

        ShadowHandler.idleMainLooper();
        assertThat(ShadowToast.getTextOfLatestToast()).isEqualTo("Enter email address!");

    }
    @Test
    public void clickingLogin_shouldStartLoginActivity() {
        LoginActivity mMainActivity = Robolectric.setupActivity(LoginActivity.class);
        mMainActivity.findViewById(R.id.btn_signup).performClick();

        Intent expectedIntent = new Intent(mMainActivity, SignupActivity.class);

        ShadowActivity shadowActivity = Shadows.shadowOf(mMainActivity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();

        assertTrue(actualIntent.filterEquals(expectedIntent));
    }
    @Test
    public void clickingLoginn_shouldStartLoginActivity() {
        LoginActivity mMainActivity = Robolectric.setupActivity(LoginActivity.class);
        mMainActivity.findViewById(R.id.btn_reset_password).performClick();

        Intent expectedIntent = new Intent(mMainActivity, ResetPasswordActivity.class);

        ShadowActivity shadowActivity = Shadows.shadowOf(mMainActivity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();

        assertTrue(actualIntent.filterEquals(expectedIntent));
    }

    @Test
    public void onCreatenotnull() throws Exception {
        Activity activity = Robolectric.setupActivity(LoginActivity.class);

        assertTrue(activity != null);

    }
    @Test
    public void clicking_shouldStartLoginActivity() {

        LoginActivity mMainActivity = Robolectric.setupActivity(LoginActivity.class);
        email = (EditText) mMainActivity.findViewById(R.id.email);
        password = (EditText) mMainActivity.findViewById(R.id.password);
        email.setText("ppugjengen@stud.ntnu.no");
        password.setText("pugjengenpåtur");
        mMainActivity.findViewById(R.id.btn_login).performClick();

        Intent expectedIntent = new Intent(mMainActivity, StudOrAss.class);

        ShadowActivity shadowActivity = Shadows.shadowOf(mMainActivity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();

        assertTrue(actualIntent.filterEquals(expectedIntent));
    }

}