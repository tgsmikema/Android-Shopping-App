package com.example.project_1_java_new_team42.Data.Fetchers;

import com.example.project_1_java_new_team42.Models.Category;

import java.util.List;

public interface ICategoryDataFetcher {

    void readData(IFetchHandler<List<Category>> iFetchHandler);

}
