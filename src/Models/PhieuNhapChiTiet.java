/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author X1
 */
public class PhieuNhapChiTiet {
    private Integer maP, maSPCT, soL, thue;
    private Double donG;
    private String phuongThuc, moTa;

    public PhieuNhapChiTiet() {
    }

    public PhieuNhapChiTiet(Integer thue) {
        this.thue = thue;
    }

    public PhieuNhapChiTiet(Integer maP, Integer maSPCT, Integer soL, Double donG) {
        this.maP = maP;
        this.maSPCT = maSPCT;
        this.soL = soL;
        this.donG = donG;
    }

    public PhieuNhapChiTiet(Integer maP, Integer maSPCT, Integer soL, Integer thue, Double donG, String phuongThuc, String moTa) {
        this.maP = maP;
        this.maSPCT = maSPCT;
        this.soL = soL;
        this.thue = thue;
        this.donG = donG;
        this.phuongThuc = phuongThuc;
        this.moTa = moTa;
    }

    public Integer getMaP() {
        return maP;
    }

    public void setMaP(Integer maP) {
        this.maP = maP;
    }

    public Integer getMaSPCT() {
        return maSPCT;
    }

    public void setMaSPCT(Integer maSPCT) {
        this.maSPCT = maSPCT;
    }

    public Integer getSoL() {
        return soL;
    }

    public void setSoL(Integer soL) {
        this.soL = soL;
    }

    public Integer getThue() {
        return thue;
    }

    public void setThue(Integer thue) {
        this.thue = thue;
    }

    public Double getDonG() {
        return donG;
    }

    public void setDonG(Double donG) {
        this.donG = donG;
    }

    public String getPhuongThuc() {
        return phuongThuc;
    }

    public void setPhuongThuc(String phuongThuc) {
        this.phuongThuc = phuongThuc;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    
}
