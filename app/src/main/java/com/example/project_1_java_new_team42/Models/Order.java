package com.example.project_1_java_new_team42.Models;

import java.util.List;

public class Order {

    private List<ItemWithQuantity> items;

    public Order(){

    }

    public List<ItemWithQuantity> getOrderItems(){
        return items;
    }

    public void placeOrder(){
        throw new UnsupportedOperationException("Not yet Implemented....");
    }

}
