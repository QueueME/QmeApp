package com.app.queueme;

import android.content.Intent;
import android.widget.Button;

import com.app.queueme.Infoscreens.WelcomeActivityStudass;
import com.app.queueme.Infoscreens.WelcomeActivityStudent;

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


/**
 * Created by Eier on 03.04.2017.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)

public class StudOrAssTest {

    StudOrAss studOrass;
    Intent intent2;
    Intent intent;

    private StudOrAss activity;

    private Button pressMeButton;

    @Before
    public void setUp() throws Exception {


        activity = Robolectric.buildActivity(StudOrAss.class).create().get();
        pressMeButton = (Button) activity.findViewById(R.id.meny);

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void onCreate() throws Exception {
        assertTrue(activity != null);
    }





        @Test
        public void clickingMeny_shouldStartLoginActivity() {
            StudOrAss mMainActivity = Robolectric.setupActivity(StudOrAss.class);
            mMainActivity.findViewById(R.id.meny).performClick();

            Intent expectedIntent = new Intent(mMainActivity, MenyActivity.class);

            ShadowActivity shadowActivity = Shadows.shadowOf(mMainActivity);
            Intent actualIntent = shadowActivity.getNextStartedActivity();

            assertTrue(actualIntent.filterEquals(expectedIntent));
        }

    @Test
    public void clickingBtnstud_shouldStartLoginActivity() {
        StudOrAss MainActivity = Robolectric.setupActivity(StudOrAss.class);
        MainActivity.findViewById(R.id.btnstud).performClick();

        Intent expectedIntent = new Intent(MainActivity, WelcomeActivityStudent.class);

        ShadowActivity shadowActivity = Shadows.shadowOf(MainActivity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();

        assertTrue(actualIntent.filterEquals(expectedIntent));
    }

    @Test
    public void clickingBtnass_shouldStartLoginActivity() {
        StudOrAss MainActivity = Robolectric.setupActivity(StudOrAss.class);
        MainActivity.findViewById(R.id.btnass).performClick();

        Intent expectedIntent = new Intent(MainActivity, WelcomeActivityStudass.class);

        ShadowActivity shadowActivity = Shadows.shadowOf(MainActivity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();

        assertTrue(actualIntent.filterEquals(expectedIntent));
    }









}
