package com.skowronsky.snkrs.data;

import com.skowronsky.snkrs.db.DataBase;
import com.skowronsky.snkrs.model.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private List<Brand> brandList = new ArrayList<>();
    private List<Shoes> shoesList = new ArrayList<>();
    private List<User> userList = new ArrayList<>();
    private DataBase dataBase;

    public Storage(DataBase dataBase) throws SQLException {
        this.dataBase = dataBase;
        dataBase.getBrands(this.brandList);
        dataBase.getShoes(this.shoesList);
        dataBase.getUsers(this.userList);

    }

    public void printFavorite(){
        for (var item :
                userList) {
            System.out.println("\tUser:"+item.getEmail());
            for (var i :
                    item.getFavoriteShoesList()) {
                System.out.println(i.getIdShoes());
                System.out.println(i.getSize());
            }
        }

    }

    public List<Brand> getBrandList() {
        return brandList;
    }
    public List<Shoes> getShoesList() {
        return shoesList;
    }

    public User getUser(String email, String password){
        for (var item :
                userList) {
            if(item.getEmail().equals(email)){
                if (item.checkPassword(password))
                    return item;
            }
        }
        return null;
    }

    public User getUser(String email){
        for (var item :
                userList) {
            if(item.getEmail().equals(email))
                return item;

        }
        return null;
    }

    public void updateUser(String email, String name, String password){
        User tmp = null;
        for (var item :
                userList) {
            if (item.getEmail().equals(email)){
                tmp = item;
                userList.remove(item);
                userList.add(new User(email,name,"photo",password,tmp.getBaseShoesList(),tmp.getFavoriteShoesList()));

                try {
                    dataBase.updateUser(email,name,password);
                } catch (SQLException throwables) {
                    System.out.println("Error update db");
                }
            }
        }
    }

    public boolean checkUserData(String email){
        for (var item :
                userList) {
            if (item.getEmail().equals(email))
                return false;
        }
            return true;
    }
    public void insertUser(User user){
        try {
            dataBase.insertUser(user.getEmail(),user.getPassword(),user.getName());
            userList.add(user);
            for (var item :
                    userList) {
                System.out.println(item.getEmail());
            }
        } catch (SQLException throwables) {
        }
    }

    public void updateFavorites(String email, List<FavoriteShoes> favoriteShoesList){
        User user = getUser(email);
        user.setFavoriteShoesList(favoriteShoesList);

        try {
            dataBase.updateFavorites(email,favoriteShoesList);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    public void updateBase(String email, List<BaseShoes> baseShoesList){
        User user = getUser(email);
        user.setBaseShoesList(baseShoesList);

        try {
            dataBase.updateBase(email,baseShoesList);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
