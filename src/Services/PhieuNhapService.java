/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import Models.MyPurchase;
import Models.NCC;
import Models.NhanSu;
import Models.PhieuNhap;
import Models.PhieuNhapChiTiet;
import Repositories.NCCRepo;
import Repositories.NhanSuRepo;
import Repositories.PhieuNhapRepository;
import ViewModels.PN_PhieuNhapDetailsViewModel;
import ViewModels.PN_PhieuNhapViewModel;
import ViewModels.PN_SanPhamViewModel;
import ViewModels.PhieuNhapViewModel;
import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author X1
 */
public class PhieuNhapService {
    PhieuNhapRepository pnRepo = new PhieuNhapRepository();
    NCCRepo nccRepo = new NCCRepo();
    NhanSuRepo nsRepo = new NhanSuRepo();
    
    //PRINT
    public ArrayList<MyPurchase> getListSupplierById(Integer id){
        return pnRepo.getListSupplierById(id);
    }
    
    public PhieuNhap getModel(){
        return pnRepo.getModel();
    }
    
    //AUTOCOMPLETE SEARCH
    public ArrayList<PN_SanPhamViewModel> getSuggestSearch(String name){
        return pnRepo.getSuggestSearch(name);
    }
    
    
    //ACTION
    public Boolean checkExists(Integer idPhieu, Integer idSP){
        return pnRepo.checkExists(idPhieu, idSP);
    }
    public Boolean mergeSP(Integer idSP, Integer soL, Integer idPhieu){
        return pnRepo.mergeSP(idSP, soL, idPhieu);
    }
    
    public String addPhieu(PhieuNhap pn){
        Boolean check = pnRepo.addPhieu(pn);
        if (check) {
            return "Tạo phiếu thành công!";
        }else{
            return "Tạo phiếu thất bại!";
        }
    }
    
    public String addPNCT(PhieuNhapChiTiet pn){
        Boolean check = pnRepo.addPNCT(pn);
        if (check) {
            return "Thêm sản phẩm vào phiếu thành công!";
        }else{
            return "Thêm sản phẩm vào phiếu thất bại!";
        }
    }
    
    public Boolean updateSP(int soL, int id) {
        return pnRepo.updateSP(soL, id);
    }
    
    public PhieuNhapChiTiet getTaxById(Integer idP){
        return pnRepo.getTaxById(idP);
    }
    public PhieuNhap getTotalByID(Integer idP){
        return pnRepo.getTotalByID(idP);
    }
    public Boolean updatetongDon(int id){
        return pnRepo.updatetongDon(id);
    }
    
    public String updateSPDetails(PhieuNhapChiTiet pn) {
        Boolean check = pnRepo.updateSPDetails(pn);
        if (check) {
            return "Cập nhật SP thành công!!";
        }else{
            return "Cập nhật SP thất bại";
        }
    }
    
    public String deleteSP(Integer idSP, Integer idP){
        Boolean check = pnRepo.deleteSP(idSP, idP);
        if (check) {
            return "Xóa sản phẩm khỏi phiếu thành công!";
        }else{
            return "Xóa sản phẩm thất bại!";
        }
    }
    
    public String heh(Integer idP){
        Boolean check = pnRepo.heh(idP);
        if (check) {
            return "Nhập hàng thành công!";
        }else{
            return "Nhập hàng thất bại! :(";
        }
    }
    
    public String deletePhieuNhap(int id){
        Boolean check = pnRepo.deletePhieuNhap(id);
        if (check) {
            return "Xóa phiếu nhập thành công :)";
        }else{
            return "Xóa phiếu nhập thất bại :(";
        }
    }
    
    public ArrayList<PN_SanPhamViewModel> searchByName(String name){
        return pnRepo.searchByName(name);
    }
    
    public PN_PhieuNhapViewModel getPNCTById(Integer id){
        return pnRepo.getPNCTById(id);
    }
    
    public PN_PhieuNhapViewModel getDataToLoad(Integer id, Integer idPhieu){
        return pnRepo.getDataToLoad(id, idPhieu);
    }
    public NCC getIdByNameNCC(String name){
        return nccRepo.getIdByName(name);
    }
    
    public ArrayList<NhanSu> getListNS(){
        return nsRepo.getListNS();
    }
    public NhanSu getIdByNameNS(String name) {
        return nsRepo.getIdByName(name);
    }
    public NhanSu getListByIdNS(Integer id) {
        return nsRepo.getListById(id);
    }
    
    //GETLIST
    public ArrayList<PN_PhieuNhapViewModel> getListPhieuById(Integer id){
        return pnRepo.getListPhieuById(id);
    }
    public ArrayList<PhieuNhapViewModel> getListPN(){
        return pnRepo.getListPN();
    }
    
    public ArrayList<PhieuNhapChiTiet> getListPNCT(){
        return pnRepo.getListPNCT();
    }
    
    public ArrayList<PN_SanPhamViewModel> getListSPViewModel(){
        return pnRepo.getListSPViewModel();
    }
    
    public PhieuNhapChiTiet checkPN(Integer idSP, Integer idP){
        return pnRepo.checkPN(idSP, idP);
    }

    public NCC getListByIdNCC(Integer id){
        return nccRepo.getListById(id);
    }
    
    
    //PHIEUNHAP MAIN
    public ArrayList<PhieuNhapViewModel> searchByNameNCC(String name){
        return pnRepo.searchByNameNCC(name);
    }
    
    public ArrayList<PhieuNhapViewModel> searchByNameNV(String name){
        return pnRepo.searchByNameNV(name);
    }
    
    public ArrayList<PhieuNhapViewModel> searchByDate(Date from, Date to){
        return pnRepo.searchByDate(from, to);
    }
    
    public ArrayList<PhieuNhapViewModel> searchByPriceASC(){
        return pnRepo.searchByPriceASC();
    }
    
    public ArrayList<PhieuNhapViewModel> searchByPriceDESC(){
        return pnRepo.searchByPriceDESC();
    }
    
    public ArrayList<PN_PhieuNhapDetailsViewModel> getDetailsPN(int id){
        return pnRepo.getDetailsPN(id);
    }
    
    //BARCODE
    public ArrayList<PN_SanPhamViewModel> SearchCode(String code){
        return pnRepo.searchCode(code);
    }
}
