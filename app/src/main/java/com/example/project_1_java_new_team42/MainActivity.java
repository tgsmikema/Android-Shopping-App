package com.example.project_1_java_new_team42;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project_1_java_new_team42.Data.IPastOrdersDataFetchHandler;
import com.example.project_1_java_new_team42.Data.PastOrdersDataFetcher;
import com.example.project_1_java_new_team42.Models.Order;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    PastOrdersDataFetcher pastO = new PastOrdersDataFetcher();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pastO.readData(new IPastOrdersDataFetchHandler() {
            @Override
            public void onFetchComplete(List<Order> ordersList) {
                System.out.println("------------------------------------------------");
                System.out.println(ordersList.size());
                System.out.println(ordersList.get(0).getPlacedDateAndTime());
                System.out.println(ordersList.get(0).getOrderItems());
                System.out.println(ordersList.get(0).getOrderItems().get(0));
            }

            @Override
            public void onFetchFail() {

            }
        });
    }
}
