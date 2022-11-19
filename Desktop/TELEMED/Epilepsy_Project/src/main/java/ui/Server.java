/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import db.interfaces.*;
import db.sqlite.DBManagerImpl;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author andre
 */
public class Server {

    private static DBManager dbManager;
    private static Socket socket;
    private static ServerSocket serverSocket;

    public static void main(String[] args) throws Exception {

        dbManager = new DBManagerImpl();
        dbManager.connect();
        dbManager.createTables();

        serverSocket = new ServerSocket(9000);

        try {
            while (true) {
                socket = serverSocket.accept();
                System.out.println("Connection client created");
                new Thread(new ServerThreads(socket, dbManager)).start();
            }
        } finally {
            releaseResources(serverSocket);
        }

    }

    private static void releaseResources(ServerSocket serverSocket) {
        try {
            serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
