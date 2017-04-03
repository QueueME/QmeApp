package com.example.queueme;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Eier on 03.04.2017.
 */
public class ChoosePersonTest {

    @After
    public void tearDown() throws Exception {


    }

    ChoosePerson p;

    @Before
    public void setUp() throws Exception {
        p = new ChoosePerson();
        p.setEmnekode("balle");

    }

    @Test
    public void onCreate() throws Exception {
        assertTrue(p != null);
    }

    public  void testEmnekode() throws Exception{
        assertEquals("balle", p.getEmnekode());
    }

    public void testListView() throws Exception{

    }

}