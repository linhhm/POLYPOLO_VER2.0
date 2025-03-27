/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Views.QLSP;

import Models.KhoHang;
import Models.SP_ChatLieu;
import Models.SP_DanhMuc;
import Models.SP_KichCo;
import Models.SP_MauSac;
import Models.SP_NhanHang;
import Models.SanPham;
import Services.SanPhamService;
import Utils.SVGImage;
import Validator.MyValidate;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author X1
 */
public class QLSP_update extends javax.swing.JFrame {
    SVGImage svgSet = new SVGImage();
    SanPhamService spService = new SanPhamService();
    
    /**
     * Creates new form SP
     */
    
    public QLSP_update() {
        initComponents();
        setLocationRelativeTo(null);
        setIcon();
        loadData();
    }
    
    private String imgPath;
    
    //<editor-fold defaultstate="collapsed" desc=" SET UP ">
    void setIcon(){
        btnEdit.setIcon(svgSet.createSVGIcon("Images/SVG/w-edit.svg", 20, 20));
        btnCancel.setIcon(svgSet.createSVGIcon("Images/SVG/w-cancel.svg", 20, 20));
        btnClose.setIcon(svgSet.createSVGIcon("Images/SVG/close.svg", 15, 15));
        btnClose.setBorderPainted(false);
        getRootPane().setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY,2, true));
    }
    //</editor-fold>
    
    void loadData(){
        loadCboDM(spService.getListDM());
        loadCboKho(spService.getListKhoHang());
        loadCboBrand(spService.getListNH());
        loadCboMau(spService.getListMauS());
        loadCboSz(spService.getListSz());
        loadCboChatL(spService.getListChatL()); 
    }
    
    //<editor-fold defaultstate="collapsed" desc=" LOAD ">
    void loadCboDM(ArrayList<SP_DanhMuc> ls) {
        DefaultComboBoxModel cboDM = (DefaultComboBoxModel) cboDanhMuc.getModel();
        for (SP_DanhMuc dm : ls) {
                cboDM.addElement(dm.getTenDM());
            }
    }
    void loadCboKho(ArrayList<KhoHang> ls) {
        DefaultComboBoxModel cboModel = (DefaultComboBoxModel) cboKhoHang.getModel();
        for (KhoHang kh : ls) {
                cboModel.addElement(kh.getTenKho());
            }
    }
    void loadCboMau(ArrayList<SP_MauSac> ls) {
        DefaultComboBoxModel cboMS = (DefaultComboBoxModel) cboMauSac.getModel();
        for (SP_MauSac ms : ls) {
            cboMS.addElement(ms.getTenMau());
        }
    }
    void loadCboSz(ArrayList<SP_KichCo> ls) {
        DefaultComboBoxModel cboKC = (DefaultComboBoxModel) cboKichCo.getModel();
        for (SP_KichCo sz : ls) {
            cboKC.addElement(sz.getTenSz());
        }
    }
    void loadCboBrand(ArrayList<SP_NhanHang> ls) {
        DefaultComboBoxModel cboB = (DefaultComboBoxModel) cboNhanHang.getModel();
        for (SP_NhanHang nh : ls) {
            cboB.addElement(nh.getBrandName());
        }
    }
    void loadCboChatL(ArrayList<SP_ChatLieu> ls) {
        DefaultComboBoxModel cboChatL = (DefaultComboBoxModel) cboChatLieu.getModel();
        for (SP_ChatLieu cl : ls) {
            cboChatL.addElement(cl.getTenChatL());
        }
    }
    //</editor-fold>
    
    void loadFoto(String imgPath){
        String defaultCurrentDirectoryPath = "C:\\Users\\X1\\OneDrive\\Documents\\NetBeansProjects\\POLYPOLO_UPDATE\\src\\Images\\IMG";
        JFileChooser imageFileChooser = new JFileChooser(defaultCurrentDirectoryPath);
        imageFileChooser.setDialogTitle("Lựa File Ảnh");

        FileNameExtensionFilter imageFilter = new FileNameExtensionFilter(
                "Image files", ImageIO.getReaderFileSuffixes());
        imageFileChooser.setFileFilter(imageFilter);

        int userSelection = imageFileChooser.showOpenDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File file = imageFileChooser.getSelectedFile();
            String path = file.getAbsolutePath();
            this.imgPath = path;
            try {
                BufferedImage img = ImageIO.read(new File(path));
                Image imgScale = img.getScaledInstance(240, 350, Image.SCALE_SMOOTH);
                ImageIcon imgIcon = new ImageIcon(imgScale);
                lblImg.setIcon(imgIcon);
            } catch (IOException ex) {
                Logger.getLogger(QLSP_update.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    //GET MODEL
    public SanPham getModel(){
        String imgPath = this.imgPath;
        
        String danhMuc = cboDanhMuc.getSelectedItem().toString();
        Integer maDM = spService.getIdByNameDanhMuc(danhMuc).getIdDM();
        
        Integer maSP = Integer.valueOf(txtID.getText());
        String tenSP = txtTen.getText();
        Integer soL = Integer.valueOf(txtSoL.getText());
        String trangT = rdoCon.isSelected() ? "Còn hàng" : "Hết hàng";
        Date ngayN = dcsNgayNhap.getDate();
        
        String khoHang = cboKhoHang.getSelectedItem().toString();
        Integer maKho = spService.getIdByNameKhoHang(khoHang).getMaKho();
        
        Integer tax = Integer.valueOf(txtTax.getText());
        Double giaNhap = Double.valueOf(txtGiaN.getText());
        Double giaBan = Double.valueOf(txtGiaB.getText());
        
        String brand = cboNhanHang.getSelectedItem().toString();
        Integer brandID = spService.getIdByNameBrand(brand).getIdBrand();
        
        String tenMau = cboMauSac.getSelectedItem().toString();
        Integer maMau = spService.getIdByNameMauSac(tenMau).getIdMau();
        
        String tenSz = cboKichCo.getSelectedItem().toString();
        Integer maSz = spService.getIdByNameSz(tenSz).getIdSz();
        
        String chatL = cboChatLieu.getSelectedItem().toString();
        Integer chatLID = spService.getIdByNameChatLieu(chatL).getIdChatL();
        
        SanPham sp = new SanPham(maSP, maDM, soL, maSz, maMau, chatLID, brandID, maKho, tenSP, trangT, imgPath, giaNhap, giaBan, ngayN);
        return sp;
    }
    
    //VALIDATE
    public Boolean validateSanPham(){
        StringBuilder stb = new  StringBuilder();
        MyValidate v = new MyValidate();
        
        v.isEmpty(txtTen, stb, "Tên sản phẩm bị trống!");
        v.isEmpty(txtSoL, stb, "Số lượng sản phẩm bị trống!");
        v.NumberLimit(txtGiaN, stb, "Giá nhập phải là kiểu số nguyên dương!",0,10000);
        v.NumberLimit(txtGiaB, stb, "Giá bán phải là kiểu số nguyên dương!",0,10000);
        if (stb.length()>0) {
            JOptionPane.showMessageDialog(this, stb);
            return false;
        }else{
            return true;
        }
    }
    
    //CLEAR FORM
    void clearForm(){
        txtTen.setText("");
        txtGiaB.setText("");
        txtGiaN.setText("");
        txtID.setText("");
        txtSoL.setText("");
        dcsNgayNhap.setDate(new Date());
        dcsNgayNhap.setBackground(Color.white);
        txtTen.setBackground(Color.white);
        txtGiaB.setBackground(Color.white);
        txtGiaN.setBackground(Color.white);
        txtID.setBackground(Color.white);
        txtSoL.setBackground(Color.white);
        rdoCon.setSelected(true);
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
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnClose = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        txtTen = new javax.swing.JTextField();
        txtSoL = new javax.swing.JTextField();
        txtGiaN = new javax.swing.JTextField();
        txtGiaB = new javax.swing.JTextField();
        cboDanhMuc = new javax.swing.JComboBox<>();
        rdoCon = new javax.swing.JRadioButton();
        rdoHet = new javax.swing.JRadioButton();
        jLabel18 = new javax.swing.JLabel();
        cboKhoHang = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        cboKichCo = new javax.swing.JComboBox<>();
        cboMauSac = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        dcsNgayNhap = new com.toedter.calendar.JDateChooser();
        cboChatLieu = new javax.swing.JComboBox<>();
        jLabel23 = new javax.swing.JLabel();
        cboNhanHang = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        txtTax = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        btnUploadFoto = new javax.swing.JButton();
        lblImg = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        btnCancel = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(0, 102, 204));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Montserrat", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("CẬP NHẬT SẢN PHẨM");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 50, -1, -1));

        btnClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCloseMouseClicked(evt);
            }
        });
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 940, Short.MAX_VALUE)
                .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnClose, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 990, 30));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 990, 100));

        jPanel4.setBackground(new java.awt.Color(250, 250, 250));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin Sản Phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 16), new java.awt.Color(51, 51, 51))); // NOI18N

        jLabel9.setText("Danh Mục:");

        jLabel7.setText("Mã Sản Phẩm:");

        jLabel8.setText("Tên Sản Phẩm:");

        jLabel12.setText("Số Lượng Tồn:");

        jLabel14.setText("Giá Nhập:");

        jLabel11.setText("Giá Bán:");

        jLabel10.setText("Trạng Thái:");

        jLabel16.setText("Kho Hàng:");

        jLabel17.setText("Kích cỡ:");

        txtID.setEnabled(false);

        txtGiaN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGiaNActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoCon);
        rdoCon.setText("Còn sẵn");

        buttonGroup1.add(rdoHet);
        rdoHet.setText("Hết hàng");

        jLabel18.setText("Màu Sắc:");

        jLabel15.setText("VND");

        jLabel19.setText("VND");

        jLabel20.setText("Chất Liệu:");

        jLabel22.setText("Ngày Nhập:");

        jLabel23.setText("Nhãn Hàng:");

        jLabel24.setText("Phần Trăm Thuế:");

        txtTax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTaxActionPerformed(evt);
            }
        });

        jLabel25.setText("%");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7)
                        .addComponent(txtID)
                        .addComponent(cboDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboKhoHang, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSoL, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(rdoCon, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(rdoHet, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(dcsNgayNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtGiaB, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtGiaN, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(cboKichCo, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(cboChatLieu, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cboMauSac, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboNhanHang, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(txtTax, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel24))
                .addGap(44, 44, 44))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel14)
                        .addGap(12, 12, 12)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtGiaN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11)
                        .addGap(12, 12, 12)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtGiaB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19))
                        .addGap(20, 20, 20)
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboNhanHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboKichCo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cboMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cboDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(24, 24, 24))
                            .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel8)
                        .addGap(14, 14, 14)
                        .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSoL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rdoCon)
                            .addComponent(rdoHet))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dcsNgayNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cboKhoHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(341, 341, 341))
        );

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 120, 660, 480));

        btnUploadFoto.setText("ẢNH");
        btnUploadFoto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUploadFotoMouseClicked(evt);
            }
        });
        jPanel1.add(btnUploadFoto, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 500, 75, -1));

        lblImg.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel1.add(lblImg, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 140, 240, 340));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        btnCancel.setBackground(new java.awt.Color(204, 0, 51));
        btnCancel.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnCancel.setForeground(new java.awt.Color(255, 255, 255));
        btnCancel.setText("HỦY");
        btnCancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCancelMouseClicked(evt);
            }
        });

        btnEdit.setBackground(new java.awt.Color(255, 220, 11));
        btnEdit.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnEdit.setForeground(new java.awt.Color(255, 255, 255));
        btnEdit.setText("LƯU");
        btnEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEditMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnCancel, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 550, 230, 50));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 629, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtGiaNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGiaNActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGiaNActionPerformed

    private void btnUploadFotoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUploadFotoMouseClicked
        // FOTO
        loadFoto(imgPath);
        System.out.println(imgPath);
    }//GEN-LAST:event_btnUploadFotoMouseClicked

    private void btnCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCloseMouseClicked
        // CLOSE
        this.dispose();
    }//GEN-LAST:event_btnCloseMouseClicked

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnCancelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelMouseClicked
        // CANCEL
        JOptionPane.showMessageDialog(this, "Đã hủy thao tác cập nhật sản phẩm!", "POLYPOLO thông báo", JOptionPane.CANCEL_OPTION);
        this.dispose();
    }//GEN-LAST:event_btnCancelMouseClicked

    private void btnEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditMouseClicked
        // UPDATE
        int result = JOptionPane.showConfirmDialog(this, "Bạn muốn sửa sản phẩm này?", "POLYPOLO xác nhận", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION && validateSanPham()) {
            String resultNek = spService.updateSP(getModel());
            JOptionPane.showMessageDialog(this, resultNek);
        } else {
            JOptionPane.showMessageDialog(this, "Đã hủy thao tác sửa sản phẩm!", "POLYPOLO thông báo", 0);
        }
        clearForm();
        this.dispose();
    }//GEN-LAST:event_btnEditMouseClicked

    private void txtTaxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTaxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTaxActionPerformed

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
                new QLSP_update().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnUploadFoto;
    private javax.swing.ButtonGroup buttonGroup1;
    public javax.swing.JComboBox<String> cboChatLieu;
    public javax.swing.JComboBox<String> cboDanhMuc;
    public javax.swing.JComboBox<String> cboKhoHang;
    public javax.swing.JComboBox<String> cboKichCo;
    public javax.swing.JComboBox<String> cboMauSac;
    public javax.swing.JComboBox<String> cboNhanHang;
    public com.toedter.calendar.JDateChooser dcsNgayNhap;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    public javax.swing.JLabel lblImg;
    public javax.swing.JRadioButton rdoCon;
    public javax.swing.JRadioButton rdoHet;
    public javax.swing.JTextField txtGiaB;
    public javax.swing.JTextField txtGiaN;
    public javax.swing.JTextField txtID;
    public javax.swing.JTextField txtSoL;
    private javax.swing.JTextField txtTax;
    public javax.swing.JTextField txtTen;
    // End of variables declaration//GEN-END:variables
}
