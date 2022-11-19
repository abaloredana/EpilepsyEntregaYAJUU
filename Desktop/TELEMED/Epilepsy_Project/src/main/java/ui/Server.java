/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import Client.EEGSample;
import Client.Patient;
import db.interfaces.*;
import db.sqlite.DBManagerImpl;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author andre
 */
public class Server {

    private static DBManager dbManager;
    private static PatientManager patientManager;
    private static EEGManager EEGManager;
    private static InputStream inputStream = null;
    private static OutputStream outputStream = null;
    private static ObjectInputStream objectInputStream = null;
    private static ObjectOutputStream objectOutputStream = null;
    private static Socket socket;
    private static ServerSocket serverSocket;
    private static Patient patient;
    private static EEGSample eegSample;
    private static ArrayList<EEGSample> eegs;

    public static void main(String[] args) throws Exception {

        dbManager = new DBManagerImpl();
        dbManager.connect();
        patientManager = dbManager.getPatient();
        EEGManager = dbManager.getEEGSample();

        dbManager.createTables();

        serverSocket = new ServerSocket(9000);

        try {
            while (true) {
                socket = serverSocket.accept();
                System.out.println("Connection client created");

                try {
                    inputStream = socket.getInputStream();
                    System.out.println("Connection from the direction " + socket.getInetAddress());
                } catch (IOException ex) {
                    System.out.println("It was not possible to start the server. Fatal error.");
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    System.exit(-1);
                }

                objectInputStream = new ObjectInputStream(inputStream);
                outputStream = socket.getOutputStream();
                objectOutputStream = new ObjectOutputStream(outputStream);

                int op = inputStream.read();

                if (op == 1) {
                    newUserS();
                } else {
                    oldUserS();
                }

                op = inputStream.read();

                switch (op) {
                    case 1:
                        ReceiveSample();
                        break;
                    case 2:
                        sendSamples();
                        break;
                    default:
                        break;
                }

            }
        } finally {
            releaseResources(objectInputStream, objectOutputStream, socket, serverSocket);
        }

    }

    private static void releaseResources(ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream, Socket socket, ServerSocket serverSocket) {
        try {
            objectInputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            objectOutputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void newUserS() {
        try {
            patient = (Patient) (objectInputStream.readObject());
            patientManager.newPatient(patient);
            patient = patientManager.getPatient(patient.getUsername(), patient.getPassword());
            objectOutputStream.writeObject(patient);
        } catch (EOFException ex) {
            System.out.println("All data have been correctly read.");
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Unable to read from the client.");
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void oldUserS() {
        try {
            patient = (Patient) (objectInputStream.readObject());
            patient = patientManager.getPatient(patient.getUsername(), patient.getPassword());
            objectOutputStream.writeObject(patient);
        } catch (EOFException ex) {
            System.out.println("All data have been correctly read.");
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Unable to read from the client.");
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void ReceiveSample() {

        try {
            eegSample = (EEGSample) (objectInputStream.readObject());
            System.out.println(eegSample.getEeg().getClass());
            EEGManager.newEEGSample(eegSample);
        } catch (EOFException ex) {
            System.out.println("All data have been correctly read.");
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Unable to read from the client.");
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void sendSamples() {
        try {
            int patient_id = inputStream.read();
            eegs = EEGManager.getEEGs(patient_id);
            outputStream.write(eegs.size());
            for (int i = 0; i < eegs.size(); i++) {
                objectOutputStream.writeObject(eegs.get(i));
            }

        } catch (EOFException ex) {
            System.out.println("All data have been correctly read.");
        } catch (IOException ex) {
            System.out.println("Unable to read from the client.");
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
