/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Views.QLBH;

import Models.HoaDonChiTiet;
import Models.HoaDon;
import Models.NhanSu;
import Models.SanPhamChiTiet;
import Services.HoaDonService;
import Services.UserService;
import Validator.MyValidate;
import ViewModels.R_GioHangViewModel;
import ViewModels.HD_InvoiceViewModel;
import ViewModels.HD_SanPhamViewModel;
import Views.Login;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
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
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.layout.LayoutResult;
import com.itextpdf.layout.property.TextAlignment;
import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 *
 * @author X1
 */
public class QLBH_add extends javax.swing.JFrame {
    HoaDonService hdService = new HoaDonService();
    UserService uService = new UserService();
    DecimalFormat formatter = new DecimalFormat("#,###");
    /**
     * Creates new form QuanLyBanHang
     */
    public QLBH_add() {
        initComponents();
        setLocationRelativeTo(null);
        load();
    }
    
    void load(){
        loadCboNV(hdService.getListNS());
        loadCboPhuongThuc(hdService.getAllHoaDon());
        adjustWidths(tblGioH);
        
        loadTableHD(hdService.getHD("Chưa thanh toán"));
        loadTableSanPham(hdService.getListSanPham());
        adjustWidths(tblSanPham);
        adjustWidths(tblHDTreo);
        
//        HoaDon hdNew = hdService.getModel();
//        loadDataToForm(hdNew);
    }
    
    //LOAD
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
    void loadTableHD(ArrayList<HoaDon> ls){
        DefaultTableModel model = (DefaultTableModel) tblHDTreo.getModel();
        model.setRowCount(0);
        for (HoaDon hd : ls) {
            String formatTongT = formatter.format(hd.getTongT()) + ".000";
            model.addRow(new Object[]{
                hd.getIdHD(), hd.getTenKH(), hd.getPhuongT(), formatTongT
            });
        }
    }
    void loadTableSanPham(ArrayList<HD_SanPhamViewModel> ls){
        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
        model.setRowCount(0);
        for (HD_SanPhamViewModel sp : ls) {
            String giaB = formatter.format(sp.getDonG());
            model.addRow(new Object[]{
                sp.getIdSP(), sp.getTenSP(), sp.getDanhM(), sp.getNhanH()
                    , sp.getMauS(), sp.getKichC(), sp.getChatL(),giaB, sp.getSoL()
            });
        }
    }
    void loadTableGioHang(ArrayList<R_GioHangViewModel> ls){
        DefaultTableModel model = (DefaultTableModel) tblGioH.getModel();
        model.setRowCount(0);
        for (R_GioHangViewModel gh : ls) {
            String giaB = formatter.format(gh.getDonG());
            String total = formatter.format(gh.getThanhT());
            String totalVAT = formatter.format(gh.getTongSauVAT());
            model.addRow(new Object[]{
               gh.getIdSPCT(), gh.getTenSP(), gh.getDanhM(), gh.getSoL()
                    , giaB, gh.getThue(), total, totalVAT
            });
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
    
    //GETMODEL
    public HoaDon getModel(){
        Integer id = hdService.getListHDCT().get(hdService.getListHDCT().size()-1).getMaHD()+1;
        
        String tenKH = txtTenKH.getText().trim();
        Integer idKH = hdService.getListKH().get(hdService.getListKH().size()-1).getMaKH()+1;
        
        String tenNV = cboTenNV.getSelectedItem().toString();
        Integer idNV = Integer.valueOf(hdService.getIdByNameNS(tenNV).getMaNhanVien());
        
        Double tongT = Double.valueOf(0);
        Date ngayN = dcsNgayLap.getDate();
        String trangT = hdService.getByHoaDon(id).getTrangT();
        String phuongT = cboPhuongThuc.getSelectedItem().toString();
        String loaiKH = "Khách Lẻ";
        
        HoaDon hd = new HoaDon(id, idKH, idNV, trangT, tenKH, phuongT, loaiKH, tongT, ngayN);
        return hd;
    }
    
    public HoaDon getModelMember(){
        Integer id = hdService.getListHDCT().get(hdService.getListHDCT().size()-1).getMaHD()+1;
        
        String sdt = txtSearchSDT.getText().trim();
        String tenKH = hdService.getListKHBySDT(sdt).getTenKH();
        
        Integer idKH = hdService.getKHBySDT(sdt).getMaKH();
        
        String tenNV = cboTenNV.getSelectedItem().toString();
        Integer idNV = Integer.valueOf(hdService.getIdByNameNS(tenNV).getMaNhanVien());
        
        Double tongT = Double.valueOf(0);
        Date ngayN = dcsNgayLap.getDate();
        String trangT = hdService.getByHoaDon(id).getTrangT();
        String phuongT = cboPhuongThuc.getSelectedItem().toString();
        
        String loaiKH = hdService.checkKH(sdt) ? "Thành Viên" : "Khách Lẻ";

        HoaDon hd = new HoaDon(id, idKH, idNV, trangT, tenKH, phuongT, loaiKH, tongT, ngayN);
        return hd;
    }
    
    
    void loadDataToForm(HoaDon hd){
        Integer id = Integer.valueOf(hd.getIdHD());
        txtId.setText(id.toString());
        txtTenKH.setText(hdService.getByHoaDon(id).getTenKH());
        
        Integer idNV = hdService.getByHoaDon(id).getIdNV();
        String tenNV = String.valueOf(hdService.getNSById(idNV).getTenNhanVien());
        cboTenNV.setSelectedItem(tenNV);
        
        double total = hdService.getTotal(id).getTongT();
        lblTongDon.setText((String.format("%,.3f", total)));
        
        lblPhiVAT.setText(hdService.getVATFee(id).toString());
        cboPhuongThuc.setSelectedItem(hdService.getByHoaDon(id).getPhuongT());
        dcsNgayLap.setDate(hdService.getByHoaDon(id).getNgayL());
    }
    
    void loadDataToFormKH(HoaDon hd){
        Integer id = Integer.valueOf(hd.getIdHD());
        txtId.setText(id.toString());

        String sdt = txtSearchSDT.getText().trim();
        txtTenKH.setText(hdService.getListKHBySDT(sdt).getTenKH());
        
        Integer idNV = hdService.getByHoaDon(id).getIdNV();
        String tenNV = String.valueOf(hdService.getNSById(idNV).getTenNhanVien());
        cboTenNV.setSelectedItem(tenNV);
        
        double total = hdService.getTotal(id).getTongT();
        lblTongDon.setText((String.format("%,.3f", total)));
        
        lblPhiVAT.setText(hdService.getVATFee(id).toString());
        cboPhuongThuc.setSelectedItem(hdService.getByHoaDon(id).getPhuongT());
        dcsNgayLap.setDate(hdService.getByHoaDon(id).getNgayL());
    }
    
    //MY VALIDATE
    public Boolean validateForm(){
        StringBuilder stb = new StringBuilder();
        MyValidate v = new MyValidate();
        
        v.isEmpty(txtTenKH, stb, "Tên khách hàng bị trống!");
        v.isDateSelected(dcsNgayLap, stb, "Ngày tạo bị trống!");
    //    v.isDateValid(dcsNgayLap, stb, "Ngày nhập không hợp lệ!");
        
        if (stb.length() > 0) {
            JOptionPane.showMessageDialog(this, stb);
            return false;
        } else {
            return true;
        }
    }
    
    public Boolean validateReceipt(){
        StringBuilder stb = new StringBuilder();
        MyValidate v = new MyValidate();
        
        v.isEmpty(txtReceived, stb, "Tiền nhận bị trống!");
        
        if (stb.length() > 0) {
            JOptionPane.showMessageDialog(this, stb);
            return false;
        } else {
            return true;
        }
    }

    
    //CLEAR FORM
    void clearForm(){
        lblTongDon.setText("0");
        txtSearchSDT.setText("");
        txtId.setText("");
        txtTenKH.setText("");
        lblTongDon.setText("0");
        lblTienThua.setText("0");
        lblPhiVAT.setText("0");
        txtTenKH.setBackground(Color.white);
        dcsNgayLap.setBackground(Color.white);
        lblTongSP.setText("0");
    }
    void clearTable(){
        DefaultTableModel model = (DefaultTableModel) tblGioH.getModel();
        model.setRowCount(0);
    }
    void clearTableSPCT(){
        txtIdSP.setText("");
        txtTenSP.setText("");
        txtBrand.setText("");
        txtChatL.setText("");
        txtMau.setText("");
        txtSz.setText("");
    }
    
    
    //INVOICE ELEMENTS
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

        jPanel3 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblGioH = new javax.swing.JTable();
        jLabel31 = new javax.swing.JLabel();
        lblTongSP = new javax.swing.JLabel();
        btnXoa = new javax.swing.JButton();
        btnEmptyBasket = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtSearchSDT = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        cboPhuongThuc = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        cboTenNV = new javax.swing.JComboBox<>();
        jSeparator1 = new javax.swing.JSeparator();
        btnSearchKH = new javax.swing.JButton();
        txtTenKH = new javax.swing.JTextField();
        txtId = new javax.swing.JTextField();
        dcsNgayLap = new com.toedter.calendar.JDateChooser();
        btnThanhToan = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel27 = new javax.swing.JLabel();
        txtReceived = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        lbllll = new javax.swing.JLabel();
        lblTongDon = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        btnPrintPDF = new javax.swing.JButton();
        jLabel32 = new javax.swing.JLabel();
        lblTienThua = new javax.swing.JLabel();
        lblPhiVAT = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        cboDanhMuc = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        btnScan = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblHDTreo = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        txtIdSP = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        txtBrand = new javax.swing.JTextField();
        txtTenSP = new javax.swing.JTextField();
        txtSz = new javax.swing.JTextField();
        txtChatL = new javax.swing.JTextField();
        txtMau = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP)), "Giỏ Hàng", javax.swing.border.TitledBorder.LEADING, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Segoe UI", 3, 14))); // NOI18N
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel3.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 380, -1));

        tblGioH.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Sản Phẩm", "Danh Mục", "Số Lượng", "Đơn Giá", "Thuế", "Sau VAT", "Thành Tiền"
            }
        ));
        tblGioH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblGioHMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblGioH);

        jPanel3.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 740, 170));

        jLabel31.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel31.setText("Tổng Sản Phẩm:");
        jPanel3.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, -1, -1));

        lblTongSP.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblTongSP.setText("0");
        jPanel3.add(lblTongSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 240, -1, -1));

        btnXoa.setText("XÓA SP");
        btnXoa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnXoaMouseClicked(evt);
            }
        });
        jPanel3.add(btnXoa, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 240, 120, 30));

        btnEmptyBasket.setText("XÓA GIỎ HÀNG");
        btnEmptyBasket.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEmptyBasketMouseClicked(evt);
            }
        });
        jPanel3.add(btnEmptyBasket, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 240, 120, 30));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 760, 280));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tạo Hóa Đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Segoe UI", 1, 18), new java.awt.Color(0, 0, 204))); // NOI18N
        jPanel2.setMaximumSize(new java.awt.Dimension(401, 409));
        jPanel2.setMinimumSize(new java.awt.Dimension(401, 409));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setText("Tên KH:");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 158, 51, -1));

        jLabel7.setText("Người Tạo:");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 410, 67, -1));
        jPanel2.add(txtSearchSDT, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 67, 187, -1));

        jLabel13.setText("PTTT:");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 490, -1, -1));

        jPanel2.add(cboPhuongThuc, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 490, 211, -1));

        jLabel14.setText("Ngày Lập:");
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 450, 67, -1));

        jPanel2.add(cboTenNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 410, 211, -1));
        jPanel2.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 108, 299, 13));

        btnSearchKH.setText("SEARCH");
        btnSearchKH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSearchKHMouseClicked(evt);
            }
        });
        jPanel2.add(btnSearchKH, new org.netbeans.lib.awtextra.AbsoluteConstraints(225, 67, 90, -1));
        jPanel2.add(txtTenKH, new org.netbeans.lib.awtextra.AbsoluteConstraints(109, 155, 200, -1));

        txtId.setEnabled(false);
        jPanel2.add(txtId, new org.netbeans.lib.awtextra.AbsoluteConstraints(109, 127, 159, -1));
        jPanel2.add(dcsNgayLap, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 450, 167, -1));

        btnThanhToan.setText("THANH TOÁN");
        btnThanhToan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThanhToanMouseClicked(evt);
            }
        });
        jPanel2.add(btnThanhToan, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 540, 302, 52));

        btnAdd.setText("TẠO ĐƠN");
        btnAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddMouseClicked(evt);
            }
        });
        jPanel2.add(btnAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 610, 93, 48));

        btnClear.setText("MỚI");
        btnClear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnClearMouseClicked(evt);
            }
        });
        jPanel2.add(btnClear, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 610, 87, 48));

        jLabel16.setText("Mã Hóa Đơn:");
        jPanel2.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, -1, -1));

        jLabel18.setText("SĐT:");
        jPanel2.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 39, -1, -1));
        jPanel2.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 189, 300, 10));

        jLabel27.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel27.setText("Khách Đưa:");
        jPanel2.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 271, -1, -1));
        jPanel2.add(txtReceived, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 295, 293, -1));

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel28.setText("Tiền Thừa:");
        jPanel2.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 329, -1, -1));

        lbllll.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbllll.setForeground(new java.awt.Color(204, 0, 51));
        lbllll.setText("Tổng Đơn:");
        jPanel2.add(lbllll, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 205, 80, -1));

        lblTongDon.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTongDon.setForeground(new java.awt.Color(204, 0, 51));
        lblTongDon.setText("0");
        jPanel2.add(lblTongDon, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 237, -1, -1));
        jPanel2.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 395, 302, 13));
        jPanel2.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 520, 302, 11));

        btnPrintPDF.setText("IN PDF");
        btnPrintPDF.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPrintPDFMouseClicked(evt);
            }
        });
        jPanel2.add(btnPrintPDF, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 610, -1, 48));

        jLabel32.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel32.setText("Phí VAT:");
        jPanel2.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 200, 65, 30));

        lblTienThua.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTienThua.setText("0");
        jPanel2.add(lblTienThua, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 361, -1, -1));

        lblPhiVAT.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblPhiVAT.setText("0");
        jPanel2.add(lblPhiVAT, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 230, 60, 30));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 10, 330, 680));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP)), "Danh Sách Sản Phẩm", javax.swing.border.TitledBorder.LEADING, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Segoe UI", 3, 14))); // NOI18N

        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Tên Sản Phẩm", "Danh Mục", "Brand", "Màu", "Size", "Chất Liệu", "Đơn Giá", "Tồn"
            }
        ));
        tblSanPham.getTableHeader().setReorderingAllowed(false);
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseEntered(evt);
            }
        });
        jScrollPane1.setViewportView(tblSanPham);

        jLabel1.setText("Lọc:");

        btnScan.setText("SCAN");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 742, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(cboDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnScan, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cboDanhMuc)
                    .addComponent(btnScan))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65))
        );

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 460, 760, 230));

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hóa Đơn Treo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 14))); // NOI18N
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblHDTreo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Tên KH", "Phương Thức", "Tổng Đơn"
            }
        ));
        tblHDTreo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHDTreoMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblHDTreo);

        jPanel5.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 28, 370, 121));

        getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 400, 160));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chi Tiết Sản Phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 14))); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel19.setText("ID:");
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 30, -1));

        txtIdSP.setEnabled(false);
        jPanel1.add(txtIdSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 30, 60, -1));

        jLabel26.setText("Brand:");
        jPanel1.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 30, -1, -1));

        txtBrand.setEnabled(false);
        jPanel1.add(txtBrand, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 30, 130, -1));

        txtTenSP.setEnabled(false);
        jPanel1.add(txtTenSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(79, 60, 250, -1));

        txtSz.setEnabled(false);
        jPanel1.add(txtSz, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 90, 120, -1));

        txtChatL.setEnabled(false);
        jPanel1.add(txtChatL, new org.netbeans.lib.awtextra.AbsoluteConstraints(79, 130, 250, -1));

        txtMau.setEnabled(false);
        jPanel1.add(txtMau, new org.netbeans.lib.awtextra.AbsoluteConstraints(79, 90, 80, -1));

        jLabel30.setText("Tên SP:");
        jPanel1.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        jLabel33.setText("Màu:");
        jPanel1.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 40, 20));

        jLabel35.setText("Size:");
        jPanel1.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 90, -1, 20));

        jLabel34.setText("Chất Liệu:");
        jPanel1.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 10, 350, 170));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblGioHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblGioHMouseClicked
        // LOAD SPCT
        int pos = tblGioH.getSelectedRow();
        Integer id = Integer.valueOf(tblGioH.getValueAt(pos, 0).toString());
        txtIdSP.setText(hdService.getSPByIdSP(id).getIdSP().toString());
        txtTenSP.setText(hdService.getSPByIdSP(id).getTenSP());
        txtBrand.setText(hdService.getSPByIdSP(id).getNhanHang());
        txtChatL.setText(hdService.getSPByIdSP(id).getChatLieu());
        txtMau.setText(hdService.getSPByIdSP(id).getMauSac());
        txtSz.setText(hdService.getSPByIdSP(id).getKichCo());
    }//GEN-LAST:event_tblGioHMouseClicked

    private void btnSearchKHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSearchKHMouseClicked
        //SEARCH
        String sdt = txtSearchSDT.getText().trim();
        if (!hdService.checkKH(sdt)) {
            JOptionPane.showMessageDialog(this, "Khách hàng không tồn tại trong hệ thống!", "POLYPOLO thông báo", JOptionPane.ERROR_MESSAGE);
            txtSearchSDT.setText("");
        } else {
            loadDataToFormKH(getModelMember());
        }
    }//GEN-LAST:event_btnSearchKHMouseClicked

    private void btnThanhToanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThanhToanMouseClicked
        //THANH TOÁN
        if (!txtReceived.getText().trim().isEmpty()) {
            Integer id = Integer.valueOf(txtId.getText().toString());

            String receivedText = txtReceived.getText().trim().replace(".", "");
            Double tienNhan = Double.valueOf(receivedText);

            String totalText = lblTongDon.getText().replace(".", "").replace(",", "");
            Double total = Double.valueOf(totalText);

            if (tienNhan < total) {
                JOptionPane.showMessageDialog(this, "Tiền nhận không đủ để thanh toán đơn hàng.", "POLYPOLO thông báo", JOptionPane.ERROR_MESSAGE);
                txtReceived.setText("");
            } else {
                Double tienThua = tienNhan - total;

                lblTienThua.setText(String.format("%,.0f", tienThua));

                JOptionPane.showMessageDialog(this, hdService.updateThanhToan(id), "POLYPOLO thông báo", JOptionPane.INFORMATION_MESSAGE);
                //    clearForm();
                loadTableHD(hdService.getHD("Chưa thanh toán"));
            }
        }else{
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tiền nhận!", "POLYPOLO thông báo", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnThanhToanMouseClicked

    private void btnAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddMouseClicked
        // TẠO HĐ
        if (validateForm()) {
            JOptionPane.showMessageDialog(this, hdService.addHD(getModel()));
            HoaDon hdNew = hdService.getModel();

            if (hdNew != null) {
                loadDataToForm(hdNew);
            } else {
                JOptionPane.showMessageDialog(this, "Không thể lấy thông tin hóa đơn mới nhất!", "POLYPOLO thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnAddMouseClicked

    private void btnClearMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnClearMouseClicked
        // RESET
        clearForm();
        clearTableSPCT();
        clearTable();
        loadTableHD(hdService.getHD("Chưa thanh toán"));
    }//GEN-LAST:event_btnClearMouseClicked

    private void btnPrintPDFMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPrintPDFMouseClicked
        // INVOICE
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu hóa đơn");
        fileChooser.setSelectedFile(new File("invoice.pdf"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("PDF Documents", "pdf"));

        int uSelection = fileChooser.showSaveDialog(null);

        if (uSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String path = fileToSave.getAbsolutePath();
            if (!path.endsWith(".pdf")) {
                path += ".pdf";
            }
            
            Integer maHD = Integer.valueOf(txtId.getText());
            String trangT = hdService.getByHoaDon(maHD).getTrangT();
            String fontPath = "C:\\Users\\X1\\OneDrive\\Documents\\resouces\\VietFontsWeb1_ttf\\vuArial.ttf";
            String logoPath = "C:\\Users\\X1\\OneDrive\\Documents\\NetBeansProjects\\POLYPOLO\\src\\Icons\\invoice-BW_logo.png";
            String qrPath = "C:\\Users\\X1\\OneDrive\\Documents\\NetBeansProjects\\POLYPOLO\\src\\Icons\\invoice-qr.png";

            if (trangT.equalsIgnoreCase("Đã thanh toán")) {
                try (PdfWriter pdfWriter = new PdfWriter(path); PdfDocument pdfDocument = new PdfDocument(pdfWriter); Document doc = new Document(pdfDocument)) {

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
                    float nawWidth[] = {twoCol};
                    float twoColWidth[] = {twoCol150, twoCol};
                    float sixColWidth[] = {40f, 180f, 70f, 95f, 40f, 100f};
                    float fullWidth[] = {525f};

                    //ROW 1 
                    Table table = new Table(twoColWidth);
                    table.addCell(new Cell().add("HÓA ĐƠN").setFont(font).setBorder(Border.NO_BORDER).setBold().setFontSize(20f));
                    table.addCell(new Cell().add("Polo Loves - Live, Love, Polo \nTel: 0375 908 159\n Add: 21 Châu Long, Ba Đình, HN").setBorder(Border.NO_BORDER).setFont(font));
                    doc.add(table.setMarginBottom(12f));

                    Border b = new SolidBorder(com.itextpdf.kernel.color.Color.LIGHT_GRAY, 2f);
                    Table divider = new Table(fullWidth);
                    divider.setBorder(b);
                    doc.add(divider.setMarginBottom(12f));

                    //ROW 2
                    Table twoColTable = new Table(twoColWidth);
                    twoColTable.addCell(storeCell("Thông tin Khách Hàng").setFont(font));
                    doc.add(twoColTable.setMarginBottom(6f));

                    ArrayList<HD_InvoiceViewModel> lsKH = hdService.getListKHById(maHD);
                    for (int i = 0; i < lsKH.size(); i++) {
                        Table twoColTable2 = new Table(twoColWidth);
                        twoColTable2.addCell(get10fLeftCell("Họ Tên:", true).setFont(font));
                        twoColTable2.addCell(get10fLeftCell("Invoice No:", true).setFont(font));
                        twoColTable2.addCell(get10fLeftCell(lsKH.get(i).getTenKhachHang(), false).setFont(font));
                        twoColTable2.addCell(get10fLeftCell(String.valueOf("HD00" + lsKH.get(i).getIdHD()), false));
                        twoColTable2.addCell(get10fLeftCell("Loại Khách Hàng: ", true)).setFont(font);
                        twoColTable2.addCell(get10fLeftCell("Phương Thức Thanh Toán:", true).setFont(font));
                        twoColTable2.addCell(get10fLeftCell(lsKH.get(i).getLoaiKH(), false));
                        twoColTable2.addCell(get10fLeftCell(lsKH.get(i).getPhuongThuc(), false).setFont(font));

                        doc.add(twoColTable2.setMarginBottom(4f));
                    }

                    Table divider2 = new Table(fullWidth);
                    Border bdg = new DashedBorder(com.itextpdf.kernel.color.Color.DARK_GRAY, 0.5f);
                    doc.add(divider2.setBorder(bdg).setMarginBottom(7f));

                    //ROW 3
                    Paragraph thirdPara = new Paragraph("Danh Sách Sản Phẩm").setFont(font).setFontSize(15f).setMarginBottom(8f);
                    doc.add(thirdPara.setBold());

                    Table fiveColTable1 = new Table(sixColWidth);
                    fiveColTable1.setBackgroundColor(com.itextpdf.kernel.color.Color.BLACK, 0.7f);
                    fiveColTable1.addCell(new Cell().add("STT").setBold().setFontColor(com.itextpdf.kernel.color.Color.WHITE).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));
                    fiveColTable1.addCell(new Cell().add("Tên Sản Phẩm").setFont(font).setBold().setFontColor(com.itextpdf.kernel.color.Color.WHITE).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));
                    fiveColTable1.addCell(new Cell().add("Số Lượng").setFont(font).setBold().setFontColor(com.itextpdf.kernel.color.Color.WHITE).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));
                    fiveColTable1.addCell(new Cell().add("Đơn Giá").setFont(font).setBold().setFontColor(com.itextpdf.kernel.color.Color.WHITE).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));
                    fiveColTable1.addCell(new Cell().add("VAT(%)").setFont(font).setBold().setFontColor(com.itextpdf.kernel.color.Color.WHITE).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));
                    fiveColTable1.addCell(new Cell().add("Thành Tiền").setFont(font).setBold().setFontColor(com.itextpdf.kernel.color.Color.WHITE).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));
                    doc.add(fiveColTable1);

                    ArrayList<R_GioHangViewModel> lsHD = hdService.printInvoiceById(maHD);
                    for (int i = 0; i < lsHD.size(); i++) {
                        Table fiveColTable2 = new Table(sixColWidth);
                        fiveColTable2.addCell(new Cell().add(String.valueOf(i + 1)).setTextAlignment(TextAlignment.CENTER));
                        fiveColTable2.addCell(new Cell().add(lsHD.get(i).getTenSP()).setTextAlignment(TextAlignment.CENTER).setFont(font));
                        fiveColTable2.addCell(new Cell().add(String.valueOf(lsHD.get(i).getSoL())).setTextAlignment(TextAlignment.CENTER));

                        double donG = lsHD.get(i).getDonG();
                        String donGia = formatter.format(donG) + ".000";
                        fiveColTable2.addCell(new Cell().add(donGia).setTextAlignment(TextAlignment.CENTER));

                        fiveColTable2.addCell(new Cell().add(String.valueOf(lsHD.get(i).getThue())).setTextAlignment(TextAlignment.CENTER));
                        
                        double thanhT = lsHD.get(i).getThanhT();
                        String thanhTien = formatter.format(thanhT) + ".000";
                        fiveColTable2.addCell(new Cell().add(thanhTien).setTextAlignment(TextAlignment.CENTER));

                        doc.add(fiveColTable2.setBorder(Border.NO_BORDER));
                    }

                    // ROW 4        
                    Table totalTable = new Table(sixColWidth);
                    totalTable.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
                    totalTable.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
                    totalTable.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
                    double total = hdService.getTotal(maHD).getTongT();
                    String tongT = formatter.format(total);
                    Text boldText = new Text("Tổng tiền: ").setBold().setFontSize(13f).setFont(font);
                    Text regularText = new Text(tongT + ".000 VND").setFont(font).setFontSize(13f);
                    Paragraph footPara = new Paragraph().add(boldText).add(regularText);
                    totalTable.addCell(new Cell(1, 3).add(footPara).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.RIGHT));

                    doc.add(totalTable.setMargins(12f, 0, 12f, 0));
                    doc.add(divider.setMarginBottom(12f));

                    // ROW 5
                    Table termsTable = new Table(fullWidth);
                    termsTable.addCell(new Cell().add("Điều kiện & Chính sách đổi trả:").setFont(font).setBold().setBorder(Border.NO_BORDER)).setFontSize(13f);
                    ArrayList<String> ls = new ArrayList<>();
                    ls.add("• Khách hàng được đổi hàng hoặc trả hàng hoàn tiền trong vòng 7 ngày sau khi nhận được hàng.\n");
                    ls.add("• Sản phẩm phải còn nguyên tem mác, nguyên trạng như lúc nhận hàng và có hóa đơn mua hàng.\n");
                    ls.add("• Việc trả hàng chỉ được thực hiện khi sản phẩm bị lỗi hoặc những sự cố phát sinh do lỗi từ phía shop.\n");

                    for (String tnc : ls) {
                        termsTable.addCell(new Cell().add(tnc).setBorder(Border.NO_BORDER).setFont(font).setFontSize(11f));
                    }

                    doc.add(termsTable.setMarginBottom(12f));
                    doc.add(divider2.setMarginBottom(8f));
                    doc.add(divider.setMarginBottom(12f));

                    //ROW 6
                    Table dateTime = new Table(fullWidth);
                    LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter date = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm:ss");
                    String nowD = now.format(date);
                    String nowT = now.format(time);

                    dateTime.addCell(new Cell().add("**** " + nowD + " | " + nowT + " ****").setFont(font).setFontSize(13f).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER));
                    doc.add(dateTime);

                    //ROW 7
                    Table ngX = new Table(fullWidth);
                    ngX.addCell(new Cell().add("( " + uService.getTenDN(Login.dataStatic) + " )").setFont(font).setFontSize(13f).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER));
                    doc.add(ngX.setMarginBottom(10f));

                    Table divider3 = new Table(nawWidth);
                    Border fin = new DashedBorder(com.itextpdf.kernel.color.Color.DARK_GRAY, 0.5f);
                    doc.add(divider3.setBorder(fin).setMarginBottom(12f).setHorizontalAlignment(com.itextpdf.layout.property.HorizontalAlignment.CENTER));

                    //ROW 8
                    float bottomMargin = 20;
                    float qrHeight = 80;
                    float spaceBetweenQRAndFooter = 10;
                    float pageWidth = pdfDocument.getDefaultPageSize().getWidth();
                    float pageHeight = pdfDocument.getDefaultPageSize().getHeight();

                    ImageData qr = ImageDataFactory.create(qrPath);
                    Image iQR = new Image(qr).scaleToFit(qrHeight, qrHeight);
                    float qrXPosition = (pageWidth - iQR.getImageScaledWidth()) / 2;

                    float qrYPosition = bottomMargin;
                    iQR.setFixedPosition(qrXPosition, qrYPosition);
                    doc.add(iQR);
                    float tableWidth = pageWidth - (2 * bottomMargin);
                    Table footerTable = new Table(1);
                    footerTable.setWidth(tableWidth);

                    footerTable.addCell(new Cell().add("Cảm ơn Quý Khách vì đã mua hàng!\n").setFont(font).setTextAlignment(TextAlignment.CENTER).setBold().setBorder(Border.NO_BORDER).setFontSize(14f));
                    footerTable.addCell(new Cell().add("Hòm thư đóng góp ý kiến: polypolo@email.com").setFont(font).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER).setFontSize(12f));

                    LayoutResult result = footerTable.createRendererSubTree().setParent(doc.getRenderer()).layout(new LayoutContext(new LayoutArea(0, new Rectangle(pageWidth, 1000))));
                    float tableHeight = result.getOccupiedArea().getBBox().getHeight();
                    float footerYPosition = qrYPosition + qrHeight + spaceBetweenQRAndFooter;
                    footerTable.setFixedPosition(bottomMargin, footerYPosition, tableWidth);

                    doc.add(footerTable);

//        Table footerTable = new Table(fullWidth);
//        footerTable.addCell(new Cell().add("Cảm ơn Quý Khách vì đã mua hàng!\n").setFont(font).setTextAlignment(TextAlignment.CENTER).setBold().setBorder(Border.NO_BORDER).setFontSize(14f));
//        footerTable.addCell(new Cell().add("Hòm thư đóng góp ý kiến: polypolo@email.com\n").setFont(font).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER).setFontSize(12f));
//        footerTable.addCell(new Cell().add("*********************************************").setFont(font).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER).setFontSize(12f));
//        doc.add(footerTable);
//  
//        ImageData qr = ImageDataFactory.create(qrPath);
//        Image iQR = new Image(qr).scaleToFit(80, 80); 
//        float bottomMargin = 20;
//        float qrYPosition = bottomMargin;
//        float pageWidth = pdfDocument.getDefaultPageSize().getWidth();
//        float qrXPosition = (pageWidth - iQR.getImageScaledWidth()) / 2;
//        iQR.setFixedPosition(qrXPosition, qrYPosition);
//        doc.add(iQR);
                    doc.close();
                    System.out.println("in thanh congg");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                JOptionPane.showInternalMessageDialog(this, "Đã in hóa đơn thành công!", "POLYPOLO thông báo", 0);
            } else {
                JOptionPane.showMessageDialog(this, "Bạn cần thanh toán để in hóa đơn!", "POLYPOLO thông báo", 0);
            }
        }
        clearForm();
        clearTable();
        txtReceived.setText("");
    }//GEN-LAST:event_btnPrintPDFMouseClicked

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        // CLICK SANPHAM -> GIOHANG
        int pos = tblSanPham.getSelectedRow();
        
        if (pos != -1) {
            String soL = JOptionPane.showInputDialog(this, "Nhập số lượng SP muốn mua:", "POLYPOLO xác nhận", JOptionPane.INFORMATION_MESSAGE);
            if (soL != null && !soL.isEmpty()) {
                try {
                    int soLuongMua = Integer.parseInt(soL);
                    HD_SanPhamViewModel sp = hdService.getListSanPham().get(pos);
                    if (sp.getSoL() >= soLuongMua) {
                        Integer maHD = Integer.valueOf(txtId.getText());
                        Integer maSP = (Integer) tblSanPham.getValueAt(pos, 0);
                        Double gia = Double.parseDouble(tblSanPham.getValueAt(pos, 7).toString());

                        if (hdService.checkExists(maSP, maHD)) {
                            hdService.mergeSP(soLuongMua, maHD, maSP);
                        } else {
                            HoaDonChiTiet hdct = new HoaDonChiTiet(maHD, maSP, soLuongMua, gia);
                            JOptionPane.showMessageDialog(this, hdService.addHDCT(hdct));
                        }

                        hdService.updateSP(maSP, sp.getSoL() - soLuongMua);
                        

                        loadTableGioHang(hdService.getListGioHangById(maHD));
                        adjustWidths(tblGioH);
                        loadTableSanPham(hdService.getListSanPham());
                        hdService.setTongT(maHD);
                        lblTongSP.setText(hdService.getTotalP(maHD).toString());
                        
                        double total = hdService.getTotal(maHD).getTongT();
                        lblTongDon.setText(String.format("%,.3f", total));
                        
                        double totalVAT = hdService.getVATFee(maHD);
                        lblPhiVAT.setText(String.format("%,.3f", totalVAT));
                    } else {
                        JOptionPane.showMessageDialog(this, "Số lượng sản phẩm hiện tại không đủ!", "POLYPOLO thông báo", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Số lượng phải là 1 số nguyên!", "POLYPOLO thông báo", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Bạn chưa nhập số lượng!", "POLYPOLO thông báo", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Bạn cần tạo hóa đơn mới trước khi thêm sản phẩm vào giỏ hàng!", "POLYPOLO thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_tblSanPhamMouseClicked

    private void btnXoaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXoaMouseClicked
        // DEL SINGLE
        int pos = tblGioH.getSelectedRow();
        if (pos == -1) {
            JOptionPane.showMessageDialog(this, "Hãy chọn sản phẩm từ giỏ hàng để xóa!", "POLYPOLO thông báo", JOptionPane.WARNING_MESSAGE);
        }else{
            Integer idSPCT = Integer.valueOf(tblGioH.getValueAt(pos, 0).toString());
            Integer soL = Integer.valueOf(tblGioH.getValueAt(pos, 3).toString());
            Integer idHD = Integer.valueOf(txtId.getText());
            JOptionPane.showMessageDialog(this, hdService.delSingle(idHD, idSPCT), "POLYPOLO thông báo", JOptionPane.INFORMATION_MESSAGE);
        
            //UPDATE SP
            SanPhamChiTiet spct = hdService.getSPById(idSPCT);
            spct.setSoLuong(spct.getSoLuong()+soL);
            hdService.updateSP(idSPCT, spct.getSoLuong());
            
            //LOAD
            loadTableGioHang(hdService.getListGioHangById(idHD));
            adjustWidths(tblGioH);
            loadTableSanPham(hdService.getListSanPham());
            
            double total = hdService.getTotal(idHD).getTongT();
            lblTongDon.setText((String.format("%,.3f", total)));
            lblTongSP.setText(hdService.getTotalP(idHD).toString());                
            lblPhiVAT.setText(hdService.getVATFee(idHD).toString());
            hdService.setTongT(idHD);
        }
    }//GEN-LAST:event_btnXoaMouseClicked

    private void tblHDTreoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHDTreoMouseClicked
        // LẤY HĐ
        int pos = tblHDTreo.getSelectedRow();
        Integer id = Integer.valueOf(tblHDTreo.getValueAt(pos, 0).toString());
        txtId.setText(hdService.getByHoaDon(id).getIdHD().toString());
        txtTenKH.setText(hdService.getByHoaDon(id).getTenKH());

        lblTongSP.setText(hdService.getTotalP(id).toString());
        
        double total = hdService.getTotal(id).getTongT();
        lblTongDon.setText((String.format("%,.3f", total)));
        
        double vatfee = hdService.getVATFee(id);
        lblPhiVAT.setText((String.format("%,.3f", vatfee)));
                            
        dcsNgayLap.setDate(hdService.getByHoaDon(id).getNgayL());
        cboPhuongThuc.setSelectedItem(hdService.getByHoaDon(id).getPhuongT());
        Integer idNV = Integer.valueOf(hdService.getByHoaDon(id).getIdNV());
        cboTenNV.setSelectedItem(hdService.getNSById(idNV).getTenNhanVien());
        
        //LOAD TABLE GIOHANG
        loadTableGioHang(hdService.getListGioHangById(id));
        adjustWidths(tblGioH);
    }//GEN-LAST:event_tblHDTreoMouseClicked

    private void btnEmptyBasketMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEmptyBasketMouseClicked
        // EMPTY BASKET
        Integer id = Integer.valueOf(txtId.getText());

        //LS -> SP
        ArrayList<HoaDonChiTiet> ls = hdService.getListHDCTById(id); 
        for (HoaDonChiTiet hdct : ls) {
            Integer idSPCT = hdct.getMaSPCT();
            Integer soL = hdct.getSoLuong();

            SanPhamChiTiet spct = hdService.getSPById(idSPCT);
            if (spct != null) {
                spct.setSoLuong(spct.getSoLuong() + soL);
                hdService.updateSP(idSPCT, spct.getSoLuong());
            }
        }
        
        //EMPTY
        JOptionPane.showMessageDialog(this, hdService.emptyBasket(id), "POLYPOLO thông báo", JOptionPane.INFORMATION_MESSAGE);
        //LOAD
        double total = hdService.getTotal(id).getTongT();
        lblTongDon.setText((String.format("%,.3f", total)));
                            
        lblPhiVAT.setText(hdService.getVATFee(id).toString());
        loadTableGioHang(hdService.getListGioHangById(id));
        loadTableSanPham(hdService.getListSanPham());
    }//GEN-LAST:event_btnEmptyBasketMouseClicked

    private void tblSanPhamMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tblSanPhamMouseEntered

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
                new QLBH_add().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnEmptyBasket;
    private javax.swing.JButton btnPrintPDF;
    private javax.swing.JButton btnScan;
    private javax.swing.JButton btnSearchKH;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cboDanhMuc;
    private javax.swing.JComboBox<String> cboPhuongThuc;
    private javax.swing.JComboBox<String> cboTenNV;
    private com.toedter.calendar.JDateChooser dcsNgayLap;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JTextField jTextField1;
    public javax.swing.JLabel lblPhiVAT;
    public javax.swing.JLabel lblTienThua;
    public javax.swing.JLabel lblTongDon;
    public javax.swing.JLabel lblTongSP;
    private javax.swing.JLabel lbllll;
    public javax.swing.JTable tblGioH;
    public javax.swing.JTable tblHDTreo;
    public javax.swing.JTable tblSanPham;
    private javax.swing.JTextField txtBrand;
    private javax.swing.JTextField txtChatL;
    public javax.swing.JTextField txtId;
    public javax.swing.JTextField txtIdSP;
    private javax.swing.JTextField txtMau;
    private javax.swing.JTextField txtReceived;
    private javax.swing.JTextField txtSearchSDT;
    private javax.swing.JTextField txtSz;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txtTenSP;
    // End of variables declaration//GEN-END:variables
}
