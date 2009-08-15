/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MainInterface.java
 *
 * Created on Jul 27, 2009, 10:21:03 PM
 */

package GPStoKML;

import java.awt.Color;
import java.io.*;
import javax.swing.JFileChooser;


/**
 *
 * @author jim
 */
public class GPSSelector extends javax.swing.JFrame {

    /** Creates new form MainInterface */
    public GPSSelector() {
        initComponents();
    }

    public void LoadWindow()
    {
        btnDestFile.setVisible(false); //This button will have function later
        EnableConvertButton();
        lblCreatingFile.setVisible(false);
        setVisible(true);

    }

    private void ShowFileChooser(String lsExtention, String lsNewExtention)
    {

        jFileChooser1 = new JFileChooser();
        jFileChooser1.setDialogTitle("Select a CSV or " + lsExtention + " File:");
        File selectedFile;
        ExtentionFilter loFilter = new ExtentionFilter();
        loFilter.SetExtention(lsExtention);
        ExtentionFilter loFilter2 = new ExtentionFilter();
        loFilter2.SetExtention("CSV");
        jFileChooser1.setFileFilter(loFilter);
        jFileChooser1.setFileFilter(loFilter2);

        int returnVal = jFileChooser1.showOpenDialog(this);

        if(returnVal == JFileChooser.APPROVE_OPTION)
            {
                selectedFile = jFileChooser1.getSelectedFile();
                txtSourceFile.setText(selectedFile.getPath().toString());
                
                txtDestFile.setText(loFilter.StripExtension(selectedFile) + "." + lsNewExtention.toLowerCase());
                EnableConvertButton();
            }

        if(returnVal == JFileChooser.CANCEL_OPTION)
            {
                //setVisible(false);
                //System.exit(1);
            }
    }

    private boolean EnableConvertButton()
    {
        boolean lbReturnValue;
        boolean lbCheckColors;

        lbReturnValue = (txtDestFile.getText().length()>0 && txtSourceFile.getText().length()> 0);
        

        //Validate the number in the text field
        lbCheckColors=GPSSelector.ValidateNumber(txtColors.getText());

        if (lbCheckColors)
        {
            txtColors.setBackground(Color.white);
        }
        else
        {
            txtColors.setBackground(Color.pink);
        }

        lbReturnValue = lbReturnValue && lbCheckColors;

        btnCreate.setEnabled(lbReturnValue);
        return lbReturnValue;
    }

    private boolean ConvertFile(String lsSource, String lsDestination)
    {
         boolean lbReturnValue = false;

         int liCodeBy = 0;

         if (rbtnSpeed.isSelected()) liCodeBy = 1;
         if (rbtnElevation.isSelected()) liCodeBy = 2;
         if (rbtnAcceleration.isSelected()) liCodeBy = 3;
         if (rbtnTime.isSelected()) liCodeBy = 4;

         int liStyles = Integer.parseInt(txtColors.getText());

         RunConverter loRunConverter = new RunConverter();
         loRunConverter.setVars(lsSource, lsDestination, liCodeBy, liStyles);
         loRunConverter.start();


         return lbReturnValue;
    }

    public class RunConverter extends Thread
        {
            public void main(String args[]) throws Throwable
                {
                    //new RunConverter().start();
                }

            String tsSource;
            String tsDestination;
            int tiCodeBy;
            int tiStyles;

            public void setVars(String lsSource,String lsDestination,int liCodeBy, int liStyles)
                {
                    tsSource = lsSource;
                    tsDestination = lsDestination;
                    tiCodeBy = liCodeBy;
                    tiStyles = liStyles;
                }

            public void run()
                {
                    GPStoKML loConverter = new GPStoKML();
                    lblCreatingFile.setVisible(true);
                    try
                        {
                            loConverter.ConvertFiles(tsSource,tsDestination,tiCodeBy, tiStyles);
                        }
                        catch (IOException e)
                        {
                            //File Does Not Exist
                        }
                    lblCreatingFile.setVisible(false);
                }
        }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        txtSourceFile = new javax.swing.JTextField();
        lblSourceFile = new javax.swing.JLabel();
        btnSourceFile = new javax.swing.JButton();
        lblDestFile = new javax.swing.JLabel();
        txtDestFile = new javax.swing.JTextField();
        btnDestFile = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        btnCreate = new javax.swing.JButton();
        rbtnSpeed = new javax.swing.JRadioButton();
        rbtnElevation = new javax.swing.JRadioButton();
        rbtnTime = new javax.swing.JRadioButton();
        txtColors = new javax.swing.JTextField();
        lblColors = new javax.swing.JLabel();
        lblCreatingFile = new javax.swing.JLabel();
        rbtnAcceleration = new javax.swing.JRadioButton();
        btnShptoKML = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("GPS to KML Tool");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtSourceFile.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSourceFileKeyReleased(evt);
            }
        });
        getContentPane().add(txtSourceFile, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, 330, -1));

        lblSourceFile.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblSourceFile.setText("Source File:");
        getContentPane().add(lblSourceFile, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, -1, -1));

        btnSourceFile.setText("Choose");
        btnSourceFile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSourceFileMouseClicked(evt);
            }
        });
        getContentPane().add(btnSourceFile, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 10, -1, -1));

        lblDestFile.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblDestFile.setText("Destination File:");
        getContentPane().add(lblDestFile, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));

        txtDestFile.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDestFileKeyReleased(evt);
            }
        });
        getContentPane().add(txtDestFile, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 40, 330, -1));

        btnDestFile.setText("Choose");
        getContentPane().add(btnDestFile, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 40, -1, -1));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Color Code By:"));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnCreate.setText("Create!");
        btnCreate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCreateMouseClicked(evt);
            }
        });
        jPanel1.add(btnCreate, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 20, -1, -1));

        buttonGroup1.add(rbtnSpeed);
        rbtnSpeed.setSelected(true);
        rbtnSpeed.setText("Speed");
        jPanel1.add(rbtnSpeed, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        buttonGroup1.add(rbtnElevation);
        rbtnElevation.setText("Elevation");
        jPanel1.add(rbtnElevation, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        buttonGroup1.add(rbtnTime);
        rbtnTime.setText("Time");
        jPanel1.add(rbtnTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, -1, -1));

        txtColors.setText("50");
        txtColors.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtColorsKeyReleased(evt);
            }
        });
        jPanel1.add(txtColors, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 20, 50, 30));

        lblColors.setText("Number of Colors:");
        jPanel1.add(lblColors, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 26, -1, 20));

        lblCreatingFile.setFont(new java.awt.Font("Lucida Sans Typewriter", 1, 12));
        lblCreatingFile.setForeground(new java.awt.Color(204, 0, 0));
        lblCreatingFile.setText("Creating Your KML File...");
        jPanel1.add(lblCreatingFile, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 60, -1, 20));

        buttonGroup1.add(rbtnAcceleration);
        rbtnAcceleration.setText("Acceleration");
        jPanel1.add(rbtnAcceleration, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 530, 90));

        btnShptoKML.setText("Test Button");
        btnShptoKML.setVisible(false);
        btnShptoKML.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnShptoKMLMouseClicked(evt);
            }
        });
        getContentPane().add(btnShptoKML, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 40, 30));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSourceFileMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSourceFileMouseClicked
        // TODO add your handling code here:
        ShowFileChooser("GPX","KML");
    }//GEN-LAST:event_btnSourceFileMouseClicked

    private void txtSourceFileKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSourceFileKeyReleased
        // TODO add your handling code here:
        EnableConvertButton();
    }//GEN-LAST:event_txtSourceFileKeyReleased

    private void txtDestFileKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDestFileKeyReleased
        // TODO add your handling code here:
        EnableConvertButton();
    }//GEN-LAST:event_txtDestFileKeyReleased

    private void btnCreateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCreateMouseClicked
        // TODO add your handling code here:
            ConvertFile(txtSourceFile.getText(), txtDestFile.getText());
    }//GEN-LAST:event_btnCreateMouseClicked

    private void txtColorsKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtColorsKeyReleased
        // TODO add your handling code here:
        EnableConvertButton();
    }//GEN-LAST:event_txtColorsKeyReleased

    private void btnShptoKMLMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnShptoKMLMouseClicked
        // TODO add your handling code here:
        //This is a test button for the SHP to KML Code
            SHPtoKML SHPconverter = new SHPtoKML();
            String lsShape = txtSourceFile.getText();
            String lsDBF = txtDestFile.getText();
            SHPconverter.ConvertFiles(lsShape, lsDBF, 0);
    }//GEN-LAST:event_btnShptoKMLMouseClicked

    public static boolean ValidateNumber(String str)
    {
        
        //It can't contain only numbers if it's null or empty...
        if (str == null || str.length() == 0 || str.length() > 8)
            return false;
        
        for (int i = 0; i < str.length(); i++) {

            //If we find a non-digit character we return false.
            if (!Character.isDigit(str.charAt(i)))
                return false;
        }
        
        return true;
    }
    
    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {


            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCreate;
    private javax.swing.JButton btnDestFile;
    private javax.swing.JButton btnShptoKML;
    private javax.swing.JButton btnSourceFile;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblColors;
    private javax.swing.JLabel lblCreatingFile;
    private javax.swing.JLabel lblDestFile;
    private javax.swing.JLabel lblSourceFile;
    private javax.swing.JRadioButton rbtnAcceleration;
    private javax.swing.JRadioButton rbtnElevation;
    private javax.swing.JRadioButton rbtnSpeed;
    private javax.swing.JRadioButton rbtnTime;
    private javax.swing.JTextField txtColors;
    private javax.swing.JTextField txtDestFile;
    private javax.swing.JTextField txtSourceFile;
    // End of variables declaration//GEN-END:variables
JFileChooser jFileChooser1 = new JFileChooser();

}