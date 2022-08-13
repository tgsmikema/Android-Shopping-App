package com.example.project_1_java_new_team42.Models;

import com.google.firebase.firestore.Exclude;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<ItemWithQuantity> items;

    private int totalPrice;

    public Cart(){
        items = new ArrayList<>();
        totalPrice = 0;
    }

    public List<ItemWithQuantity> getItems(){
        return items;
    }

    public int getTotalPrice(){
        return totalPrice;
    }

    public void addItemToCart(ItemWithQuantity item){
        items.add(item);
        int totalPriceOfCurrentItem = (item.getPrice()) * (item.getQuantity());
        totalPrice += totalPriceOfCurrentItem;
    }


    public void removeItemFromCart(String id){
        for (ItemWithQuantity itemWithQuantity : items){
            // if supplied id equals to the current itemWithQuantity Object, then delete
            // the current object from the cart.

            // maybe update cart view after deletion??
            if (id.equals(itemWithQuantity.getId())){
                items.remove(itemWithQuantity);
                int totalPriceOfCurrentItem = (itemWithQuantity.getPrice()) * (itemWithQuantity.getQuantity());
                totalPrice -= totalPriceOfCurrentItem;
            }
            //else print not found error
        }
    }

}
