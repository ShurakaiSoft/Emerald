package test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import core.Controller;
import core.Solution;

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
		Solution answer = controller.getAnswer("bcd");
		assertTrue(answer.getWordSetSize() == 0);
	}

	
	/**
	 * Test that a valid string returns an answer.
	 */
	@Test(timeout=5000)
	public void testSingleAnswer() {
		List<String> answer = getAnswerHelper("abc");
		assertTrue(answer.contains("cab") == true);
	}

	
	/**
	 * Test that we can repeatedly get an answer from the controller.
	 */
	@Test(timeout=10000)
	public void testMultipleAnswers() {
		for (String letterSet : letterSets) {
			List<String> answer = getAnswerHelper(letterSet);
			assertTrue(answer.size() > 0);
		}
	}
	
	
	/**
	 * Helper routine to continually loop until we get an answer
	 * 
	 * @param letterSet set of letters
	 * @return set of words
	 */
	public List<String> getAnswerHelper(String letterSet) {
		Solution answer = null;
		while ((answer = controller.getAnswer(letterSet)) == null) {
			// loop until we get an answer
		}
		return answer.getWordSet();
	}
	
}
