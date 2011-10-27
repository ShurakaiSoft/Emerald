package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import core.Cache;
import core.Solution;
import core.Solver;
import java.io.FileNotFoundException;

public class SolverTests {
	// private String[] letterSets = { "adfs", "eqrtwy", "iopu", "abc", "abcd" };
	private Solver solver;

	
	/**
	 * Initialize a new solver for each test.
	 * 
	 * @throws FileNotFoundException
	 */
	@Before
	public void setUp() throws FileNotFoundException {
		solver = new Solver(new Cache<String, Solution>(20), "dictionary.txt");
	}
	
	
	/**
	 * Verify an invalid filename throws an exception.
	 * 
	 * @throws FileNotFoundException
	 */
	@Test(expected=FileNotFoundException.class)
	public void testConstructor() throws FileNotFoundException {
		solver = new Solver(new Cache<String, Solution>(20), "");
	}
	
	
	/**
	 * Test requestWordSet returns false when the queue fills up. Currently the
	 * queue is only one element big.
	 * 
	 */
	@Test
	public void testRequestWordSetFull() {
		assertTrue(solver.requestSolution("asdf") == true);
		solver.requestSolution("abcd"); // this could be true or false depending on threading
		assertTrue(solver.requestSolution("qwerty") == false);
	}
	
}
