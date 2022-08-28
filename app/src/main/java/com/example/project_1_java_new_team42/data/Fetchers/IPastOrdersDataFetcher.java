package com.example.project_1_java_new_team42.data.Fetchers;

import com.example.project_1_java_new_team42.models.Order;

import java.util.List;

public interface IPastOrdersDataFetcher {

    void readData(IFetchHandler<List<Order>> dataFetchHandler);
}
