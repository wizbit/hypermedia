package com.marcusspeight.impl;

import com.marcusspeight.DocumentInterface;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Deque;
import java.util.LinkedList;

/**
 * Created by marcusspeight on 13/06/2014.
 */
public class Parser extends org.ccil.cowan.tagsoup.Parser {

    private final boolean debug = true;
    private int level = 1;

    private Document document;
    private URL baseUrl;
    private String currentProperty;
    private int currentPropertyLevel;
    private StringBuilder currentPropertyValue;

    private class ObjectLevel {
        public SimpleObject object;
        public int level;

        public ObjectLevel(SimpleObject obj) {
            object = obj;
            level = 1;
        }
    }

    private Deque<ObjectLevel> objectStack;

    public Parser(URL url) {
        super();
        baseUrl = url;
        document = new Document();
        objectStack = new LinkedList<ObjectLevel>();

        try {
            System.out.printf("GET %s ... ", url);
            parse(new InputSource(new InputStreamReader(url.openStream())));
            System.out.println("OK");
            System.out.flush();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            //e.printStackTrace();
        }
    }

    public DocumentInterface getDocument() {
        return document;
    }

    public void startDocument() {
        if(debug) {System.out.println("startDocument"); }
    }

    public void endDocument() {
        if(debug) { System.out.println("endDocument"); }
    }

    public void startElement(String uri, String name, String qName, Attributes atts) {
        ObjectLevel current = objectStack.peekFirst();

        String itemscope = atts.getValue("itemscope");
        SimpleObject nextObject = null;

        if(debug) { System.out.print(String.format("%"+ level +"s", "")); System.out.printf("%s ", qName); }

        if(itemscope != null) {
            nextObject = new SimpleObject();

            nextObject.setType(atts.getValue("itemtype"));

            String id = atts.getValue("id");
            if(id != null) {
                document.addLocalObject(id, nextObject);
            }

            if(debug) { System.out.printf("(%s) ", nextObject.getType()); }
        }

        String itemprop = atts.getValue("itemprop");
        if(itemprop != null && current != null)
        {
            if(nextObject != null) {
                current.object.setProperty(itemprop, nextObject);
            } else if(qName.equals("a")) {
                // either local or remote link
                String href = atts.getValue("href");
                if (href.charAt(0) == '#') {
                    // local link
                    current.object.setProperty(itemprop, new LocalObject(document, href.substring(1)));
                } else {
                    // remote link
                    URL url = null;
                    try {
                        url = new URL(baseUrl, href);
                        current.object.setProperty(itemprop, new RemoteObject(url));
                    } catch (MalformedURLException e) {
                        // do nothing
                    }
                }
            } else if(atts.getValue("content") != null) {
                current.object.setProperty(itemprop, new SimpleProperty(atts.getValue("content")));
            } else if(qName.equals("img")) {
                try {
                    current.object.setProperty(itemprop, new SimpleProperty(new URL(baseUrl,atts.getValue("src")).toString()));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            } else {
                currentProperty = itemprop;
                currentPropertyValue = new StringBuilder();
                currentPropertyLevel = 1;
            }
            if(debug) { System.out.printf("(prop: %s) ", itemprop); }
        }

        if(debug) { System.out.print("\n"); level++; }

        if(nextObject != null) {
            if(objectStack.isEmpty()) {
                document.addObject(nextObject);
            }
            objectStack.push(new ObjectLevel(nextObject));
        } else if(current != null) {
            current.level++;
        }

        if(qName.equals("a") && current == null && atts.getValue("rel") != null && atts.getValue("href") != null) {
            try {
                URL href = new URL(baseUrl, atts.getValue("href"));
                document.addLink(new Link(atts.getValue("rel"), href));
            } catch (MalformedURLException e) {
                for(int i = 0; i < atts.getLength(); ++i) {
                    System.out.printf("%s: %s\n", atts.getQName(i), atts.getValue(i));
                }

                e.printStackTrace();
            }

        }
    }

    public void endElement(String uri, String name, String qName) {
        ObjectLevel current = objectStack.peekFirst();

        if(debug) { level--; /*System.out.print(String.format("%"+ level +"s", "")); System.out.printf("<- %s", qName);*/ }

        if(current != null) {

            if(--currentPropertyLevel == 0) {
                objectStack.peekFirst().object.setProperty(currentProperty, new SimpleProperty(currentPropertyValue.toString().trim()));
                currentProperty = null;
                currentPropertyValue = null;
            }

            if (--current.level == 0) {

                //if(debug) { System.out.printf(" (%s)", current.object.getType()); }
                objectStack.pop();
            }
        }

        //if(debug) { System.out.print("\n"); }
    }

    public void characters (char ch[], int start, int length) {
        if(currentProperty != null) {
            currentPropertyValue.append(ch, start, length);
        }
    }

}
