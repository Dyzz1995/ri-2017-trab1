package CorpusReader;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLHandler extends DefaultHandler {
   private Document document;
   boolean id = false;
   boolean title = false;
   boolean author = false;
   boolean text = false;

   @Override
   public void startElement(String uri, 
   String localName, String qName, Attributes attributes) throws SAXException {
       if (qName.equalsIgnoreCase("DOC")) {
           document = new XMLDocument();
       } else if (qName.equalsIgnoreCase("DOCNO")) {
         id = true;
      } else if (qName.equalsIgnoreCase("TITLE")) {
         title = true;
      } else if (qName.equalsIgnoreCase("AUTHOR")) {
         author = true;
      } else if (qName.equalsIgnoreCase("TEXT")) {
         text = true;
      }
   }

   @Override
   public void characters(char ch[], int start, int length) throws SAXException {
      if (id) {
         document.setId(Integer.parseInt(new String(ch, start, length).trim()));
         id = false;
      } else if (title) {
         document.setTitle(new String(ch, start, length).trim());
         title = false;
      } else if (author) {
         document.setAuthor(new String(ch, start, length).trim());
         author = false;
      } else if (text) {
         document.setText(new String(ch, start, length).trim());
         text = false;
      }
   }
   
   public Document getDocument() {
       return document;
   }
}