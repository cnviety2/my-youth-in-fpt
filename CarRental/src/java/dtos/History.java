/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.sql.Date;
import java.util.List;

/**
 *
 * @author dell
 */
public class History {

    private String id;
    private int totalPrice;
    private Date orderDate;
    private boolean status;
    private String userEmail;
    private List<CarInHistoryDTO> carInHistory;

    public List<CarInHistoryDTO> getCarInHistory() {
        return carInHistory;
    }

    public void setCarInHistory(List<CarInHistoryDTO> carInHistory) {
        this.carInHistory = carInHistory;
    }

    public History() {
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

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

}
