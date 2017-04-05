package com.example.queueme;

import android.widget.EditText;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * Created by Marius on 04.04.2017.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)


public class AddPersonTest {

/*
    @Test
    public void addPersonTest(){

        AddPerson mMainActivity = Robolectric.setupActivity(AddPerson.class);
        mMainActivity.findViewById(R.id.save).performClick();

        Intent expectedIntent = new Intent(mMainActivity, AddSubject.class);

        ShadowActivity shadowActivity = Shadows.shadowOf(mMainActivity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();

        assertTrue(actualIntent.filterEquals(expectedIntent));

    }*/


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
    public void onCreate() throws Exception {

    }

    @Test
    public void onSaveClicked() throws Exception {

    }

    @Test
    public void onClick() throws Exception {

    }

}