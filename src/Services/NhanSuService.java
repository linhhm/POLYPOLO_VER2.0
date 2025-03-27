/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import Models.NhanSu;
import Repositories.NhanSuRepo;
import ViewModels.NhanSuViewModel;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class NhanSuService {
    NhanSuRepo nsRepo = new NhanSuRepo();
    
    public NhanSu getNhanSu(){
        return nsRepo.getNhanSu();
    }
    public NhanSu getIdByName(String name) {
        return nsRepo.getIdByName(name);
    }
    public ArrayList<NhanSu> getListNS(){
        return nsRepo.getListNS();
    }
 
    public ArrayList<NhanSuViewModel> getList() {
        return nsRepo.getList();
    }
     public NhanSuViewModel NSByID(Integer id) {
        return nsRepo.getListBy(id);
    }
     public String delete(Integer ID){
         boolean check = nsRepo.delete(ID);
         if(check){
             return "Xóa thành công";
         }else{
             return "Xóa thất bại";
         }
     }
     
     public String AddNew(NhanSu ns){
         boolean check = nsRepo.AddNew(ns);
         if(check){
             return "Thêm thành công";
         }else{
             return "Thêm thất bại";
         }
     } 
     public String UpdateNew(NhanSu ns){
         boolean check = nsRepo.updateNew(ns);
         if(check){
             return "Sửa thành công";
         }else{
             return "Sửa thất bại";
         }
     } 
     public ArrayList<NhanSuViewModel> searchByName(String name) {
        return nsRepo.searchByNameVM(name);
    }
     public ArrayList<NhanSuViewModel> searchSDT(String sdt) {
        return nsRepo.searchBySDT(sdt);
    }
     public boolean Check(String sdt) {
        return nsRepo.soDienThoaiCheck(sdt);
    }
      public Boolean checkName(Integer maND) {
        return nsRepo.checkName(maND);
    }

}


