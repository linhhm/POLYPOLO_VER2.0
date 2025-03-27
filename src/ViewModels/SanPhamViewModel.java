/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ViewModels;

import java.util.Date;

/**
 *
 * @author X1
 */
public class SanPhamViewModel {
    private Integer idSP, soL;
    private String tenSP, danhMuc,nhanHang, mauSac, kichCo, chatLieu, trangT, khoHang;
    private Double giaN, giaB;
    
    private String img;
    private Date ngayNhap;

    //PHIEUNHAP
    private Integer thue;
    private Double tongT;

    public SanPhamViewModel(Integer idSP, Integer soL, String tenSP, String danhMuc, String nhanHang, String mauSac, String kichCo, String chatLieu, Double giaN, Integer thue, Double tongT) {
        this.idSP = idSP;
        this.soL = soL;
        this.tenSP = tenSP;
        this.danhMuc = danhMuc;
        this.nhanHang = nhanHang;
        this.mauSac = mauSac;
        this.kichCo = kichCo;
        this.chatLieu = chatLieu;
        this.giaN = giaN;
        this.thue = thue;
        this.tongT = tongT;
    }

    
    public SanPhamViewModel() {
    }

    public SanPhamViewModel(Integer idSP, Integer soL, String tenSP, String danhMuc, String nhanHang, String mauSac, String kichCo, String chatLieu, String trangT, String khoHang, Double giaN, Double giaB, String img, Date ngayNhap) {
        this.idSP = idSP;
        this.soL = soL;
        this.tenSP = tenSP;
        this.danhMuc = danhMuc;
        this.nhanHang = nhanHang;
        this.mauSac = mauSac;
        this.kichCo = kichCo;
        this.chatLieu = chatLieu;
        this.trangT = trangT;
        this.khoHang = khoHang;
        this.giaN = giaN;
        this.giaB = giaB;
        this.img = img;
        this.ngayNhap = ngayNhap;
    }

    
    public SanPhamViewModel(Integer idSP, Integer soL, String tenSP, String danhMuc, String nhanHang, String mauSac, String kichCo, String chatLieu, String trangT, String khoHang, Double giaN, Double giaB) {
        this.idSP = idSP;
        this.soL = soL;
        this.tenSP = tenSP;
        this.danhMuc = danhMuc;
        this.nhanHang = nhanHang;
        this.mauSac = mauSac;
        this.kichCo = kichCo;
        this.chatLieu = chatLieu;
        this.trangT = trangT;
        this.khoHang = khoHang;
        this.giaN = giaN;
        this.giaB = giaB;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Date getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(Date ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public Integer getIdSP() {
        return idSP;
    }

    public void setIdSP(Integer idSP) {
        this.idSP = idSP;
    }

    public Integer getSoL() {
        return soL;
    }

    public void setSoL(Integer soL) {
        this.soL = soL;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getDanhMuc() {
        return danhMuc;
    }

    public void setDanhMuc(String danhMuc) {
        this.danhMuc = danhMuc;
    }

    public String getNhanHang() {
        return nhanHang;
    }

    public void setNhanHang(String nhanHang) {
        this.nhanHang = nhanHang;
    }

    public String getMauSac() {
        return mauSac;
    }

    public void setMauSac(String mauSac) {
        this.mauSac = mauSac;
    }

    public String getKichCo() {
        return kichCo;
    }

    public void setKichCo(String kichCo) {
        this.kichCo = kichCo;
    }

    public String getChatLieu() {
        return chatLieu;
    }

    public void setChatLieu(String chatLieu) {
        this.chatLieu = chatLieu;
    }

    public String getTrangT() {
        return trangT;
    }

    public void setTrangT(String trangT) {
        this.trangT = trangT;
    }

    public String getKhoHang() {
        return khoHang;
    }

    public void setKhoHang(String khoHang) {
        this.khoHang = khoHang;
    }

    public Double getGiaN() {
        return giaN;
    }

    public void setGiaN(Double giaN) {
        this.giaN = giaN;
    }

    public Double getGiaB() {
        return giaB;
    }

    public void setGiaB(Double giaB) {
        this.giaB = giaB;
    }

    public Integer getThue() {
        return thue;
    }

    public void setThue(Integer thue) {
        this.thue = thue;
    }

    public Double getTongT() {
        return tongT;
    }

    public void setTongT(Double tongT) {
        this.tongT = tongT;
    }
    
    
}
