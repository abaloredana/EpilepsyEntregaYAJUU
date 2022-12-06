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
        EEGSample newSample;
        try {
            sql = "SELECT * FROM EEGSample WHERE patient_id = ?";
            p = c.prepareStatement(sql);
            p.setInt(1, id);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                int newId = rs.getInt("id");
                String path = rs.getString("path");
                String dos = rs.getString("dos");
                String observations = rs.getString("observations");
                int patient_id = rs.getInt("patient_id");
                newSample = new EEGSample(newId,null, null, dos, observations, patient_id);
                newSample.setPath(path);
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
            sql = "INSERT INTO EEGSample(path , dos, observations, patient_id)" + "VALUES(?,?,?,?);";
            p = c.prepareStatement(sql);
            p.setObject(1, eegSample.getPath());
            p.setString(2, eegSample.getDos());
            p.setString(3, eegSample.getObservations());
            p.setInt(4, eegSample.getPatient_id());
            p.executeUpdate();
            p.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
