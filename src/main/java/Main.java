
import Tokenizers.SimpleTokenizer;
import CorpusReader.Parser;
import CorpusReader.XMLParser;
import CorpusReader.CorpusReader;
import java.io.File;

public class Main {
    public static void main(String[] args){
        if (args.length == 2) {
            if (args[1].equals("t1") || args[1].equals("t2")) {
                File file = new File(args[0]);
                if (!file.exists()) {
                    System.err.println("ERROR: File not found");
                    System.exit(1);
                }
                Parser parser = new Parser(new XMLParser());
                CorpusReader corpus = new CorpusReader();
                if (file.isDirectory())
                    corpus.setDocuments(parser.parseDir(file));
                else
                    corpus.addDocument(parser.parseFile(file));
                SimpleTokenizer simpleTokenizer = new SimpleTokenizer();
                simpleTokenizer.tokenize(corpus.getDocuments());
            } else {
                System.err.println("ERROR: Invalid choose of tokenizer! Choose t1 or t2.");
            }
        } else {
            System.err.println("ERROR: Invalid number of arguments!");
            System.out.println("USAGE: <file or dir> <t1 or t2>");
            System.exit(1);
        }
    }
}
