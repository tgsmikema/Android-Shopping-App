package com.example.project_1_java_new_team42;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project_1_java_new_team42.Data.CategoryDataFetcher;
import com.example.project_1_java_new_team42.Data.IFetchHandler;
import com.example.project_1_java_new_team42.Data.IPastOrderItemsDataFetchHandler;
import com.example.project_1_java_new_team42.Data.IPastOrdersDataFetchHandler;
import com.example.project_1_java_new_team42.Data.PastOrderItemsDataFetcher;
import com.example.project_1_java_new_team42.Data.PastOrdersDataFetcher;
import com.example.project_1_java_new_team42.Models.Category;
import com.example.project_1_java_new_team42.Models.Order;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    CategoryDataFetcher cate = new CategoryDataFetcher();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cate.readData(new IFetchHandler<List<Category>>() {
            @Override
            public void onFetchComplete(List<Category> data) {
                for (Category ca : data){
                    System.out.println(ca.getCategoryName() + "          " + ca.getImageURI());
                }
            }

            @Override
            public void onFetchFail() {

            }
        });
    }
}
