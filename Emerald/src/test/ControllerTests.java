package test;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import core.Controller;

public class ControllerTests {
	Controller controller;	
	
	@Before
	public void setUpController() {
		controller = new Controller("dictionary.txt");
	}

	
	/**
	 * Test that a string with no vowels return a null answer.
	 */
	@Test
	public void testGetAnswer() {
		String[] answer = controller.getAnswer("bcd");
		assertTrue(answer == null);
	}

	
	/**
	 * Test that a valid string returns an answer.
	 */
	@Test
	public void testValidAnswer() {
		String[] answer = controller.getAnswer("abc");
		assertTrue(answer != null);
		assertTrue(Arrays.binarySearch(answer, "cab") >= 0);
	}
	
}
