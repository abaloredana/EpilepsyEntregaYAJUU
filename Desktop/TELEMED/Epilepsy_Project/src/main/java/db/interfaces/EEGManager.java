/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package db.interfaces;

import Client.EEGSample;
import java.util.ArrayList;

/**
 *
 * @author andre
 */
public interface EEGManager {
    
    public void newEEGSample(EEGSample eegSample);

    public ArrayList<EEGSample> getEEGs(int id);

}
