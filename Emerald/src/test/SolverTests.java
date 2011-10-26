package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import core.Cache;
import core.Solution;
import core.Solver;
import java.io.FileNotFoundException;

public class SolverTests {
	private String[] letterSets = { "adfs", "eqrtwy", "iopu", "abc", "abcd" };
	private Solver solver;
	Thread solverThread;

	
	/**
	 * Initialize a new solver for each test.
	 * 
	 * @throws FileNotFoundException
	 */
	@Before
	public void setUp() throws FileNotFoundException {
		solver = new Solver(new Cache(), "dictionary.txt");
		solverThread = new Thread(solver);
	}
	
	
	/**
	 * Verify an invalid filename throws an exception.
	 * 
	 * @throws FileNotFoundException
	 */
	@Test(expected=FileNotFoundException.class)
	public void testConstructor() throws FileNotFoundException {
		solver = new Solver(new Cache(), "");
	}
	
	
	/**
	 * Test requestWordSet returns false when the queue fills up. Currently the
	 * queue is only one element big.
	 * 
	 */
	@Test
	public void testRequestWordSetFull() {
		assertTrue(solver.requestWordSet("asdf") == true);
		assertTrue(solver.requestWordSet("qwerty") == false);
	}
	
	
	/**
	 * Test we can get a solution.
	 */
	@Test(timeout=5000)
	public void testGetWordSet() {
		solverThread.start();
		assertTrue(solver.requestWordSet("asdf") == true);
		Solution solution = null;
		while (true) {
			solution = solver.getSolution();
			if (solution != null) {
				break;
			}
		}
		assertTrue(solution.getLetterSet().equals("adfs"));	// solutions letterSet is sorted.		
	}
	
	
	/**
	 * Test we can get several solutions.
	 */
	@Test(timeout=25000)
	public void testSeveralSolutions() {
		solverThread.start();
		Solution solution;
		for (String letterSet : letterSets) {
			while (solver.requestWordSet(letterSet) == true) {
				// keep trying for requestWordSet to return true
			}
			while ((solution = solver.getSolution()) == null) {
				// keep trying for a solution
			}
			assertTrue(solution.getLetterSet().equals(letterSet));
		}
	}
	
	
	/**
	 * Add two problems and then test we can get the first before the second is
	 * overwritten. 
	 */
	@Test(timeout=10000)
	public void testSolutionNeverLost() {
		solverThread.start();
		assertTrue(solver.requestWordSet(letterSets[0]) == true);
		while (solver.requestWordSet(letterSets[1]) == false) {
			// keep trying for requestWordSet to return true
		}
		Solution solution;
		while ((solution = solver.getSolution()) == null) {
			// keep trying for a solution
		}
		assertTrue(solution.getLetterSet().equals(letterSets[0]));
		while ((solution = solver.getSolution()) == null) {
			// keep trying for a solution
		}
		assertTrue(solution.getLetterSet().equals(letterSets[1]));
	}
	
}
