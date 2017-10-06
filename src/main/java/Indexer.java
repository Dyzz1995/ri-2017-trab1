
import CorpusReader.Document;
import java.util.HashMap;
import java.util.List;

public class Indexer {
    //HashMap<term, ([posting list], term frequency)>
    private HashMap<String,Pair<List<Integer>, Integer>> indexer;
    private List<Document> documents;
    private List<String> stopwords;
    
    public Indexer(List<Document> documents, String path) {
        this.indexer = new HashMap<String,Pair<List<Integer>, Integer>>(); 
        this.documents = documents;
        stemmingWords();
        stopwordsFiltering();
        indexWords();
        writeToFile(path);
    }
    
    public HashMap<String,Pair<List<Integer>, Integer>> getIndexer() {
        return indexer;
    }
    
    private void stemmingWords() {
        
    }
    
    private void stopwordsFiltering() {
        
    }
    
    private void indexWords() {
        
    }
    
    private void writeToFile(String path) {
        
    }
}
