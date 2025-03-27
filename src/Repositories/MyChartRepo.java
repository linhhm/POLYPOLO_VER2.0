/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositories;

import ViewModels.DonutPieChartViewModel;
import ViewModels.PieChartViewModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author X1
 */
public class MyChartRepo {
    DbConnection dbConnection;
    
    public ArrayList<Integer> showYear(){
        String sql = "SELECT YEAR(HoaDon.NgayLap) AS Nam\n" +
                "	FROM HoaDon GROUP BY YEAR(HoaDon.NgayLap);";
        ArrayList<Integer> ls = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer nam = rs.getInt("Nam");
                
                ls.add(nam);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
    
    public ArrayList<Integer> showMonth(int year){
        String sql = "SELECT \n" +
                        "    MONTH(HoaDon.NgayLap) AS Thang\n" +
                        "	FROM HoaDon WHERE YEAR(HoaDon.NgayLap) = ? \n" +
                        "			GROUP BY MONTH(HoaDon.NgayLap) ;";
        ArrayList<Integer> ls = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ps.setObject(1, year);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer month = rs.getInt("Thang");
                
                ls.add(month);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
    
    //BEST SELLER
    public ArrayList<PieChartViewModel> getListBestSeller(int y, int m){
        String sql = "SELECT TOP 6 \n" +
                    "    spct.TenSanPhamChiTiet, SUM(hdc.SoLuong) AS soL\n" +
                    "	FROM HoaDonChiTiet hdc\n" +
                    "			INNER JOIN HoaDon hd ON hdc.MaHoaDon = hd.MaHoaDon\n" +
                    "			INNER JOIN SanPhamChiTiet spct ON hdc.MaSanPhamChiTiet = spct.MaSanPhamChiTiet\n" +
                    "			INNER JOIN SanPham sp ON spct.MaSanPham = sp.MaSanPham\n" +
                    "		WHERE \n" +
                    "			YEAR(hd.NgayLap) = ? AND\n" +
                    "			MONTH(hd.NgayLap) = ? \n" +
                    "				GROUP BY spct.TenSanPhamChiTiet\n" +
                    "				ORDER BY SUM(hdc.SoLuong) DESC;";
        ArrayList<PieChartViewModel> ls = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ps.setObject(1, y);
            ps.setObject(2, m);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                String tenSP = rs.getString("TenSanPhamChiTiet");
                Integer soL = rs.getInt("soL");

                PieChartViewModel pc = new PieChartViewModel(tenSP, null, soL);
                ls.add(pc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
    
    //TOP CAT
    public ArrayList<PieChartViewModel> getTopSell(int y, int m){
        String sql = "SELECT TOP 6 \n" +
                        "    dm.TenDanhMuc, SUM(hdc.SoLuong) AS soL\n" +
                        "	FROM HoaDonChiTiet hdc\n" +
                        "			INNER JOIN HoaDon hd ON hdc.MaHoaDon = hd.MaHoaDon\n" +
                        "			INNER JOIN SanPhamChiTiet spct ON hdc.MaSanPhamChiTiet = spct.MaSanPhamChiTiet\n" +
                        "			INNER JOIN SanPham sp ON spct.MaSanPham = sp.MaSanPham\n" +
                        "			INNER JOIN DanhMuc dm ON sp.MaDanhMuc = dm.MaDanhMuc\n" +
                        "		WHERE \n" +
                        "			YEAR(hd.NgayLap) = ? AND MONTH(hd.NgayLap) = ?\n" +
                        "					GROUP BY dm.TenDanhMuc\n" +
                        "					ORDER BY SUM(hd.TongTien) DESC;";
        ArrayList<PieChartViewModel> ls = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ps.setObject(1, y);
            ps.setObject(2, m);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                String tenDM = rs.getString("TenDanhMuc");
                Integer soL = rs.getInt("soL");

                PieChartViewModel pc = new PieChartViewModel(null, tenDM, soL);
                ls.add(pc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
    
    //PROFIT
    public ArrayList<DonutPieChartViewModel> getTopProfit(int y, int m){
        String sql = "SELECT TOP 6 \n" +
                        "    spct.TenSanPhamChiTiet\n" +
                        "		,SUM((sp.GiaBan - sp.GiaNhap) * hdc.SoLuong) AS profit\n" +
                        "	FROM HoaDonChiTiet hdc\n" +
                        "			INNER JOIN HoaDon hd ON hdc.MaHoaDon = hd.MaHoaDon\n" +
                        "			INNER JOIN SanPhamChiTiet spct ON hdc.MaSanPhamChiTiet = spct.MaSanPhamChiTiet\n" +
                        "			INNER JOIN SanPham sp ON spct.MaSanPham = sp.MaSanPham\n" +
                        "		WHERE \n" +
                        "			YEAR(hd.NgayLap) = ? AND MONTH(hd.NgayLap) = ?\n" +
                        "				GROUP BY spct.TenSanPhamChiTiet\n" +
                        "				ORDER BY SUM((sp.GiaBan - sp.GiaNhap) * hdc.SoLuong) DESC;";
        ArrayList<DonutPieChartViewModel> ls = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ps.setObject(1, y);
            ps.setObject(2, m);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                String tenSP = rs.getString("TenSanPhamChiTiet");
                Double loiN = rs.getDouble("profit");

                DonutPieChartViewModel pc = new DonutPieChartViewModel(tenSP, loiN);
                ls.add(pc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
}
