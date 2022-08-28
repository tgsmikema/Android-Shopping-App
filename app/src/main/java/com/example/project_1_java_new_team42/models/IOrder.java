package com.example.project_1_java_new_team42.models;

import java.util.List;

public interface IOrder {
    String getOrderId();

    String getPlacedDateAndTime();

    int getTotalCost();

    List<ItemWithQuantity> getOrderItems();

    void placeOrder();
}
