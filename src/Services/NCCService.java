/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import Models.NCC;
import Repositories.NCCRepo;
import java.util.ArrayList;

/**
 *
 * @author X1
 */
public class NCCService {
    NCCRepo nccRepo = new NCCRepo();
    
    //GETLS
    public ArrayList<NCC> getList(){
        return nccRepo.getList();
    }
    public NCC getListById(Integer id){
        return nccRepo.getListById(id);
    }
    public NCC getIdByName(String name){
        return nccRepo.getIdByName(name);
    }
    public ArrayList<NCC> getListHide(){
        return nccRepo.getListHide();
    }
    
    //CHECK
    public boolean checkId(Integer id) {
        return nccRepo.checkId(id);
    }
    public boolean checkName(String name) {
        return nccRepo.checkName(name);
    }
    
    //CRUD
    public String addNCC(NCC ncc) {
        Boolean check = nccRepo.addNCC(ncc);
        if (check) {
            return "Thêm nhà cung cấp thành công!";
        }else{
            return "Thêm nhà cung cấp thất bại :(";
        }
    }
    public String updateNCC(NCC ncc) {
        Boolean check = nccRepo.updateNCC(ncc);
        if (check) {
            return "Cập nhật thông tin thành công!";
        }else{
            return "Cập nhật thông tin thất bại :(";
        }
    }
    public String hideNCC(NCC ncc) {
        Boolean check = nccRepo.hideNCC(ncc);
        if (check) {
            return "Ẩn thông tin thành công!";
        }else{
            return "Ẩn thông tin thất bại :(";
        }
    }
    public String unhideNCC(NCC ncc) {
        Boolean check = nccRepo.unhideNCC(ncc);
        if (check) {
            return "Bỏ ẩn nhà cung cấp thành công!";
        }else{
            return "Bỏ ẩn nhà cung cấp thất bại :(";
        }
    }
    
    
}
