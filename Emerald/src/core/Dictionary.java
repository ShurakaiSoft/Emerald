package core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;


/**
 * This class represents a dictionary of words as a an iterable, searchable set
 * of Strings. The words are stored in two different data structures. One is as
 * a sorted list in ascending order, that can be iterated over. The second is
 * as a length hashed sorted list of words, optimized for searching.
 * 
 * @author Steve
 *
 */
public final class Dictionary implements Iterable<String>  {
	private class SortedDictionaryIterator implements Iterator<String> {
		int index = 0;
		
		@Override
		public boolean hasNext() {
			return (index < sortedDictionary.length) ? true : false;
		}

		@Override
		public String next() {
			return sortedDictionary[index++];
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}
	
	final static char[] VOWEL_LIST = { 'a', 'e', 'i', 'o', 'u', 'y' };

	
	private String[] sortedDictionary;
	private String[][] indexedDictionary;

	
	/**
	 * A constructor to create a searchable dictionary from the given file. The
	 * given file must contain one word per line and also be sorted in
	 * ascending order. 
	 * 
	 * @param filename dictionary file.
	 * @throws FileNotFoundException if the file can't be found.
	 */
	public Dictionary(String filename) throws FileNotFoundException {
		sortedDictionary = newDictionary(filename);
		indexedDictionary = newIndexDictionary(sortedDictionary);
	}

	
	/**
	 * Uses a binary search to verify if the given word exists in the
	 * dictionary.
	 * 
	 * @param word to search for
	 * @return true, if it exists. false otherwise.
	 */
	public boolean isWord(String word) {
		int index = word.length() - 1;
		if (index >= indexedDictionary.length || indexedDictionary[index] == null) {
			return false;
		}
		return (Arrays.binarySearch(indexedDictionary[word.length() - 1], word) >= 0) ? true : false;
	}

	
	@Override
	public Iterator<String> iterator() {
		return new SortedDictionaryIterator();
	}

	
	/**
	 * Read in a file representing a dictionary of words, one per line and
	 * sorted in ascending order. Words are added to an array of strings which
	 * is returned on completion.
	 * 
	 * @param filename the ordered file of words
	 * @return a dictionary of words as an array of strings.
	 * @throws FileNotFoundException if the file can't be found in the
	 * underlying file system.
	 */
	private String[] newDictionary(String filename) throws FileNotFoundException {
		TreeSet<String> wordArray = new TreeSet<String>();
		
		Scanner fileIn = new Scanner(new BufferedReader(new FileReader(filename)));
		
		while (fileIn.hasNext() == true) {
			wordArray.add(fileIn.next());
		}
		if (fileIn != null) {
			fileIn.close();
		}
		
		return wordArray.toArray(new String[wordArray.size()]);
	}
	
	
	/**
	 * Creates a hashed, then sorted list of words from the given input. Words
	 * are hashed on their length and then sorted in ascending order.
	 * 
	 * @param wordArray a list of unique sorted words
	 * @return a hashed, then sorted list of words
	 */	
	private String[][] newIndexDictionary(String[] wordArray) {
		TreeMap<Integer, ArrayList<String>> dictionary = new TreeMap<Integer, ArrayList<String>>();
		ArrayList<String> value;
		int key;

		// sort words by length, then alphabetically
		for (String word : wordArray) {
			key = word.length();
			value = dictionary.get(key);
			if (value == null) {
				value = new ArrayList<String>(128);
				dictionary.put(new Integer(key), value);
			}
			value.add(word);
		}
		
		// convert to array
		String[][] indexedDictionary = new String[dictionary.lastKey()][];
		for (Map.Entry<Integer, ArrayList<String>> node : dictionary.entrySet()) {
			int index = node.getKey().intValue() - 1;
			ArrayList<String> words = node.getValue();
			indexedDictionary[index] = words.toArray(new String[words.size()]);
		}
		return indexedDictionary; 
	}

	
	/**
	 * Search string for vowels. If a vowel is present it will return true.
	 * NOTE: vowels include 'a', 'e', 'i', 'o', 'u' AND 'y'. and can be upper
	 * case or lower case.
	 * 
	 * @param letterSet to be searched
	 * @return true if a vowel is present.
	 */
	public static boolean hasVowels(String letterSet) {
		char[] data = letterSet.toLowerCase().toCharArray();
		Arrays.sort(data);

		for (char ch : VOWEL_LIST) {
			if (Arrays.binarySearch(data, ch) >= 0) {
				return true;
			}
		}
		return false;
	}
	
}
