package Indexer;


import Utils.Pair;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import org.tartarus.snowball.ext.englishStemmer;

public class Indexer {
    //HashMap<term, ([posting list], term frequency)>
    private HashMap<String,Pair<List<Integer>, Integer>> indexer;
    private List<Pair<String, Integer>> words;
    private final List<String> stopwords;
    
    public Indexer(List<Pair<String, Integer>> words, String path) throws FileNotFoundException {
        indexer = new HashMap<>(); 
        this.words = words;
        stopwords = new ArrayList<>();
        loadStopwords();
        stopwordsFiltering();
        stemmingWords();
        indexWords();
        writeToFile(path);
    }
    
    public HashMap<String,Pair<List<Integer>, Integer>> getIndexer() {
        return indexer;
    }
    
    private void loadStopwords() throws FileNotFoundException {
        File file = new File ("stopwords.txt");
        Scanner sc = new Scanner(file);
        while (sc.hasNext()) {
            String word = sc.nextLine();
            if (word.length() > 0)
                stopwords.add(word);
        }
    }
    
    private void stopwordsFiltering() {
        System.out.println(words.size());
        words = words.stream()                                            // convert list to stream
                .filter(word -> !stopwords.contains(word.getKey()))      // filter words that stopwords's list hasn't
                .collect(Collectors.toList());                          // convert streams to List
        System.out.println(words.size());
    }
     
    private void stemmingWords() {
        englishStemmer stemmer = new englishStemmer();
        for (int i = 0; i < words.size(); i++) {
            String term = words.get(i).getKey();
            int docId = words.get(i).getValue();
            if (docId == 1399)
                System.out.println(term + ", " + docId);
            stemmer.setCurrent(term);
            if (stemmer.stem())
                words.set(i, new Pair<>(stemmer.getCurrent(), docId));
        }
    }
    
    
    private void indexWords() {
        
    }
    
    private void writeToFile(String path) {
        
    }
}
