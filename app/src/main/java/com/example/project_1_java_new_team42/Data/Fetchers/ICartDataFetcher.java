package com.example.project_1_java_new_team42.Data.Fetchers;

import com.example.project_1_java_new_team42.Models.Cart;

public interface ICartDataFetcher {

    void readData(IFetchHandler<Cart> dataFetchHandler);
}
