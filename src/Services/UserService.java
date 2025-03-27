/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import Models.User;
import Repositories.UserRepository;
import ViewModels.UserViewModel;
import java.util.ArrayList;

/**
 *
 * @author X1
 */
public class UserService {

    UserRepository userRepo = new UserRepository();

    public ArrayList<User> getListByTenDN(String tenDN) {
        return userRepo.getListByTenDN(tenDN);
    }

    public String getName(String tenDN) {
        return userRepo.getName(tenDN);
    }

    public String getTenDN(String tenDN) {
        return userRepo.getTenDN(tenDN);
    }

    public String getMK(String mk) {
        return userRepo.getMK(mk);
    }

    public String getGioiTinh(String gt) {
        return userRepo.getGioiTinh(gt);
    }

    public String getVaiTro(String vt) {
        return userRepo.getVaiTro(vt);
    }

    public String getSDT(String sdt) {
        return userRepo.getSDT(sdt);
    }

    public String getDiaChi(String dc) {
        return userRepo.getDiaChi(dc);
    }

    public String getEmail(String email) {
        return userRepo.getEmail(email);
    }

    public String getFromEmail(String email) {
        return userRepo.getFromEmail(email);
    }

    public Integer getLastID() {
        return userRepo.getLastID();
    }

    public ArrayList<User> filterByRole(String vaiT) {
        return userRepo.filterByRole(vaiT);
    }

    public Boolean checkMail(String mail) {
        return userRepo.checkMail(mail);
    }

    public String delete(String maND) {
        Boolean check = userRepo.delete(maND);
        if (check) {
            return "Xóa thành công";
        } else {
            return "Xóa thất bại";
        }
    }

    public String update(User u) {
        Boolean kq = userRepo.update(u);
        if (kq == true) {
            return "Sửa thành công";
        } else {
            return "Sửa thất bai";
        }
    }

    public String updateMatKhau(User u) {
        Boolean kq = userRepo.updateMatKhau(u);
        if (kq == true) {
            return "Sửa thành công";
        } else {
            return "Sửa thất bai";
        }
    }

    public Boolean checkLogin(String userID, String passCode) {
        return userRepo.checkLogin(userID, passCode);
    }

    //GETLIST
    public ArrayList<UserViewModel> getList() {
        return userRepo.getList();
    }
    
    public ArrayList<UserViewModel> getListByVtAndGtTTTK(String roles,String gtinh) {
        return userRepo.getListByVtAndGtTTTK(roles,gtinh);
    }
    
    public ArrayList<UserViewModel> getListByVtTTTK(String roles) {
        return userRepo.getListByVtTTTK(roles);
    }
    
    public ArrayList<UserViewModel> getListByGtTTTK(String gtinh) {
        return userRepo.getListByGtTTTK(gtinh);
    }


    public UserViewModel getListById(Integer id) {
        return userRepo.getListById(id);
    }

    //GETLIST BY SEARCH
    public ArrayList<UserViewModel> getListByTen(String tennv, String tendn, String vTro, String gTinh) {
        return userRepo.getListByTen(tennv, tendn, vTro, gTinh);
    }

    //GETLIST USER
    public ArrayList<User> getListUser() {
        return userRepo.getListUser();
    }

    public User getListByUserId(String userId) {
        return userRepo.getListByUserId(userId);
    }

    public User getListByMaND(Integer userId) {
        return userRepo.getListByMaND(userId);
    }

    //HIDE
    public void hideAccount(User u) {
        userRepo.hideAccount(u);
    }

    //CHECK ID
    public Boolean checkName(String name) {
        return userRepo.checkName(name);
    }

    //ADD
    public String addAccount(User u) {
        Boolean check = userRepo.addAccount(u);
        if (check) {
            return "Thêm tài khoản mới thành công ~uwu~";
        } else {
            return "Thêm tài khoản mới thất bại uhu";
        }
    }

}
