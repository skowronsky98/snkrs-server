package com.skowronsky.snkrs.model;

import java.io.Serializable;

public class Shoes implements Serializable {
    private int id;
    private String brandName;
    private String modelName;
    private double factor;
    private String image;

    public Shoes(
            int id, String brandName,
            String modelName,
            double factor,
            String image) {
        
        this.id = id;
        this.brandName = brandName;
        this.modelName = modelName;
        this.factor = factor;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getBrandName() {
        return brandName;
    }

    public String getModelName() {
        return modelName;
    }

    public double getFactor() {
        return factor;
    }

    public String getImage() {
        return image;
    }
}
