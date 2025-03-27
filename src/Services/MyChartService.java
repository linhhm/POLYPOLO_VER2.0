/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import Repositories.MyChartRepo;
import ViewModels.DonutPieChartViewModel;
import ViewModels.PieChartViewModel;
import java.util.ArrayList;

/**
 *
 * @author X1
 */
public class MyChartService {
    MyChartRepo mRepo = new MyChartRepo();
    
    public ArrayList<Integer> showYear(){
        return mRepo.showYear();
    }
    public ArrayList<Integer> showMonth(int year){
        return mRepo.showMonth(year);
    }
    public ArrayList<PieChartViewModel> getListBestSeller(int y, int m){
        return mRepo.getListBestSeller(y, m);
    }
    public ArrayList<PieChartViewModel> getTopSell(int y, int m){
        return mRepo.getTopSell(y, m);
    }
    public ArrayList<DonutPieChartViewModel> getTopProfit(int y, int m){
        return mRepo.getTopProfit(y, m);
    }
}
