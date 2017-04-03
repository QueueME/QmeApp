package com.example.queueme;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Instrumentation.ActivityMonitor;
import android.content.Intent;

import com.example.queueme.Infoscreens.WelcomeActivityStudass;

import static org.junit.Assert.*;

/**
 * Created by Eier on 03.04.2017.
 */
public class StudOrAssTest {

    StudOrAss studOrass;
Intent intent2;

    Intent intent;
    @Before
    public void setUp() throws Exception {
        studOrass = new StudOrAss();
        Object s = com.example.queueme.Infoscreens.WelcomeActivityStudent.class;
        MenyActivity m = new MenyActivity();
       this.intent = new Intent(studOrass, (Class<?>) s);
        this.intent2 = new Intent(studOrass, m.getClass());

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void onCreate() throws Exception {
        assertTrue(studOrass != null);
    }

    @Test
    public void onClick() throws Exception {

    }


    @Test
    public void meny() throws Exception {
        assertEquals(intent2, studOrass.getintent());
    }

    @Test
    public void swipe() throws Exception {

    }

    @Test
    public void btnstud() throws Exception {

    }

    @Test
    public void btnass() throws Exception {

    }




}