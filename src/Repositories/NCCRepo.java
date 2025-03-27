/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositories;
import Models.NCC;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author X1
 */
public class NCCRepo {
    DbConnection dbConnection;
    
    
    //GETLIST
    public ArrayList<NCC> getList(){
        String sql = "SELECT * FROM NhaCungCap WHERE Deleted != 1";
        ArrayList<NCC> ls = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer id = rs.getInt("NhaCungCapID");
                String name = rs.getString("TenNhaCungCap");
                String add = rs.getString("DiaChi");
                String sdt = rs.getString("SoDT");
                String email = rs.getString("Email");
                
                NCC nh = new NCC(id, name, add, email, sdt);
                ls.add(nh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
    
    public NCC getListById(Integer id){
        String sql = "SELECT * FROM NhaCungCap WHERE Deleted != 1 AND NhaCungCapID = ?";
        NCC ncc = new NCC();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ps.setObject(1, id);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer idNCC = rs.getInt("NhaCungCapID");
                String name = rs.getString("TenNhaCungCap");
                String add = rs.getString("DiaChi");
                String sdt = rs.getString("SoDT");
                String email = rs.getString("Email");
                
                ncc = new NCC(idNCC, name, add, email, sdt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ncc;
    }
    
    public NCC getIdByName(String name){
        String sql = "SELECT * FROM NhaCungCap WHERE Deleted!=1 AND TenNhaCungCap = ?";
        NCC ncc = null;
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ps.setObject(1, name);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer id = rs.getInt("NhaCungCapID");
                String ten = rs.getString("TenNhaCungCap");
                String add = rs.getString("DiaChi");
                String sdt = rs.getString("SoDT");
                String email = rs.getString("Email");
                
                ncc = new NCC(id, ten, add, email, sdt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ncc;
    }
    
    //CHECK ID
    public boolean checkId(Integer id) {
        String sql = "SELECT COUNT(*) FROM NhaCungCap WHERE Deleted!=1 AND NhaCungCapID = ?";
        
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
        String sql = "SELECT COUNT(*) FROM NhaCungCap WHERE Deleted!=1 AND TenNhaCungCap = ?";
        
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
    
    //SEARCH
    public ArrayList<NCC> searchByNameNCC(String name){
        String sql = "SELECT * FROM NhaCungCap WHERE Deleted!=1 AND TenNhaCungCap LIKE '%"+name+"%'";
        ArrayList<NCC> ls = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer id = rs.getInt("NhaCungCapID");
                String ten = rs.getString("TenNhaCungCap");
                String add = rs.getString("DiaChi");
                String sdt = rs.getString("SoDT");
                String email = rs.getString("Email");
                
                NCC ncc = new NCC(id, ten, add, email, sdt);
                ls.add(ncc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
    
    //CRUD
    public Boolean addNCC(NCC ncc) {
        String sql = "INSERT INTO NhaCungCap (TenNhaCungCap, DiaChi, Email, SoDT, Deleted) VALUES \n" +
"			(?, ?, ?, ?, 0);";

        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, ncc.getTenNCC());
            ps.setObject(2, ncc.getDiaChi());
            ps.setObject(3, ncc.getEmail());
            ps.setObject(4, ncc.getSdt());

            int check = ps.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public Boolean updateNCC(NCC ncc) {
        String sql = "UPDATE NhaCungCap SET TenNhaCungCap = ?,DiaChi = ?, Email = ?, SoDT = ? "
                + "WHERE Deleted != 1 AND NhaCungCapID = ?";

        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(5, ncc.getMaNCC());
            ps.setObject(1, ncc.getTenNCC());
            ps.setObject(2, ncc.getDiaChi());
            ps.setObject(3, ncc.getEmail());
            ps.setObject(4, ncc.getSdt());

            int check = ps.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public Boolean hideNCC(NCC ncc) {
        String sql = "UPDATE NhaCungCap SET Deleted = 1 WHERE NhaCungCapID = ?";

        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, ncc.getMaNCC());

            int check = ps.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public Boolean unhideNCC(NCC ncc) {
        String sql = "UPDATE NhaCungCap SET Deleted = 0 WHERE NhaCungCapID = ?";

        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, ncc.getMaNCC());

            int check = ps.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public ArrayList<NCC> getListHide(){
        String sql = "SELECT * FROM NhaCungCap WHERE Deleted = 1";
        ArrayList<NCC> ls = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer id = rs.getInt("NhaCungCapID");
                String name = rs.getString("TenNhaCungCap");
                String add = rs.getString("DiaChi");
                String sdt = rs.getString("SoDT");
                String email = rs.getString("Email");
                
                NCC nh = new NCC(id, name, add, email, sdt);
                ls.add(nh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
}
