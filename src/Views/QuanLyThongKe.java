/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package Views;

import Services.HoaDonService;
import Services.ThongKeService;
import Services.UserService;
import ViewModels.R_GioHangViewModel;
import ViewModels.HD_HoaDonViewModel;
import ViewModels.ThongKeViewDoanhThu;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author X1
 */
public class QuanLyThongKe extends javax.swing.JInternalFrame {

    HoaDonService hdsv = new HoaDonService();
    ThongKeService tksv = new ThongKeService();
    DecimalFormat formatter = new DecimalFormat("#,###");
    UserService uService = new UserService();

    /**
     * Creates new form TK
     */
    public QuanLyThongKe() {
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
        loadData(hdsv.getListHoaDon());
        ThongKe();
        loadCboNam(tksv.showYear());
        loadCboNamBieuDO(tksv.showYear());
        loadDT(tksv.getListDoanhThu());
        loadDataBieuDo(jpanelBieuDo, 0);
    }

    //LOAD
    void loadCboNam(ArrayList<Integer> ls) {
        DefaultComboBoxModel cbomodel = (DefaultComboBoxModel) cboNam.getModel();
        cbomodel.removeAllElements();

        for (Integer year : ls) {
            cbomodel.addElement(year);
        }
    }
    ///Load CBO năm cho BieuDo

    void loadCboNamBieuDO(ArrayList<Integer> ls) {
        DefaultComboBoxModel cbomodel = (DefaultComboBoxModel) cboNam2.getModel();
        cbomodel.removeAllElements();

        for (Integer year : ls) {
            cbomodel.addElement(year);
        }
    }

    void loadCboThangBieuDO(ArrayList<Integer> months) {
        DefaultComboBoxModel cbomodel = (DefaultComboBoxModel) cboThang.getModel();
        cbomodel.removeAllElements();

        for (Integer month : months) {
            cbomodel.addElement(month);
        }
    }

    public void loadData(ArrayList<HD_HoaDonViewModel> listHD) {
        DefaultTableModel model = (DefaultTableModel) tbDoanhThu.getModel();
        model.setRowCount(0);
        for (HD_HoaDonViewModel hd : listHD) {
            String formattedthanhTien = formatter.format(hd.getTongTien());

            model.addRow(new Object[]{
                hd.getMaHD(),
                hd.getTenNV(),
                hd.getTenKH(),
                hd.getNgayLap(),
                hd.getPhuongThuc(),
                formattedthanhTien
            });
        }
    }

    void loadTable(ArrayList<R_GioHangViewModel> ls) {
        DefaultTableModel model = (DefaultTableModel) tblGioH.getModel();
        model.setRowCount(0);

        for (R_GioHangViewModel gioHang : ls) {
            String formatdonGia = formatter.format(gioHang.getDonG());
            String formatthanhTien = formatter.format(gioHang.getThanhT());

            model.addRow(new Object[]{
                gioHang.getIdSPCT(),
                gioHang.getTenSP(),
                gioHang.getDanhM(),
                gioHang.getSoL(),
                gioHang.getDonG(),
                gioHang.getThue(),
                gioHang.getTongSauVAT(),
                gioHang.getThanhT(),
                formatdonGia,
                formatthanhTien
            });
        }
    }

    public void ThongKe() {
        lblTongDonHang.setText(String.valueOf(tksv.DonHang().getMaHD()));
        lblSP.setText(String.valueOf(tksv.SPBan().getSoL()));
        lblTreo1.setText(String.valueOf(tksv.Treo().getMaHD()));
        lblHoaDonThanh1.setText(String.valueOf(tksv.TT().getMaHD()));
        String tienHDThanh = formatter.format(tksv.TienThanh().getTongTien()) + "VND";
        lblTienHoaDonThanh.setText(tienHDThanh);
        String dtNgay = formatter.format(tksv.tongNgay().getTongTien()) + "VND";
        lblTongDoanhThuNgay.setText(dtNgay);
        String dtThang = formatter.format(tksv.tongThang().getTongTien()) + "VND";
        lblTongDoanhThuThang.setText(dtThang);
        String dtNam = formatter.format(tksv.tongNam().getTongTien()) + " VND";
        lblTongDoanhThuNam.setText(dtNam);
    }

    ///LoadBangDoanhThu EM TẠO 1 ThongKeViewDoanhThu
    void loadDT(ArrayList<ThongKeViewDoanhThu> ls) {
        DefaultTableModel model = (DefaultTableModel) tbBangTheoThang.getModel();
        model.setRowCount(0);
        for (ThongKeViewDoanhThu l : ls) {
            String formatTongTien = formatter.format(l.getTongTien());
            model.addRow(new Object[]{
                l.getThang(),
                l.getSoLuong(),
                formatTongTien
            });
        }
    }

    ///Biểu đồ theo nam, EM TẠO 1 ThongKeViewDoanhThu   ĐỂ LÀM BIỂU ĐỒ NÀY
    ///Có thêm cả phần RES Với Service
    public void loadDataBieuDo(JPanel jpnItem, int nam) {
        DefaultCategoryDataset data = new DefaultCategoryDataset();

        // Thêm dữ liệu vào dataset từ tháng 1 đến tháng 12
        for (int thang = 1; thang <= 12; thang++) {
            data.addValue(getTongTienTheoThang(thang), "Tổng tiền", String.valueOf(thang));
        }

        JFreeChart barChart = ChartFactory.createBarChart(
                "",
                "Tháng", "Tổng tiền",
                data, PlotOrientation.VERTICAL, false, true, false);

        // Thay đổi màu của cột 
        CategoryPlot plot = barChart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(30, 144, 255));

        // Thêm ghi chú cho trục x và y
        plot.getDomainAxis().setLabelFont(new Font("Times New Roman", Font.BOLD, 15));
        plot.getRangeAxis().setLabelFont(new Font("Times New Roman", Font.BOLD, 15));

        // Thay đổi màu sắc và kiểu đồ thị
        plot.setBackgroundPaint(Color.WHITE); // Đặt màu nền của biểu đồ thành màu trắng

        plot.setRangeGridlinePaint(Color.BLACK);
        plot.setDomainGridlinePaint(Color.BLACK);

        // Thêm tiêu đề và cách lề trên
        TextTitle title = new TextTitle("Biểu đồ thống kê số lượng bán hàng");
        title.setFont(new Font("Times New Roman", Font.HANGING_BASELINE, 20));
        title.setPadding(new RectangleInsets(5, 0, 0, 0));
        barChart.setTitle(title);

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new Dimension(jpnItem.getWidth(), jpnItem.getHeight()));

        jpnItem.removeAll();
        jpnItem.setLayout(new CardLayout());
        jpnItem.add(chartPanel);
        jpnItem.validate();
        jpnItem.repaint();
    }

// Phương thức này trả về tổng tiền cho một tháng cụ thể
    private double getTongTienTheoThang(int thang) {
        Integer nam = (Integer) cboNam2.getSelectedItem();
        ArrayList<ThongKeViewDoanhThu> listItem = tksv.getListByNam(nam);
        if (listItem != null) {
            for (ThongKeViewDoanhThu item : listItem) {
                if (item.getThang() == thang) {
                    return item.getTongTien();
                }
            }
        }
        return 0;
    }

// Biểu đồ lọc theo tháng
    public void BieuDoThongKeTheoThang(JPanel jpnItem, Integer thang, Integer nam) {
        ArrayList<ThongKeViewDoanhThu> ls = tksv.getListThongKeTheoThang(thang, nam);

        DefaultCategoryDataset data = new DefaultCategoryDataset();
        if (ls != null) {
            for (ThongKeViewDoanhThu tk : ls) {
                data.addValue(tk.getTongTien(), "", tk.getNgay());
            }
        }

        JFreeChart barChart = ChartFactory.createBarChart(
                "",
                "Tháng", "Tổng tiền",
                data, PlotOrientation.VERTICAL, false, true, false);

        // Thay đổi màu của cột 
        CategoryPlot plot = barChart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(255, 105, 180));

        // Thêm ghi chú cho trục x và y
        plot.getDomainAxis().setLabelFont(new Font("Times New Roman", Font.BOLD, 15));
        plot.getRangeAxis().setLabelFont(new Font("Times New Roman", Font.BOLD, 15));

        // Thay đổi màu sắc và kiểu đồ thị
        plot.setBackgroundPaint(Color.WHITE); // Đặt màu nền của biểu đồ thành màu trắng

        plot.setRangeGridlinePaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.WHITE);

        // Thêm tiêu đề và cách lề trên
        TextTitle title = new TextTitle("Biểu đồ thống kê");
        title.setFont(new Font("Times New Roman", Font.HANGING_BASELINE, 20));
        title.setPadding(new RectangleInsets(5, 0, 0, 0));
        barChart.setTitle(title);

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new Dimension(jpnItem.getWidth(), jpnItem.getHeight()));

        jpnItem.removeAll();
        jpnItem.setLayout(new CardLayout());
        jpnItem.add(chartPanel);
        jpnItem.validate();
        jpnItem.repaint();
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
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblTongDonHang = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblSP = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        lblTongDoanhThuNgay = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        lblTongDoanhThuThang = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        lblHoaDonThanh1 = new javax.swing.JLabel();
        lblTreo1 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        lblTongDoanhThuNam = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblTienHoaDonThanh = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        dcsTu = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        dcsToi = new com.toedter.calendar.JDateChooser();
        btnTimNgay = new javax.swing.JButton();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbDoanhThu = new javax.swing.JTable();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblGioH = new javax.swing.JTable();
        jpanel5555 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        cboNam = new javax.swing.JComboBox<>();
        jScrollPane5 = new javax.swing.JScrollPane();
        tbBangTheoThang = new javax.swing.JTable();
        btnINTKtheothang = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cboNam2 = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        cboThang = new javax.swing.JComboBox<>();
        jpanelBieuDo = new javax.swing.JPanel();

        setMaximumSize(new java.awt.Dimension(1090, 630));
        setMinimumSize(new java.awt.Dimension(1090, 630));
        setPreferredSize(new java.awt.Dimension(1090, 630));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 102, 0));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Tổng đơn hàng");

        lblTongDonHang.setBackground(new java.awt.Color(255, 255, 255));
        lblTongDonHang.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTongDonHang.setForeground(new java.awt.Color(255, 255, 255));
        lblTongDonHang.setText("0");

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("đơn hàng");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Tổng sản phẩm đã bán: ");

        lblSP.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblSP.setForeground(new java.awt.Color(255, 255, 255));
        lblSP.setText("0");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblSP)
                        .addGap(0, 71, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblTongDonHang)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addGap(23, 23, 23))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lblTongDonHang))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lblSP))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(153, 153, 255));
        jPanel3.setPreferredSize(new java.awt.Dimension(162, 100));

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Tổng doanh thu ngày");

        lblTongDoanhThuNgay.setBackground(new java.awt.Color(255, 255, 255));
        lblTongDoanhThuNgay.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTongDoanhThuNgay.setForeground(new java.awt.Color(255, 255, 255));
        lblTongDoanhThuNgay.setText("0");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblTongDoanhThuNgay)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTongDoanhThuNgay)
                .addContainerGap(50, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 153, 51));
        jPanel4.setPreferredSize(new java.awt.Dimension(162, 100));

        jLabel15.setBackground(new java.awt.Color(255, 255, 255));
        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Tổng doanh thu tháng");

        lblTongDoanhThuThang.setBackground(new java.awt.Color(255, 255, 255));
        lblTongDoanhThuThang.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTongDoanhThuThang.setForeground(new java.awt.Color(255, 255, 255));
        lblTongDoanhThuThang.setText("0");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Hóa đơn thành công:");

        lblHoaDonThanh1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblHoaDonThanh1.setForeground(new java.awt.Color(255, 255, 255));
        lblHoaDonThanh1.setText("0");

        lblTreo1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTreo1.setForeground(new java.awt.Color(255, 255, 255));
        lblTreo1.setText("0");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Hóa đơn treo:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(lblTongDoanhThuThang)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                            .addComponent(jLabel17)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblTreo1))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                            .addComponent(jLabel16)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(lblHoaDonThanh1))))
                .addContainerGap(55, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTongDoanhThuThang)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(lblHoaDonThanh1))
                .addGap(2, 2, 2)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(lblTreo1))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 0, 255));
        jPanel5.setPreferredSize(new java.awt.Dimension(162, 100));

        jLabel22.setBackground(new java.awt.Color(255, 255, 255));
        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Tổng doanh thu năm");

        lblTongDoanhThuNam.setBackground(new java.awt.Color(255, 255, 255));
        lblTongDoanhThuNam.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTongDoanhThuNam.setForeground(new java.awt.Color(255, 255, 255));
        lblTongDoanhThuNam.setText("0");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Tổng doanh thu:");

        lblTienHoaDonThanh.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTienHoaDonThanh.setForeground(new java.awt.Color(255, 255, 255));
        lblTienHoaDonThanh.setText("0");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTongDoanhThuNam)
                    .addComponent(jLabel22)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblTienHoaDonThanh)))
                .addContainerGap(55, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTongDoanhThuNam)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(lblTienHoaDonThanh))
                .addGap(28, 28, 28))
        );

        jLabel29.setText("Thời Gian:");

        jLabel3.setText("Tới");

        btnTimNgay.setText("SEARCH");
        btnTimNgay.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTimNgayMouseClicked(evt);
            }
        });

        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel31.setText("Chi tiết doanh thu");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(224, 224, 224)
                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(487, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel31)
                .addContainerGap(203, Short.MAX_VALUE))
        );

        jLabel32.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel32.setText("Chi tiết doanh thu");

        tbDoanhThu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã HĐ", "Tên Nhân Viên", "Tên Khách Hàng", "Ngày Lập", "PTTT", "Tổng Tiền"
            }
        ));
        tbDoanhThu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDoanhThuMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbDoanhThu);

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
        jScrollPane6.setViewportView(tblGioH);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 930, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 930, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel32)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(144, 144, 144)
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(190, 190, 190)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Doanh Thu", jPanel6);

        jLabel6.setText("Năm:");

        cboNam.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cboNamMouseClicked(evt);
            }
        });
        cboNam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboNamActionPerformed(evt);
            }
        });

        tbBangTheoThang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Tháng", "Số lượng hóa phẩm", "Tổng tiền"
            }
        ));
        jScrollPane5.setViewportView(tbBangTheoThang);

        btnINTKtheothang.setText("IN TK Theo Năm");
        btnINTKtheothang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnINTKtheothangMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jpanel5555Layout = new javax.swing.GroupLayout(jpanel5555);
        jpanel5555.setLayout(jpanel5555Layout);
        jpanel5555Layout.setHorizontalGroup(
            jpanel5555Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanel5555Layout.createSequentialGroup()
                .addComponent(btnINTKtheothang)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addGap(42, 42, 42)
                .addComponent(cboNam, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(156, 156, 156))
            .addGroup(jpanel5555Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 1020, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpanel5555Layout.setVerticalGroup(
            jpanel5555Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanel5555Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpanel5555Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboNam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(btnINTKtheothang))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Bảng thống kê", jpanel5555);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setText("Năm");

        cboNam2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cboNam2MouseClicked(evt);
            }
        });

        jLabel5.setText("Tháng");

        cboThang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cboThangMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboThang, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 427, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(32, 32, 32)
                .addComponent(cboNam2, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(104, 104, 104))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cboNam2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(cboThang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jpanelBieuDo.setPreferredSize(new java.awt.Dimension(0, 296));

        javax.swing.GroupLayout jpanelBieuDoLayout = new javax.swing.GroupLayout(jpanelBieuDo);
        jpanelBieuDo.setLayout(jpanelBieuDoLayout);
        jpanelBieuDoLayout.setHorizontalGroup(
            jpanelBieuDoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jpanelBieuDoLayout.setVerticalGroup(
            jpanelBieuDoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 251, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jpanelBieuDo, javax.swing.GroupLayout.DEFAULT_SIZE, 1014, Short.MAX_VALUE))
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jpanelBieuDo, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Biểu đồ thống kê", jPanel9);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(dcsTu, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(37, 37, 37)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(36, 36, 36)
                                .addComponent(dcsToi, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(130, 130, 130))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(22, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnTimNgay, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1032, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(26, 26, 26))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel29)
                    .addComponent(dcsTu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(dcsToi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimNgay))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Doanh Số", jPanel1);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 600));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTimNgayMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTimNgayMouseClicked
        java.util.Date utilDate = dcsTu.getDate();
        java.util.Date utillDate = dcsToi.getDate();
        ArrayList<HD_HoaDonViewModel> lisHG = tksv.TheoNgay(utilDate, utillDate);
        loadData(lisHG);
    }//GEN-LAST:event_btnTimNgayMouseClicked

    private void tbDoanhThuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDoanhThuMouseClicked
        Integer maHD = Integer.valueOf(tbDoanhThu.getValueAt(tbDoanhThu.getSelectedRow(), 0).toString());
        loadTable(hdsv.getListGioHangById(maHD));
    }//GEN-LAST:event_tbDoanhThuMouseClicked

    private void cboNamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboNamMouseClicked
        // CLICK CBO
        Integer nam = (Integer) cboNam.getSelectedItem();
        loadDT(tksv.getListByNam(nam));
    }//GEN-LAST:event_cboNamMouseClicked

    private void cboNamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboNamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboNamActionPerformed

    private void cboNam2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboNam2MouseClicked
//        // TODO add your handling code here:
        Integer thang = (Integer) cboThang.getSelectedItem();
        Integer nam = (Integer) cboNam2.getSelectedItem();
        loadDataBieuDo(jpanelBieuDo, nam);
        loadCboThangBieuDO(tksv.showMonth(nam));
    }//GEN-LAST:event_cboNam2MouseClicked

    private void cboThangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboThangMouseClicked
//        // TODO add your handling code here:
        Integer thang = (Integer) cboThang.getSelectedItem();
        Integer nam = (Integer) cboNam2.getSelectedItem();
        BieuDoThongKeTheoThang(jpanelBieuDo, thang, nam);
    }//GEN-LAST:event_cboThangMouseClicked

    private void btnINTKtheothangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnINTKtheothangMouseClicked
        //IN
        try {
            XSSFWorkbook workBook = new XSSFWorkbook();
            XSSFSheet sheet = workBook.createSheet("THỐNG KÊ DOANH THU THEO NĂM");

            // Title Style
            XSSFRow titleRow = sheet.createRow(0);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue("Danh Sách Thống Kê POLYPOLO");
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
            XSSFFont font = workBook.createFont();
            font.setFontHeightInPoints((short) 19);
            font.setBold(true);
            XSSFCellStyle titleStyle = workBook.createCellStyle();
            titleStyle.setFont(font);
            titleStyle.setAlignment(HorizontalAlignment.CENTER);
            titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            titleCell.setCellStyle(titleStyle);

            // Date
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String currentDate = sdf.format(new Date());

            SimpleDateFormat sdfHrs = new SimpleDateFormat("HH:mm:ss");
            String currentTime = sdfHrs.format(new Date());

            XSSFRow dateRow = sheet.createRow(1);
            Cell dateCell = dateRow.createCell(0);
            dateCell.setCellValue("Ngày: " + currentDate + " | Giờ: " + currentTime);
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 5));
            XSSFCellStyle dateStyle = workBook.createCellStyle();
            dateStyle.setAlignment(HorizontalAlignment.RIGHT);
            dateCell.setCellStyle(dateStyle);

            // Header Style
            XSSFFont headerFont = workBook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 11);

            XSSFCellStyle headerStyle = workBook.createCellStyle();
            headerStyle.setFont(headerFont);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);

            // Adding Header
            XSSFRow headerRow = sheet.createRow(3);

            String[] headers = {"STT", "Tháng", "Số lượng hóa đơn", "Doanh thu", "lợi Nhuận"};
            for (int i = 0; i < headers.length; i++) {
                Cell headerCell = headerRow.createCell(i, CellType.STRING);
                headerCell.setCellValue(headers[i]);
                headerCell.setCellStyle(headerStyle);
            }

            // Adding Data
            int nam = (int) cboNam.getSelectedItem();
            ArrayList<ThongKeViewDoanhThu> ls = tksv.getListDoanhThuTheoNam(nam);
            for (int i = 0; i < ls.size(); i++) {
                ThongKeViewDoanhThu tk = ls.get(i);
                XSSFRow dataRow = sheet.createRow(4 + i);

                for (int j = 0; j < headers.length; j++) {
                    Cell dataCell = dataRow.createCell(j, CellType.STRING);
                    switch (j) {
                        case 0:
                            dataCell.setCellValue(i + 1);
                            break;
                        case 1:
                            dataCell.setCellValue(tk.getThang());
                            break;
                        case 2:
                            dataCell.setCellValue(tk.getSoLuong());
                            break;
                        case 3:
                            dataCell.setCellValue(tk.getTongTien());
                            break;
                        case 4:
                            dataCell.setCellValue(tk.getLoiNhuan());
                            break;
                        default:
                            break;
                    }
                }
            }

            // Auto adjust column widths
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Footer Style
            XSSFCellStyle footerStyle = workBook.createCellStyle();
            footerStyle.setFont(headerFont);
            footerStyle.setAlignment(HorizontalAlignment.LEFT);

            // Adding Footer
            int lastRow = 4 + ls.size();
            XSSFRow footerRow = sheet.createRow(lastRow + 1);

            Cell footerCell1 = footerRow.createCell(1);
            footerCell1.setCellValue("Tổng:");
            footerCell1.setCellStyle(footerStyle);

            Cell footerCell2 = footerRow.createCell(2);
            footerCell2.setCellValue(ls.size());
            footerCell2.setCellStyle(footerStyle);

            Cell userCell1 = footerRow.createCell(4);
            userCell1.setCellValue("Người Xuất:");
            userCell1.setCellStyle(footerStyle);

            Cell userCell2 = footerRow.createCell(5);
            userCell2.setCellValue(uService.getName(Login.dataStatic));
            userCell2.setCellStyle(footerStyle);

            // File Saving
            JFileChooser fileChooser = new JFileChooser("D:\\");
            fileChooser.setDialogTitle("Chọn nơi lưu file");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel Files", "xlsx", "xls");
            fileChooser.addChoosableFileFilter(filter);

            int userSelection = fileChooser.showSaveDialog(null);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                String filePath = fileToSave.getAbsolutePath();
                if (!filePath.endsWith(".xlsx")) {
                    filePath += ".xlsx";
                }

                try (FileOutputStream fos = new FileOutputStream(filePath)) {
                    workBook.write(fos);
                    JOptionPane.showMessageDialog(null, "Đã in danh sách thành công!", "POLYPOLO thông báo", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Thao tác đã bị hủy!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }//GEN-LAST:event_btnINTKtheothangMouseClicked

    private void tblGioHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblGioHMouseClicked
        // LOAD SPCT
    }//GEN-LAST:event_tblGioHMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnINTKtheothang;
    private javax.swing.JButton btnTimNgay;
    private javax.swing.JComboBox<String> cboNam;
    private javax.swing.JComboBox<String> cboNam2;
    private javax.swing.JComboBox<String> cboThang;
    private com.toedter.calendar.JDateChooser dcsToi;
    private com.toedter.calendar.JDateChooser dcsTu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JPanel jpanel5555;
    private javax.swing.JPanel jpanelBieuDo;
    private javax.swing.JLabel lblHoaDonThanh1;
    private javax.swing.JLabel lblSP;
    private javax.swing.JLabel lblTienHoaDonThanh;
    private javax.swing.JLabel lblTongDoanhThuNam;
    private javax.swing.JLabel lblTongDoanhThuNgay;
    private javax.swing.JLabel lblTongDoanhThuThang;
    private javax.swing.JLabel lblTongDonHang;
    private javax.swing.JLabel lblTreo1;
    private javax.swing.JTable tbBangTheoThang;
    private javax.swing.JTable tbDoanhThu;
    private javax.swing.JTable tblGioH;
    // End of variables declaration//GEN-END:variables
}
