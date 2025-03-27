/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ViewModels;

/**
 *
 * @author X1
 */
public class DonutPieChartViewModel {
    private String tenSP;
    private Double loiN;

    public DonutPieChartViewModel() {
    }

    public DonutPieChartViewModel(String tenSP, Double loiN) {
        this.tenSP = tenSP;
        this.loiN = loiN;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public Double getLoiN() {
        return loiN;
    }

    public void setLoiN(Double loiN) {
        this.loiN = loiN;
    }
    
    
}
