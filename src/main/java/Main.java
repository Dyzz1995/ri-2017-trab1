
import Tokenizers.SimpleTokenizer;
import CorpusReader.Parser;
import CorpusReader.XMLParser;
import CorpusReader.CorpusReader;
import java.io.File;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        if (args.length >= 2) {
            if (args[1].equals("N") || args[1].equals("Y")) {
                File file = new File(args[0]);
                if (!file.exists()) {
                    System.err.println("ERROR: File not found");
                    System.exit(1);
                }
                Parser parser = new Parser(new XMLParser());
                CorpusReader corpus = new CorpusReader();
                List<String> words;
                if (file.isDirectory())
                    corpus.setDocuments(parser.parseDir(file));
                else
                    corpus.addDocument(parser.parseFile(file));
                if (args.length == 2 && args[1].equals("N")) {
                    SimpleTokenizer simpleTokenizer = new SimpleTokenizer();
                    words = simpleTokenizer.tokenize(corpus.getDocuments());
                    System.out.println(words);
                }
                if (args.length == 3 && args[1].equals("Y")) {
                    File newFile = new File(args[2]);
                    if (newFile.exists()) {
                        System.err.println("ERROR: The file you want to create already exists!");
                        System.exit(1);
                    } else {
                        //TODO: index
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
