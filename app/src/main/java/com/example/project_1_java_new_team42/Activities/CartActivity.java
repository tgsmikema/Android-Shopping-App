package com.example.project_1_java_new_team42.Activities;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_1_java_new_team42.Adapters.CartRecyclerViewAdapter;
import com.example.project_1_java_new_team42.Data.Fetchers.CartDataFetcher;
import com.example.project_1_java_new_team42.Data.Fetchers.IFetchHandler;
import com.example.project_1_java_new_team42.Data.Senders.CartDataSender;
import com.example.project_1_java_new_team42.Data.Senders.ISendHandler;
import com.example.project_1_java_new_team42.Data.Senders.OrderDataSender;
import com.example.project_1_java_new_team42.Models.Cart;
import com.example.project_1_java_new_team42.Models.Order;
import com.example.project_1_java_new_team42.R;
import com.google.android.material.progressindicator.CircularProgressIndicator;

public class CartActivity extends AppCompatActivity {
    private static final String TAG = "CartActivity";

    protected RecyclerView cartRecyclerView;
    protected CartRecyclerViewAdapter cartItemsAdapter;
    protected CircularProgressIndicator cartItemsSpinner;

    protected CartDataFetcher cartDataFetcher = new CartDataFetcher();
    protected CartDataSender cartDataSender = new CartDataSender();
    protected OrderDataSender orderDataSender = new OrderDataSender();
    public static boolean isAddedToCart = false;

    private Cart cartData;

    TextView totalPriceTextView;
    Button placeOrderButton;

    private class CartFetchHandler implements IFetchHandler<Cart> {
        @Override
        public void onFetchComplete(Cart data) {
            cartData = data;



            cartItemsAdapter.setData(data);
            cartItemsAdapter.notifyItemRangeInserted(0, data.getItems().size());

            cartItemsSpinner.setVisibility(View.GONE);

            Log.i(TAG, "Fetched cart items successfully");

            totalPriceTextView = findViewById(R.id.cart_total_price);
            String totalPrice = "$" + data.getTotalPrice();
            totalPriceTextView.setText(totalPrice);

            placeOrderButton = findViewById(R.id.place_order_button);
            placeOrderButton.setOnClickListener(buttonListener);

            //initially, check if cart is empty
            if (data.getItems().size() == 0){
                disableSubmitButton();
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
        placeOrderButton.setText("Cart is Empty!"); 
        placeOrderButton.setBackgroundColor(getResources().getColor(R.color.button_unavailable));
        placeOrderButton.setTextColor(Color.BLACK);
        placeOrderButton.setEnabled(false);
    }

    private void enableSubmitButton(){
        placeOrderButton.setText("Place Order");
        placeOrderButton.setBackgroundColor(getResources().getColor(R.color.brand_black));
        placeOrderButton.setTextColor(Color.WHITE);
        placeOrderButton.setEnabled(true);
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

    private View.OnClickListener buttonListener = new View.OnClickListener() {
        public void onClick(View view) {
            Order newOrder = new Order(cartData.getItems());
            orderDataSender.writeCartOrderToFirestore(newOrder, new OrderDataSendHandler());
            Intent intent = new Intent(CartActivity.this, MainActivity.class);
            startActivity(intent);
        }
    };

    public void updateTotalPrice(int totalPrice) {
        String price = "$" + totalPrice;
        totalPriceTextView.setText(price);
    }

    private class OrderDataSendHandler implements ISendHandler {

        @Override
        public void onSendSuccess(boolean isSuccess) {
            Toast.makeText(CartActivity.this,"Order Placed!",Toast.LENGTH_LONG).show();
        }
    }
}
