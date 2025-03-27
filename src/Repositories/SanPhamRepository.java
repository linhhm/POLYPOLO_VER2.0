/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositories;

import Models.SP_ChatLieu;
import Models.SP_DanhMuc;
import Models.SP_KichCo;
import Models.SP_MauSac;
import Models.SP_NhanHang;
import Models.SanPham;
import Models.SanPhamChiTiet;
import ViewModels.SanPhamViewModel;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author X1
 */
public class SanPhamRepository {
    DbConnection dbConnection;
    
    //<editor-fold defaultstate="collapsed" desc=" DANH MUC ">
    public ArrayList<SP_DanhMuc> getList(){
        String sql = "SELECT * FROM DanhMuc WHERE Deleted!=1";
        ArrayList<SP_DanhMuc> ls = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer idDM = rs.getInt("MaDanhMuc");
                String tenDM = rs.getString("TenDanhMuc");
                String trangT = rs.getString("TrangThai");
                
                SP_DanhMuc dm = new SP_DanhMuc(idDM, tenDM, trangT);
                ls.add(dm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls; 
    }
    
    public SP_DanhMuc getIdByName(String name){
        String sql = "SELECT * FROM DanhMuc WHERE Deleted!=1 AND TenDanhMuc = ?";
        SP_DanhMuc dm = new SP_DanhMuc();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ps.setObject(1, name);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer maDM = rs.getInt("MaDanhMuc");
                String tenDM = rs.getString("TenDanhMuc");
                
                dm = new SP_DanhMuc(maDM, tenDM, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dm;
    }
    
    //CHECK ID
    public boolean checkIdCat(Integer id) {
        String sql = "SELECT COUNT(*) FROM DanhMuc WHERE Deleted!=1 AND MaDanhMuc = ?";
        
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
    
    //SEARCH
    public ArrayList<SP_DanhMuc> searchByNameDM(String name){
        String sql = "SELECT * FROM DanhMuc WHERE Deleted!=1 AND TenDanhMuc LIKE '%"+name+"%'";
        ArrayList<SP_DanhMuc> ls = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer idB = rs.getInt("MaDanhMuc");
                String bN = rs.getString("TenDanhMuc");
                String trangT = rs.getString("TrangThai");
                
                SP_DanhMuc nh = new SP_DanhMuc(idB, bN, trangT);
                ls.add(nh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
    
    //CRUD
    public Boolean addCat(SP_DanhMuc danhMuc) {
        String sql = "INSERT INTO DanhMuc(TenDanhMuc, TrangThai,Deleted) VALUES (?,?,0)";

        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, danhMuc.getTenDM());
            ps.setObject(2, danhMuc.getTrangThai());

            int check = ps.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public Boolean updateDanhMuc(SP_DanhMuc danhMuc) {
        String sql = "UPDATE DanhMuc SET TenDanhMuc = ?, TrangThai = ? WHERE Deleted != 1 AND MaDanhMuc = ?";

        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, danhMuc.getTenDM());
            ps.setObject(2, danhMuc.getTrangThai());
            ps.setObject(3, danhMuc.getIdDM());

            int check = ps.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public Boolean hideDanhMuc(SP_DanhMuc danhMuc) {
        String sql = "UPDATE DanhMuc SET Deleted = 1 WHERE MaDanhMuc = ?";

        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, danhMuc.getIdDM());

            int check = ps.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public Boolean unhideDanhMuc(SP_DanhMuc danhMuc) {
        String sql = "UPDATE DanhMuc SET Deleted = 0 WHERE MaDanhMuc = ?";

        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, danhMuc.getIdDM());

            int check = ps.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public ArrayList<SP_DanhMuc> getListHideDM(){
        String sql = "SELECT * FROM DanhMuc WHERE Deleted = 1";
        ArrayList<SP_DanhMuc> ls = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer idB = rs.getInt("MaDanhMuc");
                String tenDM = rs.getString("TenDanhMuc");
                String trangT = rs.getString("TrangThai");
                
                SP_DanhMuc dm = new SP_DanhMuc(idB, tenDM, trangT);
                ls.add(dm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc=" MATERIAL ">
    public ArrayList<SP_ChatLieu> getListChatL(){
        String sql = "SELECT * FROM ChatLieu WHERE Deleted!=1";
        ArrayList<SP_ChatLieu> ls = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer idMat = rs.getInt("MaChatLieu");
                String tenMat = rs.getString("TenChatLieu");
                
                SP_ChatLieu cl = new SP_ChatLieu(idMat, tenMat);
                ls.add(cl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
    
    public SP_ChatLieu getIdByNameChatL(String name){
        String sql = "SELECT * FROM ChatLieu WHERE Deleted!=1 AND TenChatLieu = ?";
        SP_ChatLieu cl = new SP_ChatLieu();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ps.setObject(1, name);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer idMat = rs.getInt("MaChatLieu");
                String tenMat = rs.getString("TenChatLieu");
                
                cl = new SP_ChatLieu(idMat, tenMat);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cl;
    }
    
    //CHECK ID
    public boolean checkIdChatL(Integer id) {
        String sql = "SELECT COUNT(*) FROM ChatLieu WHERE Deleted!=1 AND MaChatLieu = ?";
        
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
    
    //SEARCH
    public ArrayList<SP_ChatLieu> searchByNameChatL(String name){
        String sql = "SELECT * FROM ChatLieu WHERE Deleted!=1 AND TenChatLieu LIKE '%"+name+"%'";
        ArrayList<SP_ChatLieu> ls = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer idB = rs.getInt("MaChatLieu");
                String bN = rs.getString("TenChatLieu");
                
                SP_ChatLieu nh = new SP_ChatLieu(idB, bN);
                ls.add(nh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
    
    //CRUD
    public Boolean addMaterial(SP_ChatLieu material) {
        String sql = "INSERT INTO ChatLieu (TenChatLieu, Deleted) VALUES (?,0)";

        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, material);

            int check = ps.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public Boolean updateMaterial(SP_ChatLieu material) {
        String sql = "UPDATE ChatLieu SET TenChatLieu = ? WHERE Deleted != 1 AND MaChatLieu = ?";

        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, material.getTenChatL());

            int check = ps.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public Boolean hideMaterial(SP_ChatLieu material) {
        String sql = "UPDATE ChatLieu SET Deleted = 1 WHERE MaChatLieu = ?";

        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, material.getIdChatL());

            int check = ps.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public Boolean unhideMaterial(SP_ChatLieu material) {
        String sql = "UPDATE ChatLieu SET Deleted = 0 WHERE MaChatLieu = ?";

        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, material.getIdChatL());

            int check = ps.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public ArrayList<SP_ChatLieu> getListHideChatL(){
        String sql = "SELECT * FROM ChatLieu WHERE Deleted = 1";
        ArrayList<SP_ChatLieu> ls = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer idB = rs.getInt("MaChatLieu");
                String bN = rs.getString("TenChatLieu");
                
                SP_ChatLieu nh = new SP_ChatLieu(idB, bN);
                ls.add(nh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc=" BRAND ">
    public ArrayList<SP_NhanHang> getListBrand(){
        String sql = "SELECT * FROM NhanHang WHERE Deleted!=1";
        ArrayList<SP_NhanHang> ls = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer idB = rs.getInt("NhanHangID");
                String bN = rs.getString("TenNhanHang");
                
                SP_NhanHang nh = new SP_NhanHang(idB, bN);
                ls.add(nh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
    
    public SP_NhanHang getIdByNameBrand(String name){
        String sql = "SELECT * FROM NhanHang WHERE Deleted!=1 AND TenNhanHang = ?";
        SP_NhanHang nh = new SP_NhanHang();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ps.setObject(1, name);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer idB = rs.getInt("NhanHangID");
                String bN = rs.getString("TenNhanHang");
                
                nh = new SP_NhanHang(idB, bN);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nh;
    }
    
    //CHECK ID
    public boolean checkIdBrand(Integer id) {
        String sql = "SELECT COUNT(*) FROM Size WHERE Deleted!=1 AND NhanHangID = ?";
        
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
    
    //SEARCH
    public ArrayList<SP_NhanHang> searchByNameBrand(String name){
        String sql = "SELECT * FROM NhanHang WHERE Deleted!=1 AND TenNhanHang LIKE '%"+name+"%'";
        ArrayList<SP_NhanHang> ls = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer idB = rs.getInt("NhanHangID");
                String bN = rs.getString("TenNhanHang");
                
                SP_NhanHang nh = new SP_NhanHang(idB, bN);
                ls.add(nh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
    
    //CRUD
    public Boolean addBrand(SP_NhanHang brand) {
        String sql = "INSERT INTO NhanHang (TenNhanHang, Deleted) VALUES (?, 0)";

        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, brand.getBrandName());

            int check = ps.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public Boolean updateBrand(SP_NhanHang brand) {
        String sql = "UPDATE NhanHang SET TenNhanHang = ? WHERE Deleted != 1 AND NhanHangID = ?";

        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, brand.getBrandName());
            ps.setObject(2, brand.getIdBrand());

            int check = ps.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public Boolean hideBrand(SP_NhanHang brand) {
        String sql = "UPDATE NhanHang SET Deleted = 1 WHERE NhanHangID = ?";

        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, brand.getIdBrand());

            int check = ps.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public Boolean unhideBrand(SP_NhanHang brand) {
        String sql = "UPDATE NhanHang SET Deleted = 0 WHERE NhanHangID = ?";

        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, brand.getIdBrand());

            int check = ps.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public ArrayList<SP_NhanHang> getListHideBrand(){
        String sql = "SELECT * FROM NhanHang WHERE Deleted = 1";
        ArrayList<SP_NhanHang> ls = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer idB = rs.getInt("NhanHangID");
                String bN = rs.getString("TenNhanHang");
                
                SP_NhanHang nh = new SP_NhanHang(idB, bN);
                ls.add(nh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc=" SZ ">
    public ArrayList<SP_KichCo> getListSz(){
        String sql = "SELECT * FROM Size WHERE Deleted!=1";
        ArrayList<SP_KichCo> ls = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer idSz = rs.getInt("MaSize");
                String tenSz = rs.getString("TenSize");
                
                SP_KichCo sz = new SP_KichCo(idSz, tenSz);
                ls.add(sz);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
    
    public SP_KichCo getIdByNameSz(String name){
        String sql = "SELECT * FROM Size WHERE Deleted!=1 AND TenSize = ?";
        SP_KichCo sz = new SP_KichCo();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ps.setObject(1, name);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer idSz = rs.getInt("MaSize");
                String tenSz = rs.getString("TenSize");
                
                sz = new SP_KichCo(idSz, tenSz);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sz;
    }
    
    //CHECK ID
    public boolean checkIdSz(Integer id) {
        String sql = "SELECT COUNT(*) FROM Size WHERE Deleted!=1 AND MaSize = ?";
        
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
    
    //SEARCH
    public ArrayList<SP_KichCo> searchByNameSz(String name){
        String sql = "SELECT * FROM Size WHERE Deleted!=1 AND TenSize LIKE '%"+name+"%'";
        ArrayList<SP_KichCo> ls = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer idB = rs.getInt("MaSize");
                String bN = rs.getString("TenSize");
                
                SP_KichCo nh = new SP_KichCo(idB, bN);
                ls.add(nh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
    
    //CRUD
    public Boolean addSz(SP_KichCo sz) {
        String sql = "INSERT INTO Size (TenSize,Deleted) VALUES (?,0)";

        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, sz.getTenSz());

            int check = ps.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public Boolean updateSize(SP_KichCo sz) {
        String sql = "UPDATE Size SET TenSize = ? WHERE Deleted != 1 AND MaSize = ?";

        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, sz.getTenSz());
            ps.setObject(2, sz.getIdSz());

            int check = ps.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public Boolean hideSize(SP_KichCo sz) {
        String sql = "UPDATE Size SET Deleted = 1 WHERE MaSize = ?";

        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, sz.getIdSz());

            int check = ps.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public Boolean unhideSize(SP_KichCo sz) {
        String sql = "UPDATE Size SET Deleted = 0 WHERE MaSize = ?";

        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, sz.getIdSz());

            int check = ps.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public ArrayList<SP_KichCo> getListHideSz(){
        String sql = "SELECT * FROM Size WHERE Deleted = 1";
        ArrayList<SP_KichCo> ls = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer idSz = rs.getInt("MaSize");
                String tenSz = rs.getString("TenSize");
                
                SP_KichCo dm = new SP_KichCo(idSz, tenSz);
                ls.add(dm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc=" PALETTE ">
    public ArrayList<SP_MauSac> getListColor(){
        String sql = "SELECT * FROM MauSac WHERE Deleted!=1";
        ArrayList<SP_MauSac> ls = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer idMau = rs.getInt("MaMau");
                String tenMau = rs.getString("TenMau");
                
                SP_MauSac ms = new SP_MauSac(idMau, tenMau);
                ls.add(ms);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
    
    public SP_MauSac getIdByNameColor(String name){
        String sql = "SELECT * FROM MauSac WHERE Deleted!=1 AND TenMau = ?";
        SP_MauSac ms = new SP_MauSac();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ps.setObject(1, name);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer idMau = rs.getInt("MaMau");
                String tenMau = rs.getString("TenMau");
                
                ms = new SP_MauSac(idMau, tenMau);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ms;
    }
    
    //SEARCH
    public ArrayList<SP_MauSac> searchByNameColor(String name){
        String sql = "SELECT * FROM MauSac WHERE Deleted!=1 AND TenMau LIKE '%"+name+"%'";
        ArrayList<SP_MauSac> ls = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer idB = rs.getInt("MaMau");
                String bN = rs.getString("TenMau");
                
                SP_MauSac nh = new SP_MauSac(idB, bN);
                ls.add(nh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
    
    //CHECK ID
    public boolean checkIdColor(Integer id) {
        String sql = "SELECT COUNT(*) FROM MauSac WHERE Deleted!=1 AND MaMau = ?";
        
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
    
    //CRUD
    public Boolean addColor(SP_MauSac color) {
        String sql = "INSERT INTO MauSac (TenMau,Deleted) VALUES (?,0)";

        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, color);

            int check = ps.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public Boolean updateMauSac(SP_MauSac color) {
        String sql = "UPDATE MauSac SET TenMau = ? WHERE Deleted != 1 AND MaMau = ?";

        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, color.getTenMau());
            ps.setObject(2, color.getIdMau());

            int check = ps.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public Boolean hideMauSac(SP_MauSac color) {
        String sql = "UPDATE MauSac SET Deleted = 1 WHERE MaMau = ?";

        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, color.getIdMau());

            int check = ps.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public Boolean unhideMauSac(SP_MauSac color) {
        String sql = "UPDATE MauSac SET Deleted = 0 WHERE MaMau = ?";

        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, color.getIdMau());

            int check = ps.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public ArrayList<SP_MauSac> getListHideColor(){
        String sql = "SELECT * FROM MauSac WHERE Deleted = 1";
        ArrayList<SP_MauSac> ls = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer idB = rs.getInt("MaMau");
                String tenM = rs.getString("TenMau");
                
                SP_MauSac dm = new SP_MauSac(idB, tenM);
                ls.add(dm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
    //</editor-fold>
    
    //SEARCH
    public ArrayList<SanPhamViewModel> searchByName(String name){
        String sql = "SELECT sp.MaSanPham, spct.TenSanPhamChiTiet, dm.TenDanhMuc, nh.TenNhanHang, ms.TenMau, sz.TenSize, cl.TenChatLieu\n" +
"			, sp.GiaNhap, sp.GiaBan, sp.TrangThai, spct.SoLuongTon, kh.TenKhuVuc FROM SanPham sp \n" +
"										INNER JOIN SanPhamChiTiet spct ON sp.MaSanPham = spct.MaSanPham\n" +
"										INNER JOIN DanhMuc dm ON sp.MaDanhMuc = dm.MaDanhMuc\n" +
"										INNER JOIN NhanHang nh ON spct.NhanHangID = nh.NhanHangID\n" +
"										INNER JOIN MauSac ms ON spct.MaMau = ms.MaMau\n" +
"										INNER JOIN Size sz ON spct.MaSize = sz.MaSize\n" +
"										INNER JOIN ChatLieu cl ON spct.MaChatLieu = cl.MaChatLieu\n" +
"										INNER JOIN KhuVucKho kh ON sp.KhuVucID = kh.KhuVucID\n" +
"												 WHERE TenSanPhamChiTiet LIKE N'%"+name+"%'";
        ArrayList<SanPhamViewModel> ls = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer idSP = rs.getInt("MaSanPham");
                String tenSP = rs.getString("TenSanPhamChiTiet");
                String danhM = rs.getString("TenDanhMuc");
                String brand = rs.getString("TenNhanHang");
                String color = rs.getString("TenMau");
                String sz = rs.getString("TenSize");
                String material = rs.getString("TenChatLieu");
                Double giaN = rs.getDouble("GiaNhap");
                Double giaB = rs.getDouble("GiaBan");
                String status = rs.getString("TrangThai");
                Integer soL = rs.getInt("SoLuongTon");
                String area = rs.getString("TenKhuVuc");
                
                SanPhamViewModel sp = new SanPhamViewModel(idSP, soL, tenSP, danhM, brand, color, sz, material, status, area, giaN, giaB);
                ls.add(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
    
    
    
    //GETLIST
    public SanPhamChiTiet getListById(Integer id){
        String sql = "SELECT MaSanPhamChiTiet,TenSanPhamChiTiet,SoLuongTon "
                + "FROM SanPhamChiTiet WHERE Deleted!=1 AND MaSanPhamChiTiet = ?";
        
        SanPhamChiTiet spct = new SanPhamChiTiet();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer maSPCT = rs.getInt("MaSanPhamChiTiet");
                String tenSP = rs.getString("TenSanPhamChiTiet");             
                Integer soL = rs.getInt("SoLuongTon");
                
                spct = new SanPhamChiTiet(maSPCT, tenSP, soL);
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return spct;
    }
    
    public ArrayList<SanPham> getListSP(){
        String sql = "SELECT * FROM SanPham WHERE Deleted!=1";
        ArrayList<SanPham> ls = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) { 
                Integer maSP = rs.getInt("MaSanPham");
                Integer maDM = rs.getInt("MaDanhMuc");
                String trangThai = rs.getString("TrangThai");
                Double giaB = rs.getDouble("GiaNhap");
                Double giaN = rs.getDouble("GiaBan");
                Integer khoId = rs.getInt("KhuVucID");
                
                SanPham sp = new SanPham(maSP, maDM, khoId, trangThai, giaN, giaB);
                ls.add(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
    
    public ArrayList<SanPhamViewModel> getListSPVM(){
        String sql = "SELECT sp.MaSanPham, spct.TenSanPhamChiTiet, dm.TenDanhMuc, nh.TenNhanHang, ms.TenMau, sz.TenSize, cl.TenChatLieu\n" +
"			, sp.GiaNhap, sp.GiaBan, sp.TrangThai, spct.SoLuongTon, kh.TenKhuVuc FROM SanPham sp \n" +
"										INNER JOIN SanPhamChiTiet spct ON sp.MaSanPham = spct.MaSanPham\n" +
"										INNER JOIN DanhMuc dm ON sp.MaDanhMuc = dm.MaDanhMuc\n" +
"										INNER JOIN NhanHang nh ON spct.NhanHangID = nh.NhanHangID\n" +
"										INNER JOIN MauSac ms ON spct.MaMau = ms.MaMau\n" +
"										INNER JOIN Size sz ON spct.MaSize = sz.MaSize\n" +
"										INNER JOIN ChatLieu cl ON spct.MaChatLieu = cl.MaChatLieu\n" +
"										INNER JOIN KhuVucKho kh ON sp.KhuVucID = kh.KhuVucID"
                + "                                                                     WHERE sp.Deleted != 1";
        ArrayList<SanPhamViewModel> ls = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer idSP = rs.getInt("MaSanPham");
                String tenSP = rs.getString("TenSanPhamChiTiet");
                String danhM = rs.getString("TenDanhMuc");
                String brand = rs.getString("TenNhanHang");
                String color = rs.getString("TenMau");
                String sz = rs.getString("TenSize");
                String material = rs.getString("TenChatLieu");
                Double giaN = rs.getDouble("GiaNhap");
                Double giaB = rs.getDouble("GiaBan");
                String status = rs.getString("TrangThai");
                Integer soL = rs.getInt("SoLuongTon");
                String area = rs.getString("TenKhuVuc");
                
                SanPhamViewModel sp = new SanPhamViewModel(idSP, soL, tenSP, danhM, brand, color, sz, material, status, area, giaN, giaB);
                ls.add(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
    
    public ArrayList<SanPhamViewModel> getListViewModelById(Integer id) {
        String sql = "SELECT sp.MaSanPham, spct.TenSanPhamChiTiet, dm.TenDanhMuc, nh.TenNhanHang, ms.TenMau, sz.TenSize, cl.TenChatLieu\n"
                + "			, sp.GiaNhap, sp.GiaBan, sp.TrangThai, spct.SoLuongTon, kh.TenKhuVuc\n"
                + "							,spct.HinhAnh, spct.NgayNhapKho FROM SanPham sp \n"
                + "										INNER JOIN SanPhamChiTiet spct ON sp.MaSanPham = spct.MaSanPham\n"
                + "										INNER JOIN DanhMuc dm ON sp.MaDanhMuc = dm.MaDanhMuc\n"
                + "										INNER JOIN NhanHang nh ON spct.NhanHangID = nh.NhanHangID\n"
                + "										INNER JOIN MauSac ms ON spct.MaMau = ms.MaMau\n"
                + "										INNER JOIN Size sz ON spct.MaSize = sz.MaSize\n"
                + "										INNER JOIN ChatLieu cl ON spct.MaChatLieu = cl.MaChatLieu\n"
                + "										INNER JOIN KhuVucKho kh ON sp.KhuVucID = kh.KhuVucID\n"
                + "											WHERE sp.Deleted!=1 AND sp.MaSanPham = ?";
        ArrayList<SanPhamViewModel> ls = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection(); 
                PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, id);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer idSP = rs.getInt("MaSanPham");
                String tenSP = rs.getString("TenSanPhamChiTiet");
                String danhM = rs.getString("TenDanhMuc");
                String brand = rs.getString("TenNhanHang");
                String color = rs.getString("TenMau");
                String sz = rs.getString("TenSize");
                String material = rs.getString("TenChatLieu");
                Double giaN = rs.getDouble("GiaNhap");
                Double giaB = rs.getDouble("GiaBan");
                String status = rs.getString("TrangThai");
                Integer soL = rs.getInt("SoLuongTon");
                String area = rs.getString("TenKhuVuc");
                String img = rs.getString("HinhAnh");
                Date ngayN = rs.getDate("NgayNhapKho");

                SanPhamViewModel sp = new SanPhamViewModel(idSP, soL, tenSP, danhM, brand, color, sz, material, status, area, giaN, giaB, img, ngayN);
                ls.add(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
    
    public SanPhamViewModel getListViewModelByIdSP(Integer id) {
        String sql = "SELECT sp.MaSanPham, spct.TenSanPhamChiTiet, dm.TenDanhMuc, nh.TenNhanHang, ms.TenMau, sz.TenSize, cl.TenChatLieu\n"
                + "			, sp.GiaNhap, sp.GiaBan, sp.TrangThai, spct.SoLuongTon, kh.TenKhuVuc\n"
                + "							,spct.HinhAnh, spct.NgayNhapKho FROM SanPham sp \n"
                + "										INNER JOIN SanPhamChiTiet spct ON sp.MaSanPham = spct.MaSanPham\n"
                + "										INNER JOIN DanhMuc dm ON sp.MaDanhMuc = dm.MaDanhMuc\n"
                + "										INNER JOIN NhanHang nh ON spct.NhanHangID = nh.NhanHangID\n"
                + "										INNER JOIN MauSac ms ON spct.MaMau = ms.MaMau\n"
                + "										INNER JOIN Size sz ON spct.MaSize = sz.MaSize\n"
                + "										INNER JOIN ChatLieu cl ON spct.MaChatLieu = cl.MaChatLieu\n"
                + "										INNER JOIN KhuVucKho kh ON sp.KhuVucID = kh.KhuVucID\n"
                + "											WHERE sp.Deleted!=1 AND sp.MaSanPham = ?";
        SanPhamViewModel sp = new SanPhamViewModel();

        try (Connection conn = dbConnection.getConnection(); 
                PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, id);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer idSP = rs.getInt("MaSanPham");
                String tenSP = rs.getString("TenSanPhamChiTiet");
                String danhM = rs.getString("TenDanhMuc");
                String brand = rs.getString("TenNhanHang");
                String color = rs.getString("TenMau");
                String sz = rs.getString("TenSize");
                String material = rs.getString("TenChatLieu");
                Double giaN = rs.getDouble("GiaNhap");
                Double giaB = rs.getDouble("GiaBan");
                String status = rs.getString("TrangThai");
                Integer soL = rs.getInt("SoLuongTon");
                String area = rs.getString("TenKhuVuc");
                String img = rs.getString("HinhAnh");
                Date ngayN = rs.getDate("NgayNhapKho");

                sp = new SanPhamViewModel(idSP, soL, tenSP, danhM, brand, color, sz, material, status, area, giaN, giaB, img, ngayN);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sp;
    }
    
    public SanPhamViewModel getSPByIdSPCT(Integer id) {
        String sql = "SELECT sp.MaSanPham, spct.TenSanPhamChiTiet, dm.TenDanhMuc, nh.TenNhanHang, ms.TenMau, sz.TenSize, cl.TenChatLieu\n"
                + "			, sp.GiaNhap, sp.GiaBan, sp.TrangThai, spct.SoLuongTon, kh.TenKhuVuc\n"
                + "							,spct.HinhAnh, spct.NgayNhapKho FROM SanPham sp \n"
                + "										INNER JOIN SanPhamChiTiet spct ON sp.MaSanPham = spct.MaSanPham\n"
                + "										INNER JOIN DanhMuc dm ON sp.MaDanhMuc = dm.MaDanhMuc\n"
                + "										INNER JOIN NhanHang nh ON spct.NhanHangID = nh.NhanHangID\n"
                + "										INNER JOIN MauSac ms ON spct.MaMau = ms.MaMau\n"
                + "										INNER JOIN Size sz ON spct.MaSize = sz.MaSize\n"
                + "										INNER JOIN ChatLieu cl ON spct.MaChatLieu = cl.MaChatLieu\n"
                + "										INNER JOIN KhuVucKho kh ON sp.KhuVucID = kh.KhuVucID\n"
                + "											WHERE sp.Deleted!=1 AND spct.MaSanPhamChiTiet = ?";
        SanPhamViewModel sp = new SanPhamViewModel();

        try (Connection conn = dbConnection.getConnection(); 
                PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, id);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer idSP = rs.getInt("MaSanPham");
                String tenSP = rs.getString("TenSanPhamChiTiet");
                String danhM = rs.getString("TenDanhMuc");
                String brand = rs.getString("TenNhanHang");
                String color = rs.getString("TenMau");
                String sz = rs.getString("TenSize");
                String material = rs.getString("TenChatLieu");
                Double giaN = rs.getDouble("GiaNhap");
                Double giaB = rs.getDouble("GiaBan");
                String status = rs.getString("TrangThai");
                Integer soL = rs.getInt("SoLuongTon");
                String area = rs.getString("TenKhuVuc");
                String img = rs.getString("HinhAnh");
                Date ngayN = rs.getDate("NgayNhapKho");

                sp = new SanPhamViewModel(idSP, soL, tenSP, danhM, brand, color, sz, material, status, area, giaN, giaB, img, ngayN);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sp;
    }
    
    //HIDE - UNHIDE
    public Boolean hideSP(SanPham sp){
        String sql = "UPDATE SanPham SET Deleted = 1 WHERE MaSanPham = ?";
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ps.setObject(1, sp.getMaSP());
            
            int check = ps.executeUpdate();
            if (check>0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public Boolean unhideSP(SanPham sp){
        String sql = "UPDATE SanPham SET Deleted = 0 WHERE MaSanPham = ?";
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ps.setObject(1, sp.getMaSP());
            
            int check = ps.executeUpdate();
            if (check>0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public ArrayList<SanPhamViewModel> getListHide(){
        String sql = "SELECT sp.MaSanPham, spct.TenSanPhamChiTiet, dm.TenDanhMuc, nh.TenNhanHang, ms.TenMau, sz.TenSize, cl.TenChatLieu\n" +
"			, sp.GiaNhap, sp.GiaBan, sp.TrangThai, spct.SoLuongTon, kh.TenKhuVuc FROM SanPham sp \n" +
"										INNER JOIN SanPhamChiTiet spct ON sp.MaSanPham = spct.MaSanPham\n" +
"										INNER JOIN DanhMuc dm ON sp.MaDanhMuc = dm.MaDanhMuc\n" +
"										INNER JOIN NhanHang nh ON spct.NhanHangID = nh.NhanHangID\n" +
"										INNER JOIN MauSac ms ON spct.MaMau = ms.MaMau\n" +
"										INNER JOIN Size sz ON spct.MaSize = sz.MaSize\n" +
"										INNER JOIN ChatLieu cl ON spct.MaChatLieu = cl.MaChatLieu\n" +
"										INNER JOIN KhuVucKho kh ON sp.KhuVucID = kh.KhuVucID\n" +
"													WHERE sp.Deleted != 0;";
        ArrayList<SanPhamViewModel> ls = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer idSP = rs.getInt("MaSanPham");
                String tenSP = rs.getString("TenSanPhamChiTiet");
                String danhM = rs.getString("TenDanhMuc");
                String brand = rs.getString("TenNhanHang");
                String color = rs.getString("TenMau");
                String sz = rs.getString("TenSize");
                String material = rs.getString("TenChatLieu");
                Double giaN = rs.getDouble("GiaNhap");
                Double giaB = rs.getDouble("GiaBan");
                String status = rs.getString("TrangThai");
                Integer soL = rs.getInt("SoLuongTon");
                String area = rs.getString("TenKhuVuc");
                
                SanPhamViewModel sp = new SanPhamViewModel(idSP, soL, tenSP, danhM, brand, color, sz, material, status, area, giaN, giaB);
                ls.add(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;        
    }
    
    //CHECK ID
    public boolean checkId(Integer id) {
        String sql = "SELECT COUNT(*) FROM SanPhamChiTiet WHERE Deleted!=1 AND MaSanPham = ?";
        
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
    
    //CHECK NAME
    public boolean checkName(String name) {
        String sql = "SELECT COUNT(*) FROM SanPhamChiTiet WHERE Deleted!=1 AND TenSanPhamChiTiet = ?";
        
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
    
    //CRUD
    public Boolean addSP(SanPham sp){
        String sql = "INSERT INTO SanPham(MaDanhMuc, TrangThai, GiaNhap, GiaBan, KhuVucID, PhanTramThue, Deleted) \n" +
"				VALUES (?, ?, ?, ?, ?, ?, 0);"
                + "INSERT INTO SanPhamChiTiet (MaSanPham, TenSanPhamChiTiet, HinhAnh, MaSize, MaMau, MaChatLieu, TrangThai, SoLuongTon, NgayNhapKho, NhanHangID, Deleted) VALUES \n" +
"							(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0);";
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps =conn.prepareCall(sql)) {
            ps.setInt(1, sp.getMaDM());
            ps.setString(2, sp.getTrangT());
            ps.setDouble(3, sp.getGiaN());
            ps.setDouble(4, sp.getGiaB());
            ps.setInt(5, sp.getMaKho());
            ps.setInt(6, sp.getThue());
            
            ps.setInt(7, sp.getMaSP());
            ps.setString(8, sp.getTenSP());
            ps.setString(9, sp.getImg());
            ps.setInt(10, sp.getMaSz());
            ps.setInt(11, sp.getMaMau());
            ps.setInt(12, sp.getMaChatL());
            ps.setString(13, sp.getTrangT());
            ps.setInt(14, sp.getSoL());
            ps.setInt(16, sp.getMaBrand());
            
            java.util.Date utilDate = sp.getNgayNhap();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getDate());
            ps.setDate(15, sqlDate);
            
            int check = ps.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public Boolean updateSP(SanPham sp){
        String sql = "UPDATE SanPhamChiTiet SET TenSanPhamChiTiet = ?, HinhAnh = ?, MaSize = ?, MaMau = ?, MaChatLieu = ?, TrangThai = ?,SoLuongTon = ?\n" +
"						, NgayNhapKho = ?, NhanHangID = ? \n" +
"								WHERE Deleted!=1 AND MaSanPham = ?;\n" +
                    "UPDATE SanPham SET MaDanhMuc = ?, TrangThai = ?, GiaNhap = ?, GiaBan = ?, KhuVucID = ? , PhanTramThue = ? \n" +
                    "					WHERE Deleted !=1 AND MaSanPham = ?;";
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            
            ps.setObject(1, sp.getTenSP());
            ps.setObject(2, sp.getImg());
            ps.setObject(3, sp.getMaSz());
            ps.setObject(4, sp.getMaMau());
            ps.setObject(5, sp.getMaChatL());
            ps.setObject(6, sp.getTrangT());
            ps.setObject(7, sp.getSoL());
            ps.setObject(8, sp.getNgayNhap());
            ps.setObject(9, sp.getMaBrand());
            ps.setObject(10, sp.getMaSP());
            
            ps.setObject(11, sp.getMaDM());
            ps.setObject(12, sp.getTrangT());
            ps.setObject(13, sp.getGiaN());
            ps.setObject(14, sp.getGiaB());
            ps.setObject(15, sp.getMaKho());
            ps.setObject(16, sp.getMaSP());
            ps.setObject(17, sp.getThue());
            
            int check = ps.executeUpdate();
            if (check>0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public Integer getMaDanhMuc(String tenDM) {
        String sql = "SELECT MaDanhMuc FROM DanhMuc where TenDanhMuc like N'"+tenDM+"'";
        SanPham sanPham = new SanPham();
        try (Connection conn = DbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                sanPham.setMaDM(rs.getInt("MaDanhMuc"));
                return sanPham.getMaDM();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } 
        return 0;
    }
    public Integer getMaBrand(String tenBrand) {
        String sql = "SELECT NhanHangID FROM NhanHang where TenNhanHang like N'"+tenBrand+"'";
        SanPham sanPham = new SanPham();
        try (Connection conn = DbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                sanPham.setMaBrand(rs.getInt("NhanHangID"));
                return sanPham.getMaBrand();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } 
        return 0;
    }
    public Integer getMaMau(String tenMau) {
        String sql = "SELECT MaMau FROM MauSac where TenMau like N'"+tenMau+"'";
        SanPham sanPham = new SanPham();
        try (Connection conn = DbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                sanPham.setMaMau(rs.getInt("MaMau"));
                return sanPham.getMaMau();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } 
        return 0;
    }
    public Integer getMaSize(String tenSize) {
        String sql = "SELECT MaSize FROM Size where TenSize like N'"+tenSize+"'";
        SanPham sanPham = new SanPham();
        try (Connection conn = DbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                sanPham.setMaSz(rs.getInt("MaSize"));
                return sanPham.getMaSz();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } 
        return 0;
    }
    public Integer getMaChatLieu(String tenChatLieu) {
        String sql = "SELECT MaChatLieu FROM ChatLieu where TenChatLieu like N'"+tenChatLieu+"'";
        SanPham sanPham = new SanPham();
        try (Connection conn = DbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                sanPham.setMaChatL(rs.getInt("MaChatLieu"));
                return sanPham.getMaChatL();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } 
        return 0;
    }
    public Integer getMaKho(String tenKho) {
        String sql = "SELECT KhuVucID FROM KhuVucKho where TenKhuVuc like N'"+tenKho+"'";
        SanPham sanPham = new SanPham();
        try (Connection conn = DbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                sanPham.setMaKho(rs.getInt("KhuVucID"));
                return sanPham.getMaKho();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } 
        return 0;
    }
    public String getImg(String img) {
        String sql = "SELECT HinhAnh FROM SanPhamChiTiet where MaSanPham like N'"+img+"'";
        SanPham sanPham = new SanPham();
        try (Connection conn = DbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                sanPham.setImg(("HinhAnh"));
                return sanPham.getImg();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } 
        return "Loi";
    }
    
    public Boolean updateSoLuong(SanPham sp){
        String sql = "UPDATE SanPhamChiTiet SET SoLuongTon = ?\n" +
"					WHERE Deleted!=1 AND TenSanPhamChiTiet = ? \n";
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ps.setObject(1, sp.getSoL());
            ps.setObject(2, sp.getTenSP());
            
            int check = ps.executeUpdate();
            if (check>0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public Integer getSoLuong(String ten) {
        String sql = "SELECT SoLuongTon FROM SanPhamChiTiet where TenSanPhamChiTiet like N'"+ten+"'";
        SanPham sanPham = new SanPham();
        try (Connection conn = DbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                sanPham.setSoL(rs.getInt("SoLuongTon"));
                return sanPham.getSoL();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } 
        return 0;
    }
    
}
