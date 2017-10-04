package CorpusReader;

import java.util.ArrayList;
import java.util.List;

public class CorpusReader {
    private List<Document> documents;

    public CorpusReader() {
        this.documents = new ArrayList<Document>();
    }

    public void addDocument(Document document) {
        this.documents.add(document);
    }

    public List<Document> getDocuments() {
        return documents;
    }
    
    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }
}
