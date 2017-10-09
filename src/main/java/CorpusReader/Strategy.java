package CorpusReader;


import java.io.File;
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
* Strategy's pattern.
* The Strategy pattern creates objects. The strategy object changes the executing algorithm of the context object.
*/
public interface Strategy {

    /**
     * Parse a certain file.
     * @param file
     * @return Document
     */
    Document parseFile(File file);

    /**
     * Parse a certain directory.
     * @param file
     * @return list of Documents.
     */
    List<Document> parseDir(File file);
}
