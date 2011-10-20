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
	 * Test for some random words
	 */
	@Test
	public void testIsWordTrue() {
		assertTrue(dictionary.isWord("hello") == true);
		assertTrue(dictionary.isWord("fads") == true);
		assertTrue(dictionary.isWord("limes") == true);
	}
	
	/**
	 * Test failure for bogus words
	 */
	@Test
	public void testIsWordFalse() {
		assertTrue(dictionary.isWord("asdfad") == false);
		assertTrue(dictionary.isWord("oiuvblha") == false);
		assertTrue(dictionary.isWord("lnvuau") == false);
	}
		
	/**
	 * Test that the first and last words in the dictionary are found.
	 */
	@Test
	public void testIsWordBoundary() {
		assertTrue(dictionary.isWord("aa") == true);
		assertTrue(dictionary.isWord("zyzzyvas") == true);
	}
	
}
