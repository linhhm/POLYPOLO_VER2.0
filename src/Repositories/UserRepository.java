/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositories;

import Models.NhanSu;
import Models.User;
import ViewModels.UserViewModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author X1
 */
public class UserRepository {
    
    DbConnection dbConnection;
    
    public String getName(String tenDN) {
        String sql = "Select NhanVien.TenNhanVien from NhanVien inner join NguoiDung on NhanVien.MaNguoiDung = NguoiDung.MaNguoiDung where TenDangNhap = '" + tenDN + "'";
        NhanSu ns = new NhanSu();
        try (Connection conn = DbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ns.setTenNhanVien(rs.getString("TenNhanVien"));
                return ns.getTenNhanVien();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String getTenDN(String tenDN) {
        String sql = "Select NguoiDung.TenDangNhap from NhanVien inner join NguoiDung on NhanVien.MaNguoiDung = NguoiDung.MaNguoiDung where TenDangNhap = '" + tenDN + "'";
        User u = new User();
        try (Connection conn = DbConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                u.setUserName(rs.getString("TenDangNhap"));
                return u.getUserName();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Loi";
    }
    
    public ArrayList<User> filterByRole(String vaiT){
        String sql = "SELECT * FROM NguoiDung \n" +
                    "WHERE VaiTro = ? ;";
        ArrayList<User> ls = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ps.setObject(1, vaiT);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer maND = rs.getInt("MaNguoiDung");
                String tenDN = rs.getString("TenDangNhap");
                String mk = rs.getString("MatKhau");
                String vaiTro = rs.getString("VaiTro");
                String email = rs.getString("Email");
                
                ls.add(new User(maND, tenDN, mk, vaiTro, email));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
        
    }
    
    public Boolean delete(String maND) {
        String sql = "DELETE FROM [dbo].[NguoiDung]\n"
                + "      WHERE [MaNguoiDung] = '" + maND + "'";

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

    public ArrayList<User> getMaTK() {
        String sql = "select MaNguoiDung from NguoiDung";
        ArrayList<User> list = new ArrayList<>();
        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer maTK = rs.getInt("MaNguoiDung");

                User u = new User(maTK);
                list.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Boolean update(User u) {
        String sql = "UPDATE [dbo].[NguoiDung]\n"
                + "   SET [TenDangNhap] = ?\n"
                + "      ,[MatKhau] = ?\n"
                + "      ,[VaiTro] = ?\n"
                + "      ,[Email] = ?\n"
                + " WHERE MaNguoiDung = ?";

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, u.getUserName());
            //HUONG - Đổi cái này là getUSERNAME
            ps.setObject(2, u.getPassCode());
            ps.setObject(3, u.getRole());
            ps.setObject(4, u.getEmail());
            ps.setObject(5, u.getUserID());

            int check = ps.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public Boolean updateMatKhau(User u) {
        String sql = "UPDATE [dbo].[NguoiDung]\n"
                + "   SET [MatKhau] = ?\n"
                + " WHERE TenDangNhap = ?";

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
           
            //HUONG - Đổi cái này là getUSERNAME
            ps.setObject(1, u.getPassCode());
            ps.setObject(2, u.getUserName());

            int check = ps.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public User getListByUserId(String userID) {
        String sql = "SELECT * FROM NguoiDung nd WHERE nd.TenDangNhap = ? ";
        User u = new User();

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, userID);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String username = rs.getString("TenDangNhap");
                String password = rs.getString("MatKhau");
                String role = rs.getString("VaiTro");
                String email = rs.getString("Email");

                u = new User(username, password, role, email);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return u;
    }
    public ArrayList<User> getListByTenDN(String tenDN) {
        String sql = "SELECT * FROM NguoiDung nd WHERE nd.TenDangNhap like '%"+tenDN+"%' ";
        ArrayList<User> list = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer userID = rs.getInt("MaNguoiDung");
                String username = rs.getString("TenDangNhap");
                String password = rs.getString("MatKhau");
                String role = rs.getString("VaiTro");
                String email = rs.getString("Email");
                list.add(new User(userID, username, password, role,email));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public ArrayList<User> getListByVaiTro(String roles) {
        String sql = "SELECT * FROM NguoiDung nd WHERE nd.VaiTro = ?";
         ArrayList<User> list = new ArrayList<>();
        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, roles);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer userID = rs.getInt("MaNguoiDung");
                String username = rs.getString("TenDangNhap");
                String password = rs.getString("MatKhau");
                String role = rs.getString("VaiTro");

                list.add(new User(userID, username, password, role));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public ArrayList<UserViewModel> getListByVtAndGtTTTK(String roles,String gtinh) {
        String sql = "select * from NhanVien inner join NguoiDung on NhanVien.MaNguoiDung = NguoiDung.MaNguoiDung WHERE NguoiDung.VaiTro = '"+roles+"' and NhanVien.GioiTinh = N'"+gtinh+"'";
         ArrayList<UserViewModel> list = new ArrayList<>();
        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer maNV = rs.getInt("MaNhanVien");
                Integer maND = rs.getInt("MaNguoiDung");
                String hoTen = rs.getString("TenNhanVien");
                String gioiTinh = rs.getString("GioiTinh");
                String soDT = rs.getString("SoDienThoai");
                String diaChi = rs.getString("DiaChi");
                String tenDN = rs.getString("TenDangNhap");
                String mk = rs.getString("MatKhau");
                String vaiTro = rs.getString("VaiTro");
                String email = rs.getString("Email");
                Date ngSinh = rs.getDate("NgaySinh");
                
                list.add(new UserViewModel(maNV, maND, tenDN, hoTen, mk, soDT, vaiTro, diaChi, gioiTinh, ngSinh, email));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public ArrayList<UserViewModel> getListByVtTTTK(String roles) {
        String sql = "select * from NhanVien inner join NguoiDung on NhanVien.MaNguoiDung = NguoiDung.MaNguoiDung WHERE NguoiDung.VaiTro = N'"+roles+"'";
         ArrayList<UserViewModel> list = new ArrayList<>();
        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer maNV = rs.getInt("MaNhanVien");
                Integer maND = rs.getInt("MaNguoiDung");
                String hoTen = rs.getString("TenNhanVien");
                String gioiTinh = rs.getString("GioiTinh");
                String soDT = rs.getString("SoDienThoai");
                String diaChi = rs.getString("DiaChi");
                String tenDN = rs.getString("TenDangNhap");
                String mk = rs.getString("MatKhau");
                String vaiTro = rs.getString("VaiTro");
                String email = rs.getString("Email");
                Date ngSinh = rs.getDate("NgaySinh");
                
                list.add(new UserViewModel(maNV, maND, tenDN, hoTen, mk, soDT, vaiTro, diaChi, gioiTinh, email));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public ArrayList<UserViewModel> getListByGtTTTK(String gtinh) {
        String sql = "select * from NhanVien inner join NguoiDung on NhanVien.MaNguoiDung = NguoiDung.MaNguoiDung WHERE NhanVien.GioiTinh = N'"+gtinh+"'";
         ArrayList<UserViewModel> list = new ArrayList<>();
        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer maNV = rs.getInt("MaNhanVien");
                Integer maND = rs.getInt("MaNguoiDung");
                String hoTen = rs.getString("TenNhanVien");
                String gioiTinh = rs.getString("GioiTinh");
                String soDT = rs.getString("SoDienThoai");
                String diaChi = rs.getString("DiaChi");
                String tenDN = rs.getString("TenDangNhap");
                String mk = rs.getString("MatKhau");
                String vaiTro = rs.getString("VaiTro");
                String email = rs.getString("Email");
                Date ngSinh = rs.getDate("NgaySinh");
                
                list.add(new UserViewModel(maNV, maND, tenDN, hoTen, mk, soDT, vaiTro, diaChi, gioiTinh, ngSinh, email));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    //CHECKLOGIN
    public Boolean checkLogin(String userID, String passCode) {
        String sql = "SELECT * FROM NguoiDung WHERE TenDangNhap = ? AND MatKhau = ?";
        User u = null;

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, userID);
            ps.setObject(2, passCode);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String username = rs.getString("TenDangNhap");
                String password = rs.getString("MatKhau");

                u = new User(userID, passCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (u == null) {
            return false;
        } else {
            return true;
        }
    }

    //THÊM PUBLIC ARRAY NÀY
    public ArrayList<UserViewModel> getDataFromList() {
        //HUONG -SỬA SQL
        String sql = "SELECT NguoiDung.MaNguoiDung, NguoiDung.TenDangNhap, NguoiDung.MatKhau, NhanVien.TenNhanVien, NhanVien.SoDienThoai, NhanVien.GioiTinh, NhanVien.DiaChi, NhanVien.NgaySinh, NguoiDung.VaiTro\n"
                + "FROM NguoiDung INNER JOIN NhanVien\n"
                + "ON NguoiDung.MaNguoiDung = NhanVien.MaNguoiDung\n"
                + ""
                //HUONG -  BỎ DÒNG NÀY                
                //" +           WHERE nv.Deleted!=1 AND nd.Deleted!=1"
                + "";
        ArrayList<UserViewModel> ls = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                Integer maND = rs.getInt("MaNguoiDung");
                String tenDN = rs.getString("TenDangNhap");
                String mk = rs.getString("MatKhau");
                String hoTen = rs.getString("TenNhanVien");
                //THÊM
                String vaiTro = rs.getString("VaiTro");
                String soDT = rs.getString("SoDienThoai");
                String gioiTinh = rs.getString("GioiTinh");
                Date ngaySinh = rs.getDate("NgaySinh");
                String diaChi = rs.getString("DiaChi");

                //SỬA LẠI DÒNG, LẤY CONSTRUCTOR FULL
                UserViewModel u = new UserViewModel(maND, tenDN, tenDN, mk, soDT, vaiTro, diaChi, gioiTinh, ngaySinh);
                ls.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }

    //GETLIST
    public ArrayList<UserViewModel> getList() {
        String sql = "select * from NhanVien inner join NguoiDung on NhanVien.MaNguoiDung = NguoiDung.MaNguoiDung "
                //HUONG -  BỎ DÒNG NÀY                
                //" +           WHERE nv.Deleted!=1 AND nd.Deleted!=1"
                + "";
        ArrayList<UserViewModel> ls = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer maNV = rs.getInt("MaNhanVien");
                Integer maND = rs.getInt("MaNguoiDung");
                String hoTen = rs.getString("TenNhanVien");
                String gioiTinh = rs.getString("GioiTinh");
                String soDT = rs.getString("SoDienThoai");
                String diaChi = rs.getString("DiaChi");
                String tenDN = rs.getString("TenDangNhap");
                String mk = rs.getString("MatKhau");
                String vaiTro = rs.getString("VaiTro");
                String email = rs.getString("Email");
                Date ngSinh = rs.getDate("NgaySinh");
                
                ls.add(new UserViewModel(maNV, maND, tenDN, hoTen, mk, soDT, vaiTro, diaChi, gioiTinh, ngSinh, email));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }

    public UserViewModel getListById(Integer id) {
        //HUONG - SUA SQL
        String sql = "SELECT NguoiDung.MaNguoiDung, NguoiDung.TenDangNhap, NguoiDung.MatKhau\n"
                + "                   , NhanVien.TenNhanVien, NhanVien.SoDienThoai, NhanVien.GioiTinh, NhanVien.DiaChi, NhanVien.NgaySinh, NguoiDung.VaiTro, NguoiDung.Email FROM NguoiDung INNER JOIN NhanVien ON NguoiDung.MaNguoiDung = NhanVien.MaNguoiDung\n"
                //HUONG - BỎ DÒNG NÀY
                //      + "                   WHERE nv.Deleted !=1 AND nd.Deleted!=1 AND "
                + "where NguoiDung.MaNguoiDung = ?";
        UserViewModel u = new UserViewModel();

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer maND = rs.getInt("MaNguoiDung");
                String tenDN = rs.getString("TenDangNhap");
                String mk = rs.getString("MatKhau");
                String hoTen = rs.getString("TenNhanVien");
                String vaiTro = rs.getString("VaiTro");
                String Email = rs.getString("Email");
                String soDT = rs.getString("SoDienThoai");
                String diaC = rs.getString("DiaChi");
                Date ngayS = rs.getDate("NgaySinh");
                String gioiT = rs.getString("GioiTinh");

                u = new UserViewModel(maND, id, tenDN, hoTen, mk, soDT, vaiTro, diaC, gioiT, ngayS, Email);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return u;
    }

    //GETLIST USER
    public ArrayList<User> getListUser() {
        String sql = "SELECT * FROM NguoiDung ";
        ArrayList<User> ls = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("MaNguoiDung");
                String userId = rs.getString("TenDangNhap");
                String passCode = rs.getString("MatKhau");
                String role = rs.getString("VaiTro");
                String email = rs.getString("Email");
                
                User u = new User(id, userId, passCode, role,email);
                ls.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }

    //SEARCH
    public ArrayList<UserViewModel> getListByTen(String tennv,String tendn, String vTro,String gTinh) {
        String sql = "select * from NhanVien inner join NguoiDung on NhanVien.MaNguoiDung = NguoiDung.MaNguoiDung\n"
                + "WHERE NhanVien.TenNhanVien LIKE N'%" + tennv+ "%' and NguoiDung.TenDangNhap LIKE N'%" + tendn + "%' and NguoiDung.VaiTro LIKE N'%" + vTro + "%' and NhanVien.GioiTinh LIKE N'%" + gTinh+ "%'";
        ArrayList<UserViewModel> ls = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer maNV = rs.getInt("MaNhanVien");
                Integer maND = rs.getInt("MaNguoiDung");
                String hoTen = rs.getString("TenNhanVien");
                String gioiTinh = rs.getString("GioiTinh");
                String soDT = rs.getString("SoDienThoai");
                String diaChi = rs.getString("DiaChi");
                String tenDN = rs.getString("TenDangNhap");
                String mk = rs.getString("MatKhau");
                String vaiTro = rs.getString("VaiTro");
                String email = rs.getString("Email");
                
                ls.add(new UserViewModel(maNV, maND, tenDN, hoTen, mk, soDT, vaiTro, diaChi, gioiTinh, email));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }

    //CHECK ID
    public Boolean checkName(String name) {
        String sql = "SELECT COUNT(*) FROM NguoiDung WHERE TenDangNhap = ?";

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, name);

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

    //ADD ACCOUNT
    public Boolean addAccount(User u) {
        String sql = "INSERT INTO NguoiDung(TenDangNhap,MatKhau,VaiTro,Email) \n"
                + "VALUES(?,?,?,?)\n";

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, u.getUserName());
            ps.setObject(2, u.getPassCode());
            ps.setObject(3, u.getRole());
            ps.setObject(4, u.getEmail());

            int check = ps.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //HIDE
    public Boolean hideAccount(User u) {
        String sql = "UPDATE NguoiDung SET Deleted = 1 WHERE MaNguoiDung = ?";

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, u.getUserID());

            int check = ps.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
      
    public String getMK(String tenDN) {
        String sql = "Select NguoiDung.MatKhau from NhanVien inner join NguoiDung on NhanVien.MaNguoiDung = NguoiDung.MaNguoiDung where TenDangNhap = '" + tenDN + "'";
        UserViewModel userViewModel = new UserViewModel();
        try (Connection conn = DbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                userViewModel.setMatKhau(rs.getString("MatKhau"));
                return userViewModel.getMatKhau();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } 
        return "Loi";
    }
    public String getIdPerson(String tenDN) {
        String sql = "Select NhanVien.MaNhanVien from NhanVien inner join NguoiDung on NhanVien.MaNguoiDung = NguoiDung.MaNguoiDung where TenDangNhap = '" + tenDN + "'";
        UserViewModel userViewModel = new UserViewModel();
        try (Connection conn = DbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                userViewModel.setMaNV(rs.getInt("MaNhanVien"));
                return String.valueOf(userViewModel.getMaNV());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } 
        return "Loi";
    }
    
    public String getGioiTinh(String tenDN) {
        String sql = "Select NhanVien.GioiTinh from NhanVien inner join NguoiDung on NhanVien.MaNguoiDung = NguoiDung.MaNguoiDung where TenDangNhap = '" + tenDN + "'";
        UserViewModel userViewModel = new UserViewModel();
        try (Connection conn = DbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                userViewModel.setGioiT(rs.getString("GioiTinh"));
                return userViewModel.getGioiT();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } 
        return "Loi";
    }
    public String getVaiTro(String tenDN) {
        String sql = "Select NguoiDung.VaiTro from NhanVien inner join NguoiDung on NhanVien.MaNguoiDung = NguoiDung.MaNguoiDung where TenDangNhap = '" + tenDN + "'";
        UserViewModel userViewModel = new UserViewModel();
        try (Connection conn = DbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                userViewModel.setVaiTro(rs.getString("VaiTro"));
                if (userViewModel.getVaiTro() == "staff") {
                    return "Nhân viên";
                } else {
                    return "Quản trị viên";
                }
                
            }

        } catch (Exception e) {
            e.printStackTrace();
        } 
        return "Loi";
    }
    public String getSDT(String tenDN) {
        String sql = "Select NhanVien.SoDienThoai from NhanVien inner join NguoiDung on NhanVien.MaNguoiDung = NguoiDung.MaNguoiDung where TenDangNhap = '" + tenDN + "'";
        UserViewModel userViewModel = new UserViewModel();
        try (Connection conn = DbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                userViewModel.setSoDT(rs.getString("SoDienThoai"));
                return userViewModel.getSoDT();

            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return "Loi";
    }
    public String getDiaChi(String tenDN) {
        String sql = "Select NhanVien.DiaChi from NhanVien inner join NguoiDung on NhanVien.MaNguoiDung = NguoiDung.MaNguoiDung where TenDangNhap = '" + tenDN + "'";
        UserViewModel userViewModel = new UserViewModel();
        try (Connection conn = DbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                userViewModel.setDiaC(rs.getString("DiaChi"));
                return userViewModel.getDiaC();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } 
        return "Loi";
    }
    public String getFromEmail(String email) {
        String sql = "Select TenDangNhap from NguoiDung where Email = '" + email + "'";
        UserViewModel userViewModel = new UserViewModel();
        try (Connection conn = DbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                userViewModel.setTenDN(rs.getString("TenDangNhap"));
                return userViewModel.getTenDN();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } 
        return "Loi";
    }
    public String getEmail(String tenDN) {
        String sql = "Select NguoiDung.Email from NhanVien inner join NguoiDung on NhanVien.MaNguoiDung = NguoiDung.MaNguoiDung where TenDangNhap = '" + tenDN + "'";
        UserViewModel userViewModel = new UserViewModel();
        try (Connection conn = DbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                userViewModel.setEmail(rs.getString("Email"));
                return userViewModel.getEmail();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } 
        return "Loi";
    }

    public User getListByMaND(Integer userID) {
        String sql = "SELECT * FROM NguoiDung nd WHERE nd.MaNguoiDung = ? ";
        User u = new User();

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, userID);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String username = rs.getString("TenDangNhap");
                String password = rs.getString("MatKhau");
                String role = rs.getString("VaiTro");
                String email = rs.getString("Email");

                u = new User(userID, username, password, role, email);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return u;
    }
        public Integer getLastID() {
        String sql = "SELECT IDENT_CURRENT('NguoiDung') as LastID";
        User u = new User();
        try (Connection conn = DbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                u.setUserID(rs.getInt(1));
                return u.getUserID();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
        
    public Boolean checkMail(String mail) {
        String sql = "SELECT COUNT(*) FROM NguoiDung WHERE Email = ?";

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, mail);

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



