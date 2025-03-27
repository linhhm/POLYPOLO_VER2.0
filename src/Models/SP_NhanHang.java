/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author X1
 */
public class SP_NhanHang {
    private Integer idBrand;
    private String brandName;

    public SP_NhanHang() {
    }

    public SP_NhanHang(Integer idBrand, String brandName) {
        this.idBrand = idBrand;
        this.brandName = brandName;
    }

    public Integer getIdBrand() {
        return idBrand;
    }

    public void setIdBrand(Integer idBrand) {
        this.idBrand = idBrand;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
    
    
}
