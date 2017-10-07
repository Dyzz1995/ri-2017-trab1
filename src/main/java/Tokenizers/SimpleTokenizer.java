package Tokenizers;

import CorpusReader.Document;
import java.util.ArrayList;
import java.util.List;

public class SimpleTokenizer implements Tokenizer{
    
    public SimpleTokenizer() { }
    
    @Override
    public List<String> tokenize(List<Document> documents) {
        List<String> words = new ArrayList<>();
        for(int i = 0; i < documents.size(); i++){
            Document document = documents.get(i);
            String content = document.getTitle() + "\n" + document.getText();
            String newContent = content.replaceAll("[^a-zA-Z ]", "");
            String[] temp = newContent.split(" ");
            for(String s : temp){
                if(s.length()>=3)
                    words.add(s.toLowerCase());
            }
        }
        return words;
    }
}
