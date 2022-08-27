package com.example.project_1_java_new_team42.Models;

import java.util.List;

public interface ICart {
    List<ItemWithQuantity> getItems();

    int getTotalPrice();

    void addItemToCart(ItemWithQuantity item);

    void removeItemFromCart(String id);
}
