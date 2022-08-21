package com.example.project_1_java_new_team42.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_1_java_new_team42.Adapters.CartRecyclerViewAdapter;
import com.example.project_1_java_new_team42.Data.Fetchers.CartDataFetcher;
import com.example.project_1_java_new_team42.Data.Fetchers.IFetchHandler;
import com.example.project_1_java_new_team42.Models.Cart;
import com.example.project_1_java_new_team42.Models.IItem;
import com.example.project_1_java_new_team42.Models.ItemWithQuantity;
import com.example.project_1_java_new_team42.R;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private static final String TAG = "CartActivity";

    protected RecyclerView cartRecyclerView;
    protected CartRecyclerViewAdapter cartItemsAdapter;
    protected CircularProgressIndicator cartItemsSpinner;
    protected CartDataFetcher cartDataFetcher = new CartDataFetcher();

//    protected OnTextChangeWatcher textChangeListener = new OnTextChangeWatcher();

    TextView totalPriceTextView;

    private class CartFetchHandler implements IFetchHandler<Cart> {
        @Override
        public void onFetchComplete(Cart data) {
            cartItemsAdapter.setData(data);
            cartItemsAdapter.notifyItemRangeInserted(0, data.getItems().size());

            cartItemsSpinner.setVisibility(View.GONE);

            Log.i(TAG, "Fetched cart items successfully");

            totalPriceTextView = findViewById(R.id.cart_total_price);
            String totalPrice = "$" + data.getTotalPrice();
            totalPriceTextView.setText(totalPrice);
            System.out.println(totalPrice);
        }

        @Override
        public void onFetchFail() {
            System.out.println("Failed to fetch cart items");
            Toast.makeText(getApplicationContext(), "Failed to fetch cart items", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Initialize the recycler view which will be called when the activity is created in `onCreate`.
     * This has to be done in the main UI thread otherwise will get warning that no adapter is
     * attached to the recycler view.
     */
    protected void initializeCartRecyclerView() {
        cartRecyclerView = findViewById(R.id.recycler_view_cart);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        cartItemsAdapter = new CartRecyclerViewAdapter(this);
        cartRecyclerView.setAdapter(cartItemsAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartItemsSpinner = findViewById(R.id.progress_cart_items);

        initializeCartRecyclerView();
        cartDataFetcher.readData(new CartFetchHandler());
    }

//    private class OnTextChangeWatcher implements TextWatcher {
//        @Override
//        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
//        @Override
//        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
//        @Override
//        public void afterTextChanged (Editable editable) {
////            int currentQuantity = Integer.parseInt(quantity.getText().toString());
//
//            // Update total price
////            String totalPrice = "$" +
//        }
//    }
}
