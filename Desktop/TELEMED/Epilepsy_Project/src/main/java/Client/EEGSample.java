/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Client;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author andre
 */
public class EEGSample implements Serializable {

    private static final long serialVersionUID = -6291904286218553733L;

    private Integer id;
    private ArrayList<Integer> eeg = new ArrayList<Integer>();
    private ArrayList<Integer> elg = new ArrayList<Integer>();
    private String dos;
    private String observations;
    private int patient_id;

    public EEGSample(ArrayList<Integer> eeg, ArrayList<Integer> elg, String dos, String observations, int patient_id) {
        this.eeg = eeg;
        this.elg = elg;
        this.dos = dos;
        this.observations = observations;
        this.patient_id = patient_id;
    }

    public EEGSample(Integer id, ArrayList<Integer> eeg, ArrayList<Integer> elg, String dos, String observations, int patient_id) {
        this.id = id;
        this.eeg = eeg;
        this.elg = elg;
        this.dos = dos;
        this.observations = observations;
        this.patient_id = patient_id;
    }


    public ArrayList<Integer> getEeg() {
        return eeg;
    }

    public ArrayList<Integer> getElg() {
        return elg;
    }

    public String getDos() {
        return dos;
    }

    public String getObservations() {
        return observations;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setEeg(ArrayList<Integer> eeg) {
        this.eeg = eeg;
    }

    public void setElg(ArrayList<Integer> elg) {
        this.elg = elg;
    }

    public void setDos(String dos) {
        this.dos = dos;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "EEGSample{" + "id=" + id + ", eeg=" + eeg + ", elg=" + elg + ", dos=" + dos + ", observations=" + observations + ", patient_id=" + patient_id + '}';
    }

}
