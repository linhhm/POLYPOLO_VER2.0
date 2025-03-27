/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author X1
 */
public class MyPurchase {
    private Integer maNCC;
    private String tenNCC, email, sdt, diaC, phuongT;

    public MyPurchase() {
    }

    public MyPurchase(Integer maNCC, String tenNCC, String email, String sdt, String diaC, String phuongT) {
        this.maNCC = maNCC;
        this.tenNCC = tenNCC;
        this.email = email;
        this.sdt = sdt;
        this.diaC = diaC;
        this.phuongT = phuongT;
    }

    public Integer getMaNCC() {
        return maNCC;
    }

    public void setMaNCC(Integer maNCC) {
        this.maNCC = maNCC;
    }

    public String getTenNCC() {
        return tenNCC;
    }

    public void setTenNCC(String tenNCC) {
        this.tenNCC = tenNCC;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiaC() {
        return diaC;
    }

    public void setDiaC(String diaC) {
        this.diaC = diaC;
    }

    public String getPhuongT() {
        return phuongT;
    }

    public void setPhuongT(String phuongT) {
        this.phuongT = phuongT;
    }
    
    
}
