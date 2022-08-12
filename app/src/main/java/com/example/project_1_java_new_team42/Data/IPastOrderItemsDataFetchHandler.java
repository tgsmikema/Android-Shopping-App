package com.example.project_1_java_new_team42.Data;

import com.example.project_1_java_new_team42.Models.Order;

import java.util.List;

public interface IPastOrderItemsDataFetchHandler {

    void onFetchComplete(List<Order> ordersList);

    void onFetchFail(); // Still not decided what to pass in can leave blank if want.

}
