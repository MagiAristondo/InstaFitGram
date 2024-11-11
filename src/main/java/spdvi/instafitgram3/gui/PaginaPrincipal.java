/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package spdvi.instafitgram3.gui;

import java.awt.BorderLayout;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import spdvi.instafitgram3.DataAcces.DataAccess;
import spdvi.instafitgram3.dto.Attempt;
import spdvi.instafitgram3.dto.Review;
import spdvi.instafitgram3.dto.User;

/**
 *
 * @author giari
 */
public class PaginaPrincipal extends javax.swing.JFrame {

    private boolean nomesSenseReview = false;
    private int filaSeleccionada;
    private String userIdSeleccionat;
    private int idInstructor;
    private int idAttempt;
    
    /**
     * Creates new form PaginaPrincipal
     */
    public PaginaPrincipal(int idInstructor) {
        initComponents();
        setSize(725,485);
        setLocationRelativeTo(null);
        
        this.idInstructor = idInstructor;
    }
       
    public PaginaPrincipal() {
        initComponents();
        setSize(725,485);
        setLocationRelativeTo(null);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jToggleButton1 = new javax.swing.JToggleButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jLabel1.setText("jLabel1");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(341, 30, 37, 16);

        DataAccess da = new DataAccess();
        List<User> userList = da.getUsers();

        Object[][] users = new Object[userList.size()][5];

        for(int i = 0; i < userList.size(); i++)
        {
            User user = userList.get(i);
            users[i][0] = user.getId();
            users[i][1] = user.getNom();
            users[i][2] = user.getEmail();
            users[i][3] = user.getPasswordHash();
            users[i][4] = user.isIsInstructor();
        }
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            users,
            new String [] {
                "Id", "Nom", "Email", "Contrasenya", "Instructor"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(192, 91, 487, 325);

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = {};
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jList1);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(40, 91, 130, 280);

        jToggleButton1.setText("Sense review");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jToggleButton1);
        jToggleButton1.setBounds(40, 390, 130, 23);

        jButton1.setText("Crear review");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(40, 50, 130, 23);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        filaSeleccionada = jTable1.getSelectedRow();

        if (filaSeleccionada != -1)
        { // Verifiquem que hi ha una fila seleccionada
            updateJList();
        }
    }//GEN-LAST:event_jTable1MouseClicked

    public void updateJList() {
        if (filaSeleccionada != -1)
        { // Verifiquem que hi ha una fila seleccionada
            userIdSeleccionat = jTable1.getValueAt(filaSeleccionada, 0).toString();
            
            DefaultListModel<String> listModel = new DefaultListModel<>();
            
            DataAccess da = new DataAccess();
            List<Attempt> intents = da.getAttemptsByUserId(userIdSeleccionat, nomesSenseReview);
            
            for (Attempt intent : intents) {
                listModel.addElement(intent.getExercise()); // Afegim cada element individualment
            }
            
            jList1.setModel(listModel);
            
            if (jScrollPane2.getViewport().getView() != jList1) {
                jScrollPane2.setViewportView(jList1); // Actualitzar el JScrollPane per mostrar el jList1 actualitzat
            }
        }
        else
        {
            System.out.println("No hi ha cap fila seleccionada.");
        }
    }
    
    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        // TODO add your handling code here:
        if (jToggleButton1.isSelected()) {
            System.out.println("Botó seleccionat");
            nomesSenseReview = true;
            jToggleButton1.setText("Mostrar tots");
        } else {
            System.out.println("Botó no seleccionat");
            nomesSenseReview = false;
            jToggleButton1.setText("Sense review");
        }
        
        updateJList();
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
        // TODO add your handling code here:
        
        String intentSeleccionat = jList1.getSelectedValue();
        
        DataAccess da = new DataAccess();
        Attempt attempt = da.getAttemptByUserIdAndNomExercici(userIdSeleccionat, intentSeleccionat);
        
        idAttempt = attempt.getId();
        
        List<Review> reviews = da.getReviewsByIdIntent(attempt.getId());
                
        for(Review review : reviews)
        {
            mostrarReview(review);
        }
    }//GEN-LAST:event_jList1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        FerReview fr = new FerReview(idInstructor, idAttempt);
        fr.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void mostrarReview(Review review)
    {
        ReviewDialog rd = new ReviewDialog(this, false);
        
        rd.setReview(review);

        rd.setVisible(true);
    }
    
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
            java.util.logging.Logger.getLogger(PaginaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PaginaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PaginaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PaginaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PaginaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList<String> jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JToggleButton jToggleButton1;
    // End of variables declaration//GEN-END:variables
}
