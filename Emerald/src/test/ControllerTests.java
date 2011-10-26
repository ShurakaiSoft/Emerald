package test;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import core.Controller;

public class ControllerTests {
	String[] letterSets = { "ehllo", "abc", "adfs", "eqrtwy", "iopu" };
	Controller controller;	
	
	@Before
	public void setUpController() {
		controller = new Controller("dictionary.txt");
	}

	
	/**
	 * Test that a string with no vowels return an array of 0 elements.
	 */
	@Test
	public void testNoVowelFails() {
		String[] answer = controller.getAnswer("bcd");
		assertTrue(answer.length == 0);
	}

	
	/**
	 * Test that a valid string returns an answer.
	 */
	@Test(timeout=5000)
	public void testSingleAnswer() {
		String[] answer = getAnswerHelper("abc");
		assertTrue(Arrays.binarySearch(answer, "cab") >= 0);
	}

	
	/**
	 * Test that we can repeatedly get an answer from the controller.
	 */
	@Test(timeout=25000)
	public void testMultipleAnswers() {
		for (String letterSet : letterSets) {
			String[] answer = getAnswerHelper(letterSet);
			assertTrue(answer.length > 0);
		}
	}
	
	
	/**
	 * Helper routine to continually loop until we get an answer
	 * 
	 * @param letterSet set of letters
	 * @return set of words
	 */
	public String[] getAnswerHelper(String letterSet) {
		String[] wordSet = null;
		while ((wordSet = controller.getAnswer(letterSet)) == null) {
			// loop until we get an answer
		}
		return wordSet;
	}
	
}
