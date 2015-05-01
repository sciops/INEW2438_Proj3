/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hello;

/**
 *
 * @author Stephen R. Williams
 * simple website class per requirements
 */
public class Website {

    private final long id;
    private final String category;
    private final String url;

    public Website(long id, String category, String url) {
        this.id = id;
        this.category = category;
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return ""+id + "," + category + "," + url;
    }
    
    
}
