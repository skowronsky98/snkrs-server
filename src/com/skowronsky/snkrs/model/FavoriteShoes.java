package com.skowronsky.snkrs.model;

import java.io.Serializable;

public class FavoriteShoes implements Serializable {
    int idFavorite;
    int idBase;
    double size;

    public FavoriteShoes(int idFavorite, int idBase, double size){
        this.idFavorite = idFavorite;
        this.idBase = idBase;
        this.size = size;
    }

    public int getIdShoes() {
        return idFavorite;
    }
    public int getIdBase(){
        return idBase;
    }
    public double getSize() {
        return size;
    }
}


