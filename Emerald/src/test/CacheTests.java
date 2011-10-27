package test;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;
import core.Cache;
import core.Solution;

public class CacheTests {
	Cache<String, Solution> emptyCache;
	Cache<String, Solution> nonEmptyCache;
	
	@Before
	public void setUpNonEmptyCache() {
		emptyCache = new Cache<String, Solution>(20);
		nonEmptyCache = new Cache<String, Solution>(20);
		LinkedList<String> answer = new LinkedList<String>();
		answer.add("ab");
		answer.add("cab");
		Solution solution = new Solution("abc", answer);
		nonEmptyCache.store(solution.getLetterSet(), solution);
	}
	
	@Test
	public void testGetAnswer() {
		assertTrue(emptyCache.retreive("abc") == null);
		assertTrue(nonEmptyCache.retreive("def") == null);
		Solution answer = nonEmptyCache.retreive("abc");
		assertTrue(answer != null);
		assertTrue(answer.getWordSet().contains("ab") == true);
		assertTrue(answer.getWordSet().contains("cab") == true);
	}

}
