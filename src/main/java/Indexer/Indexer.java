package Indexer;

import Utils.Pair;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.Collectors;
import org.tartarus.snowball.ext.englishStemmer;

/**
 * IR, October 2017
 *
 * Assignment 1 
 *
 * @author Tiago Faria, 73714, tiagohpf@ua.pt
 * @author David dos Santos Ferreira, 72219, davidsantosferreira@ua.pt
 * 
 */

// Class Indexer that uses Stopwording filtering, Stemmer and create an Indexer.
public class Indexer {
    // List of terms (tokens)
    private List<Pair<String, Integer>> terms;
    // List of stopwords to filter
    private final List<String> stopWords;
    // Indexer. The Indexer has a list of terms like [term, docId: frequency] 
    private Map<String, List<Pair<Integer, Integer>>> indexer;
    
    /**
     * Constructor. An Indexer needs the terms and the filename to write.
     * @param terms
     * @param file
     * @throws FileNotFoundException
     */
    public Indexer(List<Pair<String, Integer>> terms, File file, String tokenizerType) throws FileNotFoundException {
        this.terms = terms;
        stopWords = new ArrayList<>();
        indexer = new TreeMap<>();
        if (tokenizerType.equals("t2")) {
            System.out.println("enter");
            loadStopwords();
            stopwordsFiltering();
            stemmingWords();
        }
        indexWords();
        writeToFile(file);
    }
    
    /**
     * Get size of indexer
     * @return indexer's size
     */
    public int getVocabularySize() {
        return indexer.size();
    }
    
    /**
     * Get the ten first terms (order alphabetically) that appear in only one document.
     * @return list of terms
     */
    public List<String> getTermsInOneDoc() {
        List<String> termsInOneDoc = new ArrayList<>();
        /*
        * For each entry on Map, get the term.
        * For each term, get its pairs <docId, frequency>.
        * If the term has only one pair, it appears in only one document.
        * The cycle finishes when the list has 10 terms.
        */
        for (Map.Entry<String, List<Pair<Integer, Integer>>> entry : indexer.entrySet()) {
            String word = entry.getKey();
            List<Pair<Integer, Integer>> listFrequencies = entry.getValue();
            if (listFrequencies.size() == 1)
                termsInOneDoc.add(word);
            if (termsInOneDoc.size() == 10)
                break;
        }
        return termsInOneDoc;
    }
    
    /**
     * Get the ten first terms with higher document frequency.
     * @return list of terms
     */
    public List<Pair<String, Integer>> getTermsWithHigherFreq() {
        // Get a list of pairs with <term, document's frequency>
        List<Pair<String, Integer>> termsFreq = getTermsAndFreq();
        // Use a Comparator to sort the list of pairs by document's frequency
        Comparator<Pair<String, Integer>> comp = (Pair<String, Integer> a, Pair<String, Integer> b) -> {
            String s1 = a.getKey();
            int d1 = a.getValue();
            String s2 = b.getKey();
            int d2 = b.getValue();
            int res;
            if (d1 > d2 || (d1 == d2 && s1.compareTo(s2) > 0))
                res = -1;
            else
                res = 1;
            return res;
        };
        Collections.sort(termsFreq, comp);
        /*
        * Get the ten first terms, sorted by document's frequency.
        * When the list has 10 terms, it finishes the cycle and returns.
        */
        List<Pair<String, Integer>> sortedTerms = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            sortedTerms.add(new Pair<>(termsFreq.get(i).getKey(),termsFreq.get(i).getValue()));
        return sortedTerms;
    }
    
    // Get of list of pairs with <term, document frequency>
    private List<Pair<String, Integer>> getTermsAndFreq() {
        // For each entry on Map, add the term and its document's frequency to a list
        List<Pair<String, Integer>> termsFreq = new ArrayList<>();
        for (Map.Entry<String, List<Pair<Integer, Integer>>> entry : indexer.entrySet()) {
            termsFreq.add(new Pair<>(entry.getKey(), entry.getValue().size()));
        }
        return termsFreq;
    }
    
    // Read the file of stopwords and load them to a list
    private void loadStopwords() throws FileNotFoundException {
        File file = new File ("stopwords.txt");
        Scanner sc = new Scanner(file);
        while (sc.hasNext()) {
            String word = sc.nextLine();
            // String.trim() to remove white spaces after text
            if (word.trim().length() > 0)
                stopWords.add(word.trim());
        }
    }
    
    /*
    * Apply the stopwording filtering.
    * If there's any term in the stopwording list, it'll be removed from terms list.
    */
    private void stopwordsFiltering() {
        terms = terms.stream()                                         // convert list to stream
                .filter(term -> !stopWords.contains(term.getKey()))   // filter words that stopwords's list hasn't
                .collect(Collectors.toList());                       // convert streams to List
    }
     
    // Stemmer for terms.
    private void stemmingWords() {
        englishStemmer stemmer = new englishStemmer();
        for (int i = 0; i < terms.size(); i++) {
            String term = terms.get(i).getKey();
            int docId = terms.get(i).getValue();
            stemmer.setCurrent(term);
            // If the term was stemmed, the terms list is updated.
            if (stemmer.stem())
                terms.set(i, new Pair<>(stemmer.getCurrent(), docId));
        }
    } 
    
    /*
    * From terms list, create a new structure that represents an indexer.
    * The key must be the term and the value is a list of documents where the term appears, plus is frequency.
    */
    private void indexWords() {
        for (Pair<String, Integer> term_doc : terms) {
            String term = term_doc.getKey();
            int docId = term_doc.getValue();
            /*
            * If the indexer hasn't the term yet, it's created a new instance.
            * If the indexer has the term and not the document, the pair <docId, frequency> is added to the list.
            * If the indexer has the term and the docId, it's incrementd in his frequency.
            */
            if (indexer.keySet().contains(term)) {
                List<Pair<Integer,Integer>> doc_freq = indexer.get(term);
                boolean containsPair = false;
                for (int i = 0; i < doc_freq.size(); i++) {
                    if (doc_freq.get(i).getKey() == docId) {
                        int value = doc_freq.get(i).getValue();
                        doc_freq.set(i, new Pair<>(doc_freq.get(i).getKey(), value + 1));
                        containsPair = true;
                        break;
                    }
                }
                if (!containsPair) {
                    doc_freq.add(new Pair<>(docId, 1));
                }
            indexer.put(term, doc_freq);
            } else {
                Pair<Integer, Integer> docId_freq = new Pair<>(docId, 1);
                indexer.put(term, new ArrayList<>(Arrays.asList(docId_freq)));
            }
        }
    }
    
    /*
    * Write the indexer to a file.
    * @param filename
    */
    private void writeToFile(File file) throws FileNotFoundException {
        try (PrintWriter pw = new PrintWriter(file)) {
            // For each entry of map, it's write an entry like: term, docId:frequency
            for (Map.Entry<String, List<Pair<Integer, Integer>>> entry : indexer.entrySet()) {
                pw.print(entry.getKey() + ",");
                List<Pair<Integer, Integer>> listFrequencies = entry.getValue();
                for (int i = 0; i < listFrequencies.size(); i++) {
                    int docId = listFrequencies.get(i).getKey();
                    int freq = listFrequencies.get(i).getValue();
                    if (i == listFrequencies.size() - 1)
                        pw.print(docId + ":" + freq + "\n");
                    else
                        pw.print(docId + ":" + freq + ",");
                }
            }
        }
    }
}
