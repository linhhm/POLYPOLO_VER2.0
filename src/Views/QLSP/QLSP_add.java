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
public class QLSP_add extends javax.swing.JFrame {
    SVGImage svgSet = new SVGImage();
    SanPhamService spService = new SanPhamService();
    
    
    /**
     * Creates new form SP
     */
    
    public QLSP_add() {
        initComponents();
        setLocationRelativeTo(null);
        setSVGIcon();
        loadData();
    }
    
    private String imgPath;
    
    void setSVGIcon() {
        btnAdd.setIcon(svgSet.createSVGIcon("Images/SVG/w-add.svg", 20, 20));
        btnCancel.setIcon(svgSet.createSVGIcon("Images/SVG/w-cancel.svg", 20, 20));
        btnNew.setIcon(svgSet.createSVGIcon("Images/SVG/clean.svg", 20, 20));
        btnClose.setIcon(svgSet.createSVGIcon("Images/SVG/close.svg", 15, 15));
        btnClose.setBorderPainted(false);
    }

    void loadData(){
        loadCboDM(spService.getListDM());
        loadCboKho(spService.getListKhoHang());
        loadCboBrand(spService.getListNH());
        loadCboMau(spService.getListMauS());
        loadCboSz(spService.getListSz());
        loadCboChatL(spService.getListChatL()); 
        //BO GÓCC
    //    setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 30, 30));
        getRootPane().setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY,2, true));
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
        DefaultComboBoxModel cboMS = (DefaultComboBoxModel) cboMau.getModel();
        for (SP_MauSac ms : ls) {
            cboMS.addElement(ms.getTenMau());
        }
    }
    void loadCboSz(ArrayList<SP_KichCo> ls) {
        DefaultComboBoxModel cboKC = (DefaultComboBoxModel) cboSz.getModel();
        for (SP_KichCo sz : ls) {
            cboKC.addElement(sz.getTenSz());
        }
    }
    void loadCboBrand(ArrayList<SP_NhanHang> ls) {
        DefaultComboBoxModel cboB = (DefaultComboBoxModel) cboBrand.getModel();
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
    
    //GETMODEL
    public SanPham getModelSP(){
        String imgPath = this.imgPath;
        
        String danhMuc = cboDanhMuc.getSelectedItem().toString();
        Integer maDM = spService.getIdByNameDanhMuc(danhMuc).getIdDM();
        
        Integer maSP = Integer.valueOf(spService.getListSP().get(spService.getListSP().size()-1).getMaSP());
        String tenSP = txtTenSP.getText();
        Integer soL = Integer.valueOf(txtSoL.getText());
        String trangT = rdoCon.isSelected() ? "Còn hàng" : "Hết hàng";
        Date ngayN = dcsNgayN.getDate();
        
        String khoHang = cboKhoHang.getSelectedItem().toString();
        Integer maKho = spService.getIdByNameKhoHang(khoHang).getMaKho();
        
        Integer tax = Integer.valueOf(txtTax.getText());
        Double giaNhap = Double.valueOf(txtGiaN.getText());
        Double giaBan = Double.valueOf(txtGiaB.getText());
        
        String brand = cboBrand.getSelectedItem().toString();
        Integer brandID = spService.getIdByNameBrand(brand).getIdBrand();
        
        String tenMau = cboMau.getSelectedItem().toString();
        Integer maMau = spService.getIdByNameMauSac(tenMau).getIdMau();
        
        String tenSz = cboSz.getSelectedItem().toString();
        Integer maSz = spService.getIdByNameSz(tenSz).getIdSz();
        
        String chatL = cboChatLieu.getSelectedItem().toString();
        Integer chatLID = spService.getIdByNameChatLieu(chatL).getIdChatL();
        
        SanPham sp = new SanPham(maSP, maDM, soL, maSz, maMau, chatLID, brandID, maKho, tax, tenSP, trangT, imgPath, giaNhap, giaBan, ngayN);
        return sp;
    }
    
    //VALIDATE
    public Boolean validateSanPham(){
        StringBuilder stb = new  StringBuilder();
        MyValidate v = new MyValidate();
        
        v.isEmpty(txtTenSP, stb, "Tên sản phẩm bị trống!");
        v.isEmpty(txtSoL, stb, "Số lượng sản phẩm bị trống!");
        v.NumberLimit(txtGiaN, stb, "Giá nhập phải là kiểu số nguyên dương!",0,10000);
        v.NumberLimit(txtGiaB, stb, "Giá bán phải là kiểu số nguyên dương!",0,10000);
        v.isDateSelected(dcsNgayN, stb, "Chưa chọn ngày tạo sản phẩm!");
        v.isDateValid(dcsNgayN, stb, "Ngày tạo sản phẩm không hợp lệ!");
        v.isRadioButtonSelected(buttonGroup1, stb, "Trạng thái bị trống!");
        
        if (stb.length()>0) {
            JOptionPane.showMessageDialog(this, stb);
            return false;
        }else{
            return true;
        }
    }
    
    //CLEAR FORM
    void clearForm(){
        txtTenSP.setText("");
        txtGiaB.setText("");
        txtGiaN.setText("");
        txtId.setText("");
        txtSoL.setText("");
        dcsNgayN.setDate(new Date());
        dcsNgayN.setBackground(Color.white);
        txtTenSP.setBackground(Color.white);
        txtGiaB.setBackground(Color.white);
        txtGiaN.setBackground(Color.white);
        txtId.setBackground(Color.white);
        txtSoL.setBackground(Color.white);
        rdoCon.setSelected(true);
        lblImg.setIcon(null);
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
        txtId = new javax.swing.JTextField();
        txtTenSP = new javax.swing.JTextField();
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
        cboBrand = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        cboSz = new javax.swing.JComboBox<>();
        jLabel21 = new javax.swing.JLabel();
        cboMau = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        dcsNgayN = new com.toedter.calendar.JDateChooser();
        cboChatLieu = new javax.swing.JComboBox<>();
        jLabel23 = new javax.swing.JLabel();
        txtTax = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        btnCancel = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        btnNew = new javax.swing.JButton();
        lblImg = new javax.swing.JLabel();
        btnUploadFoto = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(0, 102, 204));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Montserrat", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("THÊM MỚI SẢN PHẨM");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 50, -1, -1));

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
                .addContainerGap(960, Short.MAX_VALUE)
                .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnClose, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1070, 30));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1020, 100));

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

        txtId.setEnabled(false);

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

        jLabel21.setText("Nhãn Hàng:");

        jLabel22.setText("Ngày Nhập:");

        jLabel23.setText("Phần Trăm Thuế:");

        txtTax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTaxActionPerformed(evt);
            }
        });

        jLabel24.setText("%");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7)
                        .addComponent(txtId)
                        .addComponent(cboDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(dcsNgayN, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 72, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cboSz, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(51, 51, 51))
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtGiaN, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                .addComponent(txtTax, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(cboChatLieu, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cboMau, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cboBrand, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtGiaB, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23))
                .addGap(33, 33, 33))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtGiaN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15))
                        .addGap(12, 12, 12)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtGiaB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboBrand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboSz, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cboMau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jLabel23))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel7)
                        .addGap(7, 7, 7)
                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel8)
                        .addGap(12, 12, 12)
                        .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                        .addComponent(dcsNgayN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cboKhoHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(347, 347, 347))
        );

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 120, 650, 480));

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

        btnAdd.setBackground(new java.awt.Color(51, 153, 255));
        btnAdd.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setText("TẠO");
        btnAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddMouseClicked(evt);
            }
        });

        btnNew.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnNew.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNewMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAdd)
                .addGap(18, 18, 18)
                .addComponent(btnCancel)
                .addGap(18, 18, 18)
                .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(btnCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(7, 7, 7))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 550, 270, 50));

        lblImg.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel1.add(lblImg, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 140, 240, 340));

        btnUploadFoto.setText("ẢNH");
        btnUploadFoto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUploadFotoMouseClicked(evt);
            }
        });
        jPanel1.add(btnUploadFoto, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 500, 75, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1018, javax.swing.GroupLayout.PREFERRED_SIZE)
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
    }//GEN-LAST:event_btnUploadFotoMouseClicked

    private void btnNewMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNewMouseClicked
        // NEW
        clearForm();
    }//GEN-LAST:event_btnNewMouseClicked

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

    private void btnAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddMouseClicked
        // ADD
        String checkName = txtTenSP.getText().trim();
        int result = JOptionPane.showConfirmDialog(this, "Bạn muốn thêm sản phẩm?", "POLYPOLO xác nhận", JOptionPane.YES_NO_OPTION);
        if (spService.checkName(checkName)) {
            JOptionPane.showMessageDialog(this, "Tên sản phẩm bị trùng, không thể thêm!", "POLY POLO thông báo", 0);
            clearForm();
        } else {
            if (validateSanPham() && result == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(this, spService.addSP(getModelSP()));
            } else {
                JOptionPane.showMessageDialog(this, "Đã hủy thao tác thêm sản phẩm!", "POLYPOLO thông báo", 0);
            }
        }
        clearForm();
    }//GEN-LAST:event_btnAddMouseClicked

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
                new QLSP_add().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnUploadFoto;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboBrand;
    private javax.swing.JComboBox<String> cboChatLieu;
    private javax.swing.JComboBox<String> cboDanhMuc;
    private javax.swing.JComboBox<String> cboKhoHang;
    private javax.swing.JComboBox<String> cboMau;
    private javax.swing.JComboBox<String> cboSz;
    private com.toedter.calendar.JDateChooser dcsNgayN;
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
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    public javax.swing.JLabel lblImg;
    private javax.swing.JRadioButton rdoCon;
    private javax.swing.JRadioButton rdoHet;
    private javax.swing.JTextField txtGiaB;
    private javax.swing.JTextField txtGiaN;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtSoL;
    private javax.swing.JTextField txtTax;
    private javax.swing.JTextField txtTenSP;
    // End of variables declaration//GEN-END:variables
}
