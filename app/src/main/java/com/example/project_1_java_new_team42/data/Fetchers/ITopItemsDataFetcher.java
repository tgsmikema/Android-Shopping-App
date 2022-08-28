package com.example.project_1_java_new_team42.data.Fetchers;

import com.example.project_1_java_new_team42.models.IItem;

import java.util.List;

public interface ITopItemsDataFetcher {

    void readData(IFetchHandler<List<IItem>> dataFetchHandler);
}
