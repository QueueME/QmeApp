package com.example.queueme;




import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;


/**
 * Created by magnusknaedal on 03.04.2017.
 */
public class ResetPasswordActivityTest {

    ResetPasswordActivity resetPasswordActivity;

    @Before
    public void setUp() throws Exception {
    resetPasswordActivity = new ResetPasswordActivity();

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void onCreate() throws Exception {
        assertTrue(resetPasswordActivity != null);
    }



}