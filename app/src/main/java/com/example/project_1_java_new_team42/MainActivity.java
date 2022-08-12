package com.example.project_1_java_new_team42;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project_1_java_new_team42.Data.IItemDetailsDataFetchHandler;
import com.example.project_1_java_new_team42.Data.ISearchItemsDataFetchHandler;
import com.example.project_1_java_new_team42.Data.ITopItemsDataFetchHandler;
import com.example.project_1_java_new_team42.Data.ItemDetailsDataFetcher;
import com.example.project_1_java_new_team42.Data.SearchItemsDataFetcher;
import com.example.project_1_java_new_team42.Data.TopItemsDataFetcher;
import com.example.project_1_java_new_team42.Models.IItem;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    TopItemsDataFetcher fetch =  new TopItemsDataFetcher();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fetch.readData(new ITopItemsDataFetchHandler() {
            @Override
            public void onFetchComplete(List<IItem> itemsList) {
                System.out.println("----------------------------------------------------");
                for (IItem anItem : itemsList){
                    System.out.println(anItem.getName() + "sold: " + anItem.getTotalSold());
                }
            }

            @Override
            public void onFetchFail() {

            }
        });
    }
}
