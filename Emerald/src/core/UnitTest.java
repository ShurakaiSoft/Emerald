package core;

import static core.Application.addPermutation;
import static core.Application.isValid;
import static core.Application.function;
import static org.junit.Assert.assertTrue;
import static core.Application.getSub;

import java.util.LinkedList;
import java.util.Queue;

import org.junit.Before;
import org.junit.Test;

public class UnitTest {
	Queue<String> queue = null;
	
	@Before
	public void setUp() throws Exception {
		queue = new LinkedList<String>();
	}

	@Test
	public void testPermutations() {
		addPermutation(queue, "", "abc");
		assertTrue(queue.contains("abc") == true);
		assertTrue(queue.contains("acb") == true);
		assertTrue(queue.contains("bac") == true);
		assertTrue(queue.contains("bca") == true);
		assertTrue(queue.contains("cab") == true);
		assertTrue(queue.contains("cba") == true);
	}

	@Test
	public void testIsValidValid() {
		String result;
		result = isValid("az");
		assertTrue(result != null && result.equals("az") == true);
		result = isValid("AZ");
		assertTrue(result != null && result.equals("az") == true);
		result = isValid("adasfasdfz");
		assertTrue(result != null && result.equals("aaaddffssz") == true);
		result = isValid("za");
		assertTrue(result != null && result.equals("az") == true);
	}
	
	@Test
	public void testIsValidNotValid() {
		assertTrue(isValid("1") == null);
		assertTrue(isValid("-") == null);
		assertTrue(isValid("\\q") == null);
	}
	
	@Test
	public void testSubStr() {
		assertTrue(getSub("ab", 0, 0).equals("a"));
	}
	
	@Test
	public void testFunction() {
		String[] array = null;
		array = function("ab");
		assertTrue(array.length == 3);
		assertTrue(array[0].equals("a") == true);
		assertTrue(array[1].equals("ab") == true);
		assertTrue(array[2].equals("b") == true);
		
		array = function("abc");
		assertTrue(array.length == 6);
		assertTrue(array[0].equals("a") == true);
		assertTrue(array[5].equals("c") == true);
	}
	
}
