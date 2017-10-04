package CorpusReader;


import java.io.File;
import java.util.List;

public class Parser {
    private Strategy strategy;
    
    public Parser(Strategy strategy) {
        this.strategy = strategy;
    }
    
    public Document parseFile(File file) {
        return strategy.parseFile(file);
    }
    
    public List<Document> parseDir(File file) {
        return strategy.parseDir(file);
    }
}
