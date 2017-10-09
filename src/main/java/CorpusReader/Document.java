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
public abstract class Document {
    private int id;
    private String title;
    private String text;
    private String author;
    
    /**
     * Constructor. Abstract Class that represents a Document.
     */
    public Document() { }
    
    /**
     * Constructor. A Document's object uses an id, title, text and author.
     * @param id
     * @param title
     * @param text
     * @param author
     */
    public Document(int id, String title, String text, String author) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.author = author;
    }

    /**
     * Return Document's id.
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Set a new id for the Document.
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Return Document's title.
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set a new title for the Document.
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Return Document's text.
     * @return text
     */
    public String getText() {
       
        return text;
    }

    /**
     * Set a new text for The Document.
     * @param text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Return Document's author.
     * @return author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Set a new author for the Document.
     * @param author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Return a string of Object.
     * @return string
     */
    @Override
    public String toString() {
        return "{Document: " + id + "\nTitle: " + title + "\nAuthor: " + author + "\nText: " + text + "}\n";
    }
}
