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
public class HoaDonViewModel {
    private Integer id;
    private String hoTen, nhanVien, loaiK, phuongThuc, trangThai;
    private Date ngayL;
    private Double tongD;

    public HoaDonViewModel() {
    }

    public HoaDonViewModel(Integer id, String hoTen, String nhanVien, String loaiK, String phuongThuc, String trangThai, Date ngayL, Double tongD) {
        this.id = id;
        this.hoTen = hoTen;
        this.nhanVien = nhanVien;
        this.loaiK = loaiK;
        this.phuongThuc = phuongThuc;
        this.trangThai = trangThai;
        this.ngayL = ngayL;
        this.tongD = tongD;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(String nhanVien) {
        this.nhanVien = nhanVien;
    }

    public String getLoaiK() {
        return loaiK;
    }

    public void setLoaiK(String loaiK) {
        this.loaiK = loaiK;
    }

    public String getPhuongThuc() {
        return phuongThuc;
    }

    public void setPhuongThuc(String phuongThuc) {
        this.phuongThuc = phuongThuc;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public Date getNgayL() {
        return ngayL;
    }

    public void setNgayL(Date ngayL) {
        this.ngayL = ngayL;
    }

    public Double getTongD() {
        return tongD;
    }

    public void setTongD(Double tongD) {
        this.tongD = tongD;
    }
    
    
}
