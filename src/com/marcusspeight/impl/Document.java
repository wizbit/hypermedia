package com.marcusspeight.impl;

import com.marcusspeight.*;

import java.util.*;

/**
 * Created by marcusspeight on 13/06/2014.
 */
public class Document implements DocumentInterface {

    private List<ObjectInterface> objects;
    private Map<String, SimpleObject> localObjects;
    private Map<String, List<LinkInterface>> links;

    public Document() {
        objects = new ArrayList<ObjectInterface>();
        localObjects = new HashMap<String, SimpleObject>();
        links = new HashMap<String, List<LinkInterface>>();
    }

    @Override
    public List<ObjectInterface> getObjects() {
        return objects;
    }

    @Override
    public Map<String, List<FormInterface>> getForms() {
        return null;
    }

    @Override
    public Map<String, List<LinkInterface>> getLinks() {
        return links;
    }

    @Override
    public List<LinkInterface> getLinks(String relation) {
        return links.get(relation);
    }

    public void addObject(SimpleObject object) {
        objects.add(object);
    }

    public void addLink(Link link) {
        if(!links.containsKey(link.getRelation())) {
            links.put(link.getRelation(), new LinkedList<LinkInterface>());
        }

        links.get(link.getRelation()).add(link);

    }

    public void addLocalObject(String id, SimpleObject object) {
        localObjects.put(id, object);
    }

    public SimpleObject getLocalObject(String id) {
        return localObjects.get(id);
    }
}
