package Tokenizers;


import CorpusReader.Document;
import Utils.Pair;
import java.util.List;

public interface Tokenizer {
    List<Pair<String, Integer>> tokenize(List<Document> documents);
}
