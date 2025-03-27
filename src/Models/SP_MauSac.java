/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author X1
 */
public class SP_MauSac {
    private Integer idMau;
    private String tenMau;

    public SP_MauSac() {
    }

    public SP_MauSac(Integer idMau, String tenMau) {
        this.idMau = idMau;
        this.tenMau = tenMau;
    }

    public Integer getIdMau() {
        return idMau;
    }

    public void setIdMau(Integer idMau) {
        this.idMau = idMau;
    }

    public String getTenMau() {
        return tenMau;
    }

    public void setTenMau(String tenMau) {
        this.tenMau = tenMau;
    }
    
    
}
