package com.marcusspeight;

import com.marcusspeight.impl.DomParser;
import com.marcusspeight.impl.Parser;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by marcusspeight on 14/06/2014.
 */
public class Client {

    public static DocumentInterface enter(String url) throws MalformedURLException {
        return enter(new URL(url));
    }

    public static DocumentInterface enter(URL url) {
        //Parser parser = new Parser(url);
        //return parser.getDocument();

        DomParser parser = new DomParser();
        return parser.parse(url.toString());
    }
}
