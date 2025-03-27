/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositories;

import Models.NhanSu;
import ViewModels.NhanSuViewModel;
import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class NhanSuRepo {
    DbConnection dbConnection;
    
    public NhanSu getNhanSu(){
        String sql = "SELECT * FROM NhanVien WHERE Deleted !=1";
        NhanSu ns = new NhanSu();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer maNV = rs.getInt("MaNhanVien");
                Integer maNguoiDung = rs.getInt("MaNguoiDung");
                String tenNV = rs.getString("TenNhanVien");
                String gioiTinh = rs.getString("GioiTinh");
                Date ngayS = rs.getDate("NgaySinh");
                String soDT = rs.getString("SoDienThoai");
                String diaChi = rs.getString("DiaChi");
                
                ns = new NhanSu(tenNV, gioiTinh, soDT, diaChi, maNguoiDung, maNV, ngayS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ns;
    }
    
    public ArrayList<NhanSu> searchByName(String name){
        String sql = "SELECT * FROM NhanVien WHERE Deleted !=1 AND TenNhanVien = ?";
        ArrayList<NhanSu> ls = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ps.setObject(1, name);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer maNV = rs.getInt("MaNhanVien");
                Integer maNguoiDung = rs.getInt("MaNguoiDung");
                String tenNV = rs.getString("TenNhanVien");
                String gioiTinh = rs.getString("GioiTinh");
                Date ngayS = rs.getDate("NgaySinh");
                String soDT = rs.getString("SoDienThoai");
                String diaChi = rs.getString("DiaChi");
                
                NhanSu ns = new NhanSu(tenNV, gioiTinh, soDT, diaChi, maNguoiDung, maNV, ngayS);
                ls.add(ns);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
    
    public ArrayList<NhanSu> getListNS(){
        String sql = "SELECT * FROM NhanVien WHERE Deleted !=1";
        ArrayList<NhanSu> ls = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer maNV = rs.getInt("MaNhanVien");
                Integer maNguoiDung = rs.getInt("MaNguoiDung");
                String tenNV = rs.getString("TenNhanVien");
                String gioiTinh = rs.getString("GioiTinh");
                Date ngayS = rs.getDate("NgaySinh");
                String soDT = rs.getString("SoDienThoai");
                String diaChi = rs.getString("DiaChi");
                
                NhanSu ns = new NhanSu(tenNV, gioiTinh, soDT, diaChi, maNguoiDung, maNV, ngayS);
                ls.add(ns);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
    
    public NhanSu getIdByName(String name) {
        String sql = "SELECT MaNhanVien, TenNhanVien FROM NhanVien\n"
                + "WHERE Deleted!=1 AND TenNhanVien = ?";
        NhanSu ns = new NhanSu();

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, name);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer maNV = rs.getInt("MaNhanVien");
                String tenNv = rs.getString("TenNhanVien");

                ns = new NhanSu(tenNv, maNV);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ns;
    }
    
    public NhanSu getListById(Integer id) {
        String sql = "SELECT * FROM NhanVien\n"
                + "WHERE Deleted!=1 AND MaNhanVien = ?";
        NhanSu ns = new NhanSu();

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer maNV = rs.getInt("MaNhanVien");
                Integer maNguoiDung = rs.getInt("MaNguoiDung");
                String tenNV = rs.getString("TenNhanVien");
                String gioiTinh = rs.getString("GioiTinh");
                Date ngayS = rs.getDate("NgaySinh");
                String soDT = rs.getString("SoDienThoai");
                String diaChi = rs.getString("DiaChi");

                ns = new NhanSu(tenNV, gioiTinh, soDT, diaChi, maNguoiDung, maNV, ngayS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ns;
    }

public NhanSuViewModel getListBy(Integer id) {
        String sql = "SELECT nv.MaNhanVien, nv.TenNhanVien\n"
                + ", nd.MaNguoiDung, nd.TenDangNhap, nv.GioiTinh,nv.NgaySinh, nd.VaiTro\n"
                + ", nv.SoDienThoai, nv.DiaChi FROM NhanVien nv\n"
                + "Left JOIN NguoiDung nd ON nv.MaNguoiDung = nd.MaNguoiDung\n"
                + "WHERE nv.Deleted !=1 AND nv.MaNhanVien = " + id + "";
        NhanSuViewModel ns = new NhanSuViewModel();

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer maNV = rs.getInt("MaNhanVien");
                String tenNV = rs.getString("TenNhanVien");
                String tenND = rs.getString("TenDangNhap");
                String gioiTinh = rs.getString("GioiTinh");
                Date NgayS = rs.getDate("NgaySinh");
                String vaiTro = rs.getString("VaiTro");
                String soDT = rs.getString("SoDienThoai");
                String diaChi = rs.getString("DiaChi");
                Integer maNguoiDung = rs.getInt("MaNguoiDung");

                ns = new NhanSuViewModel(maNV, tenNV, maNguoiDung, tenND, gioiTinh, soDT, diaChi, vaiTro, NgayS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ns;
    }
//

    public ArrayList<NhanSuViewModel> getList() {
        String sql = "SELECT MaNhanVien, TenNhanVien, TenDangNhap, GioiTinh, NgaySinh, SoDienThoai, DiaChi, VaiTro, NhanVien.MaNguoiDung \n"
                + "FROM NhanVien \n"
                + "left JOIN NguoiDung ON NhanVien.MaNguoiDung = NguoiDung.MaNguoiDung\n"
                + "where NhanVien.Deleted !=1";
        ArrayList<NhanSuViewModel> list = new ArrayList<>();
        try (Connection conn = DbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer maNV = rs.getInt("MaNhanVien");
                String tenNV = rs.getString("TenNhanVien");
                String tenDN = rs.getString("TenDangNhap");
                String gioiTinh = rs.getString("GioiTinh");
                Date NgayS = rs.getDate("NgaySinh");
                String sdt = rs.getString("SoDienThoai");
                String diaChi = rs.getString("DiaChi");
                String vaiTro = rs.getString("VaiTro");
                Integer maND = rs.getInt("MaNguoiDung");
                list.add(new NhanSuViewModel(maNV, tenNV, maND, tenDN, gioiTinh, sdt, diaChi, vaiTro, NgayS));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Boolean AddNew(NhanSu sp) {
        String sql = " INSERT INTO NhanVien(MaNguoiDung,TenNhanVien,GioiTinh,SoDienThoai,DiaChi, NgaySinh, Deleted) VALUES(?,?,?,?,?,?,0) ";

        try (Connection conn = DbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, sp.getMaNguoiDung());
            ps.setObject(2, sp.getTenNhanVien());
            ps.setObject(3, sp.getGioiTinh());
            ps.setObject(4, sp.getSoDienThoai());
            ps.setObject(5, sp.getDiaChi());
            ps.setObject(6, sp.getNgayS());
            int kq = ps.executeUpdate();
            if (kq > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean delete(Integer maNV) {
        String sql = "DELETE FROM [dbo].[NhanVien]\n"
                + "      WHERE [MaNhanVien] = '" + maNV + "'";

        try (Connection conn = DbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            int ketQua = ps.executeUpdate();
            if (ketQua > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean updateNew(NhanSu ns) {
        String sql
                = //"alter table NhanVien nocheck constraint FK__NhanVien__MaNguo__49C3F6B7\n"+
                "update NhanVien set TenNhanVien = ?, GioiTinh = ?,SoDienThoai = ?,DiaChi = ?,"
                + "NgaySinh  = ? "
                + "where MaNhanVien = ?";
        try (Connection conn = DbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, ns.getTenNhanVien());
            ps.setObject(2, ns.getGioiTinh());
            ps.setObject(3, ns.getSoDienThoai());
            ps.setObject(4, ns.getDiaChi());
            ps.setObject(5, ns.getNgayS());
            ps.setObject(6, ns.getMaNhanVien());

            int kq = ps.executeUpdate();
            return kq > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<NhanSuViewModel> searchByNameVM(String name) {
        String sql = "SELECT nv.MaNhanVien, nv.TenNhanVien\n"
                + ", nd.TenDangNhap, nv.GioiTinh, nd.VaiTro\n"
                + ", nv.SoDienThoai, nv.DiaChi, nv.MaNguoiDung, nv.NgaySinh FROM NhanVien nv\n"
                + "INNER JOIN NguoiDung nd ON nv.MaNguoiDung = nd.MaNguoiDung\n"
                + "WHERE nv.TenNhanVien LIKE '%" + name + "%'";

        ArrayList<NhanSuViewModel> list = new ArrayList<>();

        try (Connection conn = DbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int maNhanVien = rs.getInt("MaNhanVien");
                String tenNhanVien = rs.getString("TenNhanVien");
                String tenDangNhap = rs.getString("TenDangNhap");
                String gioiTinh = rs.getString("GioiTinh");
                String vaiTro = rs.getString("VaiTro");
                String soDienThoai = rs.getString("SoDienThoai");
                String diaChi = rs.getString("DiaChi");
                int maNguoiDung = rs.getInt("MaNguoiDung");
                Date ngaySinh = rs.getDate("NgaySinh");

                NhanSuViewModel spvm = new NhanSuViewModel(maNhanVien, tenNhanVien, maNguoiDung, tenDangNhap, gioiTinh, soDienThoai, diaChi, vaiTro, ngaySinh);
                list.add(spvm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<NhanSuViewModel> searchBySDT(String sdt) {
        String sql = "SELECT nv.MaNhanVien, nv.TenNhanVien\n"
                + ", nd.TenDangNhap, nv.GioiTinh, nd.VaiTro\n"
                + ", nv.SoDienThoai, nv.DiaChi, nv.MaNguoiDung, nv.NgaySinh FROM NhanVien nv\n"
                + "INNER JOIN NguoiDung nd ON nv.MaNguoiDung = nd.MaNguoiDung\n"
                + "WHERE nv.SoDienThoai = ?";
        ArrayList<NhanSuViewModel> list = new ArrayList<>();

        try (Connection conn = DbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
            // Thiết lập tham số cho câu lệnh chuẩn bị
            ps.setString(1, sdt);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int maNhanVien = rs.getInt("MaNhanVien");
                String tenNhanVien = rs.getString("TenNhanVien");
                String tenDangNhap = rs.getString("TenDangNhap");
                String gioiTinh = rs.getString("GioiTinh");
                String vaiTro = rs.getString("VaiTro");
                String soDienThoai = rs.getString("SoDienThoai");
                String diaChi = rs.getString("DiaChi");
                int maNguoiDung = rs.getInt("MaNguoiDung");
                Date ngaySinh = rs.getDate("NgaySinh");

                NhanSuViewModel spvm = new NhanSuViewModel(maNhanVien, tenNhanVien, maNguoiDung, tenDangNhap, gioiTinh, soDienThoai, diaChi, vaiTro, ngaySinh);
                list.add(spvm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean soDienThoaiCheck(String soDienThoai) {
        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM NhanVien WHERE SoDienThoai = ?")) {
            ps.setString(1, soDienThoai);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; // Trả về true nếu số điện thoại đã tồn tại trong cơ sở dữ liệu
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public Boolean checkName(Integer maND) {
        String sql = "SELECT COUNT(*) FROM NhanVien WHERE MaNguoiDung = ?";

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, maND);

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
