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

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * Created by Marius on 04.04.2017.
 */


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)

public class AddSubjectTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }


    @Test
    public void addSubjectTest(){

        AddSubject mMainActivity = Robolectric.setupActivity(AddSubject.class);
        EditText Emnenavn = (EditText) mMainActivity.findViewById(R.id.enavn);
        EditText Emnekode = (EditText) mMainActivity.findViewById(R.id.ekode);
        Emnenavn.setText("Programvareutvikling");
        Emnekode.setText("TDT4140");
        mMainActivity.findViewById(R.id.btnesave).performClick();
        assertThat(Emnenavn.getText().toString()).isEqualTo("Programvareutvikling");
        assertThat(Emnekode.getText().toString()).isEqualTo("TDT4140");

    }

    @Test
    public void addPersonTest4(){

        AddSubject mMainActivity = Robolectric.setupActivity(AddSubject.class);
        EditText Name = (EditText) mMainActivity.findViewById(R.id.enavn);
        EditText Email = (EditText) mMainActivity.findViewById(R.id.ekode);
        Email.setText("perstud");
        Name.setText("Per");
        mMainActivity.findViewById(R.id.btnesave).performClick();

        ShadowHandler.idleMainLooper();
        assertThat(ShadowToast.getTextOfLatestToast()).isEqualTo("Saved");

    }







    @Test
    public void onCreate() throws Exception {

    }

    @Test
    public void onSaveClicked2() throws Exception {

    }

    @Test
    public void onClick() throws Exception {

    }

}