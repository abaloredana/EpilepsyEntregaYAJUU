/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import Client.EEGSample;
import Client.Patient;
import db.interfaces.DBManager;
import db.interfaces.EEGManager;
import db.interfaces.PatientManager;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
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
public class ServerThreads implements Runnable {

    private static Socket socket;
    private static DBManager dbManager;
    private static PatientManager patientManager;
    private static EEGManager EEGManager;

    public ServerThreads(Socket socket, DBManager dbManager) {
        this.socket = socket;
        this.dbManager = dbManager;
    }

    @Override
    public void run() {
        patientManager = dbManager.getPatient();
        EEGManager = dbManager.getEEGSample();
        InputStream inputStream;
        OutputStream outputStream;
        ObjectInputStream objectInputStream = null;
        ObjectOutputStream objectOutputStream = null;
        Patient patient;
        EEGSample eegSample;
        ArrayList<EEGSample> eegs;

        try {
            inputStream = socket.getInputStream();
            System.out.println("Connection from the direction " + socket.getInetAddress());
            objectInputStream = new ObjectInputStream(inputStream);
            outputStream = socket.getOutputStream();
            objectOutputStream = new ObjectOutputStream(outputStream);
            int op = inputStream.read();
            if (op == 1) {
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
            } else {
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

            op = inputStream.read();

            switch (op) {
                case 1:
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
                break;
                case 2:
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
                break;
                default:
                    break;
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerThreads.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            releaseResources(objectInputStream, objectOutputStream, socket);
        }
    }

    private static void releaseResources(ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream, Socket socket) {
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
    }
}
