package com.skowronsky.snkrs.model;

import java.io.Serializable;

public class BaseShoes implements Serializable {
    int id_shoes;
    double size;
    double hiddenSize;

    public BaseShoes(int id_shoes, double size, double hiddenSize){
        this.id_shoes = id_shoes;
        this.size = size;
        this.hiddenSize = hiddenSize;
    }

    public int getIdShoes() {
        return id_shoes;
    }

    public double getSize() {
        return size;
    }

    public double getHiddenSize() {
        return hiddenSize;
    }
}
