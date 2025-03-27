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
public class KhachHang {
    private Integer maKH;
    private String tenKH;
    private String gioiTinh;
    private String soDT;
    private String diaChi;
    private Date ngaySinh;
    private String loaiK;
    
    public KhachHang() {
    }

    public KhachHang(Integer maKH, String tenKH, String loaiK) {
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.loaiK = loaiK;
    }

    public KhachHang(Integer maKH, String tenKH, String gioiTinh, String soDT, String diaChi, Date ngaySinh) {
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.gioiTinh = gioiTinh;
        this.soDT = soDT;
        this.diaChi = diaChi;
        this.ngaySinh = ngaySinh;
    }

    public KhachHang(Integer maKH, String tenKH, String gioiTinh, String soDT, String diaChi, Date ngaySinh, String loaiK) {
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.gioiTinh = gioiTinh;
        this.soDT = soDT;
        this.diaChi = diaChi;
        this.ngaySinh = ngaySinh;
        this.loaiK = loaiK;
    }

    public String getLoaiK() {
        return loaiK;
    }

    public void setLoaiK(String loaiK) {
        this.loaiK = loaiK;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    

    public Integer getMaKH() {
        return maKH;
    }

    public void setMaKH(Integer maKH) {
        this.maKH = maKH;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getSoDT() {
        return soDT;
    }

    public void setSoDT(String soDT) {
        this.soDT = soDT;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
    
    
}
