package com.marcusspeight.impl;

import com.marcusspeight.PropertyInterface;

import java.util.Map;

/**
 * Created by marcusspeight on 13/06/2014.
 */
public class SimpleProperty implements PropertyInterface {

    private String value;

    public SimpleProperty(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }

    @Override
    public String getType() {
        return "string";
    }

}
