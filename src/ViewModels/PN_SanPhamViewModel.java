/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ViewModels;

/**
 *
 * @author X1
 */
public class PN_SanPhamViewModel {
    private Integer maSPCT, soL;
    private String tenSP;

    public PN_SanPhamViewModel() {
    }

    public PN_SanPhamViewModel(String tenSP) {
        this.tenSP = tenSP;
    }

    public PN_SanPhamViewModel(Integer maSPCT, Integer soL, String tenSP) {
        this.maSPCT = maSPCT;
        this.soL = soL;
        this.tenSP = tenSP;
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

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }
    
    
}
