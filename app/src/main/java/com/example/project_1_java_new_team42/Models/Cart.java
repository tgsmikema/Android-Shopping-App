package com.example.project_1_java_new_team42.Models;

import java.util.List;

public class Cart {
    private List<ItemWithQuantity> items;

    public Cart(){

    }

    public List<ItemWithQuantity> getCartItems(){
        return items;
    }

    public void addItemToCart(ItemWithQuantity item){
        items.add(item);
    }

    public void removeItemFromCart(String id){
        for (ItemWithQuantity itemWithQuantity : items){
            // if supplied id equals to the current itemWithQuantity Object, then delete
            // the current object from the cart.

            // maybe update cart view after deletion??
            if (id.equals(itemWithQuantity.getItem().getId())){
                items.remove(itemWithQuantity);
            }
            //else print not found error
        }
    }

}
