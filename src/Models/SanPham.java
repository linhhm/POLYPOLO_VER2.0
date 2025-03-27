/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.util.Date;

/**
 *
 * @author X1
 */
public class SanPham {
    private Integer maSP, maDM, soL, maSz, maMau, maChatL, maBrand, maKho, thue;
    private String tenSP, trangT, img;
    private Double giaN, giaB;
    private Date ngayNhap;

    //IMPORT
    public SanPham(Integer soL, String tenSP) {
        this.soL = soL;
        this.tenSP = tenSP;
    }
    
    public SanPham(Integer integer, String string, Integer integer0, String string0, String img, Double aDouble, Double aDouble0, Integer integer1, Integer integer2, Integer integer3, Integer integer4, Integer integer5, Integer integer6, Date date) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        this.maSP = integer;
        this.maDM = integer0;
        this.soL = integer3;
        this.maSz = integer1;
        this.maMau = integer2;
        this.maChatL = integer5;
        this.maBrand = integer4;
        this.maKho = integer6;
        this.tenSP = string;
        this.trangT = string0;
        this.img = img;
        this.giaN = aDouble;
        this.giaB = aDouble0;
        this.ngayNhap = date;
    }
    
    public SanPham() {
    }

    public SanPham(Integer maSP, Integer maDM, Integer soL, Integer maSz, Integer maMau, Integer maChatL, Integer maBrand, Integer maKho, Integer thue, String tenSP, String trangT, String img, Double giaN, Double giaB, Date ngayNhap) {
        this.maSP = maSP;
        this.maDM = maDM;
        this.soL = soL;
        this.maSz = maSz;
        this.maMau = maMau;
        this.maChatL = maChatL;
        this.maBrand = maBrand;
        this.maKho = maKho;
        this.thue = thue;
        this.tenSP = tenSP;
        this.trangT = trangT;
        this.img = img;
        this.giaN = giaN;
        this.giaB = giaB;
        this.ngayNhap = ngayNhap;
    }

    public Integer getThue() {
        return thue;
    }

    public void setThue(Integer thue) {
        this.thue = thue;
    }
    
    //IMPORT
    public SanPham(Integer maSP, Integer maDM, Integer maKho, String trangT, Double giaN, Double giaB) {
        this.maSP = maSP;
        this.maDM = maDM;
        this.maKho = maKho;
        this.trangT = trangT;
        this.giaN = giaN;
        this.giaB = giaB;
    }

    public SanPham(Integer maSP, Integer maDM, Integer soL, Integer maSz, Integer maMau, Integer maChatL, Integer maBrand, Integer maKho, String tenSP, String trangT, String img, Double giaN, Double giaB, Date ngayNhap) {
        this.maSP = maSP;
        this.maDM = maDM;
        this.soL = soL;
        this.maSz = maSz;
        this.maMau = maMau;
        this.maChatL = maChatL;
        this.maBrand = maBrand;
        this.maKho = maKho;
        this.tenSP = tenSP;
        this.trangT = trangT;
        this.img = img;
        this.giaN = giaN;
        this.giaB = giaB;
        this.ngayNhap = ngayNhap;
    }

    public SanPham(Integer integer, String string, Integer integer0, String string0, Double aDouble, Double aDouble0, Integer integer1, Integer integer2, Integer integer3, Integer integer4, Integer integer5, Integer integer6, Date date) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Integer getMaSP() {
        return maSP;
    }

    public void setMaSP(Integer maSP) {
        this.maSP = maSP;
    }

    public Integer getMaDM() {
        return maDM;
    }

    public void setMaDM(Integer maDM) {
        this.maDM = maDM;
    }

    public Integer getSoL() {
        return soL;
    }

    public void setSoL(Integer soL) {
        this.soL = soL;
    }

    public Integer getMaSz() {
        return maSz;
    }

    public void setMaSz(Integer maSz) {
        this.maSz = maSz;
    }

    public Integer getMaMau() {
        return maMau;
    }

    public void setMaMau(Integer maMau) {
        this.maMau = maMau;
    }

    public Integer getMaChatL() {
        return maChatL;
    }

    public void setMaChatL(Integer maChatL) {
        this.maChatL = maChatL;
    }

    public Integer getMaBrand() {
        return maBrand;
    }

    public void setMaBrand(Integer maBrand) {
        this.maBrand = maBrand;
    }

    public Integer getMaKho() {
        return maKho;
    }

    public void setMaKho(Integer maKho) {
        this.maKho = maKho;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getTrangT() {
        return trangT;
    }

    public void setTrangT(String trangT) {
        this.trangT = trangT;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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

    public Date getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(Date ngayNhap) {
        this.ngayNhap = ngayNhap;
    }
    
    
    
    
}
