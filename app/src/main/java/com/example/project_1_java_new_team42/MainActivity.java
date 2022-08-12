package com.example.project_1_java_new_team42;

import android.os.Bundle;
import android.widget.TextView;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project_1_java_new_team42.Data.CategoryItemsDataFetcher;
import com.example.project_1_java_new_team42.Data.ICategoryItemsDataFetchHandler;
import com.example.project_1_java_new_team42.Data.ItemsDataProvider;
import com.example.project_1_java_new_team42.Models.IItem;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    CategoryItemsDataFetcher fetch =  new CategoryItemsDataFetcher();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fetch.readData(new ICategoryItemsDataFetchHandler() {
            @Override
            public void onFetchComplete(List<IItem> itemsList) {

            }

            @Override
            public void onFetchFail() {

            }
        }, "Laptop");
    }
}
