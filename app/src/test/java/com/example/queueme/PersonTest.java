package com.example.queueme;

/**
 * Created by Eier on 03.04.2017.
 */

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PersonTest {


    @Test
    public void testtoString() throws Exception {
        Person person = new Person();
        person.setName("per");
        person.setEmail("per");
        String s = "Name" + "per" + '\n' + "Email" + "per" + '\n';
        Assert.assertEquals(s,person.toString());
    }

    Person person;
    @Before
    public void setUp() throws Exception {
        person = new Person();
        person.setName("per");
        person.setEmail("per@hei.no");
        person.setUid("123");
        person.setTime_to_stop("12");
    }
    @Test
    public void getName() throws Exception {

        assertEquals("per", person.getName());
        assertNotEquals("jan", person.getName());

    }

    @Test
    public void setName() throws Exception {
        assertEquals("per", person.getName());
        person.setName("jan");
        assertEquals("jan", person.getName());
    }

    @After
    public void tearDown() throws Exception {
        person = null;
        assertEquals(null, person);
    }

    @Test
    public void person() throws Exception {
        assertNotNull(person);
        Person p = person;
        assertEquals(p, person);
    }


    @Test
    public void getUid() throws Exception {
        assertEquals("123", person.getUid());
    }

    @Test
    public void setUid() throws Exception {
        person.setUid("456");
        assertEquals("456", person.getUid());
    }

    @Test
    public void getEmail() throws Exception {
        assertEquals("per@hei.no", person.getEmail());

    }

    @Test
    public void setEmail() throws Exception {
        person.setEmail("hey");
        assertEquals("hey", person.getEmail());
    }



    @Test
    public void getPersons() throws Exception {
        assertNotEquals(null, person.getPersons());
        Person jan = new Person();
        person.getPersons().add(jan);

        assertEquals(1, person.getPersons().size());
        assertEquals(jan, person.getPersons().get(0));

    }


    @Test
    public void getTime_to_stop() throws Exception {
        assertEquals("12",person.getTime_to_stop());
    }

    @Test
    public void setTime_to_stop() throws Exception {
        person.setTime_to_stop("13");
        assertEquals("13",person.getTime_to_stop());
    }

}
