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
public class PhieuNhap {
    private Integer idPhieu, idNCC, idNV;
    private Date ngayN;
    private Double tongD;
    private String trangT;

    public PhieuNhap() {
    }

    public PhieuNhap(Double tongD) {
        this.tongD = tongD;
    }

    public PhieuNhap(String trangT) {
        this.trangT = trangT;
    }

    public PhieuNhap(Integer idPhieu, Integer idNCC, Integer idNV, Date ngayN, Double tongD) {
        this.idPhieu = idPhieu;
        this.idNCC = idNCC;
        this.idNV = idNV;
        this.ngayN = ngayN;
        this.tongD = tongD;
    }

    public PhieuNhap(Integer idPhieu, Integer idNCC, Integer idNV, Date ngayN, Double tongD, String trangT) {
        this.idPhieu = idPhieu;
        this.idNCC = idNCC;
        this.idNV = idNV;
        this.ngayN = ngayN;
        this.tongD = tongD;
        this.trangT = trangT;
    }
    

    public Integer getIdPhieu() {
        return idPhieu;
    }

    public void setIdPhieu(Integer idPhieu) {
        this.idPhieu = idPhieu;
    }

    public Integer getIdNCC() {
        return idNCC;
    }

    public void setIdNCC(Integer idNCC) {
        this.idNCC = idNCC;
    }

    public Integer getIdNV() {
        return idNV;
    }

    public void setIdNV(Integer idNV) {
        this.idNV = idNV;
    }

    public Date getNgayN() {
        return ngayN;
    }

    public void setNgayN(Date ngayN) {
        this.ngayN = ngayN;
    }

    public Double getTongD() {
        return tongD;
    }

    public void setTongD(Double tongD) {
        this.tongD = tongD;
    }

    public String getTrangT() {
        return trangT;
    }

    public void setTrangT(String trangT) {
        this.trangT = trangT;
    }
    
    
    
    
}
