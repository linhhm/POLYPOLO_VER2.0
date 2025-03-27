/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import Models.Linh_HDThongKe;
import Repositories.HoaDonRepository;
import Repositories.ThongKeRespository;
import ViewModels.HD_HoaDonViewModel;
import ViewModels.LINH_ThongKeView;
import ViewModels.ThongKeViewDoanhThu;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author hmail
 */
public class ThongKeService {
    ThongKeRespository tkrp = new ThongKeRespository();
    HoaDonRepository hdrp = new HoaDonRepository();

    //LOAD CBO
    public ArrayList<Integer> showYear() {
        return tkrp.showYear();
    }

    public ArrayList<Integer> showMonth(Integer Year) {
        return tkrp.showMonth(Year);
    }

    public ArrayList<Linh_HDThongKe> getListHD() {
        return tkrp.getList();
    }
    public HD_HoaDonViewModel tongNgay() {
        return tkrp.tongDoanhThuNgay();
    }

    public HD_HoaDonViewModel tongThang() {
        return tkrp.tongDoanhThuThang();
    }

    public HD_HoaDonViewModel tongNam() {
        return tkrp.tongDoanhThuNam();
    }

    public HD_HoaDonViewModel TT() {
        return tkrp.tongDaTT();
    }

    public HD_HoaDonViewModel Treo() {
        return tkrp.tongHDTreo();
    }
    public HD_HoaDonViewModel TienThanh() {
        return tkrp.tongTienThanh();
    }
    public LINH_ThongKeView SPBan() {
        return tkrp.tongSPBan();
    }

    public ArrayList<HD_HoaDonViewModel> Tim(Integer ma) {
        return tkrp.getListHoaDonView(ma);
    }

    public ArrayList<HD_HoaDonViewModel> TheoNgay(Date bd, Date kt) {
        return tkrp.getListHoaDonVieww(bd, kt);
    }
    public ArrayList<HD_HoaDonViewModel> Tim(String ngay) {
        return tkrp.Tim(ngay);
    }

    public HD_HoaDonViewModel DonHang() {
        return tkrp.TonDonHang();
    }
    
    
    public ArrayList<ThongKeViewDoanhThu> getListByNam(Integer nam){
        return tkrp.getListByNam(nam);
    }
    public ArrayList<ThongKeViewDoanhThu> getListDoanhThu(){
        return tkrp.getListBangDoanhThu();
    }
    public ArrayList<ThongKeViewDoanhThu> getListThongKeTheoThang(Integer thang, Integer nam){
        return tkrp.getListThongKeTheoThang(thang, nam);
    }
    public ArrayList<ThongKeViewDoanhThu> getListDoanhThuTheoNam(Integer nam){
        return tkrp.getListBangDoanhThuTheoNam(nam);
    }
}
