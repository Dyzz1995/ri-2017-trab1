package CorpusReader;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

public class XMLParser implements Strategy {
    @Override
    public Document parseFile(File file) {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            XMLHandler handler = new XMLHandler();
            saxParser.parse(file, handler);
            return handler.getDocument();
        } catch (ParserConfigurationException  ex) {
            Logger.getLogger(XMLParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException  ex) {
            Logger.getLogger(XMLParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XMLParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @Override
    public List<Document> parseDir(File file) {
        File []files = file.listFiles();
        Arrays.sort(files);
        List<Document> documents = new ArrayList<Document>();
        for (File f : files) {
            Document document = parseFile(f);
            // Auto-increment in DOCNO of all Documents
            if (document.getId() != documents.size() + 1)
                document.setId(documents.size() + 1);
            documents.add(document);
        }
        return documents;
    }
}
