package Tokenizers;

import CorpusReader.Document;
import Utils.Pair;
import java.util.ArrayList;
import java.util.List;

/**
 * IR, October 2017
 *
 * Assignment 1 
 *
 * @author Tiago Faria, 73714, tiagohpf@ua.pt
 * @author David dos Santos Ferreira, 72219, davidsantosferreira@ua.pt
 * 
 */

/*
* Simple Tokenizer.
* Class that tokenizes a file or a directory, divided by whitespaces.
*/
public class SimpleTokenizer{
    private List<Pair<String, Integer>> terms;
    
    /**
     * Constructor. Given a list of documents, all ara tokenized and sorted alphabetically.
     * @param documents
     */
    public SimpleTokenizer(List<Document> documents) {
        terms = new ArrayList<>();
        tokenize(documents);
    }
    
    /**
     * Get all terms of tokenizer.
     * @return list of terms
     */
    public List<Pair<String, Integer>> getTerms() {
        return terms;
    }
    
    /**
     * Get vocabulary size.
     * @return size of terms list
     */
    public int getVocabularySize() {
        return terms.size();
    }
    
    // Tokenizing all documents.
    private void tokenize(List<Document> documents) {
        for(int i = 0; i < documents.size(); i++){
            Document document = documents.get(i);
            String content = document.getTitle() + "\n" + document.getText();
            // Remove all non-alphabetical characters.
            String newContent = content.replaceAll("[^a-zA-Z ]", " ");
            // Tokenize by whitespaces.
            String[] temp = newContent.split(" ");
            for(String s : temp){
                // Only accept words with equal or more than 3 characters.
                if(s.trim().length() >=3 )
                    terms.add(new Pair<>(s.trim().toLowerCase(), document.getId()));
            }
        }
    }
}
