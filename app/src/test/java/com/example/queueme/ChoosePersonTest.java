package com.example.queueme;

import android.app.Activity;
import android.content.Intent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

import static org.assertj.core.api.Java6Assertions.assertThat;


/**
 * Created by anders on 04.04.2017.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class ChoosePersonTest {


    @After
    public void tearDown() throws Exception {

    }
    Activity activity;
    @Before
    public void setUp() throws Exception {
        Intent i = new Intent();
        i.putExtra("emnenavn", "123");
        i.putExtra("emnekode", "123");
        Activity activity = Robolectric.buildActivity(ChoosePerson.class).withIntent(i).create().get();

    }
    @Test
    public void checkoncreate() throws Exception {
        Intent i = new Intent();
        i.putExtra("emnenavn", "123");
        i.putExtra("emnekode", "123");
        Activity mactivity = Robolectric.buildActivity(ChoosePerson.class).withIntent(i).create().get();
        ArrayList person = Robolectric.buildActivity(ChoosePerson.class).withIntent(i).create().get().getPersons();
       ArrayList<Person>  person2=new ArrayList<>();
        assertThat(person).isEqualTo(person2);


    }
    @Test
    public void check() throws Exception {
        Intent i = new Intent();
        i.putExtra("emnenavn", "123");
        i.putExtra("emnekode", "123");
        ArrayList person = Robolectric.buildActivity(ChoosePerson.class).withIntent(i).create().get().getPersons();
        ArrayList<Person>  person2=new ArrayList<>();
        assertThat(person).isEqualTo(person2);


    }

}