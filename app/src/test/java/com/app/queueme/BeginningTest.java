package com.app.queueme;
import android.content.Intent;

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
 * Created by anders on 03.04.2017.
 */


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class BeginningTest {
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
    public void clickingLogin_shouldStartLoginActivity() {
        Beginning mMainActivity = Robolectric.setupActivity(Beginning.class);
        mMainActivity.findViewById(R.id.login).performClick();

        Intent expectedIntent = new Intent(mMainActivity, LoginActivity.class);

        ShadowActivity shadowActivity = Shadows.shadowOf(mMainActivity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();

        assertTrue(actualIntent.filterEquals(expectedIntent));
    }
    @Test
    public void clickingreg_shouldStartLoginActivity() {
        Beginning mMainActivity = Robolectric.setupActivity(Beginning.class);
        mMainActivity.findViewById(R.id.reg).performClick();

        Intent expectedIntent = new Intent(mMainActivity, SignupActivity.class);

        ShadowActivity shadowActivity = Shadows.shadowOf(mMainActivity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();

        assertTrue(actualIntent.filterEquals(expectedIntent));
    }


}