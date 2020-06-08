package com.skowronsky.snkrs;

import com.skowronsky.snkrs.data.Storage;
import com.skowronsky.snkrs.db.DataBase;
import com.skowronsky.snkrs.server.Capitalizer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.concurrent.Executors;

public class DataServer {

    final static int portNumber = 59896;
    static InetAddress inetAddress;
    public static final Logger LOGGER = LogManager.getLogger("HelloWorld");


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

            LOGGER.info("Server IP: " + inetAddress.getHostAddress());
            LOGGER.info("Data server is running...");


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
