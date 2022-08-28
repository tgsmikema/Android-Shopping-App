package com.example.project_1_java_new_team42.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_1_java_new_team42.adapters.CartRecyclerViewAdapter;
import com.example.project_1_java_new_team42.adapters.NavigationAdapter;
import com.example.project_1_java_new_team42.data.Fetchers.CartDataFetcher;
import com.example.project_1_java_new_team42.data.Fetchers.ICartDataFetcher;
import com.example.project_1_java_new_team42.data.Fetchers.IFetchHandler;
import com.example.project_1_java_new_team42.data.Senders.IOrderDataSender;
import com.example.project_1_java_new_team42.data.Senders.ISendHandler;
import com.example.project_1_java_new_team42.data.Senders.OrderDataSender;
import com.example.project_1_java_new_team42.models.Cart;
import com.example.project_1_java_new_team42.models.Order;
import com.example.project_1_java_new_team42.R;
import com.example.project_1_java_new_team42.util.ItemUtil;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.progressindicator.CircularProgressIndicator;

public class CartActivity extends AppCompatActivity {
    private static final String TAG = "CartActivity";

    protected ICartDataFetcher cartDataFetcher = new CartDataFetcher();
    protected IOrderDataSender orderDataSender = new OrderDataSender();

    private Cart cartData;

    private CartRecyclerViewAdapter cartItemsAdapter;

    private ViewHolder viewHolder;

    private class ViewHolder {
        TextView totalPriceTextView;
        ImageView emptyCartImage;
        Button placeOrderButton;
        CircularProgressIndicator cartItemsSpinner;
        NavigationBarView bottomNavBar;
        RecyclerView cartRecyclerView;

        public ViewHolder() {
            totalPriceTextView = findViewById(R.id.cart_total_price);
            emptyCartImage = findViewById(R.id.cart_empty_image);
            placeOrderButton = findViewById(R.id.place_order_button);
            cartItemsSpinner = findViewById(R.id.progress_cart_items);

            initializePlaceOrderButton();
            initializeCartRecyclerView();
            initializeBottomBarViews();
        }

        private void initializeCartRecyclerView() {
            cartRecyclerView = findViewById(R.id.recycler_view_cart);
            cartRecyclerView.setLayoutManager(new LinearLayoutManager(CartActivity.this, LinearLayoutManager.VERTICAL, false));
            cartItemsAdapter = new CartRecyclerViewAdapter(CartActivity.this);
            cartRecyclerView.setAdapter(cartItemsAdapter);
        }

        private void initializePlaceOrderButton() {
            placeOrderButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Order newOrder = new Order(cartData.getItems());
                    orderDataSender.writeCartOrderToFirestore(newOrder, new OrderDataSendHandler());
                }
            });
        }

        private void initializeBottomBarViews() {
            bottomNavBar = findViewById(R.id.bottom_navigation_cart);
            bottomNavBar.setSelectedItemId(R.id.activity_cart);
            bottomNavBar.setOnItemSelectedListener(new NavigationAdapter(CartActivity.this).navigationListener);
        }
    }

    private class CartFetchHandler implements IFetchHandler<Cart> {
        @Override
        public void onFetchComplete(Cart data) {
            cartData = data;

            cartItemsAdapter.addItems(data.getItems());
            cartItemsAdapter.setItemsTotalPrice(data.getTotalPrice());

            viewHolder.cartItemsSpinner.setVisibility(View.GONE);

            Log.i(TAG, "Fetched cart items successfully");

            String totalPrice = ItemUtil.addDollarSignToPrice(data.getTotalPrice());
            viewHolder.totalPriceTextView.setText(totalPrice);

            if (data.getItems().isEmpty()){
                disableSubmitButton();
                viewHolder.emptyCartImage.setVisibility(View.VISIBLE);
            }

            viewHolder.cartRecyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
                @Override
                public void onChildViewAttachedToWindow(@NonNull View view) {
                    enableSubmitButton();
                }

                @Override
                public void onChildViewDetachedFromWindow(@NonNull View view) {
                    if (cartItemsAdapter.getItemCount() <= 0){
                        disableSubmitButton();
                        viewHolder.emptyCartImage.setVisibility(View.VISIBLE);
                    }
                }
            });
        }

        @Override
        public void onFetchFail() {
            System.out.println("Failed to fetch cart items");
            Toast.makeText(getApplicationContext(), "Failed to fetch cart items", Toast.LENGTH_SHORT).show();
        }
    }


    private void disableSubmitButton() {
        viewHolder.placeOrderButton.setText(R.string.text_cart_empty);
        viewHolder.placeOrderButton.setBackgroundColor(getResources().getColor(R.color.button_unavailable));
        viewHolder.placeOrderButton.setTextColor(Color.BLACK);
        viewHolder.placeOrderButton.setEnabled(false);
    }

    private void enableSubmitButton(){
        viewHolder.placeOrderButton.setText(R.string.text_cart_place_order);
        viewHolder.placeOrderButton.setBackgroundColor(getResources().getColor(R.color.brand_black));
        viewHolder.placeOrderButton.setTextColor(Color.WHITE);
        viewHolder.placeOrderButton.setEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        viewHolder = new ViewHolder();
        cartDataFetcher.readData(new CartFetchHandler());
    }

    public void updateTotalPrice(int totalPrice) {
        String price = ItemUtil.addDollarSignToPrice(totalPrice);
        viewHolder.totalPriceTextView.setText(price);
    }

    private class OrderDataSendHandler implements ISendHandler {
        @Override
        public void onSendSuccess(boolean isSuccess) {
            Intent intent = new Intent(CartActivity.this, SuccessfulOrderActivity.class);
            startActivity(intent);
        }
    }
}
