package hr.fer.zemris.java.student0036474052.hw12;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

/**
 * <code>Searcher</code> represents an object capable of providing similarity
 * statistics based on vector text analysis.
 * 
 * @author Filip Džidić
 *
 */
public class Searcher {
	/**
	 * the regex used for parsing documents, takes all unicode letter characters
	 */
	private final static String ALPHABET_REGEX = "\\P{L}";
	/** a map containing all the different words in our vocabulary */
	private Map<String, Integer> vocabulary;
	/** a set containing all the different words in a provided query */
	private Set<String> queryWords;
	/** a set of all the defined stop words in our database */
	private Set<String> stopWords;
	/** a list of all the documents associated by their paths on the file system */
	private ArrayList<Path> documents;
	/** tfidf vectors */
	private ArrayList<SearchVector> documentVectors;
	/** frequency vectors */
	private ArrayList<SearchVector> frequencyVectors;
	/** similarity output */
	private TreeMap<Double, Path> products;

	/**
	 * This method prints the calculated similarity result.
	 */
	public void printResults() {
		int i = 0;
		if (products.isEmpty()) {
			System.out.println("No query has been generated yet.");
			return;
		}
		for (Entry<Double, Path> entry : products.entrySet()) {
			System.out.format("[ %d] (%.4f) %s%n", i++, entry.getKey(), entry
					.getValue().toString());
			if (i > 9) {
				break;
			}
		}

	}

	/**
	 * This method finds the top 10 most similar documents.
	 */
	public void buildTop10() {
		SearchVector queryFreq = new SearchVector(vocabulary.size());
		SearchVector queryIDF = new SearchVector(vocabulary.size());
		for (String word : queryWords) {
			int index = vocabulary.get(word);
			queryFreq.set(index, queryFreq.get(index) + 1);
			double value = queryFreq.get(index)
					* Math.log(documents.size()
							/ (double) (countOccurence(word)));
			queryIDF.set(index, value);
		}
		products = new TreeMap<Double, Path>(Collections.reverseOrder());
		double norm = queryFreq.norm();
		for (int i = 0, size = documents.size(); i < size; i++) {
			SearchVector other = frequencyVectors.get(i);
			double value = other.scalarProduct(queryIDF)
					/ (norm * other.norm());
			if (value == 0) {
				continue;
			}
			products.put(Double.valueOf(value), documents.get(i));
		}

	}

	/**
	 * This method is used for parsing queries. All words not found in our
	 * vocabulary are removed.
	 * 
	 * @param string
	 *            <code>String</code> representation of a query
	 */
	public void buildQuery(String string) {
		String[] words = string.split(ALPHABET_REGEX);
		for (String word : words) {
			word = word.toLowerCase();
			if (vocabulary.containsKey(word)) {
				queryWords.add(word);
			}
		}
	}

	/**
	 * Getter method for queries.
	 * 
	 * @return our saved query
	 */
	public Set<String> getQuerySet() {
		return queryWords;
	}

	/**
	 * This general constructor builds our database and all the necessary
	 * similarity vectors.
	 * 
	 * @param rootDoc
	 *            the root folder containing all of our documents
	 * @throws IOException
	 *             if an IO error occurs
	 */
	public Searcher(Path rootDoc) throws IOException {
		if (!Files.isDirectory(rootDoc)) {
			throw new IllegalArgumentException();
		}
		fillStopWords();
		documents = new ArrayList<Path>();
		getDocuments(rootDoc.toFile());
		buildVocabulary();
		buildFrequencyVectors();
		buildDocumentVectors();
		queryWords = new HashSet<String>();
		products = new TreeMap<Double, Path>(Collections.reverseOrder());
	}

	/**
	 * This method builds tfidf vectors of all our documents.
	 */
	private void buildDocumentVectors() {
		documentVectors = new ArrayList<SearchVector>();
		for (int i = 0, size = frequencyVectors.size(); i < size; i++) {
			SearchVector docVector = new SearchVector(vocabulary.size());
			SearchVector freqVector = frequencyVectors.get(i);
			Set<String> words = vocabulary.keySet();
			for (String word : words) {
				int index = vocabulary.get(word);
				double value = freqVector.get(index)
						* Math.log((double) size / countOccurence(word));
				docVector.set(index, value);
			}
			documentVectors.add(docVector);

		}

	}

	/**
	 * Counts the occurrences of a word in all documents. Multiple occurrences
	 * in a single document are counted as one.
	 * 
	 * @param word
	 *            the word being counted
	 * @return the number of times a word occurs in all the documents
	 */
	private int countOccurence(String word) {
		int sum = 0;
		int index = vocabulary.get(word);
		for (int i = 0, size = frequencyVectors.size(); i < size; i++) {
			if (frequencyVectors.get(i).get(index) == 0.0) {
				continue;
			}
			sum++;

		}
		return sum;
	}

	/**
	 * Builds the frequency vectors of all of our documents.
	 * 
	 * @throws IOException
	 *             if an IO error occurs
	 */
	private void buildFrequencyVectors() throws IOException {
		frequencyVectors = new ArrayList<SearchVector>();
		for (int i = 0, size = documents.size(); i < size; i++) {
			String[] docWords = new String(
					Files.readAllBytes(documents.get(i)),
					StandardCharsets.UTF_8).split(ALPHABET_REGEX);
			SearchVector vector = new SearchVector(vocabulary.size());
			for (String word : docWords) {
				word = word.toLowerCase();
				if (stopWords.contains(word) || word.equals("")) {
					continue;
				}
				int index = vocabulary.get(word);
				vector.set(index, vector.get(index) + 1);
			}
			frequencyVectors.add(vector);
		}

	}

	/**
	 * This method returns the number of words in our vocabulary.
	 * 
	 * @return the number of words in our vocabulary
	 */
	public int wordCount() {
		return vocabulary.size();
	}

	/**
	 * This method builds our vocabulary by counting every different word found
	 * inside our documents.
	 * 
	 * @throws IOException
	 *             if an IO error occurs
	 */
	private void buildVocabulary() throws IOException {
		vocabulary = new HashMap<String, Integer>();
		int i = 0;
		for (Path path : documents) {
			String text = new String(Files.readAllBytes(path),
					StandardCharsets.UTF_8);
			String[] words = text.split(ALPHABET_REGEX);
			for (String word : words) {
				word = word.toLowerCase();
				if (stopWords.contains(word) || word.equals("")) {
					continue;
				}
				i = vocabulary.put(word, i) == null ? i + 1 : i;

			}

		}

	}

	/**
	 * This method recursively builds our document list by taking only text
	 * files.
	 * 
	 * @param rootDoc
	 *            the root folder containing all of our documents
	 */
	private void getDocuments(File rootDoc) {
		File[] docs = rootDoc.listFiles();
		for (File f : docs) {
			if (f.isDirectory()) {
				getDocuments(f);
			} else {
				documents.add(f.toPath());
			}
		}

	}

	/**
	 * Builds all of our stopwords from a defined text file.
	 * 
	 * @throws IOException
	 *             if an IO error occurs
	 */
	private void fillStopWords() throws IOException {
		stopWords = new HashSet<String>(Files.readAllLines(Paths
				.get("./stopwords.txt")));
	}

	/**
	 * Getter method for results of our text analysis.
	 * 
	 * @return collection associating similarity with pathnames
	 */
	public TreeMap<Double, Path> getResults() {
		return products;
	}

}