/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package GUI;

import Client.SocketOb;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author andre
 */
public class DocMenu {

    
    private static DocLogIn docLogIn;

    public static void main(String[] args) throws Exception {
        SocketOb db;
        db = new SocketOb();
        try {
            db.setSocket(new Socket("localhost", 9000));
            db.setOutputStream(db.getSocket().getOutputStream());
            db.setObjectOutputStream(new ObjectOutputStream(db.getOutputStream()));
            db.setInputStream(db.getSocket().getInputStream());
            db.setObjectInputStream(new ObjectInputStream(db.getInputStream()));
        } catch (IOException ex) {
            System.out.println("It was not possible to connect to the server.");
            System.exit(-1);
            Logger.getLogger(DocLogIn.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        db.getOutputStream().write(2);//Option to choose which thread is going to happen
        docLogIn = new DocLogIn(db);
        docLogIn.setDocLogIn(docLogIn);
        docLogIn.setVisible(true);
    }
}
