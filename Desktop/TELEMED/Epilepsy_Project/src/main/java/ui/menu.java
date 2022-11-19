/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import BITalino.BitalinoDemo;
import Client.EEGSample;
import Client.Patient;
import GUI.Welcome;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author andre
 */
public class menu {

    static BufferedReader reader;

    static int option;

    private static String username;
    private static String password;
    private static int patient_id;
    private static String MAC;
    private static EEGSample eegSample;
    private static Patient patient;
    private static ArrayList<EEGSample> eegs;
    private static Welcome welcome;

    private static OutputStream outputStream = null;
    private static InputStream inputStream = null;
    private static ObjectOutputStream objectOutputStream = null;
    private static ObjectInputStream objectInputStream = null;
    private static Socket socket = null;
    private static BufferedReader bufferedReader;
    public static boolean v1 = true;

    public static void main(String[] args) throws Exception {
        option = 0;

        reader = new BufferedReader(new InputStreamReader(System.in));
        /*System.out.print("\nHello.\n\nYou are accesing your Bitalino device for Epilepsy monitoring.\n\n");
        option = reader.read();*/

        welcome = new Welcome();
        welcome.setVisible(v1);

        /*System.out.print("Username:");
        username = reader.readLine();
        System.out.print("Password:");
        password = reader.readLine();
        outputStream.write(option);*/
        connectToServer();
        
        password = reader.readLine();
        //outputStream.write(option);
        switch (option) {
            case 1:
                //patient = newUser();
                break;
            case 2:
                patient = requestPatient();
                break;
            default:
                System.out.print("Not a valid option");
                break;
            // SALIR DEL PROGRAMA
        }

        patient_id = patient.getId();
        MAC = patient.getMAC();

        System.out.print("\nIf you want to record reading press 1\n");
        System.out.print("If you want to view past readings press 2\n");
        System.out.print("If you want to exit press 3:");
        option = Integer.parseInt(reader.readLine());
        outputStream.write(option);

        switch (option) {
            case 1:
                recordEEG();
                break;
            case 2:
                eegs = requestEEGSamples();
                System.out.print(eegs);
                break;
            case 3:
                break;
            default:
                System.out.print("Not a valid option");
                break;
        }
    }

    public static Patient newUser() throws IOException, ClassNotFoundException {
        System.out.print("MAC address of your bitalino:");
        String MAC1 = reader.readLine();
        System.out.println("Enter patient info:");
        System.out.println("Name:");
        String name = reader.readLine();
        System.out.println("Last name:");
        String lastname = reader.readLine();
        System.out.println("Gender(M/F)");
        String gender = reader.readLine();
        System.out.println("Email:");
        String email = reader.readLine();
        System.out.println("Phone:");
        String phone = reader.readLine();
        System.out.println("Date of birth:");
        String dob = reader.readLine();
        Patient patient1 = new Patient(name, lastname, gender, email, phone, dob, username, password, MAC1);
        try {
            objectOutputStream.writeObject(patient1);
            patient1 = (Patient) (objectInputStream.readObject());
        } catch (IOException ex) {
            System.out.println("Unable to write the objects on the server.");
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        }
        return patient1;
    }

    private static void recordEEG() throws IOException {
        System.out.println("Today's date:");
        String dos = reader.readLine();
        System.out.println("Extra Simptoms or observations:(if you do not have any press X)");
        String observations = reader.readLine();
        System.out.println("Your EEG and ELG readings will start now:");
        BitalinoDemo bitalinoDemo = new BitalinoDemo();
        bitalinoDemo.recordSignal(MAC); ////////// ARREGLAR ESPECIFICACIONESSSSS
        ArrayList<Integer> eeg = bitalinoDemo.getList1();
        ArrayList<Integer> elg = bitalinoDemo.getList2();
        //ArrayList<Integer> eeg = new ArrayList<>(Arrays.asList(1, 3, 5, 7));
        //ArrayList<Integer> elg = new ArrayList<>(Arrays.asList(2, 4, 6, 8));

        eegSample = new EEGSample(eeg, elg, dos, observations, patient_id);
        try {
            objectOutputStream.writeObject(eegSample);
        } catch (IOException ex) {
            System.out.println("Unable to write the objects on the server.");
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static Patient requestPatient() throws ClassNotFoundException {
        Patient patient1 = new Patient(username, password);
        try {
            objectOutputStream.writeObject(patient1);
            patient1 = (Patient) (objectInputStream.readObject());
        } catch (IOException ex) {
            System.out.println("Unable to write the objects on the server.");
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        }
        return patient1;
    }

    private static ArrayList<EEGSample> requestEEGSamples() throws IOException, ClassNotFoundException {
        ArrayList<EEGSample> eegs1 = new ArrayList<EEGSample>();
        outputStream.write(patient_id);
        int eegs_size = inputStream.read();
        for (int i = 0; i < eegs_size; i++) {
            eegs1.add((EEGSample) objectInputStream.readObject());
        }
        return eegs1;
    }

    private static void connectToServer() {
        try {
            socket = new Socket("localhost", 9000);
            outputStream = socket.getOutputStream();
            objectOutputStream = new ObjectOutputStream(outputStream);
            inputStream = socket.getInputStream();
            objectInputStream = new ObjectInputStream(inputStream);
        } catch (IOException ex) {
            System.out.println("It was not possible to connect to the server.");
            System.exit(-1);
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
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
