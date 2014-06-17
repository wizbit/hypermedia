package com.marcusspeight;

import java.util.Set;

/**
 * Created by marcusspeight on 16/06/2014.
 */
public interface ObjectInterface extends PropertyInterface {

    public PropertyInterface getProperty(String name);

    public Set<String> getProperties();
}
