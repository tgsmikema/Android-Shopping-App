package com.example.project_1_java_new_team42.Data.Senders;

import com.example.project_1_java_new_team42.Models.ItemWithQuantity;

public interface ICartDataSender {

    void addItemWithQuantityToCart(ItemWithQuantity anItemWithQuantity, ISendHandler dataSendHandler);

    void deleteSingleCartItem(String id, ISendHandler dataSendHandler);

    void deleteAllCartItems(ISendHandler dataSendHandler);
}
