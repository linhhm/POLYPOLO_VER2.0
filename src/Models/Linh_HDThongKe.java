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
public class Linh_HDThongKe {
    private Integer idHD, idKH, idNV;
    private String trangT, tenKH, phuongT, loaiKH, sdt, tennv;
    private Double tongT;
    private Date ngayL;

    public Linh_HDThongKe() {
    }
    
    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public Linh_HDThongKe(Integer idHD, Integer idKH, Integer idNV, String trangT, String tenKH, String phuongT, String loaiKH, String sdt, String tennv, Double tongT, Date ngayL) {
        this.idHD = idHD;
        this.idKH = idKH;
        this.idNV = idNV;
        this.trangT = trangT;
        this.tenKH = tenKH;
        this.phuongT = phuongT;
        this.loaiKH = loaiKH;
        this.sdt = sdt;
        this.tennv = tennv;
        this.tongT = tongT;
        this.ngayL = ngayL;
    }

    public String getTennv() {
        return tennv;
    }

    public void setTennv(String tennv) {
        this.tennv = tennv;
    }

    public Linh_HDThongKe(Integer idHD, Integer idKH, Integer idNV, String trangT, String tenKH, String phuongT, String loaiKH, String sdt, Double tongT, Date ngayL) {
        this.idHD = idHD;
        this.idKH = idKH;
        this.idNV = idNV;
        this.trangT = trangT;
        this.tenKH = tenKH;
        this.phuongT = phuongT;
        this.loaiKH = loaiKH;
        this.sdt = sdt;
        this.tongT = tongT;
        this.ngayL = ngayL;
    }

    public Linh_HDThongKe(Double tongT) {
        this.tongT = tongT;
    }

    public Linh_HDThongKe(Integer idHD, String tenKH, String phuongT, Double tongT) {
        this.idHD = idHD;
        this.tenKH = tenKH;
        this.phuongT = phuongT;
        this.tongT = tongT;
    }

    public Linh_HDThongKe(Integer idHD, Integer idKH, Integer idNV, String trangT, String tenKH, String phuongT, String loaiKH, Double tongT, Date ngayL) {
        this.idHD = idHD;
        this.idKH = idKH;
        this.idNV = idNV;
        this.trangT = trangT;
        this.tenKH = tenKH;
        this.phuongT = phuongT;
        this.loaiKH = loaiKH;
        this.tongT = tongT;
        this.ngayL = ngayL;
    }

    public Integer getIdHD() {
        return idHD;
    }

    public void setIdHD(Integer idHD) {
        this.idHD = idHD;
    }

    public Integer getIdKH() {
        return idKH;
    }

    public void setIdKH(Integer idKH) {
        this.idKH = idKH;
    }

    public Integer getIdNV() {
        return idNV;
    }

    public void setIdNV(Integer idNV) {
        this.idNV = idNV;
    }

    public String getTrangT() {
        return trangT;
    }

    public void setTrangT(String trangT) {
        this.trangT = trangT;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getPhuongT() {
        return phuongT;
    }

    public void setPhuongT(String phuongT) {
        this.phuongT = phuongT;
    }

    public String getLoaiKH() {
        return loaiKH;
    }

    public void setLoaiKH(String loaiKH) {
        this.loaiKH = loaiKH;
    }

    public Double getTongT() {
        return tongT;
    }

    public void setTongT(Double tongT) {
        this.tongT = tongT;
    }

    public Date getNgayL() {
        return ngayL;
    }

    public void setNgayL(Date ngayL) {
        this.ngayL = ngayL;
    }
    
    
}
