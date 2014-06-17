package com.marcusspeight;

import java.util.List;
import java.util.Map;

/**
 * Created by marcusspeight on 13/06/2014.
 */
public interface DocumentInterface {

    public List<ObjectInterface> getObjects();

    public Map<String, List<FormInterface>> getForms();

    public Map<String, List<LinkInterface>> getLinks();

    public List<LinkInterface> getLinks(String relation);
}
