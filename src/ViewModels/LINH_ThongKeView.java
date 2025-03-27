/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ViewModels;

/**
 *
 * @author X1
 */
public class LINH_ThongKeView {
    private Integer idSPCT, soL, thue, idHD;
    private Double donG, thanhT, tongSauVAT;
    private String tenSP, danhM;

    public LINH_ThongKeView() {
    }

    public Integer getIdHD() {
        return idHD;
    }

    public void setIdHD(Integer idHD) {
        this.idHD = idHD;
    }

    public LINH_ThongKeView(int soL) {
        this.soL = soL;
    }
    
    public LINH_ThongKeView(Integer idSPCT, Integer soL, Integer thue, Integer idHD, Double donG, Double thanhT, Double tongSauVAT, String tenSP, String danhM) {
        this.idSPCT = idSPCT;
        this.soL = soL;
        this.thue = thue;
        this.idHD = idHD;
        this.donG = donG;
        this.thanhT = thanhT;
        this.tongSauVAT = tongSauVAT;
        this.tenSP = tenSP;
        this.danhM = danhM;
    }

    public LINH_ThongKeView(Integer idSPCT, Integer soL, Integer thue, Double donG, Double thanhT, Double tongSauVAT, String tenSP, String danhM) {
        this.idSPCT = idSPCT;
        this.soL = soL;
        this.thue = thue;
        this.donG = donG;
        this.thanhT = thanhT;
        this.tongSauVAT = tongSauVAT;
        this.tenSP = tenSP;
        this.danhM = danhM;
    }

    public LINH_ThongKeView(Integer idSPCT, Integer soL, Integer thue, Double donG, Double thanhT, String tenSP) {
        this.idSPCT = idSPCT;
        this.soL = soL;
        this.thue = thue;
        this.donG = donG;
        this.thanhT = thanhT;
        this.tenSP = tenSP;
    }

    public Double getTongSauVAT() {
        return soL * thanhT;
    }

    public void setTongSauVAT(Double tongSauVAT) {
        this.tongSauVAT = tongSauVAT;
    }

    public LINH_ThongKeView(Integer idSPCT, Integer soL, Integer thue, Double donG, Double thanhT, String tenSP, String danhM) {
        this.idSPCT = idSPCT;
        this.soL = soL;
        this.thue = thue;
        this.donG = donG;
        this.thanhT = thanhT;
        this.tenSP = tenSP;
        this.danhM = danhM;
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

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getDanhM() {
        return danhM;
    }

    public void setDanhM(String danhM) {
        this.danhM = danhM;
    }

    
}
