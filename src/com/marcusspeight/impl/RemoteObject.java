package com.marcusspeight.impl;

import com.marcusspeight.ObjectInterface;
import com.marcusspeight.PropertyInterface;

import java.net.URL;
import java.util.Map;
import java.util.Set;

/**
 * Created by marcusspeight on 13/06/2014.
 */
public class RemoteObject implements ObjectInterface {

    private URL url;
    private ObjectInterface object = null;

    public RemoteObject(URL url) {
        this.url = url;
    }

    private ObjectInterface getObject() {
        if(object == null) {
            DomParser parser = new DomParser();
            object = parser.parse(url.toString()).getObjects().get(0);
        }

        return object;
    }

    public String toString() {
        return "RemoteObject:" + url.toString() + '@' + Integer.toHexString(hashCode());
    }

    @Override
    public String getType() {
        return getObject().getType();
    }

    @Override
    public PropertyInterface getProperty(String name) {
        return getObject().getProperty(name);
    }

    @Override
    public Set<String> getProperties() {
        return getObject().getProperties();
    }
}
