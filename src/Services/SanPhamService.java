/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import Models.KhoHang;
import Models.SP_ChatLieu;
import Models.SP_DanhMuc;
import Models.SP_KichCo;
import Models.SP_MauSac;
import Models.SP_NhanHang;
import Models.SanPham;
import Repositories.KhoHangRepository;
import Repositories.SanPhamRepository;
import ViewModels.SanPhamViewModel;
import java.util.ArrayList;

/**
 *
 * @author X1
 */
public class SanPhamService {
    SanPhamRepository spRepo = new SanPhamRepository();
    KhoHangRepository khRepo = new KhoHangRepository();
    
    //CHECK ID
    public Boolean checkId(Integer id) {
        return spRepo.checkId(id);
    }
    public Boolean checkName(String tenSP){
        return spRepo.checkName(tenSP);
    }
    public boolean checkIdCat(Integer id) {
        return spRepo.checkIdCat(id);
    }
    public boolean checkIdColor(Integer id) {
        return spRepo.checkIdColor(id);
    }
    public boolean checkIdSz(Integer id) {
        return spRepo.checkIdSz(id);
    }
    public boolean checkIdBrand(Integer id) {
        return spRepo.checkIdBrand(id);
    }
    public boolean checkIdChatL(Integer id) {
        return spRepo.checkIdChatL(id);
    }
    public boolean checkIdKho(Integer id) {
        return khRepo.checkIdKho(id);
    }
    
    //IMPORT
    public String addImport(SanPham sp){
       Boolean check = spRepo.addSP(sp);
        if (check) {
            return "Import data thành công!";
        }else{
            return "Import data thất bại!";
        }
    }
    
    //GETLIST
    public ArrayList<SanPhamViewModel> getListSPVM(){
        return spRepo.getListSPVM();
    }
    public ArrayList<SP_DanhMuc> getListDM(){
        return spRepo.getList();
    }
    public ArrayList<SP_ChatLieu> getListChatL(){
        return spRepo.getListChatL();
    }
    public ArrayList<SP_MauSac> getListMauS(){
        return spRepo.getListColor();
    }
    public ArrayList<SP_KichCo> getListSz(){
        return spRepo.getListSz();
    }
    public ArrayList<SP_NhanHang> getListNH(){
        return spRepo.getListBrand();
    }
    public ArrayList<KhoHang> getListKhoHang(){
        return khRepo.getList();
    }
    public SanPhamViewModel getListViewModelByIdSP(Integer id) {
        return spRepo.getListViewModelByIdSP(id);
    }
    
    //HIDE - UNHIDE
    public Boolean hideSP(SanPham sp){
        return spRepo.hideSP(sp);
    }
    public Boolean unhideSP(SanPham sp){
        return spRepo.unhideSP(sp);
    }
    public ArrayList<SanPhamViewModel> getListHide(){
        return spRepo.getListHide();
    }
    
    //GETMODEL
    public SP_DanhMuc getIdByNameDanhMuc(String name){
        return spRepo.getIdByName(name);
    }
    public SP_ChatLieu getIdByNameChatLieu(String name){
        return spRepo.getIdByNameChatL(name);
    }
    public SP_KichCo getIdByNameSz(String name){
        return spRepo.getIdByNameSz(name);
    }
    public SP_MauSac getIdByNameMauSac(String name){
        return spRepo.getIdByNameColor(name);
    }
    public SP_NhanHang getIdByNameBrand(String name){
        return spRepo.getIdByNameBrand(name);
    }
    public ArrayList<SanPhamViewModel> getListViewModelById(Integer id) {
        return spRepo.getListViewModelById(id);
    }
    public KhoHang getIdByNameKhoHang(String name){
        return khRepo.getIdByName(name);
    }
    
    //CRUD
    public ArrayList<SanPham> getListSP(){
        return spRepo.getListSP();
    }
    public String addSP(SanPham sp){
        Boolean check = spRepo.addSP(sp);
        if (check) {
            return "Thêm mới sản phẩm thành công!";
        }else{
            return "Thêm mới sản phẩm thất bại :(";
        }
    }
    public String updateSP(SanPham sp){
        Boolean check = spRepo.updateSP(sp);
        if (check) {
            return "Update sản phẩm thành công gòii~";
        }else{
            return "Update sản phẩm thất bại!!";
        }
    }
    
    //THUOCTINH
    public String addColor(SP_MauSac color) {
        Boolean check = spRepo.addColor(color);
        if (check) {
            return "Thêm thuộc tính màu sắc mới thành công!";
        } else {
            return "Thêm thuộc tính màu sắc mới thất bại :(";
        }
    }
    public String addBrand(SP_NhanHang brand) {
        Boolean check = spRepo.addBrand(brand);
        if (check) {
            return "Thêm nhãn hàng mới thành công!";
        } else {
            return "Thêm nhãn hàng mới thất bại :(";
        }
    }
    public String addMaterial(SP_ChatLieu material) {
        Boolean check = spRepo.addMaterial(material);
        if (check) {
            return "Thêm thuộc tính chất liệu mới thành công!";
        } else {
            return "Thêm thuộc tính chất liệu mới thất bại :(";
        }
    }
    public String addSz(SP_KichCo sz) {
        Boolean check = spRepo.addSz(sz);
        if (check) {
            return "Thêm thuộc tính kích cỡ mới thành công!";
        } else {
            return "Thêm thuộc tính kích cỡ mới thất bại :(";
        }
    }
    public String addCat(SP_DanhMuc danhMuc) {
        Boolean check = spRepo.addCat(danhMuc);
        if (check) {
            return "Thêm danh mục mới thành công!";
        } else {
            return "Thêm danh mục mới thất bại :(";
        }
    }
    
    public String updateBrand(SP_NhanHang brand) {
        Boolean check = spRepo.updateBrand(brand);
        if (check) {
            return "Cập nhật thương hiệu thành công!";
        }else{
            return "Cập nhật thương hiệu thất bại :(";
        }
    }
    public String updateMaterial(SP_ChatLieu material) {
        Boolean check = spRepo.updateMaterial(material);
        if (check) {
            return "Cập nhật chất liệu thành công!";
        }else{
            return "Cập nhật chất liệu thất bại :(";
        }
    }
    public String updateDanhMuc(SP_DanhMuc danhMuc) {
        Boolean check = spRepo.updateDanhMuc(danhMuc);
        if (check) {
            return "Cập nhật danh mục thành công!";
        }else{
            return "Cập nhật danh mục thất bại :(";
        }
    }
    public String updateSize(SP_KichCo sz) {
        Boolean check = spRepo.updateSize(sz);
        if (check) {
            return "Cập nhật kích cỡ thành công!";
        }else{
            return "Cập nhật kích cỡ thất bại :(";
        }
    }
    public String updateMauSac(SP_MauSac color) {
        Boolean check = spRepo.updateMauSac(color);
        if (check) {
            return "Cập nhật màu sắc thành công!";
        }else{
            return "Cập nhật màu sắc thất bại :(";
        }
    }
    
    public String hideBrand(SP_NhanHang brand) {
        Boolean check = spRepo.hideBrand(brand);
        if (check) {
            return "Ẩn thương hiệu thành công!";
        }else{
            return "Ẩn thương hiệu thất bại :(";
        }
    }
    public String hideMaterial(SP_ChatLieu material) {
        Boolean check = spRepo.hideMaterial(material);
        if (check) {
            return "Ẩn chất liệu thành công!";
        }else{
            return "Ẩn chất liệu thất bại :(";
        }
    }
    public String hideDanhMuc(SP_DanhMuc danhMuc) {
        Boolean check = spRepo.hideDanhMuc(danhMuc);
        if (check) {
            return "Ẩn danh mục thành công!";
        }else{
            return "Ẩn danh mục thất bại :(";
        }
    }
    public String hideSize(SP_KichCo sz) {
        Boolean check = spRepo.hideSize(sz);
        if (check) {
            return "Ẩn kích cỡ thành công!";
        }else{
            return "Ẩn kích cỡ thất bại :(";
        }
    }
    public String hideMauSac(SP_MauSac color) {
        Boolean check = spRepo.hideMauSac(color);
        if (check) {
            return "Ẩn màu sắc thành công!";
        }else{
            return "Ẩn màu sắc thất bại :(";
        }
    }
    
    public String unhideBrand(SP_NhanHang brand) {
        Boolean check = spRepo.unhideBrand(brand);
        if (check) {
            return "Bỏ ẩn thương hiệu thành công!";
        }else{
            return "Bỏ ẩn thương hiệu thất bại :(";
        }
    }
    public String unhideMaterial(SP_ChatLieu material) {
        Boolean check = spRepo.unhideMaterial(material);
        if (check) {
            return "Bỏ ẩn chất liệu thành công!";
        }else{
            return "Bỏ ẩn chất liệu thất bại :(";
        }
    }
    public String unhideDanhMuc(SP_DanhMuc danhMuc) {
        Boolean check = spRepo.unhideDanhMuc(danhMuc);
        if (check) {
            return "Bỏ ẩn danh mục thành công!";
        }else{
            return "Bỏ ẩn danh mục thất bại :(";
        }
    }
    public String unhideSize(SP_KichCo sz) {
        Boolean check = spRepo.unhideSize(sz);
        if (check) {
            return "Bỏ ẩn kích cỡ thành công!";
        }else{
            return "Bỏ ẩn kích cỡ thất bại :(";
        }
    }
    public String unhideMauSac(SP_MauSac color) {
        Boolean check = spRepo.unhideMauSac(color);
        if (check) {
            return "Bỏ ẩn màu sắc thành công!";
        }else{
            return "Bỏ ẩn màu sắc thất bại :(";
        }
    }
    
    public ArrayList<SP_NhanHang> getBrandHide(){
        return spRepo.getListHideBrand();
    }
    public ArrayList<SP_ChatLieu> getMaterialHide(){
        return spRepo.getListHideChatL();
    }
    public ArrayList<SP_DanhMuc> getCatHide(){
        return spRepo.getListHideDM();
    }
    public ArrayList<SP_KichCo> getSzHide(){
        return spRepo.getListHideSz();
    }
    public ArrayList<SP_MauSac> getColorHide(){
        return spRepo.getListHideColor();
    }
    
    public ArrayList<SP_NhanHang> searchByNameNH(String name){
        return spRepo.searchByNameBrand(name);
    }
    public ArrayList<SP_ChatLieu> searchByNameMaterial(String name){
        return spRepo.searchByNameChatL(name);
    }
    public ArrayList<SP_DanhMuc> searchByNameDanhMuc(String name){
        return spRepo.searchByNameDM(name);
    }
    public ArrayList<SP_KichCo> searchByNameSz(String name){
        return spRepo.searchByNameSz(name);
    }
    public ArrayList<SP_MauSac> searchByNameColor(String name){
        return spRepo.searchByNameColor(name);
    }
    
    //IMPORT
    public Integer getMaDanhMuc(String tenDM){
        return spRepo.getMaDanhMuc(tenDM);
    }
    public Integer getMaBrand(String tenBrand){
        return spRepo.getMaBrand(tenBrand);
    }
    public Integer getMaMau(String tenMau){
        return spRepo.getMaMau(tenMau);
    }
    public Integer getMaSize(String tenSize){
        return spRepo.getMaSize(tenSize);
    }
    public Integer getMaChatLieu(String tenChatLieu){
        return spRepo.getMaChatLieu(tenChatLieu);
    }
    public Integer getMaKho(String tenKho){
        return spRepo.getMaKho(tenKho);
    }
    public String getImg(String img){
        return spRepo.getImg(img);
    }
    public String  updateSoLuong(SanPham sp){
       Boolean check = spRepo. updateSoLuong(sp);
        if (check) {
            return "Thành công!";
        }else{
            return "Thất bại!";
        }
    }
    public Integer getSoLuong(String ten){
        return spRepo.getSoLuong(ten);
    }
}
