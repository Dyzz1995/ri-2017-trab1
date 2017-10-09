package Tokenizers;

import CorpusReader.Document;
import Utils.Pair;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SimpleTokenizer{
    private List<Pair<String, Integer>> words;
    
    public SimpleTokenizer(List<Document> documents) {
        words = new ArrayList<>();
        tokenize(documents);
        sortWords();
    }
    
    public int getVocabularySize() {
        return words.size();
    }
    
    public List<String> getTenTermsInOneDoc() {
        List<String> terms = new ArrayList<>();
        for (Pair<String, Integer> term : words) {
            List<Integer> filter =
                words.stream()                                             
                    .filter(line -> line.getKey().equals(term.getKey()))
                    .map(line -> line.getValue())
                    .collect(Collectors.toList());                          
            Set<Integer> set = new HashSet<>(filter);
            if (filter.size() == set.size()) {
                terms.add(term.getKey());
                if (terms.size() == 10)
                    break;
            }
        }
        return terms;
    }
    
    public List<Pair<String, Integer>> getTermsWithHigherFreq() {
        List<Pair<String, Integer>> termsFreq = orderTermsByFreq();
        Comparator<Pair<String, Integer>> comp = (Pair<String, Integer> a, Pair<String, Integer> b) -> {
            String s1 = a.getKey();
            int d1 = a.getValue();
            String s2 = b.getKey();
            int d2 = b.getValue();
            int res;
            if (d1 > d2 || (d1 == d2 && s1.compareTo(s2) > 0))
                res = -1;
            else
                res = 1;
            return res;
        };
        Collections.sort(termsFreq, comp);
        List<Pair<String, Integer>> terms = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            terms.add(new Pair<>(termsFreq.get(i).getKey(),termsFreq.get(i).getValue()));
        return terms;
    }
    
    private List<Pair<String, Integer>> orderTermsByFreq() {
        List<Pair<String, Integer>> pairs = new ArrayList<>();
        List<String> filterWords =
                words.stream()                                             
                .map(line -> line.getKey())
                .collect(Collectors.toList()); 
        Set<String> setTerms = new HashSet<>(filterWords);
        for (String term : setTerms) {
            List<Integer> filterFreq =
                    words.stream()                                             
                    .filter(line -> line.getKey().equals(term))
                    .map(line -> line.getValue())
                    .collect(Collectors.toList());
            Set<Integer> setFreq = new HashSet<>(filterFreq);
            pairs.add(new Pair<>(term, setFreq.size()));
        }
        return pairs;
    }
    
    private void tokenize(List<Document> documents) {
        for(int i = 0; i < documents.size(); i++){
            Document document = documents.get(i);
            String content = document.getTitle() + "\n" + document.getText();
            String newContent = content.replaceAll("[^a-zA-Z ]", "");
            String[] temp = newContent.split(" ");
            for(String s : temp){
                if(s.length() >=3 )
                    words.add(new Pair<>(s.toLowerCase(), document.getId()));
            }
        }
    }
    
     private void sortWords() {
        Comparator<Pair<String, Integer>> comp = (Pair<String, Integer> a, Pair<String, Integer> b) -> {
            String s1 = a.getKey();
            int d1 = a.getValue();
            String s2 = b.getKey();
            int d2 = b.getValue();
            int res;
            if (s1.compareTo(s2) > 0 || (s1.equals(s2) && d1 > d2))
                res = 1;
            else
                res = -1;
            return res;
        };
        Collections.sort(words, comp);
    }
}
