package com.example.project_1_java_new_team42;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project_1_java_new_team42.Data.CartDataSender;
import com.example.project_1_java_new_team42.Data.CategoryDataFetcher;
import com.example.project_1_java_new_team42.Data.IFetchHandler;
import com.example.project_1_java_new_team42.Data.IPastOrderItemsDataFetchHandler;
import com.example.project_1_java_new_team42.Data.IPastOrdersDataFetchHandler;
import com.example.project_1_java_new_team42.Data.ISendHandler;
import com.example.project_1_java_new_team42.Data.PastOrderItemsDataFetcher;
import com.example.project_1_java_new_team42.Data.PastOrdersDataFetcher;
import com.example.project_1_java_new_team42.Models.Category;
import com.example.project_1_java_new_team42.Models.IItem;
import com.example.project_1_java_new_team42.Models.ItemWithQuantity;
import com.example.project_1_java_new_team42.Models.Laptop;
import com.example.project_1_java_new_team42.Models.Order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

   CartDataSender cart = new CartDataSender();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<String> imagePaths = new ArrayList<>(Arrays.asList("a.jpg","b.jpg","c.jpg"));
        IItem test1 = new Laptop("laptop_777","HUAWEI",imagePaths,"Laptop",1999,0,"DESCRIPTION DESCRIPTION DESCRIPTION DESCRIPTION DESCRIPTION DESCRIPTION");
        ItemWithQuantity testItem = new ItemWithQuantity(test1, 2);

        cart.addItemWithQuantityToCart(testItem, new ISendHandler() {
            @Override
            public void onSendSuccess(boolean isSuccess) {
                System.out.println("success!!!");
            }
        });


    }
}
