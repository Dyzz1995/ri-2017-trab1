package CorpusReader;

/**
 * IR, October 2017
 *
 * Assignment 1 
 *
 * @author Tiago Faria, 73714, tiagohpf@ua.pt
 * @author David dos Santos Ferreira, 72219, davidsantosferreira@ua.pt
 * 
 */
public class XMLDocument extends Document {

    /**
     * Class that extends Document abstract class.
     * Class that represents XML Files.
     * Constructor.
     */
    public XMLDocument() { }
    
    /**
     * Constructor with Document's id, title, text and author.
     * @param id
     * @param title
     * @param text
     * @param author
     */
    public XMLDocument(int id, String title, String text, String author) {
        super(id, title, text, author);
    }
}
