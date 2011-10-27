package core;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Implements a Generic, synchronized, in-memory cache. The maximum size of this
 * cache is set by the constructor.<p>
 * 
 * Synchronization allows only one thread to access the cache at a time. There
 * is no distinction between readers and writers.
 * 
 * @author Steve
 *
 */
public final class Cache<K, V> {
	private LinkedHashMap<K, V> cachedData;
	private final int capacity;

	
	/**
	 * Construct an empty cache.
	 */
	public Cache(int cacheSize) {
		this.capacity = cacheSize;
		cachedData = new LinkedHashMap<K, V>(capacity) {
			private static final long serialVersionUID = 100L;
			@Override
			protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
		        return size() > capacity;
		     }
		}; 
	}
	
	
	/**
	 * Add a new solution to the cache.
	 * 
	 * @param solution 
	 */
	public synchronized void store(K key, V value) {
		cachedData.put(key, value);
	}

	
	/**
	 * return the solution.
	 * 
	 * @return An array of words
	 */
	public synchronized V retreive(K letterSet) {
		return cachedData.get(letterSet);
	}
	
}
