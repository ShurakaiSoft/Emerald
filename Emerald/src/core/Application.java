package core;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.TreeSet;

public class Application {
	Dictionary dictionary = null;
	
	public Application(String dictionaryFilename) throws FileNotFoundException {
		dictionary = new Dictionary(dictionaryFilename);
	}
	
	public void showWordList(String[] wordList) {
		if (wordList.length == 0) {
			System.out.println("No valid words found");
		} else {
			for (String word : wordList) {
				System.out.println("found: " + word);
			}
		}
	}
	
	/**
	 * Return a unique alphabetically ordered list of all permutations of
	 * the letters passed in.
	 * 
	 * @param letters
	 * @return
	 */
	public static String[] function(String letters) {
		TreeSet<String> stringSet = new TreeSet<String>();

		for (int i = 0; i < letters.length(); i++) {
			for (int j = i; j < letters.length(); j++) {
				stringSet.add(letters.substring(0,i) + letters.charAt(j));
			}
		}
		return stringSet.toArray(new String[stringSet.size()]);
	}
	
	public static String getSub(String string, int head, int index) {
		return string.substring(0,head) + string.charAt(index);
	}

	
	public String[] getWordList(String letterSet) {
		LinkedList<String> wordList = new LinkedList<String>();
		Queue<String> queue = new LinkedList<String>();

		// add permutations to queue
		for (int i = 0; i < letterSet.length(); i++) {
			for (int j = i; j < letterSet.length(); j++) {
				addPermutation(queue, "", letterSet.substring(0, i) + letterSet.charAt(j)); 
			}
		}
		
		// add permutations
		String[] arrayList = function(letterSet);
		for (String string : arrayList) {
			addPermutation(queue, "", string);			
		}
		
		// show permutations
		System.out.println("validating " + queue.size() + " permutations");
		
		// verify all words in queue
		String word;
		while (queue.isEmpty() != true) {
			word = queue.remove();
			if (dictionary.isWord(word)) {
				wordList.add(word);
			}
		}
		
		// return a list of words verified against a dictionary.
		return  wordList.toArray(new String[wordList.size()]);
	}

	/**
	 * Valid letters are lowercase alpahbet characters only. Uppercase letters
	 * are converted to lowercase before testing. The returned string will
	 * contain only lower case characters or it will return null.
	 * 
	 * @param letters set of characters to test
	 * @return null if not valid, else a sorted string of lower case characters.
	 */
	public static String isValid(String letters) {
		char[] array = new char[letters.length()];

		for (int i = 0; i < letters.length(); i++) {
			array[i] = Character.toLowerCase(letters.charAt(i));
			if (array[i] < 'a' || array[i] > 'z') {
				return null;
			}
		}
		java.util.Arrays.sort(array);
		return new String(array);
	}
	
	public static void main(String[] args) {
		Application scrabbleWords = null;
		Scanner inputScanner = new Scanner(System.in);
		String input;
		String letters;
		
		try {
			scrabbleWords = new Application("dictionary.txt");
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: can't find dictionary file. " + e.getMessage());
			System.exit(-1);
		}
		
		while (true) {
			System.out.print("Enter letters: ");
			input = inputScanner.next();
			if (input.equals("\\Q")) {
				System.out.println("Terminated at users request.");
				System.exit(0);
			}
			letters = isValid(input);
			if (letters == null) {
				System.out.println("Invalid input. Only enter alphabetical characters.");
			} else {
				scrabbleWords.showWordList(scrabbleWords.getWordList(letters));
			}
		}
	}

	/**
	 * Recursively generates all permutations of a given string and adds them
	 * to a queue.
	 * 
	 * @param queue
	 * @param root
	 * @param chars
	 */
	public static void addPermutation(Queue<String> queue, String root, String chars) {
		if (chars.length() <= 1) {
			String word = root + chars;
			if (queue.contains(word) == false) {
				queue.add(root + chars);
			}
		} else {
			for (int i = 0; i < chars.length(); i++) {
				try {
					String newString = chars.substring(0, i) + chars.substring(i + 1);
					addPermutation(queue, root + chars.charAt(i), newString);
				} catch (Exception e) {
					System.out.println("Possible stact overflow " + e.getMessage());
				}
			}
		}
	}
	
}
