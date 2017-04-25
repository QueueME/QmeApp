package com.app.queueme;

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
public class ChooseSubjectAssTest {
    @Before
    public void setUp() throws Exception {
        Intent i = new Intent();
        i.putExtra("emnenavn", "123");
        i.putExtra("emnekode", "123");
        Activity activity = Robolectric.buildActivity(ChooseSubjectAss.class).withIntent(i).create().get();

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void onCreate() throws Exception {

    }
    @Test
    public void checkoncreate() throws Exception {
        Intent i = new Intent();
        i.putExtra("emnenavn", "123");
        i.putExtra("emnekode", "123");
        Activity mactivity = Robolectric.buildActivity(ChooseSubjectAss.class).withIntent(i).create().get();
        ArrayList subject = Robolectric.buildActivity(ChooseSubjectAss.class).withIntent(i).create().get().getSubjectss();
        ArrayList<Subject>  person2=new ArrayList<>();
        assertThat(subject).isEqualTo(person2);


    }

}