package com.marcusspeight.impl;

import com.marcusspeight.ObjectInterface;
import com.marcusspeight.PropertyInterface;

import java.util.Map;
import java.util.Set;

/**
 * Created by marcusspeight on 13/06/2014.
 */
public class LocalObject implements ObjectInterface {

    private Document doc;
    private String id;
    private SimpleObject object;

    public LocalObject(Document doc, String id) {
        this.doc = doc;
        this.id = id;
        object = null;
    }

    private SimpleObject getObject() {
        if(object == null) {
            object = doc.getLocalObject(id);
        }

        return object;
    }

    public String toString() {
        return getObject().toString();
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
