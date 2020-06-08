package com.skowronsky.snkrs.db;

import com.skowronsky.snkrs.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBase {

    private Connection connect = null;
    private Statement statement = null;
    private Statement statmentBaseShoes = null;

    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private ResultSet resultSetBase= null;
    private String user = "client", password = "zoiesuflb0hb5t6f";
    private String url = "jdbc:mysql://snkrs-do-user-7351749-0.a.db.ondigitalocean.com:25060/sneakers" +
            "?verifyServerCertificate=false"+
            "&useSSL=true"+
            "&requireSSL=true";

    public DataBase() throws SQLException, ClassNotFoundException {
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Setup the connection with the DBDa
            connect = DriverManager
                    .getConnection(url,user,password);

        } catch (Exception e) {
            throw e;
        } finally {
            //close();
        }
    }

    public void getBrands(List<Brand> brandList) throws SQLException {
        // Statements allow to issue SQL queries to the database
        statement = connect.createStatement();
        // Result set get the result of the SQL query
        resultSet = statement
                .executeQuery("select * from brands");

        while (resultSet.next()){
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            String image = resultSet.getString(3);
            brandList.add(id-1, new Brand(id,name,image));
        }
    }

    public void getShoes(List<Shoes> shoesList) throws SQLException {
        // Statements allow to issue SQL queries to the database
        statement = connect.createStatement();
        // Result set get the result of the SQL query
        resultSet = statement
            .executeQuery("select s.id_shoes, b.name, s.model_name, s.factor, s.image from shoes s join brands b on s.id_brand = b.id_brand;");

        while (resultSet.next()){
            int id = resultSet.getInt(1);
            String brandName = resultSet.getString(2);
            String modelName = resultSet.getString(3);
            double factor = resultSet.getDouble(4);
            String image = resultSet.getString(5);

            shoesList.add(id-1, new Shoes(id,brandName,modelName,factor,image));
        }
    }

    public void getUsers(List<User> userList) throws SQLException {
        statement = connect.createStatement();
        statmentBaseShoes = connect.createStatement();

        resultSet = statement
                .executeQuery("select id_user,email,password,name,photo from user;");

        while (resultSet.next()){
            int id = resultSet.getInt(1);
            String email = resultSet.getString(2);
            String password = resultSet.getString(3);
            String name = resultSet.getString(4);
            String photo = resultSet.getString(5);
            List<BaseShoes> baseShoesList = new ArrayList<>();
            List<FavoriteShoes> favoriteShoesList = new ArrayList<>();
//            System.out.println(String.format("id: %d name: %s",id,name));
            resultSetBase = statmentBaseShoes
                    .executeQuery(String.format("select id_shoes,size,hidden_size from base_shoes where id_user = %d;",id));
            while (resultSetBase.next()){
                int id_shoes = resultSetBase.getInt(1);
                double size = resultSetBase.getDouble(2);
                double hiddenSize = resultSetBase.getDouble(3);
//                System.out.println(String.format("id_shoes: %d ",id_shoes));
                baseShoesList.add(new BaseShoes(id_shoes,size,hiddenSize));
            }
            resultSetBase = statmentBaseShoes.executeQuery(String.format("select id_shoes, id_base, size from favorite_shoes where id_user = %d",id));
            while (resultSetBase.next()){
                int id_shoes = resultSetBase.getInt(1);
                int id_base = resultSetBase.getInt(2);
                double size = resultSetBase.getDouble(3);
//                System.out.println(String.format("id_shoes: %d ",id_shoes));
                favoriteShoesList.add(new FavoriteShoes(id_shoes,id_base,size));
            }

            userList.add(new User(email,name,photo,password,baseShoesList,favoriteShoesList));
        }


    }

    public void insertUser(String email, String password, String name) throws SQLException {
        statement = connect.createStatement();
        statement.executeUpdate(String.format("insert into user(email,password,name) values ('%s','%s','%s');",email,password,name));
    }

    public void updateUser(String email, String name, String password) throws SQLException {
        statement = connect.createStatement();
        statement.executeUpdate(String.format("update user set name = '%s', password = '%s' where email like('%s');",
                name,password,email));
    }

    public void updateFavorites(String email, List<FavoriteShoes> favoriteShoesList) throws SQLException {
        int id = 0;
        statement = connect.createStatement();
        resultSet = statement.executeQuery(String.format("select id_user from user where email like('%s')",email));

        while (resultSet.next())
            id = resultSet.getInt(1);


        if(id > 0){
            statement = connect.createStatement();
            statement.executeUpdate(String.format("delete from favorite_shoes where id_user = %d;",id));

            for (FavoriteShoes favoriteShoes : favoriteShoesList) {
                statement.execute(String.format("insert into favorite_shoes(id_user,id_shoes,size) values(%d,%d,%f)", id, favoriteShoes.getIdShoes(), favoriteShoes.getSize()));
            }

        }

    }

    public void updateBase(String email, List<BaseShoes> baseShoesList) throws SQLException {
        int id = 0;
        statement = connect.createStatement();
        resultSet = statement.executeQuery(String.format("select id_user from user where email like('%s')",email));

        while (resultSet.next())
            id = resultSet.getInt(1);


        if(id > 0){
            statement = connect.createStatement();
            statement.executeUpdate(String.format("delete from base_shoes where id_user = %d;",id));

            for (BaseShoes baseShoes : baseShoesList) {
                statement.execute(String.format("insert into base_shoes(id_user,id_shoes,size,hidden_size)" +
                                " values(%d,%d,%f,%f)",
                        id,
                        baseShoes.getIdShoes(),
                        baseShoes.getSize(),
                        baseShoes.getHiddenSize()));
            }

        }

    }

    public void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if(resultSetBase != null)
                resultSetBase.close();

            if (statement != null) {
                statement.close();
            }

            if(statmentBaseShoes != null)
                statmentBaseShoes.close();

            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {

        }
    }

}
