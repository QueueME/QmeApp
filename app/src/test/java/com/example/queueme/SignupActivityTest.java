package com.example.queueme;

import android.widget.EditText;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowHandler;
import org.robolectric.shadows.ShadowToast;

import static junit.framework.Assert.assertTrue;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertEquals;


/**
 * Created by Eier on 03.04.2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class SignupActivityTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void onCreate() {

    }
    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void clickingSignin_shouldStartLoginActivity(){
        SignupActivity mMainActivity = Robolectric.setupActivity(SignupActivity.class);
        mMainActivity.findViewById(R.id.sign_in_button).performClick();
        assertTrue(mMainActivity.isFinishing());
    }

    @Test
    public void clickingSignup_shouldStartStudOrAssActivity() {

        SignupActivity mMainActivity = Robolectric.setupActivity(SignupActivity.class);

        EditText inputEmail = (EditText)mMainActivity.findViewById(R.id.email);
        inputEmail.setText("joakimjohansenerkul@stud.ntnu.no");

        EditText inputPassword = (EditText)mMainActivity.findViewById(R.id.password);
        inputPassword.setText("Gløshaugen123");

        EditText inputName = (EditText)mMainActivity.findViewById(R.id.fullname);
        inputName.setText("Joakim Johansen");


        mMainActivity.findViewById(R.id.sign_up_button).performClick();

        assertEquals(inputEmail.getText().toString(), "joakimjohansenerkul@stud.ntnu.no");

        assertEquals(inputPassword.getText().toString(), "Gløshaugen123");

        assertEquals(inputName.getText().toString(), "Joakim Johansen");


        /*Intent expectedIntent = new Intent(mMainActivity, StudOrAss.class);

        ShadowActivity shadowActivity = Shadows.shadowOf(mMainActivity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();

        assertTrue(actualIntent.filterEquals(expectedIntent));*/
    }
    @Test
    public void sdfassertValidationFailureWithNullInput() {
        SignupActivity mMainActivity = Robolectric.setupActivity(SignupActivity.class);
        EditText inputEmail = (EditText)mMainActivity.findViewById(R.id.email);
        EditText inputpassword = (EditText)mMainActivity.findViewById(R.id.password);

        inputEmail.setText("jokkefitte@stud.ntnu.no");
        //inputpassword.setText("fds");
        mMainActivity.findViewById(R.id.sign_up_button).performClick();

        ShadowHandler.idleMainLooper();
        assertThat(ShadowToast.getTextOfLatestToast()).isEqualTo("Enter password!");

    }
    @Test
    public void ssdfassertValidationFailureWithNullInput() {
        SignupActivity mMainActivity = Robolectric.setupActivity(SignupActivity.class);
        EditText inputEmail = (EditText)mMainActivity.findViewById(R.id.email);
        EditText inputpassword = (EditText)mMainActivity.findViewById(R.id.password);

        inputEmail.setText("jokkefitte@stud.ntnu.no");
        inputpassword.setText("fds");
        mMainActivity.findViewById(R.id.sign_up_button).performClick();

        ShadowHandler.idleMainLooper();
        assertThat(ShadowToast.getTextOfLatestToast()).isEqualTo("Password too short, enter minimum 6 characters!");

    }



    @Test
    public void onResume() throws Exception {

    }

}