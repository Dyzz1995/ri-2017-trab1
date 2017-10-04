package CorpusReader;


import java.io.File;
import java.util.List;

public interface Strategy {
    Document parseFile(File file);
    List<Document> parseDir(File file);
}
