/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositories;

import Models.KhoHang;
import ViewModels.KhoHangViewModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author X1
 */
public class KhoHangRepository {
    DbConnection dbConnection;
    
    //GET LIST
    public ArrayList<KhoHang> getList(){
        String sql = "SELECT * FROM KhuVucKho WHERE Deleted!=1";
        ArrayList<KhoHang> ls = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer idKhuVuc = rs.getInt("KhuVucID");
                String tenKho = rs.getString("TenKhuVuc");
                String moTa = rs.getString("MoTa");
                
                KhoHang kh = new KhoHang(idKhuVuc, tenKho, moTa);
                ls.add(kh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
    
    public ArrayList<KhoHangViewModel> getListById(Integer id){
        String sql = "SELECT sp.MaSanPham, spct.TenSanPhamChiTiet, spct.SoLuongTon, sp.TrangThai FROM KhuVucKho kh INNER JOIN SanPham sp ON kh.KhuVucID = sp.KhuVucID\n" +
"							INNER JOIN SanPhamChiTiet spct ON sp.MaSanPham = spct.MaSanPham\n" +
"								WHERE sp.Deleted!=1 AND kh.KhuVucID = ?";
        ArrayList<KhoHangViewModel> ls = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ps.setObject(1, id);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer idSP = rs.getInt("MaSanPham");
                String tenSP = rs.getString("TenSanPhamChiTiet");
                Integer soL = rs.getInt("SoLuongTon");
                String moTa = rs.getString("TrangThai");
                
                KhoHangViewModel kh = new KhoHangViewModel(idSP, soL, tenSP, moTa);
                ls.add(kh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
    
    public KhoHang getIdByName(String name){
        String sql = "SELECT * FROM KhuVucKho WHERE Deleted!=1 AND TenKhuVuc = ?";
        KhoHang kh = new KhoHang();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ps.setObject(1, name);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer idKhuVuc = rs.getInt("KhuVucID");
                String tenKho = rs.getString("TenKhuVuc");
                
                kh = new KhoHang(idKhuVuc, tenKho);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kh;
    }
    
    //SEARCH
    public ArrayList<KhoHang> getListByName(String name){
        String sql = "SELECT * FROM KhuVucKho WHERE TenKhuVuc LIKE '%" + name + "%'";
        ArrayList<KhoHang> ls = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer idKhuVuc = rs.getInt("KhuVucID");
                String tenKho = rs.getString("TenKhuVuc");
                String moTa = rs.getString("MoTa");
                
                KhoHang kh = new KhoHang(idKhuVuc, tenKho, moTa);
                ls.add(kh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
    
    public ArrayList<KhoHang> getListByDescription(String name){
        String sql = "SELECT * FROM KhuVucKho WHERE MoTa LIKE '%" + name + "%'";
        ArrayList<KhoHang> ls = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer idKhuVuc = rs.getInt("KhuVucID");
                String tenKho = rs.getString("TenKhuVuc");
                String moTa = rs.getString("MoTa");
                
                KhoHang kh = new KhoHang(idKhuVuc, tenKho, moTa);
                ls.add(kh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
    
    //ACTION
    public Boolean addKho(KhoHang kh){
        String sql = "INSERT INTO KhuVucKho (TenKhuVuc, MoTa, Deleted) VALUES"
                + "              (?, ? , 0)";
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ps.setObject(1, kh.getTenKho());
            ps.setObject(2, kh.getMoTa());
                    
            int check = ps.executeUpdate();
            if (check>0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public Boolean updateKho(KhoHang kh){
        String sql = "UPDATE KhuVucKho\n" +
                            "SET TenKhuVuc = ?\n" +
                            ", MoTa = ?\n" +
                            "WHERE KhuVucID = ?";
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ps.setObject(1, kh.getTenKho());
            ps.setObject(2, kh.getMoTa());
            ps.setObject(3, kh.getMaKho());
                    
            int check = ps.executeUpdate();
            if (check>0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public Boolean deleteKho(int id){
        String sql = "DELETE FROM KhuVucKho WHERE KhuVucID = ?";
        
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
    
    //CHECK ID
    public boolean checkIdKho(Integer id) {
        String sql = "SELECT COUNT(*) FROM KhuVucKho WHERE Deleted!=1 AND KhuVucID = ?";
        
        try (Connection conn = dbConnection.getConnection(); 
                PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean checkName(String name) {
        String sql = "SELECT COUNT(*) FROM KhuVucKho WHERE Deleted!=1 AND TenKhuVuc = ?";
        
        try (Connection conn = dbConnection.getConnection(); 
                PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setString(1, name);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
}
