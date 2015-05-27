package com.example.martin.timeregistration;

import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MyTestClass {
    private SimpleJavaClass myClass;

    @Before
    public void setUp() throws Exception {
        //super.setUp();
        myClass = new SimpleJavaClass();
    }

    @After
    public void tearDown() throws Exception {
        //super.tearDown();
        myClass = null;
    }

    @Test
    public void TestUpperText() {
        String stringToBeTested = "androidtest";

        String result = myClass.getUpperString(stringToBeTested);

        Assert.assertEquals("ANDROIDTEST", result);
    }
}
