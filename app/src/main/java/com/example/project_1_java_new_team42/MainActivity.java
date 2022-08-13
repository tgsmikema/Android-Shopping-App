package com.example.project_1_java_new_team42;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project_1_java_new_team42.Data.CartDataFetcher;
import com.example.project_1_java_new_team42.Data.IFetchHandler;
import com.example.project_1_java_new_team42.Models.Cart;
import com.example.project_1_java_new_team42.Models.ItemWithQuantity;

public class MainActivity extends AppCompatActivity {

   CartDataFetcher cart = new CartDataFetcher();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        cart.readData(new IFetchHandler<Cart>() {
            @Override
            public void onFetchComplete(Cart data) {
                System.out.println("---------------------------");
                System.out.println("total price: " + data.getTotalPrice());
                for (ItemWithQuantity it : data.getItems()){
                    System.out.println("id: "+it.getId());
                }
            }

            @Override
            public void onFetchFail() {

            }
        });


    }
}
