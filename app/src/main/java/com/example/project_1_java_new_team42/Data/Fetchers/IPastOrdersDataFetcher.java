package com.example.project_1_java_new_team42.Data.Fetchers;

import com.example.project_1_java_new_team42.Models.Order;

import java.util.List;

public interface IPastOrdersDataFetcher {

    void readData(IFetchHandler<List<Order>> dataFetchHandler);
}
