package com.example.project_1_java_new_team42.data.Fetchers;

import com.example.project_1_java_new_team42.models.Cart;

public interface ICartDataFetcher {

    void readData(IFetchHandler<Cart> dataFetchHandler);
}
