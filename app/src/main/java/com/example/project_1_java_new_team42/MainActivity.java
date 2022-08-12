package com.example.project_1_java_new_team42;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project_1_java_new_team42.Data.IItemDetailsDataFetchHandler;
import com.example.project_1_java_new_team42.Data.IOrderDataSendHandler;
import com.example.project_1_java_new_team42.Data.ISearchItemsDataFetchHandler;
import com.example.project_1_java_new_team42.Data.ITopItemsDataFetchHandler;
import com.example.project_1_java_new_team42.Data.ItemDetailsDataFetcher;
import com.example.project_1_java_new_team42.Data.OrderDataSender;
import com.example.project_1_java_new_team42.Data.SearchItemsDataFetcher;
import com.example.project_1_java_new_team42.Data.TopItemsDataFetcher;
import com.example.project_1_java_new_team42.Models.IItem;
import com.example.project_1_java_new_team42.Models.ItemWithQuantity;
import com.example.project_1_java_new_team42.Models.Laptop;
import com.example.project_1_java_new_team42.Models.Order;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    OrderDataSender order = new OrderDataSender();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<String> imagePaths = new ArrayList<>();
        imagePaths.add("image_1");
        imagePaths.add("image_2");

        IItem tItem = new Laptop("test_1","the laptop",imagePaths,"Laptop",1200,0,"This is a great Laptop!!!");
        IItem tItem2 = new Laptop("test_2","the laptop 2",imagePaths,"Laptop",200,0,"This is a great Laptop!!!");

        ItemWithQuantity item = new ItemWithQuantity(tItem,2);
        ItemWithQuantity item2 = new ItemWithQuantity(tItem2,3);

        List<ItemWithQuantity> items = new ArrayList<>();
        items.add(item);
        items.add(item2);

        Order anOrder = new Order(items);

        order.writeCartOrderToFirestore(anOrder, new IOrderDataSendHandler() {
            @Override
            public void onSendSuccess(boolean isSuccess) {
                System.out.println("--------------------------------------");
                System.out.println("Order CREATED SUCCESSFULLY");
                System.out.println(anOrder.getOrderItems().get(0).getItem().getName());
            }
        });

    }
}
