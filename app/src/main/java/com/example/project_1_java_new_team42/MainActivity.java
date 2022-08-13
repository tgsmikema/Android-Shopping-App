package com.example.project_1_java_new_team42;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project_1_java_new_team42.Data.CartDataFetcher;
import com.example.project_1_java_new_team42.Data.CartDataSender;
import com.example.project_1_java_new_team42.Data.IFetchHandler;
import com.example.project_1_java_new_team42.Data.ISendHandler;
import com.example.project_1_java_new_team42.Models.Cart;
import com.example.project_1_java_new_team42.Models.ItemWithQuantity;

public class MainActivity extends AppCompatActivity {
    
   CartDataSender cartSender = new CartDataSender();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cartSender.deleteAllCartItems(new ISendHandler() {
            @Override
            public void onSendSuccess(boolean isSuccess) {
                System.out.println("-------------------------------------------------");
                System.out.println(isSuccess);
            }
        });


    }
}
