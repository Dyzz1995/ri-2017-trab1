package Indexer;


import Utils.Pair;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.Collectors;
import org.tartarus.snowball.ext.englishStemmer;

public class Indexer {
    //HashMap<term, ([posting list], term frequency)>
    private List<Pair<String, Integer>> words;
    private final List<String> stopwords;
    private Map<String, List<Pair<Integer, Integer>>> indexer;
    
    public Indexer(List<Pair<String, Integer>> words, File file) throws FileNotFoundException {
        this.words = words;
        stopwords = new ArrayList<>();
        indexer = new TreeMap<>();
        loadStopwords();
        stopwordsFiltering();
        stemmingWords();
        indexWords();
        writeToFile(file);
    }
    
    public int getVocabularySize() {
        return indexer.size();
    }
    
    public List<String> getTenTermsInOneDoc() {
        List<String> terms = new ArrayList<>();
        for (Map.Entry<String, List<Pair<Integer, Integer>>> entry : indexer.entrySet()) {
            String word = entry.getKey();
            List<Pair<Integer, Integer>> listFrequencies = entry.getValue();
            if (listFrequencies.size() == 1)
                terms.add(word);
            if (terms.size() == 10)
                break;
        }
        return terms;
    }
    
    public List<Pair<String, Integer>> getTermsWithHigherFreq() {
        List<Pair<String, Integer>> termsFreq = getTermsAndFreq();
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
        List<Pair<String, Integer>> terms = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            terms.add(new Pair<>(termsFreq.get(i).getKey(),termsFreq.get(i).getValue()));
        return terms;
    }
    
    private List<Pair<String, Integer>> getTermsAndFreq() {
        List<Pair<String, Integer>> termsFreq = new ArrayList<>();
        for (Map.Entry<String, List<Pair<Integer, Integer>>> entry : indexer.entrySet()) {
            termsFreq.add(new Pair<>(entry.getKey(), entry.getValue().size()));
        }
        return termsFreq;
    }
    
    private void loadStopwords() throws FileNotFoundException {
        File file = new File ("stopwords.txt");
        Scanner sc = new Scanner(file);
        while (sc.hasNext()) {
            String word = sc.nextLine();
            if (word.trim().length() > 0)
                stopwords.add(word.trim());
        }
    }
    
    private void stopwordsFiltering() {
        words = words.stream()                                            // convert list to stream
                .filter(word -> !stopwords.contains(word.getKey()))      // filter words that stopwords's list hasn't
                .collect(Collectors.toList());                          // convert streams to List
    }
     
    private void stemmingWords() {
        englishStemmer stemmer = new englishStemmer();
        for (int i = 0; i < words.size(); i++) {
            String term = words.get(i).getKey();
            int docId = words.get(i).getValue();
            stemmer.setCurrent(term);
            if (stemmer.stem())
                words.set(i, new Pair<>(stemmer.getCurrent(), docId));
        }
    } 
    
    private void indexWords() {
        for (Pair<String, Integer> term_doc : words) {
            String term = term_doc.getKey();
            int docId = term_doc.getValue();
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
    
    private void writeToFile(File file) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(file);
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
        pw.close();
    }
}
