package com.example.project_1_java_new_team42.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.project_1_java_new_team42.Adapters.CartRecyclerViewAdapter;
import com.example.project_1_java_new_team42.Adapters.NavigationAdapter;
import com.example.project_1_java_new_team42.Data.Fetchers.CartDataFetcher;
import com.example.project_1_java_new_team42.Data.Fetchers.ICartDataFetcher;
import com.example.project_1_java_new_team42.Data.Fetchers.IFetchHandler;
import com.example.project_1_java_new_team42.Data.Senders.IOrderDataSender;
import com.example.project_1_java_new_team42.Data.Senders.ISendHandler;
import com.example.project_1_java_new_team42.Data.Senders.OrderDataSender;
import com.example.project_1_java_new_team42.Models.Cart;
import com.example.project_1_java_new_team42.Models.Order;
import com.example.project_1_java_new_team42.R;
import com.example.project_1_java_new_team42.Util.ItemUtil;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.progressindicator.CircularProgressIndicator;

public class CartActivity extends AppCompatActivity {
    private static final String TAG = "CartActivity";

    protected RecyclerView cartRecyclerView;
    protected CartRecyclerViewAdapter cartItemsAdapter;

    protected ICartDataFetcher cartDataFetcher = new CartDataFetcher();
    protected IOrderDataSender orderDataSender = new OrderDataSender();

    private Cart cartData;

    protected NavigationAdapter navigationAdapter;
    ViewHolder vh;

    private class ViewHolder {

        TextView totalPriceTextView;
        ImageView emptyCartImage;
        Button placeOrderButton;
        CircularProgressIndicator cartItemsSpinner;

        public ViewHolder() {
            totalPriceTextView = findViewById(R.id.cart_total_price);
            emptyCartImage = findViewById(R.id.cart_empty_image);
            placeOrderButton = findViewById(R.id.place_order_button);
            cartItemsSpinner = findViewById(R.id.progress_cart_items);
        }
    }

    private class CartFetchHandler implements IFetchHandler<Cart> {
        @Override
        public void onFetchComplete(Cart data) {
            cartData = data;

            cartItemsAdapter.addItems(data.getItems());
            cartItemsAdapter.setItemsTotalPrice(data.getTotalPrice());

            vh.cartItemsSpinner.setVisibility(View.GONE);

            Log.i(TAG, "Fetched cart items successfully");

            String totalPrice = ItemUtil.addDollarSignToPrice(data.getTotalPrice());
            vh.totalPriceTextView.setText(totalPrice);

            if (data.getItems().isEmpty()){
                disableSubmitButton();
                vh.emptyCartImage.setVisibility(View.VISIBLE);
            }

            cartRecyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
                @Override
                public void onChildViewAttachedToWindow(@NonNull View view) {
                    enableSubmitButton();
                }

                @Override
                public void onChildViewDetachedFromWindow(@NonNull View view) {
                    if (cartItemsAdapter.getItemCount() <= 0){
                        disableSubmitButton();
                        vh.emptyCartImage.setVisibility(View.VISIBLE);
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
        vh.placeOrderButton.setText(R.string.text_cart_empty);
        vh.placeOrderButton.setBackgroundColor(getResources().getColor(R.color.button_unavailable));
        vh.placeOrderButton.setTextColor(Color.BLACK);
        vh.placeOrderButton.setEnabled(false);
    }

    private void enableSubmitButton(){
        vh.placeOrderButton.setText(R.string.text_cart_place_order);
        vh.placeOrderButton.setBackgroundColor(getResources().getColor(R.color.brand_black));
        vh.placeOrderButton.setTextColor(Color.WHITE);
        vh.placeOrderButton.setEnabled(true);
    }

    private void initializePlaceOrderButton() {
        vh.placeOrderButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Order newOrder = new Order(cartData.getItems());
                orderDataSender.writeCartOrderToFirestore(newOrder, new OrderDataSendHandler());
            }
        });
    }

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

        vh = new ViewHolder();
        initializePlaceOrderButton();

        initializeCartRecyclerView();
        cartDataFetcher.readData(new CartFetchHandler());

        NavigationBarView bottomNavBar = findViewById(R.id.bottom_navigation_cart);

        // Highlight the Selected Navigation ICON
        bottomNavBar.setSelectedItemId(R.id.activity_cart);
        // Add the Bottom Bar Navigation Logic
        navigationAdapter = new NavigationAdapter(this);
        bottomNavBar.setOnItemSelectedListener(navigationAdapter.navigationListener);
    }

    public void updateTotalPrice(int totalPrice) {
        String price = ItemUtil.addDollarSignToPrice(totalPrice);
        vh.totalPriceTextView.setText(price);
    }

    private class OrderDataSendHandler implements ISendHandler {

        @Override
        public void onSendSuccess(boolean isSuccess) {
            Intent intent = new Intent(CartActivity.this, SuccessfulOrderActivity.class);
            startActivity(intent);
        }
    }
}
