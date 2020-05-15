package com.skowronsky.snkrs.model;

import java.io.Serializable;

public class Brand implements Serializable {
    private int id;
    private String name;
    private String image;
    public Brand(int id, String name, String image){
        this.id = id;
        this.name = name;
        this.image = image;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getImage() {
        return image;
    }
}
