package com.example.project_1_java_new_team42.data.fetchers;

import com.example.project_1_java_new_team42.models.Category;

import java.util.List;

public interface ICategoryDataFetcher {

    void readData(IFetchHandler<List<Category>> iFetchHandler);

}
