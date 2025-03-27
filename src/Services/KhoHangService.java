/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import Models.KhoHang;
import Repositories.KhoHangRepository;
import ViewModels.KhoHangViewModel;
import java.util.ArrayList;

/**
 *
 * @author X1
 */
public class KhoHangService {
    KhoHangRepository khRepo = new KhoHangRepository();
    
    public ArrayList<KhoHang> getList(){
        return khRepo.getList();
    }
    
    public ArrayList<KhoHangViewModel> getListById(Integer id){
        return khRepo.getListById(id);
    }
    
    //SEARCH
    public ArrayList<KhoHang> getListByName(String name){
        return khRepo.getListByName(name);
    }
    public ArrayList<KhoHang> getListByDescription(String name){
        return khRepo.getListByDescription(name);
    }
    
    //ACTION
    public String addKho(KhoHang kh){
        Boolean check = khRepo.addKho(kh);
        if (check) {
            return "Thêm kho hàng mới thành công!";
        }else{
            return "Thêm kho hàng thất bại :(";
        }
    }
    
    public String updateKho(KhoHang kh){
        Boolean check = khRepo.updateKho(kh);
        if (check) {
            return "Cập nhật kho hàng thành công!";
        }else{
            return "Cập nhật kho hàng thất bại :(";
        }
    }
    
    public String deleteKho(int id){
        Boolean check = khRepo.deleteKho(id);
        if (check) {
            return "Xóa kho hàng thành công!";
        }else{
            return "Xóa kho hàng thất bại :(";
        }
    }
    
    //CHECK NAME
    public boolean checkName(String name) {
        return khRepo.checkName(name);
    }
}
