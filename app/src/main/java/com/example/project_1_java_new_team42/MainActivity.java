package com.example.project_1_java_new_team42;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project_1_java_new_team42.Data.CartDataFetcher;
import com.example.project_1_java_new_team42.Data.CartDataSender;
import com.example.project_1_java_new_team42.Data.IFetchHandler;
import com.example.project_1_java_new_team42.Data.ISendHandler;
import com.example.project_1_java_new_team42.Data.OrderDataSender;
import com.example.project_1_java_new_team42.Models.Cart;
import com.example.project_1_java_new_team42.Models.IItem;
import com.example.project_1_java_new_team42.Models.ItemWithQuantity;
import com.example.project_1_java_new_team42.Models.Laptop;
import com.example.project_1_java_new_team42.Models.Order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    
   OrderDataSender orderDataSender = new OrderDataSender();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String id = "laptop_1";
        String name = "Apple MacBook Air 2020";
        List<String> imagePaths = new ArrayList<>(Arrays.asList("laptop_1_1","laptop_1_2","laptop_1_3","laptop_1_4"));
        String category = "Laptop";
        int price = 1299;
        int totalSold = 0;
        String description = "Screen Size:  13.3 Inches\nColor:  Space Gray\nHard Disk Size:  256 GB\nRam:  8 GB\n\nApple-designed M1 chip for a giant leap in CPU, GPU, and machine learning performance\nCharge less with up to 29 hours of battery life\n13.3-inch Retina display with P3 wide color\n8-core CPU delivers up to 3.5x faster performance to tackle projects faster than ever before\nUp to eight GPU cores with up to 5x faster graphics - FaceTime HD camera for clearer, sharper video calls\n16-core Neural Engine for advanced machine learning - 8GB of unified memory so everything you do is fast and fluid";

        IItem item = new Laptop(id, name, imagePaths, category, price, totalSold, description);

        ItemWithQuantity itemWithQuantity = new ItemWithQuantity(item, 3);

        List<ItemWithQuantity> orderItems = new ArrayList<>();

        orderItems.add(itemWithQuantity);

        Order order = new Order(orderItems);

        orderDataSender.writeCartOrderToFirestore(order, new ISendHandler() {
            @Override
            public void onSendSuccess(boolean isSuccess) {
                System.out.println("--------------------------------------");
                System.out.println("success!");
            }
        });

    }
}
