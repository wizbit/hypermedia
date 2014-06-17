package com.marcusspeight.impl;

import com.marcusspeight.LinkInterface;
import com.marcusspeight.ObjectInterface;
import com.marcusspeight.PropertyInterface;

import java.util.*;

/**
 * Created by marcusspeight on 13/06/2014.
 */
public class SimpleObject implements ObjectInterface {

    String type;
    Map<String, PropertyInterface> properties;
    private Map<String, List<LinkInterface>> links;

    public SimpleObject() {
        properties = new HashMap<String, PropertyInterface>();
        links = new HashMap<String, List<LinkInterface>>();
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public PropertyInterface getProperty(String name) {
        return properties.get(name);
    }

    @Override
    public Set<String> getProperties() {
        return properties.keySet();
    }

    public String toString() {
        return getType() + '@' + Integer.toHexString(hashCode());
    }

    public void setProperty(String name, PropertyInterface value)
    {
        PropertyInterface prop = properties.get(name);
        if(prop == null) {
            properties.put(name, value);
        } else if(prop instanceof ArrayProperty) {
            ((ArrayProperty)prop).add(value);
        } else {
            ArrayProperty newProp = new ArrayProperty(prop);
            newProp.add(value);
            properties.put(name, newProp);
        }
    }

    public void addLink(Link link) {
        if(!links.containsKey(link.getRelation())) {
            links.put(link.getRelation(), new LinkedList<LinkInterface>());
        }

        links.get(link.getRelation()).add(link);
    }
}
