/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Views;

import Services.PhieuNhapService;
import ViewModels.PN_SanPhamViewModel;
import Views.QLNH.QLNH_add;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author hmail
 */
public class ReadORCode extends javax.swing.JFrame implements Runnable, ThreadFactory {

    PhieuNhapService pnService = new PhieuNhapService();
    private WebcamPanel panel = null;
    private Webcam webcam = null;
    private Executor ex = Executors.newSingleThreadExecutor(this);
    private volatile boolean isScanning = false;
    QLNH_add ql = new QLNH_add();

    public String ScannedBarcode() {
        return txtDoc.getText();
    }

    /**
     * Creates new form WebCam
     */
    public ReadORCode() {
        initComponents();
        setLocationRelativeTo(null);
        iniWebCam();
    }

    private void iniWebCam() {
        Dimension size = WebcamResolution.QVGA.getSize();
        webcam = Webcam.getWebcams().get(0);
        webcam.setViewSize(size);

        panel = new WebcamPanel(webcam);
        panel.setPreferredSize(size);
        panel.setFPSDisplayed(true);

        panel.setBounds(0, 0, 470, 300);
        jPnCamera.add(panel);

        ex.execute(this);
    }

    @Override
    public void run() {
        do {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                java.util.logging.Logger.getLogger(ReadORCode.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }

            Result rs = null;
            BufferedImage image = null;

            if (webcam.isOpen()) {
                if ((image = webcam.getImage()) == null) {
                    continue;
                }
            }
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bipmap = new BinaryBitmap(new HybridBinarizer(source));

            try {
                rs = new MultiFormatReader().decode(bipmap);
            } catch (NotFoundException ex) {
                java.util.logging.Logger.getLogger(ReadORCode.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }

            if (rs != null) {
                txtDoc2.setText(rs.getText());
                ql.setVisible(true);
                String doc = txtDoc2.getText();
                ArrayList<PN_SanPhamViewModel> ls = pnService.SearchCode(doc);
                loadTableSP(ls);
                this.dispose();
            }
        } while (true);
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r, "OK");
        t.setDaemon(true);
        return t;
    }

    public class BarcodeScanner {

        private volatile boolean isScanning = false;
        private String scannedBarcode;

        public BarcodeScanner() {
        }

        public void startScan() {
            if (!isScanning) {
                isScanning = true;
                scanLoop();
            }
        }

        public void stopScan() {
            isScanning = false;
        }

        private void scanLoop() {
            while (isScanning) {
                BufferedImage image = webcam.getImage();

                if (image != null) {
                    Result barcodeResult = readBarcode(image);
                    if (barcodeResult != null) {
                        scannedBarcode = barcodeResult.getText();
                        System.out.println("Barcode: " + scannedBarcode);
                        // Xử lý kết quả của mã Barcode ở đây
                    }
                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

            webcam.close();
        }

        private Result readBarcode(BufferedImage image) {
            if (image != null) {
                LuminanceSource source = new BufferedImageLuminanceSource(image);
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

                try {
                    return new MultiFormatReader().decode(bitmap);
                } catch (NotFoundException e) {
                    return null;
                }
            } else {
                return null;
            }
        }

    }

    void loadTableSP(ArrayList<PN_SanPhamViewModel> ls) {
        DefaultTableModel model = (DefaultTableModel) ql.tblSP.getModel();
        model.setRowCount(0);
        for (PN_SanPhamViewModel sp : ls) {
            model.addRow(new Object[]{
                sp.getMaSPCT(), sp.getTenSP(), sp.getSoL()
            });
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPnCamera = new javax.swing.JPanel();
        txtDoc = new javax.swing.JTextField();
        Search = new javax.swing.JButton();
        txtDoc2 = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        javax.swing.GroupLayout jPnCameraLayout = new javax.swing.GroupLayout(jPnCamera);
        jPnCamera.setLayout(jPnCameraLayout);
        jPnCameraLayout.setHorizontalGroup(
            jPnCameraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 504, Short.MAX_VALUE)
        );
        jPnCameraLayout.setVerticalGroup(
            jPnCameraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 307, Short.MAX_VALUE)
        );

        txtDoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtDocMouseClicked(evt);
            }
        });

        Search.setText("Tìm");
        Search.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SearchMouseClicked(evt);
            }
        });

        txtDoc2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtDoc2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPnCamera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtDoc2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDoc, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE))
                        .addGap(53, 53, 53)
                        .addComponent(Search)))
                .addGap(0, 33, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jPnCamera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDoc2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtDoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(Search)
                                .addGap(9, 9, 9))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SearchMouseClicked
        // TODO add your handling code here:
        ql.setVisible(true);
        String doc = txtDoc.getText();
        ArrayList<PN_SanPhamViewModel> ls = pnService.SearchCode(doc);
        loadTableSP(ls);
        this.dispose();
    }//GEN-LAST:event_SearchMouseClicked

    private void txtDocMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDocMouseClicked
        // TODO add your handling code here:
        ql.setVisible(true);
        String doc = txtDoc.getText();
        ArrayList<PN_SanPhamViewModel> ls = pnService.SearchCode(doc);
        loadTableSP(ls);
        this.dispose();
    }//GEN-LAST:event_txtDocMouseClicked

    private void txtDoc2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDoc2MouseClicked
        // TODO add your handling code here:
        ql.setVisible(true);
        String doc = txtDoc2.getText();
        ArrayList<PN_SanPhamViewModel> ls = pnService.SearchCode(doc);
        loadTableSP(ls);
        this.dispose();
    }//GEN-LAST:event_txtDoc2MouseClicked

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
            java.util.logging.Logger.getLogger(Webcam.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Webcam.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Webcam.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Webcam.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ReadORCode().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Search;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPnCamera;
    public javax.swing.JTextField txtDoc;
    private javax.swing.JPasswordField txtDoc2;
    // End of variables declaration//GEN-END:variables
}
