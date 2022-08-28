package com.example.project_1_java_new_team42.data.Senders;

import com.example.project_1_java_new_team42.models.ItemWithQuantity;

public interface ICartDataSender {

    void addItemWithQuantityToCart(ItemWithQuantity anItemWithQuantity, ISendHandler dataSendHandler);

    void deleteSingleCartItem(String id, ISendHandler dataSendHandler);

    void deleteAllCartItems(ISendHandler dataSendHandler);
}
