/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Views.QLNH;

import Models.MyPurchase;
import Models.NCC;
import Models.NhanSu;
import Models.PhieuNhap;
import Models.PhieuNhapChiTiet;
import Services.NCCService;
import Services.PhieuNhapService;
import Services.UserService;
import Utils.SVGImage;
import Validator.MyValidate;
import ViewModels.PN_PhieuNhapViewModel;
import ViewModels.PN_SanPhamViewModel;
import Views.Login;
import Views.QLSP.QLSP_add;
import Views.ReadORCode;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.DashedBorder;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author X1
 */
public class QLNH_add extends javax.swing.JFrame {
    SVGImage svgSet = new SVGImage();
    PhieuNhapService pnService = new PhieuNhapService();
    NCCService nccservice = new NCCService();
    DecimalFormat formatter = new DecimalFormat("#,###");
    UserService uService = new UserService();
    
    /**
     * Creates new form QLNH
     */
    
    public QLNH_add() {
        initComponents();
        setLocationRelativeTo(null);
        load();
        setButtonIcon();
//        txtSearch.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String name = txtSearch.getText().trim();
//                txtSearch.setText(pnService.getSuggestSearch(name).toString());
//            }
//        });

    }
    
    void load(){
        loadCboPhuongThuc(pnService.getListPNCT());
        loadCboNV(pnService.getListNS());
        loadCboNCC(nccservice.getList());
        loadTableSP(pnService.getListSPViewModel());
        
        btnNhapHang.setEnabled(false);
        btnPrint.setEnabled(false);
        PhieuNhap pnNew = pnService.getModel();
        loadDataToForm(pnNew);
    }
    
    void setButtonIcon() {
        btnAdd.setIcon(svgSet.createSVGIcon("Images/SVG/add-p.svg", 17, 17));
        btnUpdate.setIcon(svgSet.createSVGIcon("Images/SVG/update.svg", 20, 20));
        btnClear.setIcon(svgSet.createSVGIcon("Images/SVG/clean.svg", 17, 17));
        btnPrint.setIcon(svgSet.createSVGIcon("Images/SVG/pdf-color.svg", 18, 18));
        btnDelete.setIcon(svgSet.createSVGIcon("Images/SVG/delete.svg", 15, 15));
        btnAddPhieu.setIcon(svgSet.createSVGIcon("Images/SVG/add.svg", 20, 20));
        btnNhapHang.setIcon(svgSet.createSVGIcon("Images/SVG/add-sp.svg", 25, 25));
        btnScan.setIcon(svgSet.createSVGIcon("Images/SVG/barcode.svg", 17, 17));
        btnNewProduct.setIcon(svgSet.createSVGIcon("Images/SVG/add-nhap.svg", 15, 15));
        getRootPane().setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY,2, true));
    }
    
    //<editor-fold defaultstate="collapsed" desc=" LOAD ">
    void loadCboPhuongThuc(ArrayList<PhieuNhapChiTiet> ls){
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboPhuongThuc.getModel();
        HashSet<String> pnSet = new HashSet<>();
        
        for (PhieuNhapChiTiet pn : ls) {
            String phuongT = pn.getPhuongThuc();
            if (!pnSet.contains(phuongT)) {
                model.addElement(phuongT);
                pnSet.add(phuongT);
            }
        }
    }
    
    void loadCboNV(ArrayList<NhanSu> ls) {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboTenNV.getModel();
        HashSet<String> nvSet = new HashSet<>();

        for (NhanSu pn : ls) {
            String nv = pn.getTenNhanVien();
            if (!nvSet.contains(nv)) {
                model.addElement(nv);
                nvSet.add(nv);
            }
        }
    }
    
    void loadCboNCC(ArrayList<NCC> ls) {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboNCC.getModel();
        HashSet<String> nccSet = new HashSet<>();

        for (NCC tenNCC : ls) {
            String ncc = tenNCC.getTenNCC();
            if (!nccSet.contains(ncc)) {
                model.addElement(ncc);
                nccSet.add(ncc);
            }
        }
    }
    
    void loadTableSP(ArrayList<PN_SanPhamViewModel> ls) {
        DefaultTableModel model = (DefaultTableModel) tblSP.getModel();
        model.setRowCount(0);
        for (PN_SanPhamViewModel sp : ls) {
            model.addRow(new Object[]{
                sp.getMaSPCT(), sp.getTenSP(), sp.getSoL()
            });
        }
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        TableColumnModel columnModel = tblSP.getColumnModel();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setCellRenderer(centerRenderer);
        }
    }
    
    void loadTablePhieu(ArrayList<PN_PhieuNhapViewModel> ls){
        DefaultTableModel model = (DefaultTableModel) tblNhapHang.getModel();
        model.setRowCount(0);
        for(PN_PhieuNhapViewModel pn : ls){
            Double giaN = pn.getGiaN();
            String formatgiaN = formatter.format(giaN);
            
            Double tongT = pn.getTongT();
            String formatTotal = formatter.format(tongT);
            
            model.addRow(new Object[]{
                pn.getIdPhieu(), pn.getIdSPCT(), pn.getTenSP(), pn.getDanhMuc()
                    , formatgiaN, pn.getSoL(), pn.getThue(), formatTotal
            });
        }
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        TableColumnModel columnModel = tblSP.getColumnModel();
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
    //</editor-fold>
    
    //GETMODEL
    public PhieuNhapChiTiet getModelPNCT(){
        Integer maP = Integer.valueOf(txtMaPhieu.getText());
        Integer maSPCT = Integer.valueOf(txtMaSP.getText().trim());
        
        Integer soL = Integer.valueOf(txtSoL.getText().trim());
        Integer thue = Integer.valueOf(txtThue.getText().trim());
        Double giaN = Double.valueOf(txtGiaNhap.getText().trim());
        String phuongT = (String) cboPhuongThuc.getSelectedItem();
        String moT = txtMoTa.getText().trim();
        
        PhieuNhapChiTiet pnct = new PhieuNhapChiTiet(maP, maSPCT, soL, thue, giaN, phuongT, moT);
        return pnct;
    }
    
    public PhieuNhap getModel(){
        Integer maP = pnService.getListPNCT().get(pnService.getListPNCT().size()-1).getMaP()+1;
        
        String tenNCC = cboNCC.getSelectedItem().toString();
        Integer ncc = pnService.getIdByNameNCC(tenNCC).getMaNCC();
        
        String tenNv = cboTenNV.getSelectedItem().toString();
        Integer maNV = pnService.getIdByNameNS(tenNv).getMaNhanVien();
        
        Date ngayN = dcsNgayNhap.getDate();
        String tongDText = lblTotal.getText();
        Double tongD = 0.0;
        DecimalFormat format = (DecimalFormat) DecimalFormat.getInstance();
        format.setParseBigDecimal(true);

        try {
            tongD = format.parse(tongDText).doubleValue();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        PhieuNhap pn = new PhieuNhap(maP, ncc, maNV, ngayN, tongD);
        return pn;
    }

    //LOADDATA
    void loadDataToForm(PhieuNhap pn) {
        Integer id = Integer.valueOf(pn.getIdPhieu().toString());
        txtMaPhieu.setText(id.toString());
        String tenNCC = pnService.getListByIdNCC(pn.getIdNCC()).getTenNCC();
        cboNCC.setSelectedItem(tenNCC);
        String tenNV = pnService.getListByIdNS(pn.getIdNV()).getTenNhanVien();
        cboTenNV.setSelectedItem(tenNV);
        dcsNgayNhap.setDate(pn.getNgayN());
    }
    
    //MYVALIDATE
    public Boolean validateSearch(){
        StringBuilder stb = new StringBuilder();
        MyValidate v = new MyValidate();
        
        v.isEmpty(txtSearch, stb, "Vui lòng nhập tên SP cần tìm!");
        if (stb.length() > 0) {
            JOptionPane.showMessageDialog(this, stb);
            return false;
        } else {
            return true;
        } 
    }
    public Boolean validateForm(){
        StringBuilder stb = new StringBuilder();
        MyValidate v = new MyValidate();
        
        v.isDateSelected(dcsNgayNhap, stb, "");
        v.isDateValid(dcsNgayNhap, stb, "Ngày nhập không hợp lệ!");
        
        if (stb.length() > 0) {
            JOptionPane.showMessageDialog(this, stb);
            return false;
        } else {
            return true;
        } 
    }
    public Boolean validateSP(){
        StringBuilder stb = new StringBuilder();
        MyValidate v = new MyValidate();
        
        v.isEmpty(txtSoL, stb, "Số lượng bị trống!");
        v.isEmpty(txtGiaNhap, stb, "Giá nhập bị trống!");
        v.isEmpty(txtThue, stb, "Giá thuế bị trống!");
        v.isNumber(txtSoL, stb, "Số lượng phải là kiểu số nguyên dương!", JOptionPane.ERROR_MESSAGE);
        v.isNumber(txtGiaNhap, stb, "Giá nhập phải là kiểu số nguyên!", JOptionPane.ERROR_MESSAGE);
        v.isNumber(txtThue, stb, "Thuế phải là kiểu số nguyên dương!", JOptionPane.ERROR_MESSAGE);
        
        if (stb.length() > 0) {
            JOptionPane.showMessageDialog(this, stb);
            return false;
        } else {
            return true;
        } 
    }
    
    //CLEAR FORM
    void clearFormSP(){
        txtSearch.setBackground(Color.white);
        txtGiaNhap.setText("");
        txtSoL.setText("");
        txtThue.setText("");
        txtGiaNhap.setBackground(Color.white);
        txtSoL.setBackground(Color.white);
        txtThue.setBackground(Color.white);
    }

    //ELEMENTS
    static Cell storeCell(String txt) {
        return new Cell().add(txt).setFontSize(15f).setBold().setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
    }
    static Cell get10fLeftCell(String txt, Boolean isBold) {
        Cell customerCell = new Cell().add(txt).setFontSize(12f).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
        return isBold ? customerCell.setBold():customerCell;
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
        txtSearch = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSP = new javax.swing.JTable();
        btnScan = new javax.swing.JButton();
        btnNewProduct = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtMaSP = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtTenSP = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtGiaNhap = new javax.swing.JTextField();
        txtSoL = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtThue = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cboPhuongThuc = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtMoTa = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtMaPhieu = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        cboTenNV = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        dcsNgayNhap = new com.toedter.calendar.JDateChooser();
        jLabel10 = new javax.swing.JLabel();
        cboNCC = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel14 = new javax.swing.JLabel();
        btnNhapHang = new javax.swing.JButton();
        btnAddPhieu = new javax.swing.JButton();
        lblTotal = new javax.swing.JLabel();
        btnPrint = new javax.swing.JButton();
        lblTax = new javax.swing.JLabel();
        btnUpdate = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblNhapHang = new javax.swing.JTable();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1150, 680));
        setMinimumSize(new java.awt.Dimension(1150, 680));
        setPreferredSize(new java.awt.Dimension(1150, 680));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Bảng Sản Phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 14))); // NOI18N

        tblSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Mã SPCT", "Tên Sản Phẩm", "Số Lượng"
            }
        ));
        tblSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSPMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblSP);

        btnScan.setText("QUÉT");
        btnScan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnScanMouseClicked(evt);
            }
        });

        btnNewProduct.setText(" NEW");
        btnNewProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNewProductMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnNewProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnScan, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtSearch))
                .addGap(15, 15, 15))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnScan, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                    .addComponent(btnNewProduct, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE))
                .addContainerGap())
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 15, 390, 300));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông Tin Sản Phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 14))); // NOI18N

        jLabel1.setText("Mã SP:");

        txtMaSP.setEnabled(false);

        jLabel4.setText("Tên SP:");

        txtTenSP.setEnabled(false);

        jLabel3.setText("Giá Nhập:");

        jLabel2.setText("Số Lượng");

        jLabel13.setText("%");

        jLabel15.setText("Thuế:");

        jLabel5.setText("Phương Thức:");

        jLabel6.setText("Mô Tả:");

        txtMoTa.setColumns(20);
        txtMoTa.setRows(5);
        jScrollPane3.setViewportView(txtMoTa);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTenSP)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addComponent(cboPhuongThuc, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(txtThue, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel5))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(txtGiaNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtSoL, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))))
                        .addContainerGap())))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cboPhuongThuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtGiaNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSoL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtThue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addContainerGap())
        );

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(417, 15, -1, 370));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông Tin Phiếu Nhập", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 24))); // NOI18N

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("PHÍ THUẾ:");

        txtMaPhieu.setEnabled(false);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel8.setText("Tên Nhân Viên:");

        cboTenNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTenNVActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel9.setText("Ngày Nhập:");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel10.setText("Nhà Cung Cấp:");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel12.setText("Mã Phiếu:");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(204, 0, 51));
        jLabel14.setText("TỔNG TIỀN:");

        btnNhapHang.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnNhapHang.setText("  NHẬP HÀNG");
        btnNhapHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNhapHangMouseClicked(evt);
            }
        });

        btnAddPhieu.setText("TẠO");
        btnAddPhieu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddPhieuMouseClicked(evt);
            }
        });

        lblTotal.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(204, 0, 51));
        lblTotal.setText("0");

        btnPrint.setText("IN PDF");
        btnPrint.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPrintMouseClicked(evt);
            }
        });

        lblTax.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblTax.setText("0");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(txtMaPhieu, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(dcsNgayNhap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cboNCC, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel10)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                            .addComponent(btnAddPhieu, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                            .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(btnNhapHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cboTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(72, 72, 72)
                            .addComponent(lblTax)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(58, 58, 58)
                        .addComponent(lblTotal)))
                .addGap(0, 24, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtMaPhieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(cboTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(dcsNgayNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cboNCC, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lblTax))
                .addGap(32, 32, 32)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(lblTotal))
                .addGap(29, 29, 29)
                .addComponent(btnNhapHang, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnPrint, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAddPhieu, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 10, 340, 640));

        btnUpdate.setText("SỬA");
        btnUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUpdateMouseClicked(evt);
            }
        });
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });
        getContentPane().add(btnUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 320, 90, 50));

        btnAdd.setText("THÊM");
        btnAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddMouseClicked(evt);
            }
        });
        getContentPane().add(btnAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, 90, 50));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chi Tiết Sản Phẩm Nhập", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 14))); // NOI18N

        tblNhapHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID ", "ID SP", "Tên SP", "Danh Mục", "Giá Nhập", "Số Lượng", "Thuế", "Tổng Tiền"
            }
        ));
        tblNhapHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhapHangMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblNhapHang);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 688, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 389, 710, 260));

        btnDelete.setText("XÓA");
        btnDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDeleteMouseClicked(evt);
            }
        });
        getContentPane().add(btnDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 320, 90, 50));

        btnClear.setText("CLEAR");
        btnClear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnClearMouseClicked(evt);
            }
        });
        getContentPane().add(btnClear, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 320, 90, 50));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnScanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnScanMouseClicked
        // SCAN
        ReadORCode qr = new ReadORCode();
        qr.setVisible(true);
        this.dispose();

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn ảnh barcode");

        // Chỉ chấp nhận các tệp hình ảnh
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Ảnh", "jpg", "jpeg", "png");
        fileChooser.setFileFilter(filter);

        int userSelection = fileChooser.showOpenDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            // Lấy đường dẫn tệp ảnh được chọn
            File selectedFile = fileChooser.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();

            try {
                // Đọc ảnh từ đường dẫn đã chọn
                BufferedImage image = ImageIO.read(new File(filePath));

                // Đọc mã vạch từ ảnh barcode
                String code = readBarcode(image);

                if (code != null) {
                    // Nếu mã vạch được đọc thành công, tiến hành tìm kiếm và cập nhật bảng
                    ArrayList<PN_SanPhamViewModel> ls = pnService.SearchCode(code);
                    loadTableSP(ls);
                } else {
                    System.out.println("Không thể đọc mã vạch từ ảnh.");
                }
            } catch (IOException ex) {
                System.out.println("Không thể đọc ảnh.");
            }
        }
    }

    private String readBarcode(BufferedImage image) {
        LuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        try {
            Result result = new MultiFormatReader().decode(bitmap);
            return result.getText();
        } catch (NotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }//GEN-LAST:event_btnScanMouseClicked

    private void btnDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDeleteMouseClicked
        // DELETE
        int pos = tblNhapHang.getSelectedRow();
        if (pos != -1) {
            int result = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa sản phẩm này?", "POLYPOLO xác nhận", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                Integer maP = Integer.valueOf(tblNhapHang.getValueAt(pos, 0).toString());
                Integer maSP = Integer.valueOf(tblNhapHang.getValueAt(pos, 1).toString());
                
                JOptionPane.showMessageDialog(this, pnService.deleteSP(maSP, maP), "POLYPOLO thông báo", JOptionPane.INFORMATION_MESSAGE);
                loadTablePhieu(pnService.getListPhieuById(maP));
            } else {
                JOptionPane.showMessageDialog(this, "Đã hủy thao tác xóa sản phẩm!", "POLYPOLO thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để xóa!", "POLYPOLO thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnDeleteMouseClicked

    private void btnUpdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUpdateMouseClicked
        // UPDATE
        int pos = tblNhapHang.getSelectedRow();
        int result = JOptionPane.showConfirmDialog(this, "Bạn muốn sửa sản phẩm này?", "POLYPOLO xác nhận", JOptionPane.YES_NO_OPTION);
        if (pos != -1) {
            Integer maP = Integer.valueOf(txtMaPhieu.getText());
            if (result == JOptionPane.YES_OPTION) {
                String kq = pnService.updateSPDetails(getModelPNCT());

                JOptionPane.showMessageDialog(this, kq, "POLYPOLO thông báo", JOptionPane.INFORMATION_MESSAGE);
                loadTablePhieu(pnService.getListPhieuById(maP));
                
                //SET TOTAL
                Integer tongTax = pnService.getTaxById(maP).getThue();
                String formatTax = formatter.format(tongTax);
                Double tongT = pnService.getTotalByID(maP).getTongD();
                String formatTotal = formatter.format(tongT);

                lblTotal.setText(formatTotal);
                lblTax.setText(formatTax);
                
            } else if (result == JOptionPane.NO_OPTION) {
                JOptionPane.showMessageDialog(this, "Đã hủy thao tác sửa sản phẩm!", "POLYPOLO thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để sửa!", "POLYPOLO thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnUpdateMouseClicked

    private void btnAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddMouseClicked
        // ADD SP
        if (tblSP.getSelectedRow() != -1 && validateSP()) {
            int pos = tblSP.getSelectedRow();
            Integer idSPCT = Integer.valueOf(tblSP.getValueAt(pos, 0).toString());
            Integer idPhieu = Integer.valueOf(txtMaPhieu.getText());
            
            txtMaSP.setText(tblSP.getValueAt(pos, 0).toString());
            txtTenSP.setText(tblSP.getValueAt(pos, 1).toString());
            
            Double giaN = Double.valueOf(txtGiaNhap.getText().trim());
            Integer tax = Integer.valueOf(txtThue.getText().trim());
            Integer soLuongNhap = Integer.parseInt(txtSoL.getText().trim());
            String phuongT = cboPhuongThuc.getSelectedItem().toString();
            String moT = txtMoTa.getText().trim();
            
            //CHECK + 
            if (pnService.checkExists(idPhieu, idSPCT)) {
                //MERGE
                Integer soLDaNhap = Integer.valueOf(pnService.checkPN(idSPCT, idPhieu).getSoL().toString());
                pnService.mergeSP(idSPCT, soLuongNhap + soLDaNhap, idPhieu);
                JOptionPane.showMessageDialog(this, "Lỗi! SP này đã được thêm vào phiếu!", "POLYPOLO thông báo", JOptionPane.INFORMATION_MESSAGE);
                btnAdd.setEnabled(false);
                clearFormSP();
            } else{
                PhieuNhapChiTiet pn = new PhieuNhapChiTiet(idPhieu, idSPCT, soLuongNhap, tax, giaN, phuongT, moT);
                JOptionPane.showMessageDialog(this, pnService.addPNCT(pn), "POLYPOLO thông báo", JOptionPane.INFORMATION_MESSAGE);
                clearFormSP();
            }
            //ADD
            PN_SanPhamViewModel sp = pnService.getListSPViewModel().get(pos);
            pnService.updateSP(sp.getSoL() + soLuongNhap, idSPCT);

            //RELOAD
            loadTablePhieu(pnService.getListPhieuById(idPhieu));
            adjustWidths(tblNhapHang);
        //    loadTableSP(pnService.getListSPViewModel());

            btnNhapHang.setEnabled(true);
            
            //SET TOTAL
            Integer tongTax = pnService.getTaxById(idPhieu).getThue();
            String formatTax = formatter.format(tongTax);
            Double tongT = pnService.getTotalByID(idPhieu).getTongD();
            String formatTotal = formatter.format(tongT);
            
            lblTotal.setText(formatTotal);
            lblTax.setText(formatTax);
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để nhập!","POLYPOLO thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnAddMouseClicked

    private void tblSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSPMouseClicked

    }//GEN-LAST:event_tblSPMouseClicked

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void cboTenNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTenNVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboTenNVActionPerformed

    private void btnAddPhieuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddPhieuMouseClicked
        // ADD
        if (validateForm()) {
            JOptionPane.showMessageDialog(this, pnService.addPhieu(getModel()));
            PhieuNhap pnNew = pnService.getModel();

            if (pnNew != null) {
                loadDataToForm(pnNew);
            } else {
                JOptionPane.showMessageDialog(this, "Không thể lấy thông tin phiếu nhập mới nhất!", "POLYPOLO thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnAddPhieuMouseClicked

    private void btnClearMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnClearMouseClicked
        // CLEAR
        clearFormSP();
    }//GEN-LAST:event_btnClearMouseClicked

    private void btnNhapHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNhapHangMouseClicked
        // NHAPHANG
        Integer idP = Integer.valueOf(txtMaPhieu.getText());
        pnService.updatetongDon(idP);
        JOptionPane.showMessageDialog(this, pnService.heh(idP), "POLYPOLO thông báo", JOptionPane.INFORMATION_MESSAGE);
        btnPrint.setEnabled(true);
    }//GEN-LAST:event_btnNhapHangMouseClicked

    private void btnPrintMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPrintMouseClicked
        // PRINT
        Integer idPhieu = Integer.valueOf(txtMaPhieu.getText());
        String fontPath = "C:\\Users\\X1\\OneDrive\\Documents\\resouces\\VietFontsWeb1_ttf\\vuArial.ttf";
        String logoPath = "C:\\Users\\X1\\OneDrive\\Documents\\NetBeansProjects\\POLYPOLO\\src\\Icons\\invoice-BW_logo.png";

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu hóa đơn");
        fileChooser.setSelectedFile(new File("stockupdate.pdf"));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF Documents", "pdf");
        fileChooser.setFileFilter(filter);

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String pathPDF = fileChooser.getSelectedFile().getAbsolutePath();
            if (!pathPDF.endsWith(".pdf")) {
                pathPDF += ".pdf";
            }
            
        try (PdfWriter pdfWriter = new PdfWriter(pathPDF); 
                PdfDocument pdfDocument = new PdfDocument(pdfWriter); Document doc = new Document(pdfDocument)) {

            pdfDocument.setDefaultPageSize(PageSize.A4);
            PdfFont font = PdfFontFactory.createFont(fontPath, PdfEncodings.IDENTITY_H, true);
            ImageData logo = ImageDataFactory.create(logoPath);

            Image iLogo = new Image(logo);
                iLogo.scaleToFit(200, Float.MAX_VALUE);
                float x = (pdfDocument.getDefaultPageSize().getWidth() - iLogo.getImageScaledWidth()) / 2;
                float y = (pdfDocument.getDefaultPageSize().getHeight() - iLogo.getImageScaledHeight()) / 2;
                iLogo.setFixedPosition(x, y);
                iLogo.setOpacity(0.2f).setFontSize(30f);
                doc.add(iLogo);

                float twoCol = 300f;
                float twoCol150 = twoCol + 150f;
                float twoColWidth[] = {twoCol150, twoCol};
                float fiveCol = 100f;
                float fiveColWidth[] = {40f, 210f, 70f, 95f, 95f};
                float sevenColWidth[] = {30f, 180f, 105f, 35f, 60f, 35f, 65f};
                float threeColWidth[] = {170,170,170};
                float fullWidth[] = {fiveCol * 5 + 10};

                //ROW 1
                Table table = new Table(twoColWidth);
                table.addCell(new Cell().add("PHIẾU NHẬP HÀNG").setFont(font).setBorder(Border.NO_BORDER).setBold().setFontSize(20f));
                table.addCell(new Cell().add("Polo Loves - Live, Love, Polo \nTel: 0375 908 159\n Add: 21 Châu Long, Ba Đình, HN").setBorder(Border.NO_BORDER).setFont(font));
                doc.add(table.setMarginBottom(12f));

                Border b = new SolidBorder(com.itextpdf.kernel.color.Color.LIGHT_GRAY, 2f);
                Table divider = new Table(fullWidth);
                divider.setBorder(b);
                doc.add(divider.setMarginBottom(12f));

                //ROW 2
                Table twoColTable = new Table(twoColWidth);
                twoColTable.addCell(storeCell("Thông tin Nhà Cung Cấp").setFont(font));
                doc.add(twoColTable.setMarginBottom(6f));

                ArrayList<MyPurchase> ls = pnService.getListSupplierById(idPhieu);
                for (int i = 0; i < ls.size(); i++) {
                    Table twoColTable2 = new Table(twoColWidth);
                    twoColTable2.addCell(get10fLeftCell("Nhà Cung Cấp:", true).setFont(font));
                    twoColTable2.addCell(get10fLeftCell("Stock Purchase No:", true).setFont(font));
                    twoColTable2.addCell(get10fLeftCell(ls.get(i).getTenNCC(), false).setFont(font));
                    twoColTable2.addCell(get10fLeftCell(String.valueOf("00" + ls.get(i).getMaNCC()), false));
                    twoColTable2.addCell(get10fLeftCell("Địa Chỉ:", true));
                    twoColTable2.addCell(get10fLeftCell("Số Điện Thoại:", true)).setFont(font);
                    twoColTable2.addCell(get10fLeftCell(ls.get(i).getDiaC(), false).setFont(font));
                    twoColTable2.addCell(get10fLeftCell(ls.get(i).getSdt(), false));
                    twoColTable2.addCell(get10fLeftCell("Email:", true).setFont(font));
                    twoColTable2.addCell(get10fLeftCell("Phương Thức:", true).setFont(font));
                    twoColTable2.addCell(get10fLeftCell(ls.get(i).getEmail(), false));
                    twoColTable2.addCell(get10fLeftCell(ls.get(i).getPhuongT(), false).setFont(font));

                    doc.add(twoColTable2.setMarginBottom(4f));
                }

                Table divider2 = new Table(fullWidth);
                Border bdg = new DashedBorder(com.itextpdf.kernel.color.Color.DARK_GRAY, 0.5f);
                doc.add(divider2.setBorder(bdg).setMarginBottom(7f));

                //ROW 3
                Paragraph thirdPara = new Paragraph("Danh Sách Sản Phẩm").setFont(font).setFontSize(15f).setMarginBottom(8f);
                doc.add(thirdPara.setBold());

                Table fiveColTable1 = new Table(sevenColWidth);
                fiveColTable1.setBackgroundColor(com.itextpdf.kernel.color.Color.BLACK, 0.7f);
                fiveColTable1.addCell(new Cell().add("STT").setBold().setFontColor(com.itextpdf.kernel.color.Color.WHITE).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));
                fiveColTable1.addCell(new Cell().add("Tên Sản Phẩm").setFont(font).setBold().setFontColor(com.itextpdf.kernel.color.Color.WHITE).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));
                fiveColTable1.addCell(new Cell().add("Danh Mục").setFont(font).setBold().setFontColor(com.itextpdf.kernel.color.Color.WHITE).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));
                fiveColTable1.addCell(new Cell().add("Qty").setFont(font).setBold().setFontColor(com.itextpdf.kernel.color.Color.WHITE).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));
                fiveColTable1.addCell(new Cell().add("Đơn Giá").setFont(font).setBold().setFontColor(com.itextpdf.kernel.color.Color.WHITE).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));
                fiveColTable1.addCell(new Cell().add("Thuế").setFont(font).setBold().setFontColor(com.itextpdf.kernel.color.Color.WHITE).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));
                fiveColTable1.addCell(new Cell().add("Tổng").setFont(font).setBold().setFontColor(com.itextpdf.kernel.color.Color.WHITE).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));
                doc.add(fiveColTable1);

                ArrayList<PN_PhieuNhapViewModel> lsSP = pnService.getListPhieuById(idPhieu);
                for (int i = 0; i < lsSP.size(); i++) {
                    Table fiveColTable2 = new Table(sevenColWidth);
                    fiveColTable2.addCell(new Cell().add(String.valueOf(i + 1)).setTextAlignment(TextAlignment.CENTER));
                    fiveColTable2.addCell(new Cell().add(lsSP.get(i).getTenSP()).setTextAlignment(TextAlignment.CENTER).setFont(font));
                    fiveColTable2.addCell(new Cell().add(String.valueOf(lsSP.get(i).getDanhMuc())).setTextAlignment(TextAlignment.CENTER));
                    fiveColTable2.addCell(new Cell().add(String.valueOf(lsSP.get(i).getSoL())).setTextAlignment(TextAlignment.CENTER));

                    double donG = lsSP.get(i).getGiaN();
                    String donGia = formatter.format(donG);
                    fiveColTable2.addCell(new Cell().add(donGia).setTextAlignment(TextAlignment.CENTER));

                    fiveColTable2.addCell(new Cell().add(String.valueOf(lsSP.get(i).getThue()) + "%").setTextAlignment(TextAlignment.CENTER));
                    
                    double thanhT = lsSP.get(i).getTongT();
                    String thanhTien = formatter.format(thanhT);
                    fiveColTable2.addCell(new Cell().add(thanhTien).setTextAlignment(TextAlignment.CENTER));

                    doc.add(fiveColTable2.setBorder(Border.NO_BORDER));
                }
        
                // ROW 4
                Table totalTable = new Table(fiveColWidth);
                totalTable.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
                totalTable.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
                double total = pnService.getTotalByID(idPhieu).getTongD();
                String tongT = formatter.format(total);
                Text boldText = new Text("Thành Tiền: ").setBold().setFontSize(15f).setFont(font);
                Text regularText = new Text(tongT + " VND").setFont(font).setFontSize(15f);
                Paragraph footPara = new Paragraph().add(boldText).add(regularText);
                totalTable.addCell(new Cell(1, 3).add(footPara).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.RIGHT));

                doc.add(totalTable.setMargins(12f, 0, 12f, 0));
                doc.add(divider.setMarginBottom(12f));

                // ROW 5
                Table termsTable = new Table(fullWidth);
                ArrayList<String> lsFooter = new ArrayList<>();
                lsFooter.add("• Tổng số tiền (viết bằng chữ):............................................................................................\n");
                lsFooter.add("• Số chứng từ gốc kèm theo:.............................................................................................\n");

                for (String tnc : lsFooter) {
                    termsTable.addCell(new Cell().add(tnc).setBorder(Border.NO_BORDER).setFont(font).setFontSize(13f));
                }

                doc.add(termsTable.setMarginBottom(12f));
                doc.add(divider.setMarginBottom(12f));

                //ROW 6
                Table dateTime = new Table(fullWidth);
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter date = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm:ss");
                String nowD = now.format(date);
                String nowT = now.format(time);

                dateTime.addCell(new Cell().add("Ngày Lập: " + nowD + " | Giờ: " + nowT ).setFont(font).setFontSize(13f).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER));
                doc.add(dateTime.setMarginBottom(12f));

                //ROW 7
                Table footerTable = new Table(threeColWidth);

                footerTable.addCell(new Cell().add("Người Lập Phiếu").setFont(font).setTextAlignment(TextAlignment.CENTER).setBold().setBorder(Border.NO_BORDER).setFontSize(15f));
                footerTable.addCell(new Cell().add("Thủ Kho").setFont(font).setTextAlignment(TextAlignment.CENTER).setBold().setBorder(Border.NO_BORDER).setFontSize(15f));
                footerTable.addCell(new Cell().add("Kế Toán Trưởng").setFont(font).setTextAlignment(TextAlignment.CENTER).setBold().setBorder(Border.NO_BORDER).setFontSize(15f));
                doc.add(footerTable);
                
                Table footerTable1 = new Table(threeColWidth);
                footerTable1.addCell(new Cell().add("(Ký, họ tên)").setFont(font).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER).setFontSize(13f).setItalic());
                footerTable1.addCell(new Cell().add("(Ký, họ tên)").setFont(font).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER).setFontSize(13f).setItalic());
                footerTable1.addCell(new Cell().add("(Ký, họ tên)").setFont(font).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER).setFontSize(13f).setItalic());
                doc.add(footerTable1.setMarginBottom(7f));
                
                Table footerTable2 = new Table(threeColWidth);
                footerTable2.addCell(new Cell().add(uService.getTenDN(Login.dataStatic)).setFont(font).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER).setFontSize(15f));
                doc.add(footerTable2);
                
                JOptionPane.showMessageDialog(this, "Đã in phiếu nhập hàng thành công!", "POLYPOLO thông báo", 0);
                doc.close();
                System.out.println("in thanh congg");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnPrintMouseClicked

    private void tblNhapHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhapHangMouseClicked
        // CLICK NHAP
        int pos = tblNhapHang.getSelectedRow();
        if (pos!=-1) {
            Integer maSPCT = Integer.valueOf(tblNhapHang.getValueAt(pos, 1).toString());
            Integer maPhieu = Integer.valueOf(txtMaPhieu.getText());
            txtMaSP.setText(pnService.getDataToLoad(maSPCT,maPhieu).getIdSPCT().toString());
            txtTenSP.setText(pnService.getDataToLoad(maSPCT,maPhieu).getTenSP());
            txtSoL.setText(pnService.getDataToLoad(maSPCT, maPhieu).getSoL().toString());
            txtGiaNhap.setText(pnService.getDataToLoad(maSPCT, maPhieu).getGiaN().toString());
            txtThue.setText(pnService.getDataToLoad(maSPCT, maPhieu).getThue().toString());
        }
    }//GEN-LAST:event_tblNhapHangMouseClicked

    private void btnNewProductMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNewProductMouseClicked
        // ADD
        QLSP_add spAdd = new QLSP_add();
        spAdd.setVisible(true);
    }//GEN-LAST:event_btnNewProductMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        FlatMacLightLaf.setup();
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QLNH_add().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnAddPhieu;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnNewProduct;
    private javax.swing.JButton btnNhapHang;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnScan;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cboNCC;
    private javax.swing.JComboBox<String> cboPhuongThuc;
    private javax.swing.JComboBox<String> cboTenNV;
    private com.toedter.calendar.JDateChooser dcsNgayNhap;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lblTax;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JTable tblNhapHang;
    public javax.swing.JTable tblSP;
    private javax.swing.JTextField txtGiaNhap;
    private javax.swing.JTextField txtMaPhieu;
    private javax.swing.JTextField txtMaSP;
    private javax.swing.JTextArea txtMoTa;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtSoL;
    private javax.swing.JTextField txtTenSP;
    private javax.swing.JTextField txtThue;
    // End of variables declaration//GEN-END:variables
}
