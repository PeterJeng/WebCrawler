package tf_idf;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Machine Learning, using the tfidf algorithm, we found the importance of different words throughout the google search results.
 * Top results indicate key words that correlate to cryptocurrency
 *
 * @author Peter
 *
 */
public class TFIDF {
	public ArrayList<Word> bagOfWords;
	public static HashMap<String, Integer> wordList;
	public static HashMap<String, Boolean> ignoreList;
	
	public static double tf(List<String> doc, String term) {
		double result = 0;
		for(String word: doc) {
			if(word.equalsIgnoreCase(term)) {
				result++;
			}
		}
	
		return result / doc.size();
	}
	
	
	public static double idf(List<List<String>> documents, String term, HashMap<String, Integer> wordList ) {
		double result = 0;
//		for(List<String> doc: documents) {
//			for(String word: doc) {
//				if(word.equalsIgnoreCase(term)) {
//					result++;
//					break;
//				}
//			}
//		}
		
		result = (double)wordList.get(term);
		
		return Math.log(documents.size() / result);
	}
	
	/**
	 * Estimate the number of words in each List<List<String>> by counting total number of words in the entire title list
	 * It is highly likely that a title will not use the same word twice
	 * @param documents
	 * @param doc
	 * @param term
	 * @param wordList
	 * @return
	 */
	public static double calculate(List<List<String>> documents, List<String> doc, String term, HashMap<String, Integer> wordList) {
		return tf(doc, term) * idf(documents, term, wordList);
	}
	
	public static void readFile(String file) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));

		String st;

		String[] line;

		while ((st = br.readLine()) != null) {
			line = st.split(" ");
			
			for(String word: line) {
				int count = 1;
				if(wordList.containsKey(word.toLowerCase())) {
					count = (int) wordList.get(word.toLowerCase());
					count++;
				}
					
				wordList.put(word.toLowerCase(), count);
			}
		}

		br.close();
	}
	
	public static void ignoreFile(String file) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(file));

		String st;

		while ((st = br.readLine()) != null) {
			ignoreList.put(st, true);
		}

		br.close();
	}
	
	public static void main(String[] args) {
		wordList = new HashMap<String, Integer>();
		ignoreList = new HashMap<String, Boolean>();
		ArrayList<Word> list = new ArrayList<Word>();
		
		
        try {
        	readFile("CryptoTitle.txt");
        	ignoreFile("ignoreWords.txt");
        	
        	List<List<String>> Titles = new ArrayList<List<String>>();
        	
        	BufferedReader br = new BufferedReader(new FileReader("CryptoTitle.txt"));

    		String st;

    		while ((st = br.readLine()) != null) {
    			String[] line;
    			line = st.split(" ");
    			
    			List<String> temp = Arrays.asList(line);
    			Titles.add(temp);
    		}

    		br.close();
    		
    		HashMap<String, Double> tfidfCount = new HashMap<String, Double>();
    		
    		//calculate the summation of the tfidf of each word
    		for(int i = 0; i < Titles.size(); i++) {
    			List<String> temp = Titles.get(i);
    			
    			for(int j = 0; j < temp.size(); j++) {
    				String word = temp.get(j).toLowerCase();
    				
    				//skip words in the ignore list
    				if(ignoreList.containsKey(word)) {
    					continue;
    				}
    				
    				if(tfidfCount.containsKey(word.toLowerCase())) {
    					double value = tfidfCount.get(word);
    					tfidfCount.put(word, value + calculate(Titles, temp, word.toLowerCase(), wordList));
    					
    				}
    				else {
    					tfidfCount.put(word, calculate(Titles, temp, word.toLowerCase(), wordList));
    				}
    			}
    		}
    		
    		for (Entry<String, Double> entry : tfidfCount.entrySet()) {
        	    String key = entry.getKey();
        	    Double value = entry.getValue();
        	    
        	    Word temp = new Word(key.toLowerCase(), value);
        	    
        	    list.add(temp);
        	}
        	
        	list.sort(null);
        	
        	for(int i = 0; i < list.size(); i++) {
        		System.out.println(list.get(i).toString());
        	}
    		
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
