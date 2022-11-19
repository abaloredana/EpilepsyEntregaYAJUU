/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db.sqlite;

import Client.Patient;
import db.interfaces.PatientManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author andre
 */
public class PatientManagerImpl implements PatientManager {

    private final Connection c;
    String sql;
    PreparedStatement p;
    ResultSet result;

    public PatientManagerImpl(Connection c) {
        this.c = c;
    }

    @Override
    public void newPatient(Patient patient) {
        try {
            sql = "INSERT INTO patient(name, lastname, gender, email, phone, dob, username, password, MAC)" + "VALUES(?,?,?,?,?,?,?,?,?);";
            p = c.prepareStatement(sql);
            p.setString(1, patient.getName());
            p.setString(2, patient.getLastname());
            p.setString(3, patient.getGender());
            p.setString(4, patient.getEmail());
            p.setString(5, patient.getPhone());
            p.setString(6, patient.getDob());
            p.setString(7, patient.getUsername());
            p.setString(8, patient.getPassword());
            p.setString(9, patient.getMAC());
            p.executeUpdate();
            p.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Patient getPatient(String username, String password) {
        sql = "SELECT * FROM patient WHERE username = ? AND password = ?";
        PreparedStatement p;
        try {
            p = c.prepareStatement(sql);
            p.setString(1, username);
            p.setString(2, password);
            result = p.executeQuery();
            int id = result.getInt("id");
            String dob = result.getString("dob");
            String gender = result.getString("gender");
            String name = result.getString("name");
            String lastname = result.getString("lastname");
            String email = result.getString("email");
            String phone = result.getString("phone");
            String MAC = result.getString("MAC");
            Patient patient = new Patient(id, name, lastname, gender, email, phone, dob, username, password, MAC);
            return patient;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }
}
