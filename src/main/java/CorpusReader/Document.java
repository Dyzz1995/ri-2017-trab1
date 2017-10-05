package CorpusReader;

public abstract class Document {
    private int id;
    private String title;
    private String text;
    private String author;
    
    public Document() { }
    
    public Document(int id, String title, String text, String author) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
       
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String toString() {
        return "{Document: " + id + "\nTitle: " + title + "\nAuthor: " + author + "\nText: " + text + "}\n";
    }
}
