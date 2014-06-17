package com.marcusspeight;

import java.net.URL;

/**
 * Created by marcusspeight on 13/06/2014.
 */
public interface LinkInterface {

    /**
     * Get the link relation.
     * @return The relation
     */
    public String getRelation();

    /**
     * Get the url this link points to.
     * @return The url
     */
    public URL getUrl();

    /**
     * Retrieve the link
     * @return The document at the link
     */
    public DocumentInterface get();
}
