package com.app.queueme;

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
 * Created by Marius on 04.04.2017.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)


public class AddPersonTest {


    @Test
    public void addPersonTest3(){

        AddPerson mMainActivity = Robolectric.setupActivity(AddPerson.class);
        mMainActivity.findViewById(R.id.btnsubject).performClick();

        Intent expectedIntent = new Intent(mMainActivity, AddSubject.class);

        ShadowActivity shadowActivity = Shadows.shadowOf(mMainActivity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();

        assertTrue(actualIntent.filterEquals(expectedIntent));

    }
    @Test
    public void addPersonTest4(){

        AddPerson mMainActivity = Robolectric.setupActivity(AddPerson.class);
        EditText Name = (EditText) mMainActivity.findViewById(R.id.name);
        EditText Email = (EditText) mMainActivity.findViewById(R.id.email);
        Email.setText("perstud");
        Name.setText("Per");
        mMainActivity.findViewById(R.id.save).performClick();

        ShadowHandler.idleMainLooper();
        assertThat(ShadowToast.getTextOfLatestToast()).isEqualTo("Save");

    }


    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void addPersonTest(){

        AddPerson mMainActivity = Robolectric.setupActivity(AddPerson.class);
        EditText Name = (EditText) mMainActivity.findViewById(R.id.name);
        EditText Email = (EditText) mMainActivity.findViewById(R.id.email);
        Email.setText("perstud");
        Name.setText("Per");
        mMainActivity.findViewById(R.id.save).performClick();
        assertThat(Name.getText().toString()).isEqualTo("Per");
        assertThat(Email.getText().toString()).isEqualTo("perstud");

    }
    @Test
    public void addPersonTest2(){

        AddPerson mMainActivity = Robolectric.setupActivity(AddPerson.class);
        EditText Name = (EditText) mMainActivity.findViewById(R.id.name);
        EditText Email = (EditText) mMainActivity.findViewById(R.id.email);
        Email.setText("perstud");
        Name.setText("Per");
        assertThat(Name.getText().toString()).isEqualTo("Per");
        assertThat(Email.getText().toString()).isEqualTo("perstud");

    }


    @Test
    public void onCreate() throws Exception {

    }

    @Test
    public void onSaveClicked() throws Exception {

    }

    @Test
    public void onClick() throws Exception {

    }

}