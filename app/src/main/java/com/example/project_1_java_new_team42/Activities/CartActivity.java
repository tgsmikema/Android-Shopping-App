package com.example.project_1_java_new_team42.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_1_java_new_team42.Adapters.CartRecyclerViewAdapter;
import com.example.project_1_java_new_team42.Data.Fetchers.CartDataFetcher;
import com.example.project_1_java_new_team42.Data.Fetchers.IFetchHandler;
import com.example.project_1_java_new_team42.Models.Cart;
import com.example.project_1_java_new_team42.Models.IItem;
import com.example.project_1_java_new_team42.R;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.List;

public class CartActivity extends AppCompatActivity {
    private static final String TAG = "CartActivity";

    protected RecyclerView cartRecyclerView;
    protected CartRecyclerViewAdapter cartItemsAdapter;
    protected CircularProgressIndicator cartItemsSpinner;
    protected CartDataFetcher cartDataFetcher = new CartDataFetcher();

    Button increment, decrement;
    TextView quantity;

    private class CartFetchHandler implements IFetchHandler<Cart> {
        @Override
        public void onFetchComplete(Cart data) {
//            cartItemsAdapter.setData(data);
//            cartItemsAdapter.notifyItemRangeInserted(0, data.size());

            cartItemsSpinner.setVisibility(View.GONE);

            Log.i(TAG, "Fetched items successfully");
        }

        @Override
        public void onFetchFail() {
            System.out.println("Failed to fetch items");
            Toast.makeText(getApplicationContext(), "Failed to fetch categories", Toast.LENGTH_SHORT).show();
        }
    }

    protected void initializeCartRecyclerView() {
        cartRecyclerView = findViewById(R.id.recycler_view_cart);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

//        cartAdapter = new ItemsRecyclerViewAdapter(this);

//        cartRecyclerView.setAdapter(cartAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartItemsSpinner = findViewById(R.id.progress_cart_items);

        initializeCartRecyclerView();

        cartDataFetcher.readData(new CartFetchHandler());

        increment = findViewById(R.id.button_plus);
        decrement = findViewById(R.id.button_minus);
        quantity = findViewById(R.id.item_quantity);
    }
}
