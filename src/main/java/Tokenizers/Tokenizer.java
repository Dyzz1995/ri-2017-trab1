package Tokenizers;

import CorpusReader.Document;
import java.util.List;

public interface Tokenizer {
    public List<String> tokenize(List<Document> documents);
}
