/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author X1
 */
public class SP_ChatLieu {
    private Integer idChatL;
    private String tenChatL;

    public SP_ChatLieu() {
    }

    public SP_ChatLieu(Integer idChatL, String tenChatL) {
        this.idChatL = idChatL;
        this.tenChatL = tenChatL;
    }

    public Integer getIdChatL() {
        return idChatL;
    }

    public void setIdChatL(Integer idChatL) {
        this.idChatL = idChatL;
    }

    public String getTenChatL() {
        return tenChatL;
    }

    public void setTenChatL(String tenChatL) {
        this.tenChatL = tenChatL;
    }
    
    
}
