package test;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import core.Dictionary;

public class DictionaryTests {
	private static Dictionary dictionary = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dictionary = new Dictionary("testDictionary.txt");
	}

	
	/**
	 * Test for some words that do exist in the dictionary.
	 */
	@Test
	public void testIsWordTrue() {
		assertTrue(dictionary.isWord("hello") == true);
		assertTrue(dictionary.isWord("fads") == true);
		assertTrue(dictionary.isWord("limes") == true);
	}
	
	
	/**
	 * Test a for word that is longer than the longest in the dictionary.
	 */
	@Test
	public void testWordTooLong() {
		assertTrue(dictionary.isWord("abcdefghi") == false);		
	}
	
	
	/**
	 * Test for word lengths with no words
	 */
	@Test
	public void testWordSetGap() {
		assertTrue(dictionary.isWord("a") == false);
		assertTrue(dictionary.isWord("abcdef") == false);
	}
	
		
	/**
	 * Test for a word that is not in a word set.
	 */
	@Test
	public void testIsWordFalse() {
		assertTrue(dictionary.isWord("sixsi") == false);
		assertTrue(dictionary.isWord("lo") == false);
	}
	
	
	/**
	 * Test that the first and last words in the dictionary are found.
	 */
	@Test
	public void testIsWordBoundary() {
		assertTrue(dictionary.isWord("aa") == true);
		assertTrue(dictionary.isWord("zyzzyvas") == true);
	}
	
	
	/**
	 * Verify hasVowels detects all vowels and no constants.
	 */
	@Test
	public void testHasVowels() {
		assertTrue(Dictionary.hasVowels("a") == true);
		assertTrue(Dictionary.hasVowels("e") == true);
		assertTrue(Dictionary.hasVowels("i") == true);
		assertTrue(Dictionary.hasVowels("o") == true);
		assertTrue(Dictionary.hasVowels("u") == true);
		assertTrue(Dictionary.hasVowels("y") == true);
		assertTrue(Dictionary.hasVowels("Y") == true);
		assertTrue(Dictionary.hasVowels("A") == true);
		assertTrue(Dictionary.hasVowels("E") == true);
		assertTrue(Dictionary.hasVowels("I") == true);
		assertTrue(Dictionary.hasVowels("O") == true);
		assertTrue(Dictionary.hasVowels("U") == true);
		assertTrue(Dictionary.hasVowels("Y") == true);
		assertTrue(Dictionary.hasVowels("bcdfghjklmnpqrstvwxz") == false);
		assertTrue(Dictionary.hasVowels("BCDFGHJKLMNPQRSTVWXZ") == false);
		assertTrue(Dictionary.hasVowels("") == false);
	}

}
