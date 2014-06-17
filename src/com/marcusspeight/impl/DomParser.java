package com.marcusspeight.impl;

import com.marcusspeight.DocumentInterface;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by marcusspeight on 16/06/2014.
 */
public class DomParser {

    private final boolean debug = false;
    private int level = 1;
    private URL baseUrl;

    public DocumentInterface parse(String url) {
        try {
            Connection conn = Jsoup.connect(url);
            org.jsoup.nodes.Document doc = conn.get();

            baseUrl = new URL(url);

            Document d = new Document();

            parseElement(d, doc.body(), null);

            return d;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    private void parseElement(Document doc, Element node, SimpleObject currentObject) {
        SimpleObject nextObject = null;

        if(debug) {
            System.out.printf("%"+ level +"s%s", "", node.nodeName()); level++;

            if(node.hasAttr("itemprop")) {
                System.out.printf(" %s", node.attr("itemprop"));
            }

            if(node.hasAttr("itemscope")) {
                System.out.printf(" %s", node.attr("itemtype"));
            }

            System.out.print("\n");
        }

        if(node.hasAttr("itemscope")) {
            nextObject = new SimpleObject();
            nextObject.setType(node.attr("itemtype"));

            if(currentObject == null) {
                doc.addObject(nextObject);
            }

            if(node.hasAttr("id")) {
                doc.addLocalObject(node.id(), nextObject);
            }
        }

        if(node.hasAttr("itemprop") && currentObject != null) {
            String itemprop = node.attr("itemprop");
            if (nextObject != null) {
                currentObject.setProperty(itemprop, nextObject);
            } else if (node.nodeName().contentEquals("a")) {
                // either local or remote link
                String href = node.attr("href");
                if (href.charAt(0) == '#') {
                    currentObject.setProperty(itemprop, new LocalObject(doc, href.substring(1)));
                } else {
                    try {
                        currentObject.setProperty(itemprop, new RemoteObject(new URL(baseUrl, href)));
                    } catch (MalformedURLException e) {
                        // do nothing
                    }
                }
            } else if (node.hasAttr("content")) {
                currentObject.setProperty(itemprop, new SimpleProperty(node.attr("content")));
            } else if(node.nodeName().equals("img")) {
                try {
                    currentObject.setProperty(itemprop, new SimpleProperty(new URL(baseUrl,node.attr("src")).toString()));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            } else {
                currentObject.setProperty(itemprop, new SimpleProperty(node.text()));
            }
        }

        if(node.nodeName().equals("a") && node.hasAttr("rel") && node.hasAttr("href")) {
            try {
                URL href = new URL(baseUrl, node.attr("href"));
                if (currentObject == null) {
                    doc.addLink(new Link(node.attr("rel"), href));
                } else {
                    currentObject.addLink(new Link(node.attr("rel"), href));
                }
            } catch (MalformedURLException e) {
                // do nothing
            }
        }

        for(Element child: node.children()) {
            parseElement(doc, child, nextObject != null ? nextObject : currentObject);
        }

        if(debug) { level--; }
    }
}
