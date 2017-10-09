
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

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        if (args.length >= 2) {
            if (args[1].equals("N") || args[1].equals("Y")) {
                File file = new File(args[0]);
                if (!file.exists()) {
                    System.err.println("ERROR: File not found");
                    System.exit(1);
                }
                Parser parser = new Parser(new XMLParser());
                CorpusReader corpus = new CorpusReader();
                List<Document> documents;
                if (file.isDirectory())
                    corpus.setDocuments(parser.parseDir(file));
                else
                    corpus.addDocument(parser.parseFile(file));
                documents = corpus.getDocuments();
                if (args.length == 2 && args[1].equals("N")) {
                    SimpleTokenizer simpleTokenizer = new SimpleTokenizer(documents);
                    System.out.println("**********************************************");
                    System.out.println("Simple Tokenizer");
                    System.out.println("**********************************************");
                    System.out.println("Vocabulary Size: " + simpleTokenizer.getVocabularySize());
                    System.out.println("----------------------------------------------");
                    System.out.println("First 10 terms in only one document:"); 
                    List<String> terms = simpleTokenizer.getTenTermsInOneDoc();
                    for (String term : terms)
                        System.out.println(term);
                    System.out.println("----------------------------------------------");
                    System.out.println("First 10 terms with higher document frequency:"); 
                    List<Pair<String, Integer>>  termsFreq = simpleTokenizer.getTermsWithHigherFreq();
                    for (Pair<String, Integer>term : termsFreq)
                        System.out.println(term);
                    System.out.println("**********************************************");
                }
                if (args.length == 3 && args[1].equals("Y")) {
                    File newFile = new File(args[2]);
                    if (newFile.exists()) {
                        System.err.println("ERROR: The file you want to create already exists!");
                        System.exit(1);
                    } else {
                        CompleteTokenizer completeTokenizer = new CompleteTokenizer(documents);
                        List<Pair<String, Integer>> terms = completeTokenizer.getTerms();
                        Indexer indexer = new Indexer(terms, newFile);
                        System.out.println("**********************************************");
                        System.out.println("Indexer with Complete Tokenizer");
                        System.out.println("**********************************************");
                        System.out.println("Vocabulary Size: " + indexer.getVocabularySize());
                        System.out.println("----------------------------------------------");
                        System.out.println("First 10 terms in only one document:");
                        List<String> termsFreqOneDoc = indexer.getTenTermsInOneDoc(); 
                        for (String term : termsFreqOneDoc)
                            System.out.println(term);
                        System.out.println("----------------------------------------------");
                        System.out.println("First 10 terms with higher document frequency:");
                        List<Pair<String, Integer>> termsFreq = indexer.getTermsWithHigherFreq();
                        for (Pair<String, Integer> term : termsFreq)
                            System.out.println(term);
                        System.out.println("**********************************************");
                    }
                } else if (args.length == 2 && args[1].equals("Y")) {
                    System.err.println("ERROR: Invalid arguments!");
                    System.out.println("USAGE: <file or dir> <Y or N> (<new file>)");
                    System.exit(1);
                }
            } else {
                System.err.println("ERROR: Invalid choose of tokenizer! Choose N or Y.");
                System.exit(1);
            }
        } else {
            System.err.println("ERROR: Invalid number of arguments!");
            System.out.println("USAGE: <file or dir> <Y or N> (<new file>)\n"
                    + "If you want to use indexer, use Y and the three parameters. Othwerwise, use N and ony two parameters");
            System.exit(1);
        }
    }
}
