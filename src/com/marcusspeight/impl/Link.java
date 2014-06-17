package com.marcusspeight.impl;

import com.marcusspeight.DocumentInterface;
import com.marcusspeight.LinkInterface;

import java.net.URL;

/**
 * Created by marcusspeight on 13/06/2014.
 */
public class Link implements LinkInterface {
    private String rel;
    private URL url;

    public Link(String relation, URL href) {
        rel = relation;
        url = href;
    }

    public String toString() {
        return getUrl().toString();
    }

    @Override
    public String getRelation() {
        return rel;
    }

    @Override
    public URL getUrl() {
        return url;
    }

    @Override
    public DocumentInterface get() {
        Parser parser = new Parser(getUrl());
        return parser.getDocument();
    }
}
