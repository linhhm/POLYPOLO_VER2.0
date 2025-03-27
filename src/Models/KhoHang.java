/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author X1
 */
public class KhoHang {
    private Integer maKho;
    private String tenKho, moTa;

    public KhoHang() {
    }

    public KhoHang(Integer maKho, String tenKho, String moTa) {
        this.maKho = maKho;
        this.tenKho = tenKho;
        this.moTa = moTa;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public KhoHang(Integer maKho, String tenKho) {
        this.maKho = maKho;
        this.tenKho = tenKho;
    }

    public Integer getMaKho() {
        return maKho;
    }

    public void setMaKho(Integer maKho) {
        this.maKho = maKho;
    }

    public String getTenKho() {
        return tenKho;
    }

    public void setTenKho(String tenKho) {
        this.tenKho = tenKho;
    }
    
    
}
