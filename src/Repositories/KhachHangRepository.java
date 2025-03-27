/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositories;

import Models.KhachHang;
import Models.KH_HoaDonViewModel;
import ViewModels.KhachHangViewModel;
import java.util.ArrayList;
import java.sql.*;

/**
 *
 * @author X1
 */
public class KhachHangRepository {

    DbConnection dbConnection;

    //GETLIST
    public ArrayList<KhachHangViewModel> getList() {
        String sql = "select KhachHang.MaKhachHang, hd.MaHoaDon, KhachHang.TenKhachHang, GioiTinh, SoDienThoai, DiaChi, NgaySinh from KhachHang\n"
                + "left join HoaDon hd ON hd.MaKhachHang = KhachHang.MaKhachHang;";
        ArrayList<KhachHangViewModel> ls = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer maKH = rs.getInt("MaKhachHang");
                String tenKh = rs.getString("TenKhachHang");
                Integer maHD = rs.getInt("MaHoaDon");
                String gioiTinh = rs.getString("GioiTinh");
                String soDT = rs.getString("SoDienThoai");
                String diaChi = rs.getString("DiaChi");
                Date ngayS = rs.getDate("NgaySinh");
                KhachHangViewModel kh = new KhachHangViewModel(maKH, tenKh, gioiTinh, soDT, diaChi, maHD, ngayS);
                ls.add(kh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }

    ///TABLE HOADON
    public ArrayList<KH_HoaDonViewModel> getList_Bang2(Integer MaHD) {
        String sql = " SELECT HoaDon.MaHoaDon, SanPhamChiTiet.TenSanPhamChiTiet, KhachHang.TenKhachHang, SoLuong, DonGia, HoaDon.NgayLap\n"
                + "FROM HoaDonChiTiet\n"
                + "JOIN SanPhamChiTiet ON HoaDonChiTiet.MaSanPhamChiTiet = SanPhamChiTiet.MaSanPhamChiTiet\n"
                + "JOIN HoaDon ON HoaDon.MaHoaDon = HoaDonChiTiet.MaHoaDon \n"
                + "JOIN KhachHang ON HoaDon.MaKhachHang = KhachHang.MaKhachHang\n"
                + "WHERE HoaDon.MaHoaDon = ?";
        ArrayList<KH_HoaDonViewModel> list = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, MaHD);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer maHD = rs.getInt("MaHoaDon");
                String tenSP = rs.getString("TenSanPhamChiTiet");
                String tenKh = rs.getString("TenKhachHang");
                Integer soLuong = rs.getInt("SoLuong");
                Double DonGia = rs.getDouble("DonGia");
                Date ngay = rs.getDate("NgayLap");
                KH_HoaDonViewModel kh = new KH_HoaDonViewModel(maHD, tenSP, tenKh, soLuong, DonGia, DonGia, ngay);
                list.add(kh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    ////Tìm theo tên khách hàng
    public ArrayList<KhachHangViewModel> getListSearch(String id) {
        String sql = "SELECT KhachHang.MaKhachHang, KhachHang.TenKhachHang,KhachHang.GioiTinh, HoaDon.MaHoaDon, KhachHang.DiaChi, KhachHang.SoDienThoai, KhachHang.NgaySinh \n"
                + "FROM KhachHang\n"
                + "LEFT JOIN HoaDon ON HoaDon.MaKhachHang = KhachHang.MaKhachHang  WHERE KhachHang.TenKhachHang LIKE '%" + id + "%'";
        ArrayList<KhachHangViewModel> ls = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer maKH = rs.getInt("MaKhachHang");
                String tenKh = rs.getString("TenKhachHang");
                String GioiT = rs.getString("GioiTinh");
                Integer maHD = rs.getInt("MaHoaDon");
                String soDT = rs.getString("SoDienThoai");
                String diaChi = rs.getString("DiaChi");
                Date ngayS = rs.getDate("NgaySinh");
                KhachHangViewModel kh = new KhachHangViewModel(maKH, tenKh, GioiT, soDT, diaChi, maHD, ngayS);
                ls.add(kh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }

    /////Tìm theo SĐT
    public KhachHang getKHBySDT(String sdt) {
        String sql = "SELECT * FROM KhachHang WHERE KhachHang.SoDienThoai = ?";
        KhachHang kh = new KhachHang();

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sdt);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer maKH = rs.getInt("MaKhachHang");
                String tenKh = rs.getString("TenKhachHang");
                String gioiTinh = rs.getString("GioiTinh");
                String soDT = rs.getString("SoDienThoai");
                String diaChi = rs.getString("DiaChi");
                Date ngaySinh = rs.getDate("NgaySinh");

                kh = new KhachHang(maKH, tenKh, gioiTinh, soDT, diaChi, ngaySinh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kh;
    }

    public ArrayList<KhachHangViewModel> getListSearchSDT(String sdt) {
        String sql = "SELECT KhachHang.MaKhachHang, KhachHang.TenKhachHang,KhachHang.GioiTinh, HoaDon.MaHoaDon, KhachHang.DiaChi, KhachHang.SoDienThoai, KhachHang.NgaySinh \n"
                + "FROM KhachHang\n"
                + "LEFT JOIN HoaDon ON HoaDon.MaKhachHang = KhachHang.MaKhachHang WHERE KhachHang.SoDienThoai = ?";
        ArrayList<KhachHangViewModel> ls = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sdt);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer maKH = rs.getInt("MaKhachHang");
                String tenKh = rs.getString("TenKhachHang");
                Integer maHD = rs.getInt("MaHoaDon");
                String gioiTinh = rs.getString("GioiTinh");
                String soDT = rs.getString("SoDienThoai");
                String diaChi = rs.getString("DiaChi");
                Date ngaySinh = rs.getDate("NgaySinh");
                KhachHangViewModel kh = new KhachHangViewModel(maKH, tenKh, gioiTinh, soDT, diaChi, maHD, ngaySinh);
                ls.add(kh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }

    public boolean addNew(KhachHang kh) {

        String sql = "INSERT INTO KhachHang ( TenKhachHang, GioiTinh, SoDienThoai,NgaySinh, DiaChi,LoaiKhachHang,Deleted) VALUES (?, ?, ?, ?,?,N'Thành Viên', 0)";

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, kh.getTenKH());
            ps.setString(2, kh.getGioiTinh());
            ps.setString(3, kh.getSoDT());
            ps.setObject(4, kh.getNgaySinh());
            ps.setString(5, kh.getDiaChi());

            int result = ps.executeUpdate();
            if (result > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(KhachHang kh) {
        String sql = "UPDATE KhachHang SET TenKhachHang = ?, GioiTinh = ?, SoDienThoai = ?, DiaChi = ?, NgaySinh = ?\n"
                + "WHERE MaKhachHang = ?";

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, kh.getTenKH());
            ps.setObject(2, kh.getGioiTinh());
            ps.setObject(3, kh.getSoDT());
            ps.setObject(4, kh.getDiaChi());
            ps.setObject(5, kh.getNgaySinh());
            ps.setObject(6, kh.getMaKH());

            int result = ps.executeUpdate();

            if (result > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean xoa(Integer ID) {

        String sql = "SELECT * FROM KhachHang where MaKhachHang =" + ID + "";

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, ID);
            int result = ps.executeUpdate();

            if (result > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    ///
    public Boolean XoaKH(KhachHang kh) {
        String sql = "UPDATE KhachHang SET Deleted = 1 WHERE MaKhachHang = ?";
        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, kh.getMaKH());

            int check = ps.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /////Danh sach da an
    public ArrayList<KhachHangViewModel> DanhSachAn() {
        String sql = "Select MaKhachHang, TenKhachHang, MaHoaDon, GioiTinh, SoDienThoai, DiaChi from KhachHang where Deleted = 1";
        ArrayList<KhachHangViewModel> ls = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer maKH = rs.getInt("MaKhachHang");
                String tenKh = rs.getString("TenKhachHang");
                Integer maHD = rs.getInt("MaHoaDon");
                String gioiTinh = rs.getString("GioiTinh");
                String soDT = rs.getString("SoDienThoai");
                String diaChi = rs.getString("DiaChi");

                KhachHangViewModel kh = new KhachHangViewModel(maKH, tenKh, gioiTinh, soDT, diaChi, maHD, null);
                ls.add(kh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }

    ////Bo an
    public Boolean boAn(KhachHang kh) {
        String sql = "UPDATE KhachHang SET Deleted = 0 WHERE MaKhachHang = ?";
        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, kh.getMaKH());

            int check = ps.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    ///
    public KhachHangViewModel getListt(Integer id) {
        String sql = "SELECT KhachHang.MaKhachHang, KhachHang.TenKhachHang, HoaDon.MaHoaDon, KhachHang.GioiTinh,KhachHang.SoDienThoai,KhachHang.DiaChi,KhachHang.NgaySinh FROM KhachHang \n"
                + "left JOIN HoaDon ON HoaDon.MaKhachHang = KhachHang.MaKhachHang WHERE KhachHang.MaKhachHang = ?";
        KhachHangViewModel kh = new KhachHangViewModel();

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer maKH = rs.getInt("MaKhachHang");
                String tenKh = rs.getString("TenKhachHang");
                Integer maHD = rs.getInt("MaHoaDon");
                String gioiTinh = rs.getString("GioiTinh");
                String soDT = rs.getString("SoDienThoai");
                String diaChi = rs.getString("DiaChi");
                Date ngayS = rs.getDate("NgaySinh");
                kh = new KhachHangViewModel(maKH, tenKh, gioiTinh, soDT, diaChi, maHD, ngayS);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kh;
    }

    public boolean XoaKH(Integer maKH) {
        String sql = "DELETE FROM KhachHang WHERE MaKhachHang = ?";
        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, maKH);

            int check = ps.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //GETLIST
    public ArrayList<KhachHang> getListKH() {
        String sql = "SELECT * FROM KhachHang";
        ArrayList<KhachHang> ls = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer maKH = rs.getInt("MaKhachHang");
                String tenKH = rs.getString("TenKhachHang");
                String gioiT = rs.getString("GioiTinh");
                Date ngayS = rs.getDate("NgaySinh");
                String sdt = rs.getString("SoDienThoai");
                String diaC = rs.getString("DiaChi");
                String loaiK = rs.getString("LoaiKhachHang");

                KhachHang kh = new KhachHang(maKH, tenKH, gioiT, sdt, diaC, ngayS, loaiK);
                ls.add(kh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
}
