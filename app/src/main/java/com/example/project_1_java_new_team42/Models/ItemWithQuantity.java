package com.example.project_1_java_new_team42.Models;

import java.util.List;

public class ItemWithQuantity extends Item{

    private int quantity;

    public ItemWithQuantity(){

    }

    public ItemWithQuantity(String id, String name, List<String> imagePaths,
                            String category, int price, int totalSold, String description, int quantity){
        this.id = id;
        this.name = name;
        this.imagePaths = imagePaths;
        this.category = category;
        this.price = price;
        this.totalSold = totalSold;
        this.description = description;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

}
