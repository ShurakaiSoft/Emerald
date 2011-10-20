package test;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.BeforeClass;
import org.junit.Test;

import core.Dictionary;
import core.Solution;
import core.Unsolved;

public class UnsolvedTests {
	
	
	private static Dictionary dictionary;		// normal dictionary
	private static Dictionary abcDictionary;	// special test dictionary

	
	/**
	 * Initialize two separate dictionary objects, so different tests can use
	 * different dictionary files.
	 * 
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		try {
			dictionary = new Dictionary("dictionary.txt");
			abcDictionary = new Dictionary("abcDictionary.txt");
		} catch (FileNotFoundException e) {
			System.exit(-1);
		}
	}

	
	/**
	 * Test that getting a solution before it has been solved, fails. 
	 */
	@Test
	public void testGetSolutoinEarly() {
		Unsolved unsolved = new Unsolved("abc");
		Solution solution = unsolved.newSolution();
		assertTrue(solution == null	);
	}

	
	/**
	 * Uses a custom dictionary file that has all permutations of "abc", so we
	 * can test that all permutations are being created properly
	 *  
	 */
	@Test
	public void testAllMutations() {
		setDictionary(abcDictionary);
		Unsolved unsolved = new Unsolved("abc");
		unsolved.run();
		Solution solution = unsolved.newSolution();
		String[] answer = solution.getWordSet();
		assertTrue(answer.length == 15);
		assertTrue(answer[0].equals("a"));
		assertTrue(answer[4].equals("acb"));
		assertTrue(answer[5].equals("b"));
		assertTrue(answer[9].equals("bca"));
		assertTrue(answer[10].equals("c"));
		assertTrue(answer[14].equals("cba"));
	}

	
	/**
	 * Verify that two strings with the same characters but in different order,
	 * produce the same permutation list.
	 */
	@Test
	public void testMutationSetConsistancy() {
		setDictionary(dictionary);
		Unsolved abcUnsolved = new Unsolved("abc");
		Unsolved cbaUnsolved = new Unsolved("cba");
		abcUnsolved.run();
		cbaUnsolved.run();
		Solution abcSolution = abcUnsolved.newSolution();
		Solution cbaSolution = cbaUnsolved.newSolution();
		String[] abcAnswer = abcSolution.getWordSet();
		String[] cbaAnswer = cbaSolution.getWordSet();
		assertTrue(abcAnswer.length == cbaAnswer.length);
		for (int i = 0; i < abcAnswer.length; i++) {
			assertTrue(abcAnswer[i].equals(cbaAnswer[i]));
		}
	}
	
	
	/**
	 * convenience method to select which dictionary will be used for
	 * individual tests.
	 * 
	 * @param dictionary
	 */
	private void setDictionary(Dictionary dictionary) {
		Unsolved.setDictionary(dictionary);		
	}
}
