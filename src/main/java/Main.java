


import Tokenizers.SimpleTokenizer;
import CorpusReader.Parser;
import CorpusReader.XMLParser;
import CorpusReader.CorpusReader;
import CorpusReader.Document;
import Indexer.Indexer;
import Tokenizers.CompleteTokenizer;
import Utils.Pair;
import java.io.File;
import java.io.FileNotFoundException;
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
public class Main {

    /**
     * Read files and options from arguments. After that, show the answers.
     * 
     * The files have to be unique.
     * If you want to use the Simple Tokenizer, insert [filename] [t1] [filename2].
     * If you want to use the Complete Tokenizer and create a indexer, insert [filename] [t2] [filename2].
     * 
     * @param args
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        if (args.length == 3) {
            if (args[1].equals("t1") || args[1].equals("t2")) {
                File file = new File(args[0]);
                if (!file.exists()) {
                    System.err.println("ERROR: File not found");
                    System.exit(1);
                }
                Parser parser = new Parser(new XMLParser());
                CorpusReader reader = new CorpusReader();
                List<Document> documents;
                // In case of filename is a directory
                if (file.isDirectory())
                    reader.setDocuments(parser.parseDir(file));
                // In case of a filename is a only file
                else
                    reader.addDocument(parser.parseFile(file));
                documents = reader.getDocuments();
                // Indexer file need to have an unique name in actual directory
                File indexerFile = new File(args[2]);
                if (indexerFile.exists()) {
                    System.err.println("ERROR: The file you want to create already exists!");
                    System.exit(1);
                }
                List<Pair<String, Integer>> listTerms;
                Indexer indexer;
                long startTime;
                // Use of Simple Tokenizer
                if (args[1].equals("t1")) {
                    startTime = System.currentTimeMillis();
                    SimpleTokenizer simpleTokenizer = new SimpleTokenizer();
                    simpleTokenizer.tokenize(documents);
                    listTerms = simpleTokenizer.getTerms();
                    System.out.println("**********************************************");
                    System.out.println("Indexer with Simple Tokenizer");
                    System.out.println("**********************************************");
                }
                // Use of Complete Tokenizer
                else {
                    startTime = System.currentTimeMillis();
                    CompleteTokenizer completeTokenizer = new CompleteTokenizer();
                    completeTokenizer.tokenize(documents);
                    listTerms = completeTokenizer.getTerms();
                    System.out.println("**********************************************");
                    System.out.println("Indexer with Complete Tokenizer");
                    System.out.println("**********************************************");
                }
                // Create the Indexer
                indexer = new Indexer(listTerms, indexerFile, args[1]);
                // Get the vocabulary size
                System.out.println("Vocabulary Size: " + indexer.getVocabularySize());
                System.out.println("----------------------------------------------");
                System.out.println("First 10 terms in only one document:");
                // Get the ten first terms that appears in only one document
                List<String> termsInOneDoc = indexer.getTermsInOneDoc(); 
                for (String term : termsInOneDoc)
                    System.out.println(term);
                System.out.println("----------------------------------------------");
                System.out.println("First 10 terms with higher document frequency:");
                // Get the ten first terms with higher document frequency
                List<Pair<String, Integer>> termsFreq = indexer.getTermsWithHigherFreq();
                for (Pair<String, Integer> term : termsFreq)
                    System.out.println(term);
                long stopTime = System.currentTimeMillis();
                long elapsedTime = stopTime - startTime;
                System.out.println("**********************************************");
                System.out.println("Indexer time: " + elapsedTime + " ms");
            } else {
                System.err.println("ERROR: Invalid choose of tokenizer! Choose t1 or t2.");
                System.exit(1);
            }
        } else {
            System.err.println("ERROR: Invalid number of arguments!");
            System.out.println("USAGE: <file or dir> <t1 or t2> <indexer file>");
            System.exit(1);
        }
    }
}
