/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db.sqlite;

import Client.EEGSample;
import db.interfaces.EEGManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author andre
 */
public class EEGManagerImpl implements EEGManager {

    private final Connection c;
    String sql;
    PreparedStatement p;

    public EEGManagerImpl(Connection c) {
        this.c = c;
    }

    @Override
    public ArrayList<EEGSample> getEEGs(int id) {
        ArrayList<EEGSample> newSamples = new ArrayList<>();
        ArrayList<Integer> eeg1;
        ArrayList<Integer> elg1;
        EEGSample newSample;
        try {
            sql = "SELECT * FROM EEGSample WHERE patient_id = ?";
            p = c.prepareStatement(sql);
            p.setInt(1, id);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                int newId = rs.getInt("id");
                String eeg = rs.getString("eeg");
                eeg1 = ConvertToArray(eeg);
                String elg = rs.getString("elg");
                elg1 = ConvertToArray(elg);
                String dos = rs.getString("dos");
                String observations = rs.getString("observations");
                int patient_id = rs.getInt("patient_id");
                newSample = new EEGSample(newId, eeg1, elg1, dos, observations, patient_id);
                newSamples.add(newSample);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newSamples;
    }

    @Override
    public void newEEGSample(EEGSample eegSample) {
        try {
            sql = "INSERT INTO EEGSample(eeg, elg, dos, observations, patient_id)" + "VALUES(?,?,?,?,?);";
            p = c.prepareStatement(sql);
            p.setObject(1, eegSample.getEeg());
            p.setObject(2, eegSample.getElg());
            p.setString(3, eegSample.getDos());
            p.setString(4, eegSample.getObservations());
            p.setInt(5, eegSample.getPatient_id());
            p.executeUpdate();
            p.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private ArrayList<Integer> ConvertToArray(String sample) {
        String replace = sample.replace("[", "");
        String replace1 = replace.replace("]", "");
        String replace2 = replace1.replace(" ", "");
        ArrayList<String> myList = new ArrayList<>(Arrays.asList(replace2.split(",")));
        ArrayList<Integer> result1 = new ArrayList<>();
        for (String stringValue : myList) {
            try {
                //Convert String to Integer, and store it into integer array list.
                result1.add(Integer.parseInt(stringValue));
            } catch (NumberFormatException nfe) {
                System.out.println("Could not parse " + nfe);
            }
        }

        return result1;
    }

}
