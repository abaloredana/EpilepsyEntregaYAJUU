/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package db.interfaces;

import Client.Patient;
import java.util.ArrayList;

/**
 *
 * @author andre
 */
public interface PatientManager {

    public void newPatient(Patient patient);

    public Patient getPatient(String username, String password);

    public boolean UsernameExists(String username);

    public boolean UsernameAndPasswordMatch(String username, String password);
    
    public ArrayList<Patient> getPatients();

}
