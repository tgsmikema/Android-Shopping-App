package com.example.project_1_java_new_team42.Models;

public class ItemWithQuantity {

    private IItem item;
    private int quantity;

    public ItemWithQuantity(IItem item, int quantity){
        this.item = item;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public IItem getItem(){
        return item;
    }
}
