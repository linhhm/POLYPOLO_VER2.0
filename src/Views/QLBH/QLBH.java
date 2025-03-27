/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package Views.QLBH;

import Models.HoaDon;
import Models.NhanSu;
import Services.HoaDonService;
import Utils.SVGImage;
import Validator.MyValidate;
import ViewModels.HoaDonViewModel;
import java.awt.Color;
import java.awt.Component;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import javax.swing.DefaultComboBoxModel;
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
public class QLBH extends javax.swing.JInternalFrame {
    SVGImage svgSet = new SVGImage();
    HoaDonService hdService = new HoaDonService();
    DecimalFormat formatter = new DecimalFormat("#,###");
    /**
     * Creates new form QLBH
     */
    public QLBH() {
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
        load();
    }
    
    //LOAD
    void load() {
        loadCboNV(hdService.getListNS());
        loadCboPhuongThuc(hdService.getAllHoaDon());
        loadTable(hdService.getListViewModel());
        adjustWidths(tblHoaDon);
        
        setButtonIcon();
    }
    void setButtonIcon() {
        btnAdd.setIcon(svgSet.createSVGIcon("Images/SVG/add.svg", 19, 19));
        btnXem.setIcon(svgSet.createSVGIcon("Images/SVG/update.svg", 19, 19));
        btnLoad.setIcon(svgSet.createSVGIcon("Images/SVG/reload.svg", 18, 18));
        btnHide.setIcon(svgSet.createSVGIcon("Images/SVG/delete.svg", 18, 18));
        btnExport.setIcon(svgSet.createSVGIcon("Images/SVG/pdf-color.svg", 19, 19));
        btnSearchDate.setIcon(svgSet.createSVGIcon("Images/SVG/search.svg", 20, 20));
    }
    
    //LOAD
    void loadCboNV(ArrayList<NhanSu> ls) {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboNV.getModel();
        HashSet<String> nvSet = new HashSet<>();

        for (NhanSu pn : ls) {
            String nv = pn.getTenNhanVien();
            if (!nvSet.contains(nv)) {
                model.addElement(nv);
                nvSet.add(nv);
            }
        }
    }
    void loadCboPhuongThuc(ArrayList<HoaDon> ls){
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboPhuongThuc.getModel();
        HashSet<String> pTSet = new HashSet<>();

        for (HoaDon hd : ls) {
            String pT = hd.getPhuongT();
            if (!pTSet.contains(pT)) {
                model.addElement(pT);
                pTSet.add(pT);
            }
        }
    }
    void loadTable(ArrayList<HoaDonViewModel> ls){
        DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
        model.setRowCount(0);
        for (HoaDonViewModel hd : ls) {
            String formatTotal = formatter.format(hd.getTongD());
            
            model.addRow(new Object[]{
                hd.getId() , hd.getHoTen(), hd.getNhanVien(), hd.getLoaiK()
                    , hd.getPhuongThuc(), hd.getNgayL(), formatTotal, hd.getTrangThai()
            }); 
        }
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        TableColumnModel columnModel = tblHoaDon.getColumnModel();
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel3 = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        btnXem = new javax.swing.JButton();
        btnHide = new javax.swing.JButton();
        btnExport = new javax.swing.JButton();
        btnLoad = new javax.swing.JButton();
        cboSearch = new javax.swing.JComboBox<>();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cboNV = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        dcsFrom = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        dcsTo = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        btnSearchDate = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        cboPhuongThuc = new javax.swing.JComboBox<>();
        rdoChua = new javax.swing.JRadioButton();
        rdoDa = new javax.swing.JRadioButton();

        setMaximumSize(new java.awt.Dimension(1090, 680));
        setMinimumSize(new java.awt.Dimension(1090, 680));
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

        btnXem.setText("XEM");
        btnXem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnXemMouseClicked(evt);
            }
        });

        btnHide.setText("XÓA");
        btnHide.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHideMouseClicked(evt);
            }
        });

        btnExport.setText("EXPORT");
        btnExport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnExportMouseClicked(evt);
            }
        });
        btnExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportActionPerformed(evt);
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

        cboSearch.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất Cả", "Tên Khách Hàng", "Số Điện Thoại" }));
        cboSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cboSearchMouseClicked(evt);
            }
        });

        btnSearch.setText("SEARCH");
        btnSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSearchMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnXem, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnHide, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnLoad, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnExport, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(92, 92, 92)
                .addComponent(cboSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
                .addGap(19, 19, 19))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnExport, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cboSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSearch))
                    .addComponent(btnLoad, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnHide, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnXem, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 10, 1050, 60));

        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Họ Tên", "Nhân Viên", "Phân Loại", "Phương Thức", "Ngày Lập", "Tổng Tiền", "Trạng Thái"
            }
        ));
        tblHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblHoaDon);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(352, 103, 710, 510));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm Kiếm Nâng Cao", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 16))); // NOI18N

        jLabel1.setText("Nhân Viên:");

        cboNV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cboNVMouseClicked(evt);
            }
        });

        jLabel2.setText("Trạng Thái:");

        jLabel4.setText("Đến Ngày:");

        jLabel5.setText("Từ Ngày:");

        btnSearchDate.setText("SEARCH");
        btnSearchDate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSearchDateMouseClicked(evt);
            }
        });

        jSeparator1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jSeparator1.setPreferredSize(new java.awt.Dimension(4, 5));

        jLabel3.setText("Phương Thức:");

        cboPhuongThuc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cboPhuongThucMouseClicked(evt);
            }
        });

        buttonGroup1.add(rdoChua);
        rdoChua.setText("Chưa thanh toán");
        rdoChua.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoChuaMouseClicked(evt);
            }
        });

        buttonGroup1.add(rdoDa);
        rdoDa.setText("Đã thanh toán");
        rdoDa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoDaMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addComponent(rdoChua)
                                    .addGap(49, 49, 49)
                                    .addComponent(rdoDa))
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(cboPhuongThuc, javax.swing.GroupLayout.Alignment.LEADING, 0, 262, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cboNV, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(dcsFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btnSearchDate, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(dcsTo, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 2, Short.MAX_VALUE)))
                .addGap(29, 29, 29))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cboNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboPhuongThuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoChua)
                    .addComponent(rdoDa))
                .addGap(13, 13, 13)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addGap(12, 12, 12)
                .addComponent(dcsFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dcsTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSearchDate, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 94, 320, 450));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddMouseClicked
        // ADD
        QLBH_add add = new QLBH_add();
        add.setVisible(true);
    }//GEN-LAST:event_btnAddMouseClicked

    private void btnXemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXemMouseClicked
        // CHECK DETAILS
        
    }//GEN-LAST:event_btnXemMouseClicked

    private void btnHideMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHideMouseClicked
        // DELETE
        int pos = tblHoaDon.getSelectedRow();
        if (pos >= 0) {
            int result = JOptionPane.showConfirmDialog(this, "Bạn chắc chắn muốn xóa hóa đơn này?", "POLYPOLO xác nhận", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                Integer id = Integer.valueOf(tblHoaDon.getValueAt(pos, 0).toString());
                hdService.deleteHD(id);
                JOptionPane.showMessageDialog(this, "Xóa hóa đơn thành công!", "POLYPOLO thông báo", JOptionPane.INFORMATION_MESSAGE);
                loadTable(hdService.getListViewModel());
            } else if (result == JOptionPane.NO_OPTION) {
                JOptionPane.showMessageDialog(this, "Đã hủy thao tác xóa hóa đơn!", "POLYPOLO thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn trên bảng để xóa!", "POLYPOLO thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnHideMouseClicked

    private void btnExportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExportMouseClicked
        // EXPORT
//        try {
//            XSSFWorkbook workBook = new XSSFWorkbook();
//            XSSFSheet sheet = workBook.createSheet("Danh Sách Nhập Hàng POLYPOLO");
//
//            //STYLE TITLE
//            XSSFRow titleRow = sheet.createRow(0);
//            Cell titleCell = titleRow.createCell(0);
//            titleCell.setCellValue("Danh Sách Nhập Hàng POLYPOLO");
//            sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(0,0,0,8));
//            XSSFFont font = workBook.createFont();
//            font.setFontHeightInPoints((short) 19);
//            font.setBold(true);
//            XSSFCellStyle style = workBook.createCellStyle();
//            style.setFont(font);
//            style.setAlignment(HorizontalAlignment.CENTER);
//            style.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER);
//            titleCell.setCellStyle(style);
//
//            //DATE
//            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//            String currentDate = sdf.format(new Date());
//
//            SimpleDateFormat sdfHrs = new SimpleDateFormat("HH:mm:ss");
//            String currentTime = sdfHrs.format(new Date());
//
//            XSSFRow dateRow = sheet.createRow(1);
//            org.apache.poi.ss.usermodel.Cell dateCell = dateRow.createCell(0);
//            dateCell.setCellValue("Ngày: " + currentDate + " | Giờ: " +currentTime);
//            sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(1, 1, 0, 8)); // Merge từ cột 0 đến 9
//            XSSFCellStyle dateStyle = workBook.createCellStyle();
//            dateStyle.setAlignment(HorizontalAlignment.RIGHT);
//            dateCell.setCellStyle(dateStyle);
//
//            //STYLE FONT
//            Font headerFont = workBook.createFont();
//            headerFont.setBold(true);
//            headerFont.setFontHeightInPoints((short) 11);
//
//            CellStyle headerStyle = workBook.createCellStyle();
//            headerStyle.setFont(headerFont);
//            headerStyle.setAlignment(HorizontalAlignment.CENTER);
//
//            //ADD
//            XSSFRow row = null;
//            Cell cell = null;
//            row = sheet.createRow(3);
//
//            cell = row.createCell(0, org.apache.poi.ss.usermodel.CellType.STRING);
//            cell.setCellValue("STT");
//
//            cell = row.createCell(1, org.apache.poi.ss.usermodel.CellType.STRING);
//            Sheet s = cell.getSheet();
//            cell.setCellValue("Mã Phiếu");
//            s.autoSizeColumn(1);
//
//            cell = row.createCell(2, org.apache.poi.ss.usermodel.CellType.STRING);
//            cell.setCellValue("Nhà Cung Cấp");
//
//            cell = row.createCell(3, org.apache.poi.ss.usermodel.CellType.STRING);
//            cell.setCellValue("Nhân Viên");
//
//            cell = row.createCell(4, org.apache.poi.ss.usermodel.CellType.STRING);
//            cell.setCellValue("Ngày Tạo");
//
//            cell = row.createCell(5, org.apache.poi.ss.usermodel.CellType.STRING);
//            cell.setCellValue("Tổng Đơn");
//
//            for (int i = 0; i < 13; i++) {
//                cell = row.getCell(i);
//                cell.setCellStyle(headerStyle);
//            }
//
//            ArrayList<PhieuNhapViewModel> ls = pnService.getListPN();
//            for (int i = 0; i < ls.size(); i++) {
//                row = sheet.createRow(4 + i);
//
//                //STYLE FONT
//                CellStyle docuStyle = workBook.createCellStyle();
//                docuStyle.setAlignment(HorizontalAlignment.CENTER);
//
//                CellStyle cellStyleFormatNumber = null;
//
//                if (cellStyleFormatNumber == null) {
//                    short format = (short) BuiltinFormats.getBuiltinFormat("#,##0");
//                    Workbook workbook = row.getSheet().getWorkbook();
//                    cellStyleFormatNumber = workbook.createCellStyle();
//                    cellStyleFormatNumber.setDataFormat(format);
//                }
//
//                cell = row.createCell(0, org.apache.poi.ss.usermodel.CellType.NUMERIC);
//                cell.setCellValue(i + 1);
//                cell.setCellStyle(docuStyle);
//
//                cell = row.createCell(1, org.apache.poi.ss.usermodel.CellType.NUMERIC);
//                cell.setCellValue("PN" + ls.get(i).getMaPhieu());
//                cell.setCellStyle(docuStyle);
//
//                cell = row.createCell(2, org.apache.poi.ss.usermodel.CellType.STRING);
//                cell.setCellValue(ls.get(i).getTenNCC());
//                s.autoSizeColumn(2);
//
//                cell = row.createCell(3, org.apache.poi.ss.usermodel.CellType.STRING);
//                cell.setCellValue(ls.get(i).getTenNV());
//                s.autoSizeColumn(3);
//
//                cell = row.createCell(4, org.apache.poi.ss.usermodel.CellType.STRING);
//                cell.setCellValue(ls.get(i).getNgayL());
//                s.autoSizeColumn(4);
//
//                cell = row.createCell(5, org.apache.poi.ss.usermodel.CellType.STRING);
//                cell.setCellValue(ls.get(i).getTongDon());
//                cell.setCellStyle(cellStyleFormatNumber);
//                cell.setCellStyle(docuStyle);
//            }
//
//            //STYLE
//            CellStyle footerStyle = workBook.createCellStyle();
//            footerStyle.setFont(headerFont);
//            footerStyle.setAlignment(HorizontalAlignment.LEFT);
//
//            //ROW CUỐI
//            int rowS = 5 + ls.size();
//
//            XSSFRow tongSPRow = sheet.createRow(rowS);
//            org.apache.poi.ss.usermodel.Cell tongSPCell = tongSPRow.createCell(1);
//
//            tongSPCell.setCellValue("Tổng Đơn:");
//            tongSPCell.setCellStyle(footerStyle);
//            org.apache.poi.ss.usermodel.Cell tongSPCelll = tongSPRow.createCell(2);
//            tongSPCelll.setCellValue(ls.size());
//            sheet.autoSizeColumn(1);
//            sheet.autoSizeColumn(2);
//
//            org.apache.poi.ss.usermodel.Cell ngX = tongSPRow.createCell(7);
//            ngX.setCellValue("Người Xuất:");
//            ngX.setCellStyle(footerStyle);
//            org.apache.poi.ss.usermodel.Cell thongT = tongSPRow.createCell(8);
//            thongT.setCellValue(uService.getName(Login.dataStatic));
//            sheet.autoSizeColumn(7);
//            sheet.autoSizeColumn(8);
//
//            String defaultCurrentDirectoryPath = "C:\\Users\\X1\\OneDrive\\Documents\\Custom Office Templates";
//            JFileChooser fileChooser = new JFileChooser(defaultCurrentDirectoryPath);
//            fileChooser.setDialogTitle("Chọn nơi lưu file");
//            fileChooser.setAcceptAllFileFilterUsed(false);
//            FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel Files", "xlsx", "xls");
//            fileChooser.addChoosableFileFilter(filter);
//
//            int userSelection = fileChooser.showSaveDialog(null);
//
//            if (userSelection == JFileChooser.APPROVE_OPTION) {
//                File fileToSave = fileChooser.getSelectedFile();
//                String filePath = fileToSave.getAbsolutePath();
//                if (!filePath.endsWith(".xlsx")) {
//                    filePath += ".xlsx";
//                }
//
//                File file = new File(filePath);
//                try {
//                    FileOutputStream fis = new FileOutputStream(file);
//                    workBook.write(fis);
//                    fis.close();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                JOptionPane.showMessageDialog(this, "Đã in danh sách thành công!", "POLYPOLO thông báo", 0);
//            } else {
//                JOptionPane.showMessageDialog(null, "Thao tác đã bị hủy!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }//GEN-LAST:event_btnExportMouseClicked

    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnExportActionPerformed

    private void btnLoadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLoadMouseClicked
        // RELOAD
        
    }//GEN-LAST:event_btnLoadMouseClicked

    private void btnLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLoadActionPerformed

    private void tblHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMouseClicked
        // CLICK
    }//GEN-LAST:event_tblHoaDonMouseClicked

    private void cboNVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboNVMouseClicked
        // SEARCH NV
        String name = cboNV.getSelectedItem().toString();
        ArrayList<HoaDonViewModel> ls = hdService.getListByNV(name);
        loadTable(ls);
    }//GEN-LAST:event_cboNVMouseClicked

    private void btnSearchDateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSearchDateMouseClicked
        // SEARCH DATE
        Date from = dcsFrom.getDate();
        Date to = dcsTo.getDate();
        if (from != null && to != null) {
            java.sql.Date sqlFromDate = new java.sql.Date(from.getTime());
            java.sql.Date sqlToDate = new java.sql.Date(to.getTime());

            ArrayList<HoaDonViewModel> ls =hdService.getListByDate(sqlFromDate, sqlToDate);
            loadTable(ls);
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn đủ khoảng ngày để tìm!", "POLYPOLO thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnSearchDateMouseClicked

    private void cboPhuongThucMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboPhuongThucMouseClicked
        // SEARCH phuongT
        String name = cboPhuongThuc.getSelectedItem().toString();
        ArrayList<HoaDonViewModel> ls = hdService.getListByPhuongT(name);
        loadTable(ls);
    }//GEN-LAST:event_cboPhuongThucMouseClicked

    private void rdoChuaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoChuaMouseClicked
        // TT
        ArrayList<HoaDonViewModel> ls = hdService.getListByTrangT("Chưa thanh toán");
        loadTable(ls);
    }//GEN-LAST:event_rdoChuaMouseClicked

    private void rdoDaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoDaMouseClicked
        // TT
        ArrayList<HoaDonViewModel> ls = hdService.getListByTrangT("Đã thanh toán");
        loadTable(ls);
    }//GEN-LAST:event_rdoDaMouseClicked

    private void btnSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSearchMouseClicked
        // SEARCH
        if (validateTimKiem()) {
            if (cboSearch.getSelectedItem().equals("Tên Khách Hàng")) {
                String name = txtSearch.getText().trim();
                ArrayList<HoaDonViewModel> ls = hdService.getListByTenKH(name);
                loadTable(ls);
                txtSearch.setText("");
            }
        } else {
            txtSearch.setText("");
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một mục để lọc!", "POLYPOLO thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnSearchMouseClicked

    private void cboSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboSearchMouseClicked
        // ALL
        txtSearch.setBackground(Color.WHITE);
        loadTable(hdService.getListViewModel());
    }//GEN-LAST:event_cboSearchMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnHide;
    private javax.swing.JButton btnLoad;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnSearchDate;
    private javax.swing.JButton btnXem;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboNV;
    private javax.swing.JComboBox<String> cboPhuongThuc;
    private javax.swing.JComboBox<String> cboSearch;
    private com.toedter.calendar.JDateChooser dcsFrom;
    private com.toedter.calendar.JDateChooser dcsTo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JRadioButton rdoChua;
    private javax.swing.JRadioButton rdoDa;
    public javax.swing.JTable tblHoaDon;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
