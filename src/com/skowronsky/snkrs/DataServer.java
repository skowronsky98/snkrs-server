package com.skowronsky.snkrs;

import com.skowronsky.snkrs.data.Storage;
import com.skowronsky.snkrs.db.DataBase;
import com.skowronsky.snkrs.model.User;
import com.skowronsky.snkrs.server.Capitalizer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.concurrent.Executors;

public class DataServer {

    final static int portNumber = 59895;
    static InetAddress inetAddress;

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        DataBase dataBase =  new DataBase();
        Storage storage = new Storage(dataBase);


        try {
            inetAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        try (var serverSocket = new ServerSocket(portNumber)){
            System.out.println("Server IP: " + inetAddress.getHostAddress());
            System.out.println("Data server is running...");

            var pool = Executors.newFixedThreadPool(20);

            while (true)
                pool.execute(new Capitalizer(serverSocket.accept(),storage));

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            dataBase.close();
        }
    }




}
