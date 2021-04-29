/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dell
 */
public class HistoryPaymentDTO {
    private String id;
    private int totalPrice;
    private Date paymentDate;
    private List<HistoryFoodInPaymentDTO> listHistoryFood;

    public HistoryPaymentDTO(String id, int totalPrice, Date paymentDate, List<HistoryFoodInPaymentDTO> listHistoryFood) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.paymentDate = paymentDate;
        this.listHistoryFood = listHistoryFood;
    }

    public List<HistoryFoodInPaymentDTO> getListHistoryFood() {
        return listHistoryFood;
    }

    public void setListHistoryFood(List<HistoryFoodInPaymentDTO> listHistoryFood) {
        this.listHistoryFood = listHistoryFood;
    }

    

    public HistoryPaymentDTO() {
        listHistoryFood = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }
    
    
    
    
    
}
