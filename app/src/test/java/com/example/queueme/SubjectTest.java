package com.example.queueme;

/**
 * Created by Eier on 03.04.2017.
 */

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Eier on 02.04.2017.
 */
public class SubjectTest {

    Subject sub;

    @Before
    public void setUp() throws Exception {
        sub = new Subject();
        sub.setEmnenavn("matte");
        sub.setEmnekode("123");
    }

    @After
    public void tearDown() throws Exception {
        sub = null;
    }

    @Test
    public void getEmnenavn() throws Exception {
        assertEquals("matte", sub.getEmnenavn());
    }

    @Test
    public void setEmnenavn() throws Exception {
        sub.setEmnenavn("norsk");
        assertEquals("norsk", sub.getEmnenavn());
    }

    @Test
    public void getEmnekode() throws Exception {
        assertEquals("123", sub.getEmnekode());
    }

    @Test
    public void setEmnekode() throws Exception {
        sub.setEmnekode("456");
        assertEquals("456", sub.getEmnekode());
    }

    @Test
    public void testtoString() throws Exception {
        String s = "123 matte";
        assertEquals(s, sub.toString());
    }
    @Test
    public void alt() throws Exception {
        Subject sub = new Subject();
        assertNotNull(sub);

    }
    @Test
    public void notnull() throws Exception {
        sub = null;
        assertEquals(null, sub);
    }



}
