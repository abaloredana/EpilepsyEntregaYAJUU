/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db.interfaces;
import java.sql.Connection;

/**
 *
 * @author andre
 */
public interface DBManager {
    public void connect();
	public void disconnect();
	public void createTables();
	public PatientManager getPatient();
	public EEGManager getEEGSample();
	public Connection getC();

}
