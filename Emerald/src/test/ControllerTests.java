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
	
	
	/**
	 * Verify hasVowels detects all vowels and no constants.
	 */
	@Test
	public void testHasVowels() {
		assertTrue(Controller.hasVowels("a") == true);
		assertTrue(Controller.hasVowels("e") == true);
		assertTrue(Controller.hasVowels("i") == true);
		assertTrue(Controller.hasVowels("o") == true);
		assertTrue(Controller.hasVowels("u") == true);
		assertTrue(Controller.hasVowels("y") == true);
		assertTrue(Controller.hasVowels("Y") == true);
		assertTrue(Controller.hasVowels("A") == true);
		assertTrue(Controller.hasVowels("E") == true);
		assertTrue(Controller.hasVowels("I") == true);
		assertTrue(Controller.hasVowels("O") == true);
		assertTrue(Controller.hasVowels("U") == true);
		assertTrue(Controller.hasVowels("Y") == true);
		assertTrue(Controller.hasVowels("bcdfghjklmnpqrstvwxz") == false);
		assertTrue(Controller.hasVowels("BCDFGHJKLMNPQRSTVWXZ") == false);
		assertTrue(Controller.hasVowels("") == false);
	}

}
