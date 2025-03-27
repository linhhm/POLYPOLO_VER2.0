/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package Views.KHOHANG;

import Models.KhoHang;
import Services.KhoHangService;
import Utils.SVGImage;
import Validator.MyValidate;
import ViewModels.KhoHangViewModel;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author X1
 */
public class QuanLyKhoHang extends javax.swing.JInternalFrame {
    SVGImage svgSet = new SVGImage();
    KhoHangService khService  = new KhoHangService();
    /**
     * Creates new form QLSANPHAM
     */
    public QuanLyKhoHang() {
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
        setButtonIcon();
        load();
    }

    void load(){
        loadTableKhoHang(khService.getList());
        adjustWidths(tblKhoHang);
    }
    void setButtonIcon() {
        btnAdd.setIcon(svgSet.createSVGIcon("Images/SVG/add.svg", 20, 20));
        btnUpdate.setIcon(svgSet.createSVGIcon("Images/SVG/update.svg", 20, 20));
        btnDelete.setIcon(svgSet.createSVGIcon("Images/SVG/delete.svg", 18, 18));
        btnLoad.setIcon(svgSet.createSVGIcon("Images/SVG/reload.svg", 18, 18));
    //    btnExport.setIcon(svgSet.createSVGIcon("Images/SVG/pdf-color.svg", 25, 25));
    }

    //LOAD
    void loadTableKhoHang(ArrayList<KhoHang> ls){
        DefaultTableModel model = (DefaultTableModel) tblKhoHang.getModel();
        model.setRowCount(0);
        for (KhoHang kh : ls) {
            model.addRow(new Object[]{
                kh.getMaKho(), kh.getTenKho(), kh.getMoTa()
            });
        }
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        TableColumnModel columnModel = tblKhoHang.getColumnModel();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setCellRenderer(centerRenderer);
        }
    }
    
    void loadTableDetails(ArrayList<KhoHangViewModel> ls){
        DefaultTableModel model = (DefaultTableModel) tblSanPhamKho.getModel();
        model.setRowCount(0);
        int a = 1;
        for (KhoHangViewModel kh : ls) {
            String sp = "SP0" + kh.getIdSP();
            model.addRow(new Object[]{
                a++, sp, kh.getTenSP(), kh.getSoL(), kh.getTrangThai()
            });
        }
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        TableColumnModel columnModel = tblKhoHang.getColumnModel();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setCellRenderer(centerRenderer);
        }
    }
    
    void adjustWidths(JTable table) {
        for (int column = 0; column < table.getColumnCount(); column++) {
            TableColumn tableColumn = table.getColumnModel().getColumn(column);

            int preferredWidth = tableColumn.getMinWidth();
            int maxWidth = tableColumn.getMaxWidth();

            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
                Component c = table.prepareRenderer(cellRenderer, row, column);
                int width = c.getPreferredSize().width + table.getIntercellSpacing().width;
                preferredWidth = Math.max(preferredWidth, width);

                if (preferredWidth >= maxWidth) {
                    preferredWidth = maxWidth;
                    break;
                }
            }
            tableColumn.setPreferredWidth(preferredWidth);
        }
    }
    
    //CLEAR
    void clearTable(){
        DefaultTableModel model = (DefaultTableModel) tblKhoHang.getModel();
        model.setRowCount(0);
    }
    
    //VALIDATE
    public Boolean validateTimKiem() {
        StringBuilder stb = new StringBuilder();
        MyValidate v = new MyValidate();

        v.isEmpty(txtSearch, stb, "Thông tin bị trống!");
        if (stb.length() > 0) {
            JOptionPane.showMessageDialog(this, stb);
            return false;
        } else {
            return true;
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

        jPanel3 = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnLoad = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();
        txtSearch = new javax.swing.JTextField();
        cboSearch = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblKhoHang = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSanPhamKho = new javax.swing.JTable();

        setBackground(new java.awt.Color(227, 241, 254));
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1090, 680));
        setMinimumSize(new java.awt.Dimension(1090, 680));
        setPreferredSize(new java.awt.Dimension(1090, 630));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel3.setAlignmentX(1.0F);
        jPanel3.setAlignmentY(1.0F);

        btnAdd.setText("THÊM");
        btnAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddMouseClicked(evt);
            }
        });

        btnUpdate.setText("SỬA");
        btnUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUpdateMouseClicked(evt);
            }
        });

        btnDelete.setText("XÓA");
        btnDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDeleteMouseClicked(evt);
            }
        });
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnLoad.setText("LOAD");
        btnLoad.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLoadMouseClicked(evt);
            }
        });
        btnLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoadActionPerformed(evt);
            }
        });

        btnSearch.setText("SEARCH");
        btnSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSearchMouseClicked(evt);
            }
        });

        cboSearch.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất Cả", "Tên Kho", "Mô Tả" }));
        cboSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cboSearchMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnLoad, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 136, Short.MAX_VALUE)
                .addComponent(cboSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLoad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 13, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8))
                    .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 22, 1050, 70));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông Tin Kho Hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 18))); // NOI18N

        tblKhoHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Mã Kho", "Tên Khu Vực", "Mô Tả"
            }
        ));
        tblKhoHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKhoHangMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblKhoHang);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 550, 460));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh Sách Sản Phẩm Kho", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 16))); // NOI18N

        tblSanPhamKho.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "STT", "ID", "Tên Sản Phẩm", "Tồn", "Trạng Thái"
            }
        ));
        jScrollPane1.setViewportView(tblSanPhamKho);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 120, 470, 460));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddMouseClicked
        // ADD
        QLKH_add add = new QLKH_add();
        add.setVisible(true);
    }//GEN-LAST:event_btnAddMouseClicked

    private void btnUpdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUpdateMouseClicked
        // UPDATE
        int pos = tblKhoHang.getSelectedRow();
        if (pos == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn kho hàng muốn cập nhật!", "POLYPOLO thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        } else {
            QLKH_update update = new QLKH_update();
            update.setVisible(true);
            update.pack();

            Integer id = (Integer) tblKhoHang.getValueAt(pos, 0);
            update.txtId.setText(id.toString());
            update.txtTenKho.setText((String) tblKhoHang.getValueAt(pos, 1));
            update.txtMoTa.setText((String) tblKhoHang.getValueAt(pos, 2));
        }
        loadTableKhoHang(khService.getList());
    }//GEN-LAST:event_btnUpdateMouseClicked

    private void btnLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLoadActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void tblKhoHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKhoHangMouseClicked
        // CLICK
        int pos = tblKhoHang.getSelectedRow();
        if (pos != -1) {
            Integer id = Integer.valueOf(tblKhoHang.getValueAt(pos, 0).toString());
            ArrayList<KhoHangViewModel> ls = khService.getListById(id);
            loadTableDetails(ls);
            adjustWidths(tblSanPhamKho);
        }else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn kho hàng trên bảng để hiển thị!", "POLYPOLO thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_tblKhoHangMouseClicked

    private void cboSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboSearchMouseClicked
        //ALL
        txtSearch.setBackground(Color.white);
        loadTableKhoHang(khService.getList());
    }//GEN-LAST:event_cboSearchMouseClicked

    private void btnSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSearchMouseClicked
        // SEARCH
        if (validateTimKiem()) {
            if (cboSearch.getSelectedItem().equals("Tên Kho")) {
                String name = txtSearch.getText().trim();
                ArrayList<KhoHang> ls = khService.getListByName(name);
                loadTableKhoHang(ls);
                txtSearch.setText("");
            } else if (cboSearch.getSelectedItem().equals("Mô Tả")) {
                String name = txtSearch.getText().trim();
                ArrayList<KhoHang> ls = khService.getListByDescription(name);
                loadTableKhoHang(ls);
                txtSearch.setText("");
            }
        } else {
            txtSearch.setText("");
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một mục để lọc!", "POLYPOLO thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnSearchMouseClicked

    private void btnDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDeleteMouseClicked
        // DELETE
        int pos = tblKhoHang.getSelectedRow();
        if (pos != -1) {
            int result = JOptionPane.showConfirmDialog(this, "Bạn muốn xóa kho hàng này?", "POLYPOLO xác nhận", JOptionPane.YES_NO_OPTION);
            Integer id = Integer.valueOf(tblKhoHang.getValueAt(pos, 0).toString());
            if (result == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(this, khService.deleteKho(id), "POLYPOLO thông báo", JOptionPane.WARNING_MESSAGE);
                loadTableKhoHang(khService.getList());
            } else {
                JOptionPane.showMessageDialog(this, "Đã hủy thao tác xóa kho hàng!", "POLYPOLO thông báo", JOptionPane.WARNING_MESSAGE);
            }
        }else{
            JOptionPane.showMessageDialog(this, "Vui lòng chọn kho hàng muốn xóa!", "POLYPOLO thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnDeleteMouseClicked

    private void btnLoadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLoadMouseClicked
        // LOAD
        ArrayList<KhoHang> ls = khService.getList();
        loadTableKhoHang(ls);
    }//GEN-LAST:event_btnLoadMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnLoad;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cboSearch;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblKhoHang;
    private javax.swing.JTable tblSanPhamKho;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
