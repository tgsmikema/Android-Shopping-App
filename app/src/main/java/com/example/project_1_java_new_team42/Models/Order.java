package com.example.project_1_java_new_team42.Models;

import java.time.LocalDateTime;
import java.util.List;

public class Order {

    private int orderId;
    private LocalDateTime placedDateAndTime;
    private int totalCost;
    private List<ItemWithQuantity> items;

    //empty constructor
    public Order(){

    }

    public int getOrderId() {
        return orderId;
    }

    public LocalDateTime getPlacedDateAndTime() {
        return placedDateAndTime;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public List<ItemWithQuantity> getOrderItems(){
        return items;
    }

    public void placeOrder(){
        throw new UnsupportedOperationException("Not yet Implemented....");
    }

}
