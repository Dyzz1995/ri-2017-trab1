
import java.util.HashMap;
import java.util.List;

public class Indexer {
    //HashMap<term, ([posting list], term frequency)>
    private HashMap<String,Pair<List<Integer>, Integer>> indexer;
    private List<String> words;
    private List<String> stopwords;
    
    public Indexer(List<String> words, String path) {
        this.indexer = new HashMap<String,Pair<List<Integer>, Integer>>(); 
        this.words = words;
        stopwordsFiltering();
        stemmingWords();
        indexWords();
        writeToFile(path);
    }
    
    public HashMap<String,Pair<List<Integer>, Integer>> getIndexer() {
        return indexer;
    }
    
    private void stopwordsFiltering() {
        
    }
    
    private void stemmingWords() {
        
    }
    
    private void indexWords() {
        
    }
    
    private void writeToFile(String path) {
        
    }
}
