/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import Models.HoaDonChiTiet;
import Models.HoaDon;
import Models.KhachHang;
import Models.NhanSu;
import Models.SanPhamChiTiet;
import Repositories.HoaDonRepository;
import Repositories.KhachHangRepository;
import Repositories.NhanSuRepo;
import Repositories.SanPhamRepository;
import ViewModels.HD_HoaDonViewModel;
import ViewModels.R_GioHangViewModel;
import ViewModels.HD_InvoiceViewModel;
import ViewModels.HD_SanPhamViewModel;
import ViewModels.HoaDonViewModel;
import ViewModels.SanPhamViewModel;
import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author X1
 */
public class HoaDonService {
    HoaDonRepository hdRepo = new HoaDonRepository();
    NhanSuRepo nsRepo = new NhanSuRepo();
    SanPhamRepository spRepo = new SanPhamRepository();
    KhachHangRepository khRepo = new KhachHangRepository();
    
    //<editor-fold defaultstate="collapsed" desc=" ADD ">
    //INVOICE 
    public ArrayList<HD_InvoiceViewModel> getListKHById(Integer id){
        return hdRepo.getListKHById(id);
    }
    public ArrayList<R_GioHangViewModel> printInvoiceById(Integer id) {
        return hdRepo.printInvoiceById(id);
    }
    
    //ACTION
    public Boolean checkExists(Integer idSPCT, Integer id){
        return hdRepo.checkExists(idSPCT, id);
    }
    
    public Integer getTotalP(int id) {
        return hdRepo.getTotalP(id);
    }
    
    public String mergeSP(Integer soL, Integer id, Integer idSPCT){
        Boolean check = hdRepo.mergeSP(soL, id, idSPCT);
        if (check) {
            return "Thêm sản phẩm vào giỏ hàng thành công!!";
        }else{
            return "Thêm sản phẩm vào giỏ hàng thất bại :(";
        }
    }
    
    public String addHD(HoaDon hd){
        Boolean check = hdRepo.addHD(hd);
        if (check) {
            return "Tạo hóa đơn mới thành công!";
        }else{
            return "Tạo hóa đơn mới thất bại :(";
        }
    }
    
    public String addHDCT(HoaDonChiTiet hdct) {
        Boolean check = hdRepo.addHDCT(hdct);
        if (check) {
            return "Thêm sản phẩm vào giỏ hàng thành công!";
        }else{
            return "Thêm sản phẩm thất bại :(";
        }
    }
    
    public String updateSP(Integer id, Integer soL){
        Boolean check = hdRepo.updateSP(id, soL);
        if (check) {
            return "Thêm sản phẩm vào giỏ hàng thành công!!";
        }else{
            return "Thêm sản phẩm vào giỏ hàng thất bại :(";
        }
    }
    
    public HoaDon getTotal(Integer id){
        return hdRepo.getTotal(id);
    }
    public Double getVATFee(Integer id) {
        return hdRepo.getVATFee(id);
    }
    
    public Boolean setTongT (int idHD){
        return hdRepo.setTongT(idHD);
    }
    
    public Boolean checkKH(String sdt){
        return hdRepo.checkKH(sdt);
    }
    
    public KhachHang getKHBySDT(String sdt){
        return khRepo.getKHBySDT(sdt);
    }
    
    public String delSingle(int id, int idSPCT){
        Boolean check = hdRepo.delSingle(id, idSPCT);
        if (check) {
            return "Xóa sản phẩm thành công!";
        }else{
            return "Xóa sản phẩm thất bại!";
        }
    }
    
    public String emptyBasket(int id){
        Boolean check = hdRepo.emptyBasket(id);
        if (check) {
            return "Xóa giỏ hàng thành công!";
        }else{
            return "Xóa giỏ hàng thất bại :(";
        }
    }
    
    public String updateThanhToan(int id){
        Boolean check = hdRepo.updateThanhToan(id);
        if (check) {
            return "Thanh toán thành công!";
        }else{
            return "Thanh toán thất bại :(";
        }
    }
    
    public SanPhamChiTiet getSPById(Integer id){
        return spRepo.getListById(id);
    }
    
    //SEARCH
    public KhachHang getListKHBySDT(String sdt){
        return hdRepo.getListKHBySDT(sdt);
    }
    
    //GETMODEL
    public HoaDon getModel(){
        return hdRepo.getModel();
    }
    public ArrayList<HoaDonChiTiet> getListHDCT(){
        return hdRepo.getListHDCT();
    }
    public ArrayList<KhachHang> getListKH(){
        return khRepo.getListKH();
    }
    public NhanSu getNSById(Integer id) {
        return nsRepo.getListById(id);
    }
    public HoaDon getByHoaDon(Integer id){
        return hdRepo.getByHoaDon(id);
    }
    public HoaDonChiTiet getByHDCT(int id){
        return hdRepo.getByHDCT(id);
    }
    
    //GETLIST
    public ArrayList<HoaDonChiTiet> getListHDCTById(int id){
        return hdRepo.getListHDCTById(id);
    }
    public ArrayList<NhanSu> getListNS(){
        return nsRepo.getListNS();
    }
    public ArrayList<HoaDon> getAllHoaDon(){
        return hdRepo.getAllHoaDon();
    }
    public NhanSu getIdByNameNS(String name) {
        return nsRepo.getIdByName(name);
    }
    public ArrayList<HoaDon> getHD(String trangT){
        return hdRepo.getHoaDon(trangT);
    }
    public ArrayList<HD_SanPhamViewModel> getListSanPham(){
        return hdRepo.getListSanPham();
    }
    public ArrayList<R_GioHangViewModel> getListGioHangById(Integer id){
        return hdRepo.getListGioHangById(id);
    }
    public SanPhamViewModel getSPByIdSP(Integer id) {
        return spRepo.getSPByIdSPCT(id);
    }
    //</editor-fold>
    
    //SEARCH
    public ArrayList<HoaDonViewModel> getListViewModel(){
        return hdRepo.getListViewModel();
    }
    public ArrayList<HoaDonViewModel> getListByNV(String name){
        return hdRepo.getListByNV(name);
    }
    public ArrayList<HoaDonViewModel> getListByPhuongT(String name){
        return hdRepo.getListByPhuongT(name);
    }
    public ArrayList<HoaDonViewModel> getListByTrangT(String name){
        return hdRepo.getListByTrangT(name);
    }
    public ArrayList<HoaDonViewModel> getListByDate(Date from, Date to){
        return hdRepo.getListByDate(from, to);
    }
    public ArrayList<HoaDonViewModel> getListByTenKH(String name){
        return hdRepo.getListByTenKH(name);
    }
    
    //DEL
    public String deleteHD(int id){
        Boolean check = hdRepo.deleteHD(id);
        if (check) {
            return "Xóa hóa đơn thành công!";
        }else{
            return "Xóa hóa đơn thất bại :(";
        }
    }
    
    //SCAN
    public ArrayList<HD_SanPhamViewModel> getListSanPhamCODE(String code){
        return hdRepo.getListSanPhamCode(code);
    }
    
    public String updateSPCode(String id, Integer soL){
        Boolean check = hdRepo.updateSPCode(id, soL);
        if (check) {
            return "Thêm sản phẩm vào giỏ hàng thành công!!";
        }else{
            return "Thêm sản phẩm vào giỏ hàng thất bại :(";
        }
    }
    
    //LINH
    public ArrayList<HD_HoaDonViewModel> getListHoaDon(){
        return hdRepo.getListHoaDon();
    }
}
