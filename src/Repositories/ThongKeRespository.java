/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositories;

import Models.HoaDon;
import Models.Linh_HDThongKe;
import ViewModels.HD_HoaDonViewModel;
import ViewModels.LINH_ThongKeView;
import ViewModels.ThongKeViewDoanhThu;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author hmail
 */
public class ThongKeRespository {

    DbConnection dbConnection;

    public ArrayList<Linh_HDThongKe> getList() {
        String sql = "SELECT * FROM HoaDon WHERE Deleted !=1";
        ArrayList<Linh_HDThongKe> lsTrangThai = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer maHD = rs.getInt("MaHoaDon");
                String tenNV = rs.getString("TenNhanVien");
                String tenKH = rs.getString("TenKhachHang");
                String phuongThuc = rs.getString("PhuongThucThanhToan");
                Date ngayLap = rs.getDate("NgayLap");
                Double tongTien = rs.getDouble("TongTien");
                String trangThai = rs.getString("TrangThai");
                Linh_HDThongKe hd = new Linh_HDThongKe(maHD, null, null, trangThai, tenKH, phuongThuc, null, null, tenNV, tongTien, ngayLap);
                lsTrangThai.add(hd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lsTrangThai;
    }
    
    //LOAD PIE_CHART
    public ArrayList<Integer> showYear() {
        String sql = "SELECT YEAR(NgayLap) AS NamLap FROM HoaDon WHERE HoaDon.Deleted!=1 GROUP BY YEAR(NgayLap);";
        ArrayList<Integer> years = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer namLap = rs.getInt("NamLap");
                years.add(namLap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return years;
    }

    public ArrayList<Integer> showMonth(int year) {
        String sql = "SELECT DISTINCT MONTH(NgayLap) AS ThangLap FROM HoaDon WHERE YEAR(NgayLap) = ? ORDER BY ThangLap;";
        ArrayList<Integer> months = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setInt(1, year);  // Set the year parameter
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer thangLap = rs.getInt("ThangLap");
                months.add(thangLap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return months;
    }

//    //
//    public ArrayList<HD_HoaDonViewModel> getListHoaDonView(Date ngay) {
//        String sql = "SELECT hdct.MaHoaDonChiTiet, kh.TenKhachHang, kh.SoDienThoai ,\n"
//                + "					 hd.PhuongThucThanhToan, hd.TongTien , hd.NgayLap, hd.TenNhanVien FROM HoaDonChiTiet hdct\n"
//                + "                INNER JOIN KhachHang kh ON kh.MaHoaDon = hdct.MaHoaDon\n"
//                + "                INNER JOIN HoaDon hd ON hd.MaHoaDon = hdct.MaHoaDon where NgayLap = ?";
//        ArrayList<HD_HoaDonViewModel> lsHoaDon = new ArrayList<>();
//
//        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                Integer maHD = rs.getInt("MaHoaDonChiTiet");
//                String tenKH = rs.getString("TenKhachHang");
//                String soDT = rs.getString("SoDienThoai");
//                String phuongThuc = rs.getString("PhuongThucThanhToan");
//                Double tongTien = rs.getDouble("TongTien");
//                Date ngayLap = rs.getDate("NgayLap");
//                String tenNV = rs.getString("TenNhanVien");
//
//                HD_HoaDonViewModel hoaDon = new HD_HoaDonViewModel(maHD, tenKH, soDT, phuongThuc, tongTien, ngayLap, tenNV);
//                lsHoaDon.add(hoaDon);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return lsHoaDon;
//    }
////Tong doanh thu theo ngày hom nay them dkien đã thanh toán
    public HD_HoaDonViewModel tongDoanhThuNgay() {
        String sql = "SELECT CAST(COALESCE(SUM(TongTien), 0) AS VARCHAR(MAX)) AS TongDoanhThu\n"
                + "FROM HoaDon\n"
                + "WHERE NgayLap = CONVERT(date, GETDATE()) AND TrangThai = N'Đã thanh toán';";

        HD_HoaDonViewModel hd = new HD_HoaDonViewModel();
        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                hd = new HD_HoaDonViewModel(rs.getDouble(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hd;
    }
////Tong doanh thu theo tháng này them dkien đã thanh toán

    public HD_HoaDonViewModel tongDoanhThuThang() {
        String sql = "SELECT CAST(COALESCE(SUM(TongTien), 0) AS VARCHAR(MAX)) AS TongDoanhThu\n"
                + "FROM HoaDon\n"
                + "WHERE YEAR(NgayLap) = YEAR(GETDATE())\n"
                + "AND MONTH(NgayLap) = MONTH(GETDATE()) AND TrangThai = N'Đã thanh toán';";

        HD_HoaDonViewModel hd = new HD_HoaDonViewModel();
        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                hd = new HD_HoaDonViewModel(rs.getDouble(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hd;
    }
////Tong doanh thu theo năm nay them dkien đã thanh toán

    public HD_HoaDonViewModel tongDoanhThuNam() {
        String sql = "SELECT CAST(COALESCE(SUM(TongTien), 0) AS VARCHAR(MAX)) AS TongDoanhThu\n"
                + "FROM HoaDon\n"
                + "WHERE YEAR(NgayLap) = YEAR(GETDATE()) AND TrangThai = N'Đã thanh toán';";

        HD_HoaDonViewModel hd = new HD_HoaDonViewModel();
        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                hd = new HD_HoaDonViewModel(rs.getDouble(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hd;
    }

    ////Đếm số hóa đơn đã hoàn thành
    public HD_HoaDonViewModel tongDaTT() {
        String sql = "SELECT COUNT(MaHoaDon) AS HoaDon\n"
                + "FROM HoaDon\n"
                + "WHERE TrangThai = N'Đã thanh toán';";

        HD_HoaDonViewModel hd = new HD_HoaDonViewModel();
        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                hd = new HD_HoaDonViewModel(rs.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hd;
    }
///Đếm số hóa đơn vẫn treo chưa thanh toán

    public HD_HoaDonViewModel tongHDTreo() {
        String sql = "SELECT COUNT(MaHoaDon) AS HoaDon\n"
                + "FROM HoaDon\n"
                + "WHERE TrangThai = N'Chưa thanh toán';";

        HD_HoaDonViewModel hd = new HD_HoaDonViewModel();
        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                hd = new HD_HoaDonViewModel(rs.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hd;
    }
////Tính tổng tiền hóa đơn đã thanh toán

    public HD_HoaDonViewModel tongTienThanh() {
        String sql = "SELECT SUM(TongTien)\n"
                + "FROM HoaDon\n"
                + "WHERE TrangThai = N'Đã thanh toán';";

        HD_HoaDonViewModel hd = new HD_HoaDonViewModel();
        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                hd = new HD_HoaDonViewModel(rs.getDouble(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hd;
    }
////Tính tổng sản phẩm đã bán ra
    public LINH_ThongKeView tongSPBan() {
        String sql = "SELECT SUM(SoLuong) AS TongSoLuongDaBan\n"
                + "FROM HoaDonChiTiet;";

        LINH_ThongKeView hd = new LINH_ThongKeView();
        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                hd = new LINH_ThongKeView(rs.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hd;
    }

    public HD_HoaDonViewModel TonDonHang() {
        String sql = "SELECT COUNT(MaHoaDon) AS HoaDon\n"
                + "FROM HoaDon\n";

        HD_HoaDonViewModel hd = new HD_HoaDonViewModel();
        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                hd = new HD_HoaDonViewModel(rs.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hd;
    }

    //
    public ArrayList<HD_HoaDonViewModel> getListHoaDonView(Integer maHDD) {
        String sql = "SELECT hdct.MaHoaDonChiTiet, kh.TenKhachHang, kh.SoDienThoai\n"
                + ", hd.PhuongThucThanhToan, hd.TongTien , hd.NgayLap, hd.TenNhanVien FROM HoaDonChiTiet hdct\n"
                + "INNER JOIN KhachHang kh ON kh.MaHoaDon = hdct.MaHoaDon\n"
                + "INNER JOIN HoaDon hd ON hd.MaHoaDon = hdct.MaHoaDon where hdct.MaHoaDon = " + maHDD;
        ArrayList<HD_HoaDonViewModel> lsHoaDon = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer maHD = rs.getInt("MaHoaDonChiTiet");
                String tenKH = rs.getString("TenKhachHang");
                String soDT = rs.getString("SoDienThoai");
                String phuongThuc = rs.getString("PhuongThucThanhToan");
                Double tongTien = rs.getDouble("TongTien");
                Date ngayLap = rs.getDate("NgayLap");
                String tenNV = rs.getString("TenNhanVien");

                HD_HoaDonViewModel hoaDon = new HD_HoaDonViewModel(maHD, tenKH, soDT, phuongThuc, tongTien, ngayLap, tenNV);
                lsHoaDon.add(hoaDon);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lsHoaDon;
    }

    public ArrayList<HD_HoaDonViewModel> getListHoaDonVieww(java.util.Date bd, java.util.Date kt) {
        String sql = "SELECT hdct.MaHoaDonChiTiet, kh.TenKhachHang, kh.SoDienThoai,\n"
                + "hd.PhuongThucThanhToan, hd.TongTien, hd.NgayLap, nv.TenNhanVien \n"
                + "FROM HoaDonChiTiet hdct\n"
                + "INNER JOIN  HoaDon hd ON hd.MaHoaDon = hdct.MaHoaDon\n"
                + "INNER JOIN KhachHang kh ON hd.MaKhachHang = kh.MaKhachHang\n"
                + "INNER JOIN NhanVien nv ON hd.MaNhanVien = nv.MaNhanVien\n"
                + "WHERE hd.NgayLap BETWEEN  ? AND ? ;";
        ArrayList<HD_HoaDonViewModel> lsHoaDon = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, bd);
            ps.setObject(2, kt);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer maHD = rs.getInt("MaHoaDonChiTiet");
                String tenKH = rs.getString("TenKhachHang");
                String soDT = rs.getString("SoDienThoai");
                String phuongThuc = rs.getString("PhuongThucThanhToan");
                Double tongTien = rs.getDouble("TongTien");
                Date ngayLap = rs.getDate("NgayLap");
                String tenNV = rs.getString("TenNhanVien");

                HD_HoaDonViewModel hoaDon = new HD_HoaDonViewModel(maHD, tenKH, soDT, phuongThuc, tongTien, ngayLap, tenNV);
                lsHoaDon.add(hoaDon);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lsHoaDon;
    }

    public ArrayList<HD_HoaDonViewModel> Tim(String Ngay) {
        String sql = "SELECT hdct.MaHoaDonChiTiet, kh.TenKhachHang, kh.SoDienThoai\n"
                + ", hd.PhuongThucThanhToan, hd.TongTien , hd.NgayLap, hd.TenNhanVien FROM HoaDonChiTiet hdct\n"
                + "INNER JOIN KhachHang kh ON kh.MaHoaDon = hdct.MaHoaDon\n"
                + "INNER JOIN HoaDon hd ON hd.MaHoaDon = hdct.MaHoaDon where hd.NgayLap = ?";
        ArrayList<HD_HoaDonViewModel> lsHoaDon = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer maHD = rs.getInt("MaHoaDonChiTiet");
                String tenKH = rs.getString("TenKhachHang");
                String soDT = rs.getString("SoDienThoai");
                String phuongThuc = rs.getString("PhuongThucThanhToan");
                Double tongTien = rs.getDouble("TongTien");
                Date ngayLap = rs.getDate("NgayLap");
                String tenNV = rs.getString("TenNhanVien");

                HD_HoaDonViewModel hoaDon = new HD_HoaDonViewModel(maHD, tenKH, soDT, phuongThuc, tongTien, ngayLap, tenNV);
                lsHoaDon.add(hoaDon);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lsHoaDon;
    }

    public ArrayList<HD_HoaDonViewModel> Loc(int ngay) {
        String sql = "SELECT hdct.MaHoaDonChiTiet, kh.TenKhachHang, kh.SoDienThoai\n"
                + "                , hd.PhuongThucThanhToan, hd.TongTien , hd.NgayLap, hd.TenNhanVien FROM HoaDonChiTiet hdct\n"
                + "               INNER JOIN KhachHang kh ON kh.MaHoaDon = hdct.MaHoaDon\n"
                + "                 INNER JOIN HoaDon hd ON hd.MaHoaDon = hdct.MaHoaDon where NgayLap = " + ngay;
        ArrayList<HD_HoaDonViewModel> lsHoaDon = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer maHD = rs.getInt("MaHoaDonChiTiet");
                String tenKH = rs.getString("TenKhachHang");
                String soDT = rs.getString("SoDienThoai");
                String phuongThuc = rs.getString("PhuongThucThanhToan");
                Double tongTien = rs.getDouble("TongTien");
                Date ngayLap = rs.getDate("NgayLap");
                String tenNV = rs.getString("TenNhanVien");

                HD_HoaDonViewModel hoaDon = new HD_HoaDonViewModel(maHD, tenKH, soDT, phuongThuc, tongTien, ngayLap, tenNV);
                lsHoaDon.add(hoaDon);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lsHoaDon;
    }

///GetList bang doanh thu
    public ArrayList<ThongKeViewDoanhThu> getListByNam(Integer nam) {
        String sql = "SELECT \n"
                + "    MONTH(NgayLap) AS Thang,\n"
                + "    COUNT(*) AS SoLuongHoaDon,\n"
                + "    SUM(TongTien) AS TongTien \n"
                + "FROM \n"
                + "    HoaDon\n"
                + "WHERE \n"
                + "    YEAR(NgayLap) = ?\n"
                + "GROUP BY \n"
                + "    YEAR(NgayLap), MONTH(NgayLap)\n"
                + "ORDER BY \n"
                + "    YEAR(NgayLap), MONTH(NgayLap)";
        ArrayList<ThongKeViewDoanhThu> ls = new ArrayList<>();
        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, nam); // Thiết lập tham số nam
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer thang = rs.getInt("Thang");
                Integer soL = rs.getInt("SoLuongHoaDon");
                Integer tongTien = rs.getInt("TongTien");

                ThongKeViewDoanhThu tkv = new ThongKeViewDoanhThu(thang, soL, tongTien);
                ls.add(tkv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }

/////Load Bieu Do Thong Ke
    public ArrayList<ThongKeViewDoanhThu> getListBangDoanhThu() {
        String sql = "SELECT MONTH(NgayLap) AS Thang,YEAR(NgayLap) AS Nam, COUNT(*) AS SoLuongHoaDon, SUM(TongTien) AS TongTien,\n"
                + "       SUM(TongTien) - SUM(SanPham.GiaNhap) AS LoiNhuan\n"
                + "FROM HoaDon\n"
                + "JOIN HoaDonChiTiet ON HoaDonChiTiet.MaHoaDon = HoaDon.MaHoaDon\n"
                + "JOIN SanPhamChiTiet ON SanPhamChiTiet.MaSanPhamChiTiet = HoaDonChiTiet.MaSanPhamChiTiet\n"
                + "JOIN SanPham ON SanPham.MaSanPham = SanPhamChiTiet.MaSanPham\n"
                + "GROUP BY YEAR(NgayLap), MONTH(NgayLap)\n"
                + "ORDER BY YEAR(NgayLap), MONTH(NgayLap)";
        ArrayList<ThongKeViewDoanhThu> ls = new ArrayList<>();
        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer thang = rs.getInt("Thang");
                Integer nam = rs.getInt("Nam");
                Integer soL = rs.getInt("SoLuongHoaDon");
                Integer tongTien = rs.getInt("TongTien");
                Integer LoiN = rs.getInt("LoiNhuan");
                ThongKeViewDoanhThu tkv = new ThongKeViewDoanhThu(thang, nam, soL, tongTien, null, null, LoiN);
                ls.add(tkv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }

    /////Load Bieu Do Thong Ke theo thang
    public ArrayList<ThongKeViewDoanhThu> getListThongKeTheoThang(Integer thang, Integer nam) {
        String sql = "SELECT DAY(NgayLap) as NgayLap,\n"
                + "    COUNT(*) AS SoLuongHoaDon,\n"
                + "    SUM(TongTien) AS TongTien \n"
                + "FROM \n"
                + "    HoaDon \n"
                + "WHERE \n"
                + "    MONTH(NgayLap) = ? AND YEAR(NgayLap) = ?\n"
                + "GROUP BY \n"
                + "    YEAR(NgayLap), MONTH(NgayLap), NgayLap\n" // This line seems redundant
                + "ORDER BY \n"
                + "    YEAR(NgayLap), MONTH(NgayLap);";
        ArrayList<ThongKeViewDoanhThu> ls = new ArrayList<>();
        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setInt(1, thang);
            ps.setInt(2, nam);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer ngay = rs.getInt("NgayLap");
                Integer soLuong = rs.getInt("SoLuongHoaDon");
                Integer tongTien = rs.getInt("TongTien");

                ThongKeViewDoanhThu tk = new ThongKeViewDoanhThu(null, null, soLuong, tongTien, ngay, null);
                ls.add(tk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }

    public ArrayList<ThongKeViewDoanhThu> getListBangDoanhThuTheoNam(Integer nam) {
        String sql = "SELECT MONTH(NgayLap) AS Thang, COUNT(*) AS SoLuongHoaDon, SUM(TongTien) AS TongTien,\n"
                + "SUM(TongTien) - SUM(SanPham.GiaNhap) AS LoiNhuan\n"
                + "FROM HoaDon\n"
                + "join HoaDonChiTiet on HoaDonChiTiet.MaHoaDon = HoaDon.MaHoaDon\n"
                + "join SanPhamChiTiet on SanPhamChiTiet.MaSanPhamChiTiet = HoaDonChiTiet.MaSanPhamChiTiet\n"
                + "join SanPham on SanPham.MaSanPham = SanPhamChiTiet.MaSanPham\n"
                + "WHERE YEAR(NgayLap) = ?\n"
                + "GROUP BY MONTH(NgayLap)\n"
                + "ORDER BY MONTH(NgayLap)";
        ArrayList<ThongKeViewDoanhThu> ls = new ArrayList<>();
        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setInt(1, nam);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer thang = rs.getInt("Thang");
                Integer soLuong = rs.getInt("SoLuongHoaDon");
                Integer tongTien = rs.getInt("TongTien");
                Integer LoiN = rs.getInt("LoiNhuan");
                ThongKeViewDoanhThu tkv = new ThongKeViewDoanhThu(thang, nam, soLuong, tongTien, null, null, LoiN);
                ls.add(tkv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }

}
