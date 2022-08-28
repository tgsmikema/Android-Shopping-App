package com.example.project_1_java_new_team42.models;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class Order implements IOrder, Serializable {

    private String orderId;
    private String placedDateAndTime;
    private int totalCost;
    private List<ItemWithQuantity> orderItems;

    //empty constructor
    public Order(){

    }

    public Order(List<ItemWithQuantity> items){
        // use current date + time(accurate to milliseconds) concate as 1 string for orderID
        orderId = getCurrentDateTimeConcatenateAsString();
        placedDateAndTime = getCurrentDateTimeAsString();
        int price = 0;
        for (ItemWithQuantity item : items){
            price += (item.getQuantity()) * (item.getPrice());
        }
        this.totalCost = price;
        this.orderItems = items;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getPlacedDateAndTime() {
        return placedDateAndTime;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public List<ItemWithQuantity> getOrderItems(){
        return orderItems;
    }

    public void placeOrder(){
        throw new UnsupportedOperationException("Not yet Implemented....");
    }

    private String getCurrentDateTimeConcatenateAsString(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat("MMddHHmmss");
        String dateStr = s.format(calendar.getTime());

        return dateStr;
    }

    private String getCurrentDateTimeAsString(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat("dd MMMM yyyy");
        String dateStr = s.format(calendar.getTime());

        return dateStr;
    }

    public void updateItemsTotalSoldField(){
        for (ItemWithQuantity itemWithQuantity : orderItems){
            itemWithQuantity.updateTotalSold(itemWithQuantity.getQuantity());
        }
    }

}
