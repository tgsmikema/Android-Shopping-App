package com.example.project_1_java_new_team42.Data.Fetchers;

import com.example.project_1_java_new_team42.Models.IItem;

import java.util.List;

public interface ICategoryItemsDataFetcher {
    void readData(String category, IFetchHandler<List<IItem>> dataFetchHandler);
}
