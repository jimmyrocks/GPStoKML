/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ConversionStatus.java
 *
 * Created on Aug 6, 2009, 10:53:48 PM
 */

package GPStoKML;

/**
 *
 * @author jim
 */
public class ConversionStatus extends javax.swing.JDialog {

    /** Creates new form ConversionStatus */
    public ConversionStatus(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public void setWindowName(String lsTitle)
    {
        setTitle(lsTitle);
    }

    public void setVals(boolean lbShowScreen, int liMax, int liCurrVal)
    {
        setVisible(lbShowScreen);

        //Set Progress bar
            prgProgress.setMinimum(0);
            prgProgress.setMaximum(liMax);
            prgProgress.setValue(liCurrVal);

        //Set Percent
            if (liMax>0)
                {
                    lblProgress.setText((((liCurrVal*100)/liMax)) + "%");
                }
            else
                {
                    lblProgress.setText("0%");
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

        lblLoader = new javax.swing.JLabel();
        lblCreatingFile = new javax.swing.JLabel();
        prgProgress = new javax.swing.JProgressBar();
        lblProgress = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Title");

        lblLoader.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GPStoKML/ajax-loader.gif"))); // NOI18N

        lblCreatingFile.setFont(new java.awt.Font("Lucida Sans Typewriter", 1, 12));
        lblCreatingFile.setForeground(new java.awt.Color(204, 0, 0));
        lblCreatingFile.setText("Creating Your KML File...");

        lblProgress.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblProgress.setText("0%");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(lblCreatingFile)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(lblProgress, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                        .add(18, 18, 18)
                        .add(lblLoader))
                    .add(layout.createSequentialGroup()
                        .add(1, 1, 1)
                        .add(prgProgress, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblCreatingFile, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(lblLoader)
                    .add(lblProgress))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(prgProgress, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ConversionStatus dialog = new ConversionStatus(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblCreatingFile;
    private javax.swing.JLabel lblLoader;
    private javax.swing.JLabel lblProgress;
    private javax.swing.JProgressBar prgProgress;
    // End of variables declaration//GEN-END:variables

}