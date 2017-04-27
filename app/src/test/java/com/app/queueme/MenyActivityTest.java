package com.app.queueme;

import com.google.firebase.auth.FirebaseUser;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

/**
 * Created by magnusknaedal on 03.04.2017.
 */
public class MenyActivityTest {

    MenyActivity activity;

    @Before
    public void setUp() throws Exception {
    activity = new MenyActivity();

        FirebaseUser user = null;
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void onCreate() throws Exception {
        assertTrue(activity != null);


    }

    @Test
    public void signOut() throws Exception {

    }

    @Test
    public void onResume() throws Exception {

    }

    @Test
    public void onStart() throws Exception {

    }

    @Test
    public void onStop() throws Exception {

    }

    /*@Test
    public void shouldStartLoginActivity() {
        MenyActivity activity = Robolectric.setupActivity(MenyActivity.class);
        //MenyActivity.findViewById(R.id.btnstud).performClick();
       // FirebaseUser user = new FirebaseUser();
        FirebaseAuth auth = FirebaseAuth.getInstance();



        Intent expectedIntent = new Intent(activity, LoginActivity.class);

        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();

        assertTrue(actualIntent.filterEquals(expectedIntent));
    }*/


}