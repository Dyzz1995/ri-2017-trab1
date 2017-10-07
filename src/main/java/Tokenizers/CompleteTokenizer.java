package Tokenizers;

import CorpusReader.Document;
import Utils.Pair;
import java.util.ArrayList;
import java.util.List;

public class CompleteTokenizer{
    
    public CompleteTokenizer() { }
    
    public List<Pair<String, Integer>> tokenize(List<Document> documents) {
        List<Pair<String, Integer>> words = new ArrayList<>();
        for (Document document : documents) {
            int id = document.getId();
            //Remove some special characters
            String content = (document.getTitle() + "\n" + document.getText()).replaceAll("[,:;'\"]", "");
            content = content.replaceAll("\n", " ");
            //Tokenize by white space
            String []text = content.split(" ");
            for (int i = 0; i < text.length; i++) {
                String term = text[i];
                // Remove '-' in small words. Otherwise, keep the term
                if (term.contains("-") && term.length() < 10)
                    term = term.replaceAll("[.,]", "");
                // In case of number like 92.3, keep the term
                else if (term.contains(".")) {
                    int index = term.indexOf('.');
                    //Check if '.' is between at least two numbers
                    if (term.length() > index + 1 && index > 0 
                            && (term.charAt(index - 1) + "").contains("[0-9]") 
                            && (term.charAt(index + 1) + "").contains("[0-9]")) {
                        term = term.replaceAll("[,-]", "");
                    }
                    else
                        term = term.replaceAll("[.,-]", "");
                }
                // In case of number like 92,3, keep the term
                else if (term.contains(",")) {
                    int index = term.indexOf(',');
                    // Check if ',' is between at least two numbers
                    if (term.length() > index + 1 && index > 0
                            && (term.charAt(index - 1) + "").contains("[0-9]") 
                            && (term.charAt(index + 1) + "").contains("[0-9]")) {
                        term = term.replaceAll("[.-]", "");
                    }
                    else
                        term = term.replaceAll("[,.-]", "");
                }
                // Just add tokens with content
                if (term.length() > 0)
                    words.add(new Pair<>(term, id));
            }
        }
        return words;
    }
    
}
