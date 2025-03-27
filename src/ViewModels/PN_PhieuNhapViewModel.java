/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ViewModels;

/**
 *
 * @author X1
 */
public class PN_PhieuNhapViewModel {
    private Integer idPhieu, idSPCT, soL, thue;
    private String tenSP, danhMuc;
    private Double giaN, tongT;

    public PN_PhieuNhapViewModel() {
    }

    public PN_PhieuNhapViewModel(Integer idPhieu, Integer idSPCT, Integer soL, Integer thue, String tenSP, String danhMuc, Double giaN, Double tongT) {
        this.idPhieu = idPhieu;
        this.idSPCT = idSPCT;
        this.soL = soL;
        this.thue = thue;
        this.tenSP = tenSP;
        this.danhMuc = danhMuc;
        this.giaN = giaN;
        this.tongT = tongT;
    }

    public Integer getIdPhieu() {
        return idPhieu;
    }

    public void setIdPhieu(Integer idPhieu) {
        this.idPhieu = idPhieu;
    }

    public Integer getIdSPCT() {
        return idSPCT;
    }

    public void setIdSPCT(Integer idSPCT) {
        this.idSPCT = idSPCT;
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

    public Double getGiaN() {
        return giaN;
    }

    public void setGiaN(Double giaN) {
        this.giaN = giaN;
    }

    public Double getTongT() {
        return tongT;
    }

    public void setTongT(Double tongT) {
        this.tongT = tongT;
    }
    
    
}
