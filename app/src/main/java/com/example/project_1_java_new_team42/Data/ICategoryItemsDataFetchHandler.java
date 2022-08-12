package com.example.project_1_java_new_team42.Data;

import com.example.project_1_java_new_team42.Models.IItem;

import java.util.List;

public interface ICategoryItemsDataFetchHandler{

    void onFetchComplete(List<IItem> itemsList);

    void onFetchFail(); // Still not decided what to pass in can leave blank if want.
}
