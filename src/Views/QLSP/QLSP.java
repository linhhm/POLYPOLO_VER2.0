/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package Views.QLSP;

import Models.SanPham;
import Services.SanPhamService;
import Services.UserService;
import Utils.SVGImage;
import ViewModels.SanPhamViewModel;
import Views.Login;
import com.barcodelib.barcode.Linear;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author X1
 */
public class QLSP extends javax.swing.JInternalFrame {
    SVGImage svgSet = new SVGImage();
    SanPhamService spService = new SanPhamService();
    DecimalFormat format = new DecimalFormat("#,###");
    UserService uService = new UserService();
    
    /**
     * Creates new form QLSANPHAM
     */
    public QLSP() {
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
        setButtonIcon();
        loadTable(spService.getListSPVM());
        adjustWidths(tblSP);
        
    }

    void setButtonIcon() {
        btnAdd.setIcon(svgSet.createSVGIcon("Images/SVG/add.svg", 20, 20));
        btnUpdate.setIcon(svgSet.createSVGIcon("Images/SVG/update.svg", 20, 20));
        btnLoad.setIcon(svgSet.createSVGIcon("Images/SVG/reload.svg", 18, 18));
        btnUnhide.setIcon(svgSet.createSVGIcon("Images/SVG/unhide.svg", 18, 18));
        btnHidden.setIcon(svgSet.createSVGIcon("Images/SVG/w-view-fill.svg", 15, 15));
        
        btnBarcode.setIcon(svgSet.createSVGIcon("Images/SVG/sp-qr-solid.svg", 20, 20));
        btnImport.setIcon(svgSet.createSVGIcon("Images/SVG/g-excel.svg", 20, 20));
        btnExport.setIcon(svgSet.createSVGIcon("Images/SVG/pdf-color.svg", 20, 20));
        btnHidden.setIcon(svgSet.createSVGIcon("Images/SVG/hide-regular.svg", 20, 20));
        btnViewHide.setIcon(svgSet.createSVGIcon("Images/SVG/b-hidels.svg", 20, 20));
    }
    
    void loadTable(ArrayList<SanPhamViewModel> ls){
        DefaultTableModel model = (DefaultTableModel) tblSP.getModel();
        model.setRowCount(0);
        for (SanPhamViewModel sp : ls) {
            String giaN = format.format(sp.getGiaN());
            String giaB = format.format(sp.getGiaB());
            
            model.addRow(new Object[]{
                sp.getIdSP(), sp.getTenSP(), sp.getDanhMuc(), sp.getNhanHang()
                    , sp.getMauSac(), sp.getKichCo(), sp.getChatLieu()
                    , giaN, giaB, sp.getTrangT(), sp.getSoL(), sp.getKhoHang()
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
    
    //GETMODEL
    public SanPham getModelSP(){
        
        SanPham sp = new SanPham();
        
        int pos = tblSP.getSelectedRow();
        Integer idSP = (Integer) tblSP.getValueAt(pos, 0);
        String tenSP = spService.getListViewModelByIdSP(idSP).getTenSP();
        
        String danhMuc = spService.getListViewModelByIdSP(idSP).getDanhMuc();
        Integer maDM = spService.getIdByNameDanhMuc(danhMuc).getIdDM();
        
        Integer soL = spService.getListViewModelByIdSP(idSP).getSoL();
        String trangT = spService.getListViewModelByIdSP(idSP).getTrangT();
        Date ngayN = spService.getListViewModelByIdSP(idSP).getNgayNhap();
        
        String khoHang = spService.getListViewModelByIdSP(idSP).getKhoHang();
        Integer maKho = spService.getIdByNameKhoHang(khoHang).getMaKho();
        
        Double giaNhap = spService.getListViewModelByIdSP(idSP).getGiaN();
        Double giaBan = spService.getListViewModelByIdSP(idSP).getGiaB();
        
        String brand = spService.getListViewModelByIdSP(idSP).getNhanHang();
        Integer brandID = spService.getIdByNameBrand(brand).getIdBrand();
        
        String tenMau = spService.getListViewModelByIdSP(idSP).getMauSac();
        Integer maMau = spService.getIdByNameMauSac(tenMau).getIdMau();
        
        String tenSz = spService.getListViewModelByIdSP(idSP).getKichCo();
        Integer maSz = spService.getIdByNameSz(tenSz).getIdSz();
        
        String chatL = spService.getListViewModelByIdSP(idSP).getChatLieu();
        Integer chatLID = spService.getIdByNameChatLieu(chatL).getIdChatL();
        
        String imgPath = spService.getListViewModelByIdSP(idSP).getImg();
        
        sp = new SanPham(idSP, maDM, soL, maSz, maMau, chatLID, brandID, maKho, tenSP, trangT, imgPath, giaNhap, giaBan, ngayN);
        return sp;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        btnImport = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblSP = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnHidden = new javax.swing.JButton();
        btnViewHide = new javax.swing.JButton();
        cboSearch = new javax.swing.JComboBox<>();
        txtSearch = new javax.swing.JTextField();
        btnLoad = new javax.swing.JButton();
        btnUnhide = new javax.swing.JButton();
        btnExport = new javax.swing.JButton();
        btnBarcode = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        txtMaDanhMuc = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        btnThemDM = new javax.swing.JButton();
        btnSuaDM = new javax.swing.JButton();
        btnClearDM = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        txtTenDanhMuc = new javax.swing.JTextField();
        btnAnDM = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        rdoDMConHang = new javax.swing.JRadioButton();
        rdoDMHethang = new javax.swing.JRadioButton();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblDanhMuc = new javax.swing.JTable();
        jLabel27 = new javax.swing.JLabel();
        txtSearchDM = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setBackground(new java.awt.Color(227, 241, 254));
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1090, 630));
        setMinimumSize(new java.awt.Dimension(1090, 630));
        setPreferredSize(new java.awt.Dimension(1090, 630));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTabbedPane1.setMaximumSize(new java.awt.Dimension(1090, 630));
        jTabbedPane1.setMinimumSize(new java.awt.Dimension(1090, 630));
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(1090, 630));

        jPanel1.setMaximumSize(new java.awt.Dimension(1090, 630));
        jPanel1.setMinimumSize(new java.awt.Dimension(1090, 630));
        jPanel1.setPreferredSize(new java.awt.Dimension(1090, 630));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnImport.setText("IMPORT");
        btnImport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnImportMouseClicked(evt);
            }
        });
        jPanel1.add(btnImport, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 480, 120, 40));

        tblSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID SP", "Tên SP", "Danh Mục", "Nhãn Hàng", "Màu Sắc", "Size", "Chất Liệu", "Giá Nhập", "Giá Bán", "Trạng Thái", "Tồn", "Kho Hàng"
            }
        ));
        tblSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSPMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tblSP);

        jPanel1.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 85, 1030, 380));

        jPanel5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel5.setAlignmentX(1.0F);
        jPanel5.setAlignmentY(1.0F);

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

        btnHidden.setText("ẨN");
        btnHidden.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHiddenMouseClicked(evt);
            }
        });
        btnHidden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHiddenActionPerformed(evt);
            }
        });

        btnViewHide.setText("DS ẨN");
        btnViewHide.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnViewHideMouseClicked(evt);
            }
        });
        btnViewHide.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewHideActionPerformed(evt);
            }
        });

        cboSearch.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất Cả", " " }));

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

        btnUnhide.setText("BỎ ẨN");
        btnUnhide.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUnhideMouseClicked(evt);
            }
        });
        btnUnhide.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUnhideActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnLoad, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnHidden, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnViewHide, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnUnhide, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
                .addComponent(cboSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(10, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnHidden, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLoad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnViewHide, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnUnhide, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        cboSearch.getAccessibleContext().setAccessibleName("Tất Cả\n");

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 1040, 60));

        btnExport.setText("EXPORT");
        btnExport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnExportMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnExportMousePressed(evt);
            }
        });
        jPanel1.add(btnExport, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 480, 130, 40));

        btnBarcode.setText("LƯU");
        btnBarcode.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBarcodeMouseClicked(evt);
            }
        });
        jPanel1.add(btnBarcode, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 480, 120, 40));

        jTabbedPane1.addTab("Thông Tin Sản Phẩm", jPanel1);

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP)), "Thông tin Danh Mục", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Segoe UI", 3, 14))); // NOI18N

        txtMaDanhMuc.setEnabled(false);

        jLabel25.setText("Trạng Thái:");

        btnThemDM.setText("THÊM");
        btnThemDM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThemDMMouseClicked(evt);
            }
        });

        btnSuaDM.setText("SỬA");
        btnSuaDM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSuaDMMouseClicked(evt);
            }
        });

        btnClearDM.setText("MỚI");
        btnClearDM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnClearDMMouseClicked(evt);
            }
        });

        jLabel26.setText("Mã Danh Mục:");

        btnAnDM.setText("XÓA");
        btnAnDM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAnDMMouseClicked(evt);
            }
        });
        btnAnDM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnDMActionPerformed(evt);
            }
        });

        jLabel28.setText("Tên Danh Mục:");

        rdoDMConHang.setText("Còn hàng");
        rdoDMConHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoDMConHangActionPerformed(evt);
            }
        });

        rdoDMHethang.setText("Hết hàng");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThemDM)
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTenDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addComponent(txtMaDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(162, 162, 162)
                                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(38, 38, 38)
                                .addComponent(rdoDMConHang)
                                .addGap(66, 66, 66)
                                .addComponent(rdoDMHethang))))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(btnSuaDM)
                        .addGap(49, 49, 49)
                        .addComponent(btnClearDM)
                        .addGap(54, 54, 54)
                        .addComponent(btnAnDM)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel26)
                            .addComponent(txtMaDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTenDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnThemDM)
                            .addComponent(btnSuaDM)
                            .addComponent(btnClearDM)
                            .addComponent(btnAnDM)))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel25)
                            .addComponent(rdoDMConHang)
                            .addComponent(rdoDMHethang))))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP)), "Chi Tiết Danh Mục", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Segoe UI", 3, 14))); // NOI18N

        tblDanhMuc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Mã Danh Mục", "Tên Danh Mục", "Trạng Thái"
            }
        ));
        tblDanhMuc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDanhMucMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblDanhMuc);

        jLabel27.setText("Tìm kiếm danh mục:");

        jButton1.setText("SEARCH");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel27)
                        .addGap(18, 18, 18)
                        .addComponent(txtSearchDM, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(jButton1))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel15Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 949, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearchDM, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Danh Mục", jPanel2);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 1050, 570));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSPMouseClicked
    
    }//GEN-LAST:event_tblSPMouseClicked

    private void btnThemDMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemDMMouseClicked
        //ADD DM
//        int result = JOptionPane.showConfirmDialog(this, "Bạn muốn thêm mới danh mục?", "POLYPOLO xác nhận", JOptionPane.YES_NO_OPTION);
//        if (result == JOptionPane.YES_OPTION && validateDanhMuc()) {
//            JOptionPane.showMessageDialog(this, spService.addDanhMuc(getModelDM()));
//            loadTableDanhMuc(spService.getList());
//        }else{
//            JOptionPane.showMessageDialog(this, "Đã hủy thao tác thêm mới danh mục!", "POLYPOLO thông báo", 0);
//        }
    }//GEN-LAST:event_btnThemDMMouseClicked

    private void btnSuaDMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSuaDMMouseClicked
        // UPDATE
//        int result = JOptionPane.showConfirmDialog(this, "Bạn muốn cập nhật danh mục?", "POLYPOLO xác nhận", JOptionPane.YES_NO_OPTION);
//        if (result == JOptionPane.YES_OPTION && validateDanhMuc()) {
//            JOptionPane.showMessageDialog(this, spService.updateDanhMuc(getModelDM()));
//            loadTableDanhMuc(spService.getList());
//        } else {
//            JOptionPane.showMessageDialog(this, "Đã hủy thao tác cập nhật danh mục!", "POLYPOLO thông báo", 0);
//        }
    }//GEN-LAST:event_btnSuaDMMouseClicked

    private void btnClearDMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnClearDMMouseClicked
        // CLEAR
        txtMaDanhMuc.setText("");
        txtTenDanhMuc.setText("");
        txtTenDanhMuc.setBackground(Color.white);
    }//GEN-LAST:event_btnClearDMMouseClicked

    private void btnAnDMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAnDMMouseClicked
        // HIDE
//        JOptionPane.showMessageDialog(this, spService.anDanhMuc(getModelDM()));
//        loadTableDanhMuc(spService.getList());
    }//GEN-LAST:event_btnAnDMMouseClicked

    private void btnAnDMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnDMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAnDMActionPerformed

    private void rdoDMConHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoDMConHangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoDMConHangActionPerformed

    private void tblDanhMucMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDanhMucMouseClicked
        // DANHMUC
//        int pos = tblDanhMuc.getSelectedRow();
//        Integer id = (Integer) tblDanhMuc.getValueAt(pos, 0);
//        DanhMuc dm = spService.getListDMById(id);
//        txtMaDanhMuc.setText(dm.getMaDM().toString());
//        txtTenDanhMuc.setText(dm.getTenDM());
    }//GEN-LAST:event_tblDanhMucMouseClicked

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // SEARCH DM
//        String name = txtSearchDM.getText();
//        txtSearchDM.setText("");
//        ArrayList<DanhMuc> ls = spService.getListDMByName(name);
//        loadTableDanhMuc(ls);
    }//GEN-LAST:event_jButton1MouseClicked

    private void btnAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddMouseClicked
        // ADD
        QLSP_add spAdd = new QLSP_add();
        spAdd.setVisible(true);
    }//GEN-LAST:event_btnAddMouseClicked

    private void btnUpdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUpdateMouseClicked
        // UPDATE
        int pos = tblSP.getSelectedRow();
        if (pos == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn sản phẩm để cập nhật!", "POLYPOLO thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        } else {
            QLSP_update spUpdate = new QLSP_update();
            spUpdate.setVisible(true);
            spUpdate.pack();

            Integer maSP = (Integer) tblSP.getValueAt(pos, 0);
            spUpdate.txtID.setText(maSP.toString());
            spUpdate.txtTen.setText((String) tblSP.getValueAt(pos, 1));
            spUpdate.cboDanhMuc.setSelectedItem(tblSP.getValueAt(pos, 2));
            spUpdate.cboNhanHang.setSelectedItem(tblSP.getValueAt(pos, 3));
            spUpdate.cboMauSac.setSelectedItem(tblSP.getValueAt(pos, 4));
            spUpdate.cboKichCo.setSelectedItem(tblSP.getValueAt(pos, 5));
            spUpdate.cboChatLieu.setSelectedItem(tblSP.getValueAt(pos, 6));
            spUpdate.txtGiaN.setText((String) tblSP.getValueAt(pos, 7));
            spUpdate.txtGiaB.setText((String) tblSP.getValueAt(pos, 8));
            spUpdate.txtSoL.setText(tblSP.getValueAt(pos, 10).toString());
            spUpdate.cboKhoHang.setSelectedItem(tblSP.getValueAt(pos, 11));
            
            String trangT = (String) tblSP.getValueAt(pos, 9);
            if ("Còn hàng".equals(trangT)) {
                spUpdate.rdoCon.setSelected(true);
            } else {
                spUpdate.rdoHet.setSelected(true);
            }
            Date ngayN = spService.getListViewModelById(maSP).get(0).getNgayNhap();
            spUpdate.dcsNgayNhap.setDate(ngayN);

            String imgPath = spService.getListViewModelById(maSP).get(0).getImg();
            ImageIcon imageIcon = new ImageIcon(new ImageIcon(imgPath).getImage().getScaledInstance(240, 340, Image.SCALE_SMOOTH));
            spUpdate.lblImg.setIcon(imageIcon);
        }
    }//GEN-LAST:event_btnUpdateMouseClicked

    private void btnViewHideActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewHideActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnViewHideActionPerformed

    private void btnImportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnImportMouseClicked
        // IMPORT
        QLSP_import sp = new QLSP_import();
        sp.setVisible(true);
    }//GEN-LAST:event_btnImportMouseClicked

    private void btnHiddenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHiddenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHiddenActionPerformed

    private void btnLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLoadActionPerformed

    private void btnLoadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLoadMouseClicked
        // XEM
        loadTable(spService.getListSPVM());
    }//GEN-LAST:event_btnLoadMouseClicked

    private void btnHiddenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHiddenMouseClicked
        // HIDE
        int pos = tblSP.getSelectedRow();
        if (pos >= 0) {
            int result = JOptionPane.showConfirmDialog(this, "Bạn muốn ẩn sản phẩm không?", "POLYPOLO xác nhận", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                spService.hideSP(getModelSP());
                JOptionPane.showMessageDialog(this, "Ẩn sản phẩm thành công!", "POLYPOLO thông báo", JOptionPane.INFORMATION_MESSAGE);
                loadTable(spService.getListSPVM());
            } else if (result == JOptionPane.NO_OPTION) {
                JOptionPane.showMessageDialog(this, "Đã hủy thao tác ẩn sản phẩm!", "POLYPOLO thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm trên bảng để ẩn!", "POLYPOLO thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnHiddenMouseClicked

    private void btnViewHideMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnViewHideMouseClicked
        // HIDE LS
        loadTable(spService.getListHide());
    }//GEN-LAST:event_btnViewHideMouseClicked

    private void btnUnhideMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUnhideMouseClicked
        // UNHIDE
        int selectedRow = tblSP.getSelectedRow();
        if (selectedRow != -1) {
            int result = JOptionPane.showConfirmDialog(this, "Bạn muốn bỏ ẩn sản phẩm không?", "POLYPOLO thông báo", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                Integer maSP = Integer.valueOf(tblSP.getModel().getValueAt(selectedRow, 0).toString());
                SanPham sp = new SanPham();
                sp.setMaSP(maSP);
                if (spService.unhideSP(sp)) {
                    JOptionPane.showMessageDialog(this, "Bỏ ẩn sản phẩm thành công!", "POLYPOLO thông báo", JOptionPane.INFORMATION_MESSAGE);
                    loadTable(spService.getListSPVM());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để bỏ ẩn!", "POLYPOLO thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnUnhideMouseClicked

    private void btnUnhideActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUnhideActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUnhideActionPerformed

    private void btnExportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExportMouseClicked
        // EXPORT
        try {
            XSSFWorkbook workBook = new XSSFWorkbook();
            XSSFSheet sheet = workBook.createSheet("Danh Sách Sản Phẩm POLYPOLO");
            
            //STYLE TITLE
            XSSFRow titleRow = sheet.createRow(0);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue("Danh Sách Sản Phẩm POLYPOLO"); 
            sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(0,0,0,11)); 
            XSSFFont font = workBook.createFont();
            font.setFontHeightInPoints((short) 19);
            font.setBold(true);
            XSSFCellStyle style = workBook.createCellStyle();
            style.setFont(font);
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER);
            titleCell.setCellStyle(style);
           
            //DATE
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String currentDate = sdf.format(new Date());
            
            SimpleDateFormat sdfHrs = new SimpleDateFormat("HH:mm:ss");
            String currentTime = sdfHrs.format(new Date());
            
            XSSFRow dateRow = sheet.createRow(1);
            org.apache.poi.ss.usermodel.Cell dateCell = dateRow.createCell(0);
            dateCell.setCellValue("Ngày: " + currentDate + " | Giờ: " +currentTime);
            sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(1, 1, 0, 11)); // Merge từ cột 0 đến 9
            XSSFCellStyle dateStyle = workBook.createCellStyle();
            dateStyle.setAlignment(HorizontalAlignment.RIGHT);
            dateCell.setCellStyle(dateStyle);
            
            //STYLE FONT
            Font headerFont = workBook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 11);
            
            CellStyle headerStyle = workBook.createCellStyle();
            headerStyle.setFont(headerFont);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);

            //ADD
            XSSFRow row = null;
            Cell cell = null;
            row = sheet.createRow(3);

            cell = row.createCell(0, org.apache.poi.ss.usermodel.CellType.STRING);
            cell.setCellValue("STT");

            cell = row.createCell(1, org.apache.poi.ss.usermodel.CellType.STRING);
            Sheet s = cell.getSheet();
            cell.setCellValue("Mã Sản Phẩm");
            s.autoSizeColumn(1);

            cell = row.createCell(2, org.apache.poi.ss.usermodel.CellType.STRING);
            cell.setCellValue("Tên Sản Phẩm");
            
            cell = row.createCell(3, org.apache.poi.ss.usermodel.CellType.STRING);
            cell.setCellValue("Danh Mục");
            
            cell = row.createCell(4, org.apache.poi.ss.usermodel.CellType.STRING);
            cell.setCellValue("Nhãn Hàng");

            cell = row.createCell(5, org.apache.poi.ss.usermodel.CellType.STRING);
            cell.setCellValue("Màu Sắc");

            cell = row.createCell(6, org.apache.poi.ss.usermodel.CellType.STRING);
            cell.setCellValue("Kích Cỡ");
            
            cell = row.createCell(7, org.apache.poi.ss.usermodel.CellType.STRING);
            cell.setCellValue("Chất Liệu");

            cell = row.createCell(8, org.apache.poi.ss.usermodel.CellType.STRING);
            cell.setCellValue("Giá Nhập");

            cell = row.createCell(9, org.apache.poi.ss.usermodel.CellType.STRING);
            cell.setCellValue("Giá Bán");

            cell = row.createCell(10, org.apache.poi.ss.usermodel.CellType.STRING);
            cell.setCellValue("Trạng Thái");

            cell = row.createCell(11, org.apache.poi.ss.usermodel.CellType.STRING);
            cell.setCellValue("Tồn Kho");
            
            cell = row.createCell(12, org.apache.poi.ss.usermodel.CellType.STRING);
            cell.setCellValue("Khu Vực Kho");

            for (int i = 0; i < 13; i++) {
                cell = row.getCell(i);
                cell.setCellStyle(headerStyle);
            }

            ArrayList<SanPhamViewModel> ls = spService.getListSPVM();
            for (int i = 0; i < ls.size(); i++) {
                row = sheet.createRow(4 + i);

                //STYLE FONT
                CellStyle docuStyle = workBook.createCellStyle();
                docuStyle.setAlignment(HorizontalAlignment.CENTER);
                
                CellStyle cellStyleFormatNumber = null;

                if (cellStyleFormatNumber == null) {
                    short format = (short) BuiltinFormats.getBuiltinFormat("#,##0");
                    Workbook workbook = row.getSheet().getWorkbook();
                    cellStyleFormatNumber = workbook.createCellStyle();
                    cellStyleFormatNumber.setDataFormat(format);
                }

                cell = row.createCell(0, org.apache.poi.ss.usermodel.CellType.NUMERIC);
                cell.setCellValue(i + 1);
                cell.setCellStyle(docuStyle);

                cell = row.createCell(1, org.apache.poi.ss.usermodel.CellType.NUMERIC);
                cell.setCellValue("SP" + ls.get(i).getIdSP());
                cell.setCellStyle(docuStyle);

                cell = row.createCell(2, org.apache.poi.ss.usermodel.CellType.STRING);
                cell.setCellValue(ls.get(i).getTenSP());
                s.autoSizeColumn(2);

                cell = row.createCell(3, org.apache.poi.ss.usermodel.CellType.STRING);
                cell.setCellValue(ls.get(i).getDanhMuc());
                s.autoSizeColumn(3);

                cell = row.createCell(4, org.apache.poi.ss.usermodel.CellType.STRING);
                cell.setCellValue(ls.get(i).getNhanHang());
                s.autoSizeColumn(4);

                cell = row.createCell(5, org.apache.poi.ss.usermodel.CellType.STRING);
                cell.setCellValue(ls.get(i).getMauSac());
                cell.setCellStyle(docuStyle);

                cell = row.createCell(6, org.apache.poi.ss.usermodel.CellType.STRING);
                cell.setCellValue(ls.get(i).getKichCo());
                cell.setCellStyle(docuStyle);   

                cell = row.createCell(7, org.apache.poi.ss.usermodel.CellType.STRING);
                cell.setCellValue(ls.get(i).getChatLieu());
                cell.setCellStyle(docuStyle);

                cell = row.createCell(8, org.apache.poi.ss.usermodel.CellType.STRING);
                cell.setCellValue(ls.get(i).getGiaN());
                cell.setCellStyle(cellStyleFormatNumber);
                cell.setCellStyle(docuStyle);

                cell = row.createCell(9, org.apache.poi.ss.usermodel.CellType.NUMERIC);
                cell.setCellValue(ls.get(i).getGiaB());
                cell.setCellStyle(cellStyleFormatNumber);
                cell.setCellStyle(docuStyle);
                
                cell = row.createCell(10, org.apache.poi.ss.usermodel.CellType.STRING);
                cell.setCellValue(ls.get(i).getTrangT());
                s.autoSizeColumn(10);
                cell.setCellStyle(docuStyle);

                cell = row.createCell(11, org.apache.poi.ss.usermodel.CellType.STRING);
                cell.setCellValue(ls.get(i).getSoL());
                cell.setCellStyle(docuStyle);

                cell = row.createCell(12, org.apache.poi.ss.usermodel.CellType.NUMERIC);
                cell.setCellValue(ls.get(i).getKhoHang());
                s.autoSizeColumn(12);
                cell.setCellStyle(docuStyle);
            }
            
            //STYLE
            CellStyle footerStyle = workBook.createCellStyle();
            footerStyle.setFont(headerFont);
            footerStyle.setAlignment(HorizontalAlignment.LEFT);
            
            //ROW CUỐI
            int rowS = 5 + ls.size();

            XSSFRow tongSPRow = sheet.createRow(rowS);
            org.apache.poi.ss.usermodel.Cell tongSPCell = tongSPRow.createCell(1);

            tongSPCell.setCellValue("Tổng Sản Phẩm:");
            tongSPCell.setCellStyle(footerStyle);
            org.apache.poi.ss.usermodel.Cell tongSPCelll = tongSPRow.createCell(2);
            tongSPCelll.setCellValue(ls.size());
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);

            org.apache.poi.ss.usermodel.Cell ngX = tongSPRow.createCell(7);
            ngX.setCellValue("Người Xuất:");
            ngX.setCellStyle(footerStyle);
            org.apache.poi.ss.usermodel.Cell thongT = tongSPRow.createCell(8);
            thongT.setCellValue(uService.getName(Login.dataStatic));
            sheet.autoSizeColumn(7);
            sheet.autoSizeColumn(8);
            
            String defaultCurrentDirectoryPath = "C:\\Users\\X1\\OneDrive\\Documents\\Custom Office Templates";
            JFileChooser fileChooser = new JFileChooser(defaultCurrentDirectoryPath);
            fileChooser.setDialogTitle("Chọn nơi lưu file");
            fileChooser.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel Files", "xlsx", "xls");
            fileChooser.addChoosableFileFilter(filter);

            int userSelection = fileChooser.showSaveDialog(null);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                String filePath = fileToSave.getAbsolutePath();
                if (!filePath.endsWith(".xlsx")) {
                    filePath += ".xlsx";
                }

            File file = new File(filePath);
                try {
                    FileOutputStream fis = new FileOutputStream(file);
                    workBook.write(fis);
                    fis.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                JOptionPane.showMessageDialog(this, "Đã in danh sách thành công!", "POLYPOLO thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Thao tác đã bị hủy!", "POLYPOLO thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnExportMouseClicked

    private void btnExportMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExportMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnExportMousePressed

    private void btnBarcodeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBarcodeMouseClicked
        // BARCODE
        int pos = tblSP.getSelectedRow();
        if (pos == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn sản phẩm để lưu barcode!", "POLYPOLO thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        } else {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showSaveDialog(null);
            Integer id = Integer.valueOf(tblSP.getValueAt(pos, 0).toString());

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String fname = selectedFile.getAbsolutePath();

                try {
                    Linear barcode = new Linear();
                    barcode.setType(Linear.CODE128B);
                    barcode.setData("SP0" + id + "_" + "POLY_POLO");
                    barcode.setI(11.0f);
                    barcode.renderBarcode(fname + ".png");

                    JOptionPane.showMessageDialog(null, "Lưu barcode thành công!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Lưu barcode thất bại: " + ex.getMessage(), "POLYPOLO thông báo", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_btnBarcodeMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnAnDM;
    private javax.swing.JButton btnBarcode;
    private javax.swing.JButton btnClearDM;
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnHidden;
    private javax.swing.JButton btnImport;
    private javax.swing.JButton btnLoad;
    private javax.swing.JButton btnSuaDM;
    private javax.swing.JButton btnThemDM;
    private javax.swing.JButton btnUnhide;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnViewHide;
    private javax.swing.JComboBox<String> cboSearch;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JRadioButton rdoDMConHang;
    private javax.swing.JRadioButton rdoDMHethang;
    private javax.swing.JTable tblDanhMuc;
    private javax.swing.JTable tblSP;
    private javax.swing.JTextField txtMaDanhMuc;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtSearchDM;
    private javax.swing.JTextField txtTenDanhMuc;
    // End of variables declaration//GEN-END:variables
}
