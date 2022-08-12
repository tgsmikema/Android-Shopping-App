package com.example.project_1_java_new_team42;

import android.os.Bundle;
import android.widget.TextView;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project_1_java_new_team42.Data.IFetchHandler;
import com.example.project_1_java_new_team42.Data.ItemsDataFetcher;
import com.example.project_1_java_new_team42.Data.ItemsDataProvider;
import com.example.project_1_java_new_team42.Models.IItem;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ItemsDataFetcher fetcher = new ItemsDataFetcher();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fetcher.readItems(new IFetchHandler() {
            @Override
            public void onFetchComplete(List<IItem> itemsList) {
                //do stuff with itemsList
            }

            @Override
            public void onFetchFail() {

            }
        });
    }
}
