package Tokenizers;


import CorpusReader.Document;
import java.util.ArrayList;
import java.util.List;

public class SimpleTokenizer implements Tokenizer{
    
    
    
    public SimpleTokenizer() {
        
    
        
    }
    
    public List<String> tokenize(List<Document> documents) {
        
        List<String> words = new ArrayList<String>();
        
        for(int i=0; i<documents.size(); i++){
            
            Document doc = documents.get(i);
            
            String content = doc.getTitle() + "\n" + doc.getText();
            
            String newContent = content.replaceAll("[^a-zA-Z]", " ");
            
            
            String[] temp = newContent.split(" ");
            
            for(String s: temp){
                
                if(s.length()>=3){
                    words.add(s.toLowerCase());
//                    System.out.println(s.toLowerCase());
                }
                
            }
            
            
        }
        
        



        return words;
    }
}
