/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ViewModels;

/**
 *
 * @author X1
 */
public class HD_SanPhamViewModel {
    private Integer idSP, soL;
    private String tenSP, danhM, nhanH, mauS, kichC, chatL;
    private Double donG;

    public HD_SanPhamViewModel() {
    }

    public HD_SanPhamViewModel(Integer idSP, Integer soL, String tenSP, String danhM, String nhanH, String mauS, String kichC, String chatL, Double donG) {
        this.idSP = idSP;
        this.soL = soL;
        this.tenSP = tenSP;
        this.danhM = danhM;
        this.nhanH = nhanH;
        this.mauS = mauS;
        this.kichC = kichC;
        this.chatL = chatL;
        this.donG = donG;
    }

    public Integer getIdSP() {
        return idSP;
    }

    public void setIdSP(Integer idSP) {
        this.idSP = idSP;
    }

    public Integer getSoL() {
        return soL;
    }

    public void setSoL(Integer soL) {
        this.soL = soL;
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

    public String getNhanH() {
        return nhanH;
    }

    public void setNhanH(String nhanH) {
        this.nhanH = nhanH;
    }

    public String getMauS() {
        return mauS;
    }

    public void setMauS(String mauS) {
        this.mauS = mauS;
    }

    public String getKichC() {
        return kichC;
    }

    public void setKichC(String kichC) {
        this.kichC = kichC;
    }

    public String getChatL() {
        return chatL;
    }

    public void setChatL(String chatL) {
        this.chatL = chatL;
    }

    public Double getDonG() {
        return donG;
    }

    public void setDonG(Double donG) {
        this.donG = donG;
    }
    
    
}
