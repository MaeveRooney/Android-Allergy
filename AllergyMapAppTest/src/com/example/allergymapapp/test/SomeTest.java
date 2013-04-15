package com.example.allergymapapp.test;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class SomeTest {

	public SomeTest() {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
    public void testSomething() throws Throwable {
       Assert.assertTrue(1 + 1 == 2);
    }

	@Test
    public void testSomethingElse() throws Throwable {
       Assert.assertTrue(1 + 1 == 3);
    }

}
