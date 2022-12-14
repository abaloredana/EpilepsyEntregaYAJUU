/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import Client.EEGSample;
import Client.Patient;
import Client.SocketOb;
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
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author loredanaabalo
 */
public class MySignals extends javax.swing.JFrame implements WindowListener {

    private SocketOb db;
    private Patient patient;
    private MySignals sam;
    public ClientMenu cmenu;
    public DisplayPlot displayPlot;
    public ArrayList<EEGSample> eegs1;

    /**
     * Creates new form MySignals
     *
     * @param db
     * @param patient
     * @param eegs1
     */
    public MySignals(SocketOb db, Patient patient, ArrayList<EEGSample> eegs1) {
        this.db = db;
        this.patient = patient;
        this.eegs1 = eegs1;
        initComponents();

        DefaultTableModel tblModel = (DefaultTableModel) jTable2.getModel();
        for (int i = 0; i < eegs1.size(); i++) {
            String data[] = {Integer.toString(eegs1.get(i).getId()), eegs1.get(i).getDos(), eegs1.get(i).getObservations()};
            tblModel.addRow(data);
        }
    }

    public MySignals() {
    }

    public void setSam(MySignals sam) {
        this.sam = sam;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        pnlSignals = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        sampleId = new javax.swing.JTextField();
        select = new javax.swing.JButton();
        Menu = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlSignals.setBorder(javax.swing.BorderFactory.createTitledBorder("Signals"));

        jLabel1.setText("Please enter the id of the signal you wish to see:");

        sampleId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sampleIdActionPerformed(evt);
            }
        });

        select.setText("Select");
        select.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectActionPerformed(evt);
            }
        });

        Menu.setText("Menu");
        Menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuActionPerformed(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sample ID", "Date of Sample", "Simptoms and notes"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout pnlSignalsLayout = new javax.swing.GroupLayout(pnlSignals);
        pnlSignals.setLayout(pnlSignalsLayout);
        pnlSignalsLayout.setHorizontalGroup(
            pnlSignalsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSignalsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(sampleId, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(select)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Menu)
                .addGap(15, 15, 15))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSignalsLayout.createSequentialGroup()
                .addContainerGap(54, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 588, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38))
        );
        pnlSignalsLayout.setVerticalGroup(
            pnlSignalsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSignalsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlSignalsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
                    .addComponent(sampleId, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(select)
                    .addComponent(Menu))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlSignals, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlSignals, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void MenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuActionPerformed
        cmenu = new ClientMenu(db);
        cmenu.setPatient(patient);
        cmenu.setClimen(cmenu);
        cmenu.setVisible(true);
        this.sam.setVisible(false);
        int option = 1;
        try {
            db.getOutputStream().write(option);
        } catch (IOException ex) {
            Logger.getLogger(Welcome.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_MenuActionPerformed

    private void sampleIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sampleIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sampleIdActionPerformed

    private void selectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectActionPerformed
        displayPlot = new DisplayPlot(db, patient, eegs1);
        ArrayList<Integer> eeg = null;
        ArrayList<Integer> elg = null;
        String idAux = sampleId.getText();
        int id = -1;
        try {
            id = Integer.parseInt(idAux);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Enter a valid option");
        }
        boolean Check = false;
        if (id != -1) {
            for (int i = 0; i < eegs1.size(); i++) {
                if (eegs1.get(i).getId() == id) {
                    Check = true;
                }
            }
            if (Check) {
                try {
                    db.getOutputStream().write(0);
                    db.getOutputStream().write(id);
                    eeg = (ArrayList) db.getObjectInputStream().readObject();
                    elg = (ArrayList) db.getObjectInputStream().readObject();
                } catch (IOException | ClassNotFoundException ex) {
                    Logger.getLogger(MySignals.class.getName()).log(Level.SEVERE, null, ex);
                }
                displayPlot.setEeg(eeg);
                displayPlot.setElg(elg);
                displayPlot.setDisplayPlot(displayPlot);
                displayPlot.setVisible(true);
                this.sam.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(null, "Enter an existing id");
            }
        }
    }//GEN-LAST:event_selectActionPerformed

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
            java.util.logging.Logger.getLogger(MySignals.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MySignals.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MySignals.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MySignals.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        System.out.print("holaaaa");

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MySignals().setVisible(true);
            }
        });
    }

    private static void releaseResources(ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream, Socket socket,
            InputStream inputStream, OutputStream outputStream) {
        try {
            objectInputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(MySignals.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            objectOutputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(MySignals.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(MySignals.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            outputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(MySignals.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            inputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(MySignals.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void windowClosing(WindowEvent e) {
        releaseResources(db.getObjectInputStream(), db.getObjectOutputStream(), db.getSocket(),
                db.getInputStream(), db.getOutputStream());
    }

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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Menu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JPanel pnlSignals;
    private javax.swing.JTextField sampleId;
    private javax.swing.JButton select;
    // End of variables declaration//GEN-END:variables
}
