/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

/**
 *
 * @author dell
 */
public class HistoryFoodInPaymentDTO {
    private String paymentID;
    private String name;
    private int id;
    private int price;
    private int quantity;

    public HistoryFoodInPaymentDTO() {
    }

    public HistoryFoodInPaymentDTO(String paymentID, String name, int id, int price, int quantity) {
        this.paymentID = paymentID;
        this.name = name;
        this.id = id;
        this.price = price;
        this.quantity = quantity;
    }
    
    

    public String getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    
    
}
