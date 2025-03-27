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
public class PhieuNhapViewModel {
    private Integer maPhieu;
    private String tenNCC, tenNV;
    private Date ngayL;
    private Double tongDon; 

    public PhieuNhapViewModel() {
    }

    public PhieuNhapViewModel(Integer maPhieu, String tenNCC, String tenNV, Date ngayL, Double tongDon) {
        this.maPhieu = maPhieu;
        this.tenNCC = tenNCC;
        this.tenNV = tenNV;
        this.ngayL = ngayL;
        this.tongDon = tongDon;
    }

    public Integer getMaPhieu() {
        return maPhieu;
    }

    public void setMaPhieu(Integer maPhieu) {
        this.maPhieu = maPhieu;
    }

    public String getTenNCC() {
        return tenNCC;
    }

    public void setTenNCC(String tenNCC) {
        this.tenNCC = tenNCC;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public Date getNgayL() {
        return ngayL;
    }

    public void setNgayL(Date ngayL) {
        this.ngayL = ngayL;
    }

    public Double getTongDon() {
        return tongDon;
    }

    public void setTongDon(Double tongDon) {
        this.tongDon = tongDon;
    }
    
    

    
    
}
