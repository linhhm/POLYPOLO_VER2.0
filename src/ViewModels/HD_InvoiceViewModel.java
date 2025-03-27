/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ViewModels;

/**
 *
 * @author X1
 */
public class HD_InvoiceViewModel {
    private Integer idHD;
    private String tenKhachHang, phuongThuc, loaiKH;

    public HD_InvoiceViewModel() {
    }

    public HD_InvoiceViewModel(Integer idHD, String tenKhachHang, String phuongThuc, String loaiKH) {
        this.idHD = idHD;
        this.tenKhachHang = tenKhachHang;
        this.phuongThuc = phuongThuc;
        this.loaiKH = loaiKH;
    }

    public Integer getIdHD() {
        return idHD;
    }

    public void setIdHD(Integer idHD) {
        this.idHD = idHD;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getPhuongThuc() {
        return phuongThuc;
    }

    public void setPhuongThuc(String phuongThuc) {
        this.phuongThuc = phuongThuc;
    }

    public String getLoaiKH() {
        return loaiKH;
    }

    public void setLoaiKH(String loaiKH) {
        this.loaiKH = loaiKH;
    }

   
    
}
