package com.app.queueme;

import android.content.Intent;

import com.app.queueme.Infoscreens.WelcomeActivityStudass;

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
 * Created by Marius on 04.04.2017.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)

public class WelcomeActivityStudassTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }


    @Test
    public void welcomeActivityStudassTest(){

        WelcomeActivityStudass mMainActivity = Robolectric.setupActivity(WelcomeActivityStudass.class);
        mMainActivity.findViewById(R.id.btn_skip).performClick();
        mMainActivity.findViewById(R.id.btn_next).performClick();

        Intent expectedIntent = new Intent(mMainActivity, ChooseSubjectAss.class);

        ShadowActivity shadowActivity = Shadows.shadowOf(mMainActivity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();

        assertTrue(actualIntent.filterEquals(expectedIntent));

    }



    @Test
    public void onCreate() throws Exception {

    }

}