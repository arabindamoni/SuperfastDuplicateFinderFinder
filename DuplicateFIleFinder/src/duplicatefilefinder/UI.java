/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package duplicatefilefinder;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JLabel;
import javax.swing.SwingWorker;

/**
 *
 * @author arabindamoni
 */
public class UI extends javax.swing.JFrame {

    /**
     * Creates new form UI
     */
    private File file;
    private HashMap<String, ArrayList<String>> map;
    private DuplicateFIleFinder df ;   
    private SwingWorker worker;

    public UI() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();
        Open = new javax.swing.JButton();
        Scan = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Display = new javax.swing.JLabel();
        Size = new javax.swing.JLabel();
        ScannedCount = new javax.swing.JLabel();
        stop = new javax.swing.JButton();

        jFileChooser1.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Open.setText("Open");
        Open.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OpenActionPerformed(evt);
            }
        });

        Scan.setText("Scan");
        Scan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ScanActionPerformed(evt);
            }
        });

        Display.setText("Please Select a Directory to Scan");
        Display.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        Display.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane1.setViewportView(Display);

        Size.setText("Total size of duplicate files : ");

        stop.setText("Cancel");
        stop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(Size, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(Open)
                                .addGap(229, 229, 229)
                                .addComponent(Scan)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(60, 60, 60)
                                .addComponent(ScannedCount, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 44, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(stop)
                                .addGap(73, 73, 73))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Open)
                    .addComponent(Scan)
                    .addComponent(stop))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Size)
                    .addComponent(ScannedCount))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void OpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OpenActionPerformed
        // TODO add your handling code here:
        jFileChooser1.showOpenDialog(null);
        file = jFileChooser1.getSelectedFile();
    }//GEN-LAST:event_OpenActionPerformed

    private void disableButtons() {
        // disable buttons
        Display.setText("Scanning " + file.getAbsolutePath());
        Scan.setText("Scanning...");
        Scan.setEnabled(false);
        Open.setEnabled(false);
        stop.setEnabled(true);
    }

    private void enableButtons() {
        //enable buttons
        Scan.setEnabled(true);
        Open.setEnabled(true);
        stop.setEnabled(false);
        Scan.setText("Scan");
    }

    
    private String convertSize(int size){
        
        double KB = size/1024;
        if(KB < 1) return size+"B";
        double MB = KB/1024;
        if(MB < 1) return KB + "KB";
        double GB = MB/1024;
        if(GB < 1) return MB+"MB";
        return GB+"GB";                         
    }
    
    private void ScanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ScanActionPerformed
        // TODO add your handling code here:

        if (file == null) {
            Display.setText("<html> <h1> <font color=red> First select a directory to scan using the Open button </h1></html>");
            return;
        }       

        disableButtons();

        worker = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                df = new DuplicateFIleFinder();                
                map = df.findDuplicate(file,ScannedCount);                                    

                int totalsize = 0;
                String str = "<html> <font color=black>";
                for (String key : map.keySet()) {                                      
                    ArrayList<String> paths = map.get(key);
                    if (paths.size() > 1) {
                        int fsize = Integer.parseInt(key.substring(key.lastIndexOf(":") + 1));  
                        totalsize += fsize;
                        str += "<p><b>" + paths.get(0).substring(paths.get(0).lastIndexOf("\\") + 1) + " : " + convertSize(fsize) + "</b><ol>";
                        for (String s : paths) {
                            str += "<li>" + s + "</li>";
                        }
                        str += "</ol></p>";
                    }
                }
                str += "</html>";
                Display.setText(str);
                Size.setText("Total size of duplicate files:" + convertSize(totalsize));
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public void done() {
                enableButtons();                  
            }
        };             
        
        worker.execute();        
    }//GEN-LAST:event_ScanActionPerformed

    private void stopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopActionPerformed
               // TODO add your handling code here:
       worker.cancel(true); 
       df.stop();
       enableButtons();
    }//GEN-LAST:event_stopActionPerformed

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
            java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UI().setVisible(true);

            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Display;
    private javax.swing.JButton Open;
    private javax.swing.JButton Scan;
    private javax.swing.JLabel ScannedCount;
    private javax.swing.JLabel Size;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton stop;
    // End of variables declaration//GEN-END:variables
}
