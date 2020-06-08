package com.skowronsky.snkrs.model;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    private String email;
    private String name;
    private String photo;
    private String password;
    private List<BaseShoes> baseShoesList;
    private List<FavoriteShoes> favoriteShoesList;

    public User(String email,
                String name,
                String photo,
                String password,
                List<BaseShoes> baseShoesList,
                List<FavoriteShoes>favoriteShoesList){

        this.email = email;
        this.name = name;
        this.photo = photo;
        this.password = password;
        this.baseShoesList = baseShoesList;
        this.favoriteShoesList = favoriteShoesList;
    }

    public User(String email,
                String name,
                String password,
                String photo){

        this.email = email;
        this.password = password;
        this.name = name;
        this.photo = photo;
    }


    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword(){
        return password;
    }

    public String getPhoto() {
        return photo;
    }

    public boolean checkPassword(String password) {
        if(this.password.equals(password))
            return true;
        return false;
    }

    public void setBaseShoesList(List<BaseShoes> baseShoesList) {
        this.baseShoesList = baseShoesList;
    }

    public List<BaseShoes> getBaseShoesList() {
        return baseShoesList;
    }

    public void setFavoriteShoesList(List<FavoriteShoes> favoriteShoesList) {
        this.favoriteShoesList = favoriteShoesList;
    }

    public List<FavoriteShoes> getFavoriteShoesList() {
        return favoriteShoesList;
    }
}
