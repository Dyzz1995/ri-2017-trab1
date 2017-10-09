package Tokenizers;

import CorpusReader.Document;
import Utils.Pair;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.util.ArrayList;
import java.util.List;

public class CompleteTokenizer {
    private List<Pair<String, Integer>> words;
    
    public CompleteTokenizer(List<Document> documents) {
        words = new ArrayList<>();
        tokenize(documents);
    }
    
    public List<Pair<String, Integer>> getTerms() {
        return words;
    }
    
    private void tokenize(List<Document> documents) {
        for (Document document : documents) {
            int id = document.getId();
            //Remove some special characters
            String content = (document.getTitle() + "\n" + document.getText()).replaceAll("[*+/:;'()\"]", "");
            content = content.replaceAll("\n", " ");
            //Tokenize by white space
            String []text = content.split(" ");
            for (int i = 0; i < text.length; i++) {
                String term = text[i];
                //TODO: REVIEW
                // Remove '-' in small words. Otherwise, keep the term
                if (term.contains("-")) {
                    term = term.replaceAll("[.,]", "");
                    if (term.length() >= 2 && term.charAt(0) == '-' && term.charAt(1) == '-') {
                        if (term.length() > 2 && Character.isDigit(term.charAt(2)))
                            term = term.substring(1, term.length());
                        else if (term.length() < 10)
                            term = term.replaceAll("-", "");
                    }
                    else if (term.length() >= 2 && term.charAt(0) == '-' && Character.isLetter(term.charAt(1))) {
                        term = term.substring(1, term.length());
                        if (term.length() < 10)
                           term = term.replaceAll("-", ""); 
                    }
                    else if (term.length() <= 1 || term.length() < 10)
                        term = term.replaceAll("-", "");
                }
                // In case of number like 92.3, keep the term
                else if (term.contains(".")) {
                    int index = term.indexOf('.');
                    //Check if '.' is between at least two numbers
                     if (term.length() > index + 1 && index > 0
                            && (Character.isDigit(term.charAt(index - 1))) 
                            && (Character.isDigit(term.charAt(index + 1)))) {
                        term = term.replaceAll("[,-]", "");
                    if ((term.charAt(term.length() - 1) + "").contains(",")) {
                        term = term.substring(0, term.length() - 1);
                        }
                    }
                    else
                        term = term.replaceAll("[.,-]", "");
                } 
                // In case of number like 92,3, keep the term
                else if (term.contains(",")) {
                    int index = term.indexOf(',');
                    // Check if ',' is between at least two numbers
                    if (term.length() > index + 1 && index > 0
                            && (Character.isDigit(term.charAt(index - 1))) 
                            && (Character.isDigit(term.charAt(index + 1)))) {
                        term = term.replaceAll("[.-]", "");
                    if ((term.charAt(term.length() - 1) + "").contains(",")) {
                        term = term.substring(0, term.length() - 1);
                        }
                    }
                    else
                        term = term.replaceAll("[,.-]", "");
                }
                else if (term.length() == 1 && term.charAt(0) == '=') {
                    term = term.replaceAll("=", "");                }
                // Just add tokens with content
                if (term.trim().length() > 0)
                    words.add(new Pair<>(term, id));
            }
        }
    }
    
}
