package com.example.project_1_java_new_team42.Data.Fetchers;

import com.example.project_1_java_new_team42.Models.Order;

import java.util.List;

public interface IPastOrderItemsDataFetcher {
    void readData(String orderId, IFetchHandler<List<Order>> dataFetchHandler);
}
