package Tokenizers;

import CorpusReader.Document;
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

// Interface to tokenize documents
public interface Tokenizer {
    public void tokenize(List<Document> documents);
}
