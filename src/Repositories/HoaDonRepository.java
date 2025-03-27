/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositories;

import Models.HoaDonChiTiet;
import Models.HoaDon;
import Models.KhachHang;
import ViewModels.HD_HoaDonViewModel;
import ViewModels.R_GioHangViewModel;
import ViewModels.HD_InvoiceViewModel;
import ViewModels.HD_SanPhamViewModel;
import ViewModels.HoaDonViewModel;
import java.sql.*;
import java.util.ArrayList;
/**
 *
 * @author X1
 */
public class HoaDonRepository {
    DbConnection dbConnection;
    
    //<editor-fold defaultstate="collapsed" desc=" ADD ">
    //INVOICE
    public ArrayList<HD_InvoiceViewModel> getListKHById(Integer id){
        String sql = "SELECT hd.MaHoaDon, hd.TenKhachHang, hd.LoaiKhachHang, hd.PhuongThucThanhToan\n" +
"							FROM HoaDon hd WHERE hd.MaHoaDon = ?";
        ArrayList<HD_InvoiceViewModel> ls = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ps.setObject(1, id);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer maHD = rs.getInt("MaHoaDon");
                String tenKH = rs.getString("TenKhachHang");
                String phuongThuc = rs.getString("PhuongThucThanhToan");
                String loaiK = rs.getString("LoaiKhachHang");
                
                HD_InvoiceViewModel listKH = new HD_InvoiceViewModel(maHD, tenKH, phuongThuc, loaiK);
                ls.add(listKH);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
    
    public ArrayList<R_GioHangViewModel> printInvoiceById(Integer id) {
        String sql = "SELECT spct.MaSanPham, spct.TenSanPhamChiTiet\n" +
"				, hdct.SoLuong, hdct.DonGia, sp.PhanTramThue\n" +
"				,  SUM(hdct.DonGia * hdct.SoLuong) * (1 + sp.PhanTramThue/100.0) AS 'Total' FROM SanPham sp\n" +
"               INNER JOIN SanPhamChiTiet spct ON spct.MaSanPham = sp.MaSanPham\n" +
"               INNER JOIN HoaDonChiTiet hdct ON hdct.MaSanPhamChiTiet = spct.MaSanPhamChiTiet\n" +
"               INNER JOIN HoaDon hd ON hd.MaHoaDon = hdct.MaHoaDon\n" +
"                WHERE hd.MaHoaDon = ? \n" +
"                			GROUP BY spct.MaSanPham, spct.TenSanPhamChiTiet, hdct.SoLuong, hdct.DonGia, sp.PhanTramThue\n" +
"                             ;";
        ArrayList<R_GioHangViewModel> ls = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ps.setObject(1, id);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer maSP = rs.getInt("MaSanPham");
                String tenSP = rs.getString("TenSanPhamChiTiet");
                Integer soL = rs.getInt("SoLuong");
                Double donG = rs.getDouble("DonGia");
                Integer thue = rs.getInt("PhanTramThue");
                Double total = rs.getDouble("Total");
                
                R_GioHangViewModel invoice = new R_GioHangViewModel(maSP, soL, thue, donG, total, tenSP);
                ls.add(invoice);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
    
    //ACTION
    public Boolean checkExists(int idSPCT, int id){
        String sql = "SELECT COUNT(*) FROM HoaDonChiTiet "
                + "WHERE MaSanPhamChiTiet = ? AND MaHoaDon = ? AND Deleted!=1";
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ps.setObject(1, idSPCT);
            ps.setObject(2, id);
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;  
    }
    
    //CHECK KH
    public Boolean checkKH (String sdt){
        String sql = "SELECT COUNT(*) FROM KhachHang WHERE SoDienThoai = ?;";
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ps.setObject(1, sdt);
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;  
    }
    
    //TOTAL SOL
    public Integer getTotalP(int id) {
        String sql = "SELECT SUM(hdct.SoLuong) AS 'TOTALP' FROM HoaDon hd INNER JOIN HoaDonChiTiet hdct ON hd.MaHoaDon = hdct.MaHoaDon \n"
                + "							WHERE hd.MaHoaDon = ?";

        try (Connection conn = dbConnection.getConnection(); 
                PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("TOTALP");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Boolean mergeSP(Integer soL, Integer id, Integer idSPCT){
        String sql = "UPDATE HoaDonChiTiet "
                + "SET SoLuong = SoLuong + ? "
                + "WHERE MaHoaDon = ? AND MaSanPhamChiTiet = ?";
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ps.setObject(1, soL);
            ps.setObject(2, id);
            ps.setObject(3, idSPCT);
            
            int check = ps.executeUpdate();
            if (check>0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public Boolean addHD(HoaDon hd){
        String sql = "INSERT INTO HoaDon (MaNhanVien, MaKhachHang, TenKhachHang, TongTien, NgayLap, TrangThai, PhuongThucThanhToan, LoaiKhachHang, Deleted)\n" +
                        "VALUES (?, ?, ?, 0, ?, N'Chưa thanh toán', ?, ?, 0);";
        
        try (Connection conn = dbConnection.getConnection(); 
                PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, hd.getIdNV());
            ps.setObject(2, hd.getIdKH());
            ps.setObject(3, hd.getTenKH());
            ps.setObject(4, hd.getNgayL());
            ps.setObject(5, hd.getPhuongT());
            ps.setObject(6, hd.getLoaiKH());

            int check = ps.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public Boolean addHDCT(HoaDonChiTiet hdct) {
        String sql = "INSERT INTO HoaDonChiTiet (MaHoaDon, MaSanPhamChiTiet, SoLuong, DonGia, Deleted) VALUES \n"
                + "				(?, ?, ?, ?, 0);";

        try (Connection conn = dbConnection.getConnection(); 
                PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, hdct.getMaHD());
            ps.setObject(2, hdct.getMaSPCT());
            ps.setObject(3, hdct.getSoLuong());
            ps.setObject(4, hdct.getDonGia());
            
            int check = ps.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public Boolean updateSP(Integer id, Integer soL){
        String sql = "UPDATE SanPhamChiTiet SET SoLuongTon =? WHERE MaSanPhamChiTiet =?";
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ps.setObject(1, soL);
            ps.setObject(2, id);
            
            int check = ps.executeUpdate();
            if (check>0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public HoaDon getTotal(Integer id){
        String sql = "SELECT SUM((hdct.SoLuong * hdct.DonGia) * (1 + sp.PhanTramThue/100)) AS 'total'\n" +
"				FROM HoaDonChiTiet hdct\n" +
"				INNER JOIN SanPhamChiTiet spct ON hdct.MaSanPhamChiTiet = spct.MaSanPhamChiTiet\n" +
"				INNER JOIN SanPham sp ON sp.MaSanPham = spct.MaSanPham\n" +
"				WHERE hdct.MaHoaDon = ? AND hdct.Deleted !=1";
        HoaDon hd = null;

        try (Connection conn = dbConnection.getConnection(); 
                PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                hd = new HoaDon(rs.getDouble(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hd;
    }
    
    public Double getVATFee(Integer id) {
        String sql = "SELECT SUM(hdct.SoLuong * hdct.DonGia) AS 'totalWithoutVAT', "
                + "SUM(hdct.SoLuong * hdct.DonGia * (1 + sp.PhanTramThue / 100)) AS 'totalWithVAT' "
                + "FROM HoaDonChiTiet hdct "
                + "INNER JOIN SanPhamChiTiet spct ON hdct.MaSanPhamChiTiet = spct.MaSanPhamChiTiet "
                + "INNER JOIN SanPham sp ON sp.MaSanPham = spct.MaSanPham "
                + "WHERE hdct.MaHoaDon = ? AND hdct.Deleted != 1";

        double vatFee = 0.0;

        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                double totalWithoutVAT = rs.getDouble("totalWithoutVAT");
                double totalWithVAT = rs.getDouble("totalWithVAT");
                vatFee = totalWithVAT - totalWithoutVAT;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vatFee;
    }
    
    public Boolean setTongT (int idHD){
        String sql = "UPDATE HoaDon \n" +
"                   SET TongTien = (SELECT SUM((hdct.SoLuong * hdct.DonGia) * (1 + sp.PhanTramThue/100)) AS 'total'\n" +
"				FROM HoaDonChiTiet hdct\n" +
"				INNER JOIN SanPhamChiTiet spct ON hdct.MaSanPhamChiTiet = spct.MaSanPhamChiTiet\n" +
"				INNER JOIN SanPham sp ON sp.MaSanPham = spct.MaSanPham\n" +
"				WHERE hdct.MaHoaDon = ?)\n" +
"                       WHERE MaHoaDon = ?";
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ps.setObject(1, idHD);
            ps.setObject(2, idHD);
            
            int check = ps.executeUpdate();
            if (check>0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean delSingle(int id, int idSPCT){
        String sql = "DELETE FROM HoaDonChiTiet \n" +
                        "WHERE MaHoaDon  = ? AND MaSanPhamChiTiet = ?";
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ps.setObject(1, id);
            ps.setObject(2, idSPCT);
            
            int check = ps.executeUpdate();
            if (check>0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public Boolean emptyBasket(int id){
        String sql = "DELETE FROM HoaDonChiTiet WHERE MaHoaDon = ?";
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ps.setObject(1, id);
            
            int check = ps.executeUpdate();
            if (check>0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public Boolean updateThanhToan(int id){
        String sql = "UPDATE HoaDon\n" +
                        "SET TrangThai = N'Đã thanh toán'\n" +
                        "WHERE MaHoaDon = ?";
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ps.setObject(1, id);
            
            int check = ps.executeUpdate();
            if (check>0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;  
    }
    
    //SEARCH 
    public KhachHang getListKHBySDT(String sdt){
        String sql = "SELECT * FROM HoaDon hd INNER JOIN KhachHang kh ON hd.MaKhachHang = kh.MaKhachHang \n" +
"						WHERE kh.SoDienThoai = ?";
        KhachHang kh = new KhachHang();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ps.setObject(1, sdt);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer maKH = rs.getInt("MaKhachHang");
                String tenKH = rs.getString("TenKhachHang");
                String loaiKH = rs.getString("LoaiKhachHang");
                
                kh = new KhachHang(maKH, tenKH, loaiKH);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kh;
    }
        
    //GETLIST
    public ArrayList<HoaDonChiTiet> getListHDCTById(int id){
        String sql = "SELECT * FROM HoaDonChiTiet WHERE MaHoaDon = ?";
        ArrayList<HoaDonChiTiet> ls = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ps.setObject(1, id);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer idHD = rs.getInt("MaHoaDon");
                Integer idSPCT = rs.getInt("MaSanPhamChiTiet");
                Integer soL = rs.getInt("SoLuong");
                Double donG = rs.getDouble("DonGia");
                
                HoaDonChiTiet hdct = new HoaDonChiTiet(idHD, idSPCT, soL, donG);
                ls.add(hdct);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
    
    public HoaDon getByHoaDon(Integer id){
        String sql = "SELECT * FROM HoaDon WHERE Deleted != 1 AND MaHoaDon =?";
        HoaDon hd = new HoaDon();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, id);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer idHoaDon = rs.getInt("MaHoaDon");
                Integer idKH = rs.getInt("MaKhachHang");
                Integer idNV = rs.getInt("MaNhanVien");
                String tenKH = rs.getString("TenKhachHang");
                String trangT = rs.getString("TrangThai");
                String phuongT = rs.getString("PhuongThucThanhToan");
                String loaiK = rs.getString("LoaiKhachHang");
                Double tongD = rs.getDouble("TongTien");
                Date ngayL = rs.getDate("NgayLap");
                
                hd = new HoaDon(idHoaDon, idKH, idNV, trangT, tenKH, phuongT, loaiK, tongD, ngayL);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hd;
    }
    
    public HoaDonChiTiet getByHDCT(int id){
        String sql = "SELECT * FROM HoaDonChiTiet WHERE MaHoaDon = ?";
        HoaDonChiTiet hdct = new HoaDonChiTiet();

        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ps.setObject(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer idHD = rs.getInt("MaHoaDon");
                Integer idSPCT = rs.getInt("MaSanPhamChiTiet");
                Integer soL = rs.getInt("SoLuong");
                Double donG = rs.getDouble("DonGia");
                
                hdct = new HoaDonChiTiet(idHD, idSPCT, soL, donG);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hdct;
    }
    
    public ArrayList<HoaDon> getAllHoaDon(){
        String sql = "SELECT * FROM HoaDon WHERE Deleted!=1";
        ArrayList<HoaDon> ls = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer id = rs.getInt("MaHoaDon");
                String tenKH = rs.getString("TenKhachHang");
                String phuongT = rs.getString("PhuongThucThanhToan");
                Double tongD = rs.getDouble("TongTien");
                
                HoaDon hd = new HoaDon(id, tenKH, phuongT, tongD);
                ls.add(hd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
    
    public ArrayList<HoaDon> getHoaDon(String trangT){
        String sql = "SELECT * FROM HoaDon WHERE Deleted!=1 AND TrangThai = N'Chưa thanh toán'";
        ArrayList<HoaDon> ls = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer id = rs.getInt("MaHoaDon");
                String tenKH = rs.getString("TenKhachHang");
                String phuongT = rs.getString("PhuongThucThanhToan");
                Double tongD = rs.getDouble("TongTien");
                
                HoaDon hd = new HoaDon(id, tenKH, phuongT, tongD);
                ls.add(hd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
    
    public ArrayList<HD_SanPhamViewModel> getListSanPham(){
        String sql = "SELECT spct.MaSanPhamChiTiet, spct.TenSanPhamChiTiet, dm.TenDanhMuc, br.TenNhanHang\n" +
"		, ms.TenMau, sz.TenSize, cl.TenChatLieu, sp.GiaBan, spct.SoLuongTon FROM SanPhamChiTiet spct INNER JOIN SanPham sp ON spct.MaSanPham = sp.MaSanPham\n" +
"									INNER JOIN DanhMuc dm ON sp.MaDanhMuc = dm.MaDanhMuc\n" +
"									INNER JOIN NhanHang br ON spct.NhanHangID = br.NhanHangID\n" +
"									INNER JOIN MauSac ms ON spct.MaMau = ms.MaMau\n" +
"									INNER JOIN Size sz ON spct.MaSize = sz.MaSize\n" +
"									INNER JOIN ChatLieu cl ON spct.MaChatLieu = cl.MaChatLieu WHERE sp.Deleted!=1";
        ArrayList<HD_SanPhamViewModel> ls = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer id = rs.getInt("MaSanPhamChiTiet");
                String tenSP = rs.getString("TenSanPhamChiTiet");
                String danhM = rs.getString("TenDanhMuc");
                String brand = rs.getString("TenNhanHang");
                String mauS = rs.getString("TenMau");
                String sz = rs.getString("TenSize");
                String chatL = rs.getString("TenChatLieu");
                Double giaB = rs.getDouble("GiaBan");
                Integer soL = rs.getInt("SoLuongTon");
                
                HD_SanPhamViewModel sp = new HD_SanPhamViewModel(id, soL, tenSP, danhM, brand, mauS, sz, chatL, giaB);
                ls.add(sp);    
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
    
    public ArrayList<R_GioHangViewModel> getListGioHangById(Integer id){
        String sql = "SELECT spct.MaSanPhamChiTiet, spct.TenSanPhamChiTiet, dm.TenDanhMuc, sp.GiaBan, hdct.SoLuong, sp.PhanTramThue,  \n" +
"								(sp.GiaBan + (sp.GiaBan * sp.PhanTramThue / 100)) AS 'TOTAL_VAT'  FROM SanPham sp \n" +
"										INNER JOIN SanPhamChiTiet spct ON sp.MaSanPham = spct.MaSanPham\n" +
"										INNER JOIN DanhMuc dm ON sp.MaDanhMuc = dm.MaDanhMuc\n" +
"										INNER JOIN HoaDonChiTiet hdct ON spct.MaSanPhamChiTiet = hdct.MaSanPhamChiTiet\n" +
"										INNER JOIN HoaDon hd ON hdct.MaHoaDon = hd.MaHoaDon\n" +
"													WHERE sp.Deleted != 1 AND hd.MaHoaDon = ?";
        ArrayList<R_GioHangViewModel> ls = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ps.setObject(1, id);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer idSP = rs.getInt("MaSanPhamChiTiet");
                String tenSP = rs.getString("TenSanPhamChiTiet");
                String danhM = rs.getString("TenDanhMuc");
                Double donG = rs.getDouble("GiaBan");
                Integer soL = rs.getInt("SoLuong");
                Integer thue = rs.getInt("PhanTramThue");
                Double total = rs.getDouble("TOTAL_VAT");
                
                R_GioHangViewModel gh = new R_GioHangViewModel(idSP, soL, thue, donG, total, tenSP, danhM);
                ls.add(gh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
    
    public HoaDon getModel(){
        String sql = "SELECT TOP 1 * FROM HoaDon ORDER BY MaHoaDon DESC;";
        HoaDon hd = new HoaDon();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer maHD = rs.getInt("MaHoaDon");
                Integer maNV = rs.getInt("MaNhanVien");
                Integer maKH = rs.getInt("MaKhachHang");
                String tenKH = rs.getString("TenKhachHang");
                Double tongT = rs.getDouble("TongTien");
                Date ngayL = rs.getDate("NgayLap");
                String trangT = rs.getString("TrangThai");
                String phuongT = rs.getString("PhuongThucThanhToan");
                String loaiK = rs.getString("LoaiKhachHang");
                
                hd = new HoaDon(maHD, maKH, maNV, trangT, tenKH, phuongT, loaiK, tongT, ngayL);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hd;
    }
    
    public ArrayList<HoaDonChiTiet> getListHDCT(){
        String sql = "SELECT * FROM HoaDonChiTiet";
        ArrayList<HoaDonChiTiet> ls = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer idHoaDonCT = rs.getInt("MaHoaDonChiTiet");
                Integer idHoaDon = rs.getInt("MaHoaDon");
                Integer idSPCT = rs.getInt("MaSanPhamChiTiet");
                Integer soL = rs.getInt("SoLuong");
                Double donG = rs.getDouble("DonGia");
                
                HoaDonChiTiet hd = new HoaDonChiTiet(idHoaDon, idHoaDonCT, idSPCT, soL, donG);
                ls.add(hd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
    //</editor-fold>
    
    //SEARCH
    public ArrayList<HoaDonViewModel> getListViewModel(){
        String sql = "SELECT hd.MaHoaDon, hd.TenKhachHang, nv.TenNhanVien, hd.LoaiKhachHang, hd.PhuongThucThanhToan, hd.NgayLap, hd.TongTien, hd.TrangThai FROM HoaDon hd \n" +
"										INNER JOIN NhanVien nv ON hd.MaNhanVien = nv.MaNhanVien \n" +
"													";
        ArrayList<HoaDonViewModel> ls = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer idHD = rs.getInt("MaHoaDon");
                String hoTen = rs.getString("TenKhachHang");
                String nVien = rs.getString("TenNhanVien");
                String loaiK = rs.getString("LoaiKhachHang");
                String phuongT = rs.getString("PhuongThucThanhToan");
                Date ngayL = rs.getDate("NgayLap");
                Double tongT = rs.getDouble("TongTien");
                String trangT = rs.getString("TrangThai");
                
                HoaDonViewModel hd = new HoaDonViewModel(idHD, hoTen, nVien, loaiK, phuongT, trangT, ngayL, tongT);
                ls.add(hd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
    
    public ArrayList<HoaDonViewModel> getListByNV(String name){
        String sql = "SELECT hd.MaHoaDon, hd.TenKhachHang, nv.TenNhanVien, hd.LoaiKhachHang, hd.PhuongThucThanhToan, hd.NgayLap, hd.TongTien, hd.TrangThai FROM HoaDon hd \n" +
"										INNER JOIN NhanVien nv ON hd.MaNhanVien = nv.MaNhanVien \n" +
"											WHERE nv.TenNhanVien = ?		";
        ArrayList<HoaDonViewModel> ls = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ps.setObject(1, name);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer idHD = rs.getInt("MaHoaDon");
                String hoTen = rs.getString("TenKhachHang");
                String nVien = rs.getString("TenNhanVien");
                String loaiK = rs.getString("LoaiKhachHang");
                String phuongT = rs.getString("PhuongThucThanhToan");
                Date ngayL = rs.getDate("NgayLap");
                Double tongT = rs.getDouble("TongTien");
                String trangT = rs.getString("TrangThai");
                
                HoaDonViewModel hd = new HoaDonViewModel(idHD, hoTen, nVien, loaiK, phuongT, trangT, ngayL, tongT);
                ls.add(hd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
    
    public ArrayList<HoaDonViewModel> getListByPhuongT(String name){
        String sql = "SELECT hd.MaHoaDon, hd.TenKhachHang, nv.TenNhanVien, hd.LoaiKhachHang, hd.PhuongThucThanhToan, hd.NgayLap, hd.TongTien, hd.TrangThai FROM HoaDon hd \n" +
"										INNER JOIN NhanVien nv ON hd.MaNhanVien = nv.MaNhanVien \n" +
"											WHERE hd.PhuongThucThanhToan = ?		";
        ArrayList<HoaDonViewModel> ls = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ps.setObject(1, name);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer idHD = rs.getInt("MaHoaDon");
                String hoTen = rs.getString("TenKhachHang");
                String nVien = rs.getString("TenNhanVien");
                String loaiK = rs.getString("LoaiKhachHang");
                String phuongT = rs.getString("PhuongThucThanhToan");
                Date ngayL = rs.getDate("NgayLap");
                Double tongT = rs.getDouble("TongTien");
                String trangT = rs.getString("TrangThai");
                
                HoaDonViewModel hd = new HoaDonViewModel(idHD, hoTen, nVien, loaiK, phuongT, trangT, ngayL, tongT);
                ls.add(hd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
    
    public ArrayList<HoaDonViewModel> getListByTrangT(String name){
        String sql = "SELECT hd.MaHoaDon, hd.TenKhachHang, nv.TenNhanVien, hd.LoaiKhachHang, hd.PhuongThucThanhToan, hd.NgayLap, hd.TongTien, hd.TrangThai FROM HoaDon hd \n" +
"										INNER JOIN NhanVien nv ON hd.MaNhanVien = nv.MaNhanVien \n" +
"											WHERE hd.TrangThai = ?";
        ArrayList<HoaDonViewModel> ls = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ps.setObject(1, name);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer idHD = rs.getInt("MaHoaDon");
                String hoTen = rs.getString("TenKhachHang");
                String nVien = rs.getString("TenNhanVien");
                String loaiK = rs.getString("LoaiKhachHang");
                String phuongT = rs.getString("PhuongThucThanhToan");
                Date ngayL = rs.getDate("NgayLap");
                Double tongT = rs.getDouble("TongTien");
                String trangT = rs.getString("TrangThai");
                
                HoaDonViewModel hd = new HoaDonViewModel(idHD, hoTen, nVien, loaiK, phuongT, trangT, ngayL, tongT);
                ls.add(hd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
    
    public ArrayList<HoaDonViewModel> getListByDate(Date from, Date to){
        String sql = "SELECT hd.MaHoaDon, hd.TenKhachHang, nv.TenNhanVien, hd.LoaiKhachHang, hd.PhuongThucThanhToan, hd.NgayLap, hd.TongTien, hd.TrangThai FROM HoaDon hd \n" +
"										INNER JOIN NhanVien nv ON hd.MaNhanVien = nv.MaNhanVien \n" +
"											WHERE hd.NgayLap BETWEEN ? AND ?		";
        ArrayList<HoaDonViewModel> ls = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ps.setObject(1, from);
            ps.setObject(2, to);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer idHD = rs.getInt("MaHoaDon");
                String hoTen = rs.getString("TenKhachHang");
                String nVien = rs.getString("TenNhanVien");
                String loaiK = rs.getString("LoaiKhachHang");
                String phuongT = rs.getString("PhuongThucThanhToan");
                Date ngayL = rs.getDate("NgayLap");
                Double tongT = rs.getDouble("TongTien");
                String trangT = rs.getString("TrangThai");
                
                HoaDonViewModel hd = new HoaDonViewModel(idHD, hoTen, nVien, loaiK, phuongT, trangT, ngayL, tongT);
                ls.add(hd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
    
    public ArrayList<HoaDonViewModel> getListByTenKH(String name){
        String sql = "SELECT hd.MaHoaDon, hd.TenKhachHang, nv.TenNhanVien, hd.LoaiKhachHang, hd.PhuongThucThanhToan, hd.NgayLap, hd.TongTien, hd.TrangThai FROM HoaDon hd \n" +
"										INNER JOIN NhanVien nv ON hd.MaNhanVien = nv.MaNhanVien \n" +
"											WHERE hd.TenKhachHang LIKE '%" + name + "%'";
        ArrayList<HoaDonViewModel> ls = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer idHD = rs.getInt("MaHoaDon");
                String hoTen = rs.getString("TenKhachHang");
                String nVien = rs.getString("TenNhanVien");
                String loaiK = rs.getString("LoaiKhachHang");
                String phuongT = rs.getString("PhuongThucThanhToan");
                Date ngayL = rs.getDate("NgayLap");
                Double tongT = rs.getDouble("TongTien");
                String trangT = rs.getString("TrangThai");
                
                HoaDonViewModel hd = new HoaDonViewModel(idHD, hoTen, nVien, loaiK, phuongT, trangT, ngayL, tongT);
                ls.add(hd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
    
    //DELETE
    public Boolean deleteHD(int id){
        String sql = "DELETE FROM HoaDonChiTiet\n" +
                                    "WHERE MaHoaDon = ? ; 													\n" +
                                    "DELETE FROM HoaDon \n" +
                                    "WHERE MaHoaDon = ? ;";
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ps.setObject(1, id);
            ps.setObject(2, id);

            int check = ps.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    //SCAN
    public ArrayList<HD_SanPhamViewModel> getListSanPhamCode(String Code) {
        String sql = "SELECT spct.MaSanPhamChiTiet, spct.TenSanPhamChiTiet, dm.TenDanhMuc, br.TenNhanHang\n"
                + "		, ms.TenMau, sz.TenSize, cl.TenChatLieu, sp.GiaBan, spct.SoLuongTon FROM SanPhamChiTiet spct INNER JOIN SanPham sp ON spct.MaSanPham = sp.MaSanPham\n"
                + "									INNER JOIN DanhMuc dm ON sp.MaDanhMuc = dm.MaDanhMuc\n"
                + "									INNER JOIN NhanHang br ON spct.NhanHangID = br.NhanHangID\n"
                + "									INNER JOIN MauSac ms ON spct.MaMau = ms.MaMau\n"
                + "									INNER JOIN Size sz ON spct.MaSize = sz.MaSize\n"
                + "									INNER JOIN ChatLieu cl ON spct.MaChatLieu = cl.MaChatLieu WHERE sp.Deleted!=1 AND spct.Barcode = ?";
        ArrayList<HD_SanPhamViewModel> ls = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, Code);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("MaSanPhamChiTiet");
                String tenSP = rs.getString("TenSanPhamChiTiet");
                String danhM = rs.getString("TenDanhMuc");
                String brand = rs.getString("TenNhanHang");
                String mauS = rs.getString("TenMau");
                String sz = rs.getString("TenSize");
                String chatL = rs.getString("TenChatLieu");
                Double giaB = rs.getDouble("GiaBan");
                Integer soL = rs.getInt("SoLuongTon");

                HD_SanPhamViewModel sp = new HD_SanPhamViewModel(id, soL, tenSP, danhM, brand, mauS, sz, chatL, giaB);
                ls.add(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
    
    public Boolean updateSPCode(String code, Integer soL) {
        String sql = "UPDATE SanPhamChiTiet SET SoLuongTon =? WHERE Barcode =?";

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, soL);
            ps.setObject(2, code);

            int check = ps.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
 
    
    //LINH
    public ArrayList<HD_HoaDonViewModel> getListHoaDon() {
        String sql = "select hd.MaHoaDon, nv.TenNhanVien, hd.TenKhachHang, kh.SoDienThoai, hd.PhuongThucThanhToan, hd.TongTien, hd.NgayLap, hd.TrangThai from HoaDon hd\n"
                + "left JOIN KhachHang kh ON kh.MaKhachHang = hd.MaKhachHang\n"
                + "left JOIN NhanVien nv ON nv.MaNhanVien = hd.MaNhanVien";
        ArrayList<HD_HoaDonViewModel> ls = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer maHD = rs.getInt("MaHoaDon");
                String tenNV = rs.getString("TenNhanVien");
                String tenKH = rs.getString("TenKhachHang");
                String soDT = rs.getString("SoDienThoai");
                String phuongThuc = rs.getString("PhuongThucThanhToan");
                Double tongTien = rs.getDouble("TongTien");
                Date ngayLap = rs.getDate("NgayLap");
                String trangT = rs.getString("TrangThai");

                HD_HoaDonViewModel hd = new HD_HoaDonViewModel(maHD, tenKH, soDT, phuongThuc, trangT, tongTien, ngayLap, tenNV);
                ls.add(hd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
}
