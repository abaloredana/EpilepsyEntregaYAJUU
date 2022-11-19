/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.interfaces.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBManagerImpl implements DBManager {

    private Connection c;
    private PatientManager patient;
    private EEGManager eegSample;

    public DBManagerImpl() {
        super();
    }

    @Override
    public void connect() {
        try {
            this.c = DriverManager.getConnection("jdbc:sqlite:C:/sqlite/db/dbProject.db");
            c.createStatement().execute("PRAGMA foreign_keys=ON");
            patient = new PatientManagerImpl(c);
            eegSample = new EEGManagerImpl(c);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } 
    }

    @Override
    public void disconnect() {
        try {
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createTables() {
        Statement statement1;
        try {
            statement1 = c.createStatement();
            String table1 = "CREATE TABLE IF NOT EXISTS patient " + "(id     INTEGER  PRIMARY KEY AUTOINCREMENT,"
                    + " name  TEXT   NOT NULL, " + " lastname  TEXT   NOT NULL, " + " email   TEXT NOT NULL, " 
                    + " username  TEXT   NOT NULL, " + " password  TEXT   NOT NULL, " + " gender TEXT CHECK (gender = 'M' OR gender = 'F'), " 
                    + " dob TEXT NOT NULL, "+ " mac TEXT NOT NULL, " + " phone TEXT)";
            statement1.executeUpdate(table1);
            statement1 = c.createStatement();
            String table2 = "CREATE TABLE IF NOT EXISTS EEGSample " + "(id     INTEGER  PRIMARY KEY AUTOINCREMENT,"
                    + " eeg TEXT NOT NULL, " + " elg TEXT NOT NULL, " + " dos DATE NOT NULL, " + "observations TEXT, " 
                    + " patient_id INTEGER, " + " FOREIGN KEY(patient_id) REFERENCES patient(id))";
            statement1.executeUpdate(table2);
            statement1.close();
        } catch (SQLException e) {
            System.out.println("error de execute");
            e.printStackTrace();
        }
    }

    @Override
    public PatientManager getPatient() {
        return patient;
    }

    @Override
    public EEGManager getEEGSample() {
        return eegSample;
    }

    @Override
    public Connection getC() {
        return c;
    }

}
