/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import Client.EEGSample;
import Client.Patient;
import Client.SocketOb;
import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;


/**
 *
 * @author andre
 */
public class DisplayPlot extends javax.swing.JFrame implements WindowListener  {

    private SocketOb db;
    private DisplayPlot displayPlot;
    private ArrayList<Integer> eeg = new ArrayList();
    private ArrayList<Integer> elg = new ArrayList();
    private MySignals sam;
    private Patient patient;
    public ArrayList<EEGSample> eegs1;
    public ClientMenu cmenu;

    /**
     * Creates new form displayPlot
     */
    public DisplayPlot() {
    }

    public void setEeg(ArrayList<Integer> eeg) {
        this.eeg = eeg;
    }

    public void setElg(ArrayList<Integer> elg) {
        this.elg = elg;
    }

    DisplayPlot(SocketOb db, Patient patient, ArrayList<EEGSample> eegs1) {
        this.db = db;
        this.patient = patient;
        this.eegs1 = eegs1;
        initComponents();
    }

    public void setDisplayPlot(DisplayPlot displayPlot) {
        this.displayPlot = displayPlot;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Menu = new javax.swing.JButton();
        goBack = new javax.swing.JButton();
        menuu = new javax.swing.JButton();
        EEG = new javax.swing.JButton();
        ELG = new javax.swing.JButton();

        Menu.setText("Menu");
        Menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        goBack.setText("Go Back");
        goBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goBackActionPerformed(evt);
            }
        });

        menuu.setText("Menu");
        menuu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuuActionPerformed(evt);
            }
        });

        EEG.setText("EEG");
        EEG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EEGActionPerformed(evt);
            }
        });

        ELG.setText("LUX");
        ELG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ELGActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(menuu)
                .addGap(18, 18, 18)
                .addComponent(goBack, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
            .addGroup(layout.createSequentialGroup()
                .addGap(220, 220, 220)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(ELG)
                    .addComponent(EEG))
                .addContainerGap(237, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(101, 101, 101)
                .addComponent(EEG)
                .addGap(34, 34, 34)
                .addComponent(ELG)
                .addGap(89, 89, 89)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(goBack)
                    .addComponent(menuu))
                .addContainerGap(7, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void goBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_goBackActionPerformed
        sam = new MySignals(db, patient, eegs1);
        sam.setSam(sam);
        sam.setVisible(true);
        this.displayPlot.setVisible(false);
        int option = 1;
        try {
            db.getOutputStream().write(option);
        } catch (IOException ex) {
            Logger.getLogger(Welcome.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_goBackActionPerformed

    private void MenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuActionPerformed

    }//GEN-LAST:event_MenuActionPerformed

    private void ELGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ELGActionPerformed
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < elg.size(); i++) {
            Double value = (double) elg.get(i);
            dataset.addValue(value, "Values", Integer.toString(i));
        }

        JFreeChart chart = ChartFactory.createLineChart("ELG", "time", "Electric Signal", dataset, PlotOrientation.VERTICAL,
                false,
                false,
                false
        );
        chart.setBackgroundPaint(Color.white);
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        ChartFrame frame = new ChartFrame("ELG", chart);
        plot.setRangeGridlinePaint(Color.BLUE);
        frame.setVisible(true);
        frame.setSize(650, 650);
    }//GEN-LAST:event_ELGActionPerformed

    private void EEGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EEGActionPerformed
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < eeg.size(); i++) {
            Double value = (double) eeg.get(i);
            dataset.addValue(value, "Values", Integer.toString(i));
        }

        JFreeChart chart = ChartFactory.createLineChart("EEG", "time", "Electric Signal", dataset, PlotOrientation.VERTICAL,
                false,
                false,
                false
        );
        chart.setBackgroundPaint(Color.white);
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        ChartFrame frame = new ChartFrame("EEG", chart);
        plot.setRangeGridlinePaint(Color.BLUE);
        frame.setVisible(true);
        frame.setSize(650, 650);
    }//GEN-LAST:event_EEGActionPerformed

    private void menuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuuActionPerformed
        int option = 1;
        cmenu = new ClientMenu(db);
        cmenu.setPatient(patient);
        cmenu.setClimen(cmenu);
        cmenu.setVisible(true);
        this.displayPlot.setVisible(false);
        try {
            db.getOutputStream().write(option);
            db.getOutputStream().write(option);
        } catch (IOException ex) {
            Logger.getLogger(Welcome.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_menuuActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DisplayPlot.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DisplayPlot.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DisplayPlot.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DisplayPlot.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DisplayPlot().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton EEG;
    private javax.swing.JButton ELG;
    private javax.swing.JButton Menu;
    private javax.swing.JButton goBack;
    private javax.swing.JButton menuu;
    // End of variables declaration//GEN-END:variables

    @Override
    public void windowOpened(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void windowClosing(WindowEvent e) {
        releaseResources(db.getObjectInputStream(), db.getObjectOutputStream(), db.getSocket(),
                db.getInputStream(), db.getOutputStream());}

    @Override
    public void windowClosed(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void windowIconified(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void windowActivated(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    private static void releaseResources(ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream, Socket socket,
            InputStream inputStream, OutputStream outputStream) {
        try {
            objectInputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(DisplayPlot.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            objectOutputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(DisplayPlot.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(DisplayPlot.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            outputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(DisplayPlot.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            inputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(DisplayPlot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
