/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ViewModels;

/**
 *
 * @author X1
 */
public class PN_PhieuNhapDetailsViewModel {
    private Integer id, soL, thue;
    private String tenSP, tenDM, tenMau, tenSz, tenBrand, tenCL;
    private Double donG, thanhT;

    public PN_PhieuNhapDetailsViewModel() {
    }

    public PN_PhieuNhapDetailsViewModel(Integer id, Integer soL, Integer thue, String tenSP, String tenDM, String tenMau, String tenSz, String tenBrand, String tenCL, Double donG, Double thanhT) {
        this.id = id;
        this.soL = soL;
        this.thue = thue;
        this.tenSP = tenSP;
        this.tenDM = tenDM;
        this.tenMau = tenMau;
        this.tenSz = tenSz;
        this.tenBrand = tenBrand;
        this.tenCL = tenCL;
        this.donG = donG;
        this.thanhT = thanhT;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getTenDM() {
        return tenDM;
    }

    public void setTenDM(String tenDM) {
        this.tenDM = tenDM;
    }

    public String getTenMau() {
        return tenMau;
    }

    public void setTenMau(String tenMau) {
        this.tenMau = tenMau;
    }

    public String getTenSz() {
        return tenSz;
    }

    public void setTenSz(String tenSz) {
        this.tenSz = tenSz;
    }

    public String getTenBrand() {
        return tenBrand;
    }

    public void setTenBrand(String tenBrand) {
        this.tenBrand = tenBrand;
    }

    public String getTenCL() {
        return tenCL;
    }

    public void setTenCL(String tenCL) {
        this.tenCL = tenCL;
    }

    public Double getDonG() {
        return donG;
    }

    public void setDonG(Double donG) {
        this.donG = donG;
    }

    public Double getThanhT() {
        return thanhT;
    }

    public void setThanhT(Double thanhT) {
        this.thanhT = thanhT;
    }
    
    
}
