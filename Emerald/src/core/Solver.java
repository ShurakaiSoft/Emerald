package core;

import java.io.FileNotFoundException;

/**
 * The solver handles processing letter sets into word sets.<p>
 * 
 * The are two ways to do this. Use the static solve() method which blocks while
 * the solution is being calculated.<p> 
 * 
 * The second method uses the requestWordSet() and getSolution() pair along with
 * a thread to do the heavy lifting in the run method.<p> This uses a buffer to
 * store problems and solutions until the run method is ready to process them.
 * 
 * @author Steve
 *
 */
public final class Solver implements Runnable {
	private Dictionary dictionary;	
	private Cache cache;			

	private String unsolved;		// unsolved buffer
	private Solution solution;		// solved buffer
	

	/**
	 * Construct a Solver subsystem with a given dictionary filename.
	 * 
	 * @param dictionaryFilename name of the dictionary file.
	 * @throws FileNotFoundException if the system can't find the given file
	 */
	public Solver(Cache cache, String dictionaryFilename) throws FileNotFoundException {
		this.cache = cache;
		dictionary = new Dictionary(dictionaryFilename);
		Unsolved.setDictionary(dictionary);
		this.solution = null;
		this.unsolved = null;
	}
	
	
	/**
	 * Adds a set of letters to the unsolved problems queue. The method run()
	 * checks this queue and processes problems, adding them to the solved
	 * queue.
	 * 
	 * @param letterSet set of letters to solve
	 * @return false if queue is full, true otherwise.
	 * @see run
	 */
	public boolean requestWordSet(String letterSet) {
		synchronized (this) {
			if (unsolved != null) {
				return false;
			}
			unsolved = letterSet;
		}
		return true;
	}
		
	
	/**
	 * Return the head of the solutions queue or null if it is empty. 
	 * 
	 * @return a instance of a Solution object or null if the queue is empty.
	 */
	public Solution getSolution() {
		Solution temp = null;
		synchronized (this) { 
			temp = solution;
			solution = null;
		}
		return temp;
	}
	
	
	/**
	 * Solves a single solution given as an argument and returns the answer.
	 * This method is synchronous. It doesn't depend on run() to solve the
	 * problem.
	 * 
	 * @param unsolved the Unsolved problem
	 * @return the solution.
	 */
	public Solution solve(Unsolved unsolved) {
		unsolved.run();
		return unsolved.newSolution();
	}
	
	
	/**
	 * This is an infinite loop that checks the unsolved problems. If it is
	 * non-empty, it will solve the problem and add the solution to the
	 * solved problems queue, space permitting. It will also add the solution
	 * to the cache.
	 */
	@Override
	public void run() {
		while (true) {
			if (unsolved != null && solution == null) {
				Solution temp = solve(new Unsolved(unsolved));
				cache.addSolution(temp);
				synchronized (this) {
					solution = temp;
					unsolved = null;
				}
			} else {
				try {
					Thread.sleep(500); // XXX this should be a wait but I get an IllegalMonitorStateException
				} catch (InterruptedException e) {
					// wake up and check the queue for more problems.
				}
			}
		}
	}

}
