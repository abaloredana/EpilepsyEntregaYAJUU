/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import Client.*;
import db.interfaces.*;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author andre
 */
public class ServerThreads implements Runnable {

    private static String passwServ = "xd";
    public static Socket socket;
    private static DBManager dbManager;
    private static PatientManager patientManager;
    private static EEGManager EEGManager;
    public static InputStream inputStream;
    public static OutputStream outputStream;
    public static ObjectInputStream objectInputStream;
    public static ObjectOutputStream objectOutputStream;

    public ServerThreads(Socket socket, DBManager dbManager) {
        this.socket = socket;
        this.dbManager = dbManager;

    }

    @Override
    public void run() {
        patientManager = dbManager.getPatient();
        EEGManager = dbManager.getEEGSample();
        objectInputStream = null;
        objectOutputStream = null;
        Patient patient;
        EEGSample eegSample;
        ArrayList<EEGSample> eegs = null;
        ArrayList<Patient> pats = null;
        try {
            inputStream = socket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            System.out.println("Connection from the direction " + socket.getInetAddress());
            objectInputStream = new ObjectInputStream(inputStream);
            outputStream = socket.getOutputStream();
            objectOutputStream = new ObjectOutputStream(outputStream);
            int gb = 1;
            int chooseThread = inputStream.read();
            System.out.println(chooseThread);
            if (chooseThread == 1) {
                while (gb == 1) {
                    int op = inputStream.read();
                    switch (op) {
                        case 1:
                            String username1 = bufferedReader.readLine();
                            boolean usernameExists = patientManager.UsernameExists(username1);
                            objectOutputStream.writeObject(usernameExists);
                            if (!usernameExists) {
                                gb = inputStream.read();
                                if (gb == 0) {
                                    try {
                                        patient = (Patient) (objectInputStream.readObject());
                                        patientManager.newPatient(patient);
                                        patient = patientManager.getPatient(patient.getUsername(), patient.getPassword());
                                        objectOutputStream.writeObject(patient);
                                    } catch (EOFException ex) {
                                        System.out.println("All data have been correctly read.");
                                    } catch (ClassNotFoundException ex) {
                                        Logger.getLogger(ServerThreads.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }
                            break;
                        case 2:
                            String username2 = bufferedReader.readLine();
                            System.out.println(username2);
                            boolean usernameExists1 = patientManager.UsernameExists(username2);
                            objectOutputStream.writeObject(usernameExists1);
                            if (usernameExists1) {
                                String username3 = bufferedReader.readLine();
                                String password1 = bufferedReader.readLine();
                                boolean usernameAndPasswordMatch = patientManager.UsernameAndPasswordMatch(username3, password1);
                                objectOutputStream.writeObject(usernameAndPasswordMatch);
                                if (usernameAndPasswordMatch) {
                                    gb = inputStream.read();
                                    if (gb == 0) {
                                        try {
                                            String username = bufferedReader.readLine();
                                            String password = bufferedReader.readLine();
                                            patient = patientManager.getPatient(username, password);
                                            objectOutputStream.writeObject(patient);
                                        } catch (EOFException ex) {
                                            System.out.println("All data have been correctly read.");
                                        }
                                    }
                                }
                            }
                            break;
                    }
                }

                int gb2 = 1;
                int gb3;
                File file;
                while (gb2 == 1) {
                    int op2 = inputStream.read();
                    switch (op2) {
                        case 1:
                            gb3 = 1;
                            while (gb3 == 1) {
                                gb3 = 0;
                                gb2 = inputStream.read();
                                if (gb2 == 0) {
                                    try {
                                        eegSample = (EEGSample) (objectInputStream.readObject());
                                        if (eegSample != null) {
                                            String pathAux = (String) (eegSample.getPatient_id() + "__" + eegSample.getDos());
                                            String path = "C:/sqlite/db/" + pathAux + ".txt";
                                            eegSample.setPath(path);
                                            file = new File(path);
                                            file.createNewFile();
                                            FileWriter fw = new FileWriter(file);
                                            for (int i = 0; i < eegSample.getEeg().size(); i++) {
                                                fw.write(Integer.toString(eegSample.getEeg().get(i)));
                                            }
                                            fw.write("_");
                                            for (int i = 0; i < eegSample.getElg().size(); i++) {
                                                fw.write(Integer.toString(eegSample.getElg().get(i)));
                                            }
                                            fw.close();
                                            EEGManager.newEEGSample(eegSample);
                                        }
                                    } catch (EOFException ex) {
                                        System.out.println("All data have been correctly read.");
                                    } catch (ClassNotFoundException ex) {
                                        Logger.getLogger(ServerThreads.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    gb3 = inputStream.read();
                                }
                            }
                            break;
                        case 2:
                            int patient_id = inputStream.read();
                            eegs = EEGManager.getEEGs(patient_id);
                            outputStream.write(eegs.size());
                            for (int i = 0; i < eegs.size(); i++) {
                                objectOutputStream.writeObject(eegs.get(i));
                            }
                            gb3 = 1;
                            while (gb3 == 1) {
                                gb3 = 0;
                                gb2 = inputStream.read();
                                if (gb2 == 0) {
                                    int sampleId = inputStream.read();
                                    int position = -1;
                                    for (int i = 0; i < eegs.size(); i++) {
                                        if (eegs.get(i).getId() == sampleId) {
                                            position = i;
                                        }
                                    }
                                    if (position != -1) {
                                        file = new File(eegs.get(position).getPath());
                                        Scanner myReader = new Scanner(file);
                                        String data = myReader.nextLine();
                                        String[] parts = data.split("_");
                                        myReader.close();
                                        ArrayList<Integer> eeg = ConvertToArray(parts[0]);
                                        ArrayList<Integer> elg = ConvertToArray(parts[1]);
                                        objectOutputStream.writeObject(eeg);
                                        objectOutputStream.writeObject(elg);
                                        gb3 = inputStream.read();
                                    } else {
                                        gb3 = 1;
                                    }
                                }
                            }
                            break;
                        default:
                            break;
                    }
                }
            } else {// codigo del doccc
                String passwDoc = "";
                while (!passwDoc.equals(passwServ)) {
                    passwDoc = bufferedReader.readLine();
                    if (passwDoc.equals(passwServ)) {
                        outputStream.write(1);
                        pats = patientManager.getPatients();
                        objectOutputStream.writeObject(pats);
                        int gbD1 = 1;
                        while (gbD1 == 1) {
                            int patient_id2 = inputStream.read();
                            eegs = EEGManager.getEEGs(patient_id2);
                            objectOutputStream.writeObject(eegs);
                            int gbD2 = 1;
                            while (gbD2 == 1) {
                                gbD1 = inputStream.read();
                                System.out.println("gbD1");
                                System.out.println(gbD1);
                                if (gbD1 == 0) {
                                    int sampleId = inputStream.read();
                                    int position = -1;
                                    for (int i = 0; i < eegs.size(); i++) {
                                        if (eegs.get(i).getId() == sampleId) {
                                            position = i;
                                        }
                                    }
                                    if (position != -1) {
                                        File file = new File(eegs.get(position).getPath());
                                        Scanner myReader = new Scanner(file);
                                        String data = myReader.nextLine();
                                        String[] parts = data.split("_");
                                        myReader.close();
                                        ArrayList<Integer> eeg = ConvertToArray(parts[0]);
                                        ArrayList<Integer> elg = ConvertToArray(parts[1]);
                                        objectOutputStream.writeObject(eeg);
                                        objectOutputStream.writeObject(elg);
                                    }
                                    gbD2 = inputStream.read();
                                    System.out.println("gbD2");
                                    System.out.println(gbD2);
                                } else {
                                    gbD2 = 0;
                                }
                            }
                        }
                    } else {
                        outputStream.write(0);
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println("Client disconnected");
        } finally {
            try {
                objectInputStream.close();
                objectOutputStream.close();
                outputStream.close();
                inputStream.close();
                socket.close();
            } catch (IOException ex) {
                Logger.getLogger(ServerThreads.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NullPointerException e) {
            }
        }
    }

    private ArrayList<Integer> ConvertToArray(String sample) {
        String replace = sample.replace("[", "");
        String replace1 = replace.replace("]", "");
        String replace2 = replace1.replace(" ", "");
        ArrayList<String> myList = new ArrayList<>(Arrays.asList(replace2.split("")));
        ArrayList<Integer> result1 = new ArrayList<>();
        for (String stringValue : myList) {
            try {
                result1.add(Integer.parseInt(stringValue));
            } catch (NumberFormatException nfe) {
                System.out.println("Could not parse " + nfe);
            }
        }
        return result1;
    }
}
