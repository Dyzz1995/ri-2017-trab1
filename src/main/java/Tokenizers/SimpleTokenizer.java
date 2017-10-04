package Tokenizers;


import CorpusReader.Document;
import java.util.ArrayList;
import java.util.List;

public class SimpleTokenizer implements Tokenizer{
    public SimpleTokenizer() { }
    
    public List<String> tokenize(List<Document> documents) {
        List<String> words = new ArrayList<String>();
        //TODO: simple tokenizer that splits on whitespace, lowercase tokens, 
        //remove all non-alphabetic characters and keeps only terms >= 3 characters
        return words;
    }
}
