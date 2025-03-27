/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author X1
 */
public class SP_DanhMuc {
    private Integer idDM;
    private String tenDM, trangThai;

    public SP_DanhMuc() {
    }

    public SP_DanhMuc(Integer idDM, String tenDM, String trangThai) {
        this.idDM = idDM;
        this.tenDM = tenDM;
        this.trangThai = trangThai;
    }

    public Integer getIdDM() {
        return idDM;
    }

    public void setIdDM(Integer idDM) {
        this.idDM = idDM;
    }

    public String getTenDM() {
        return tenDM;
    }

    public void setTenDM(String tenDM) {
        this.tenDM = tenDM;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
    
    
    
}
