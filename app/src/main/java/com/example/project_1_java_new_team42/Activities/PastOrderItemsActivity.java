package com.example.project_1_java_new_team42.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_1_java_new_team42.Adapters.NavigationAdapter;
import com.example.project_1_java_new_team42.Adapters.PastOrderItemsRecyclerViewAdapter;
import com.example.project_1_java_new_team42.Data.Fetchers.IFetchHandler;
import com.example.project_1_java_new_team42.Data.Fetchers.IPastOrderItemsDataFetcher;
import com.example.project_1_java_new_team42.Data.Fetchers.PastOrderItemsDataFetcher;
import com.example.project_1_java_new_team42.Models.Order;
import com.example.project_1_java_new_team42.R;
import com.example.project_1_java_new_team42.Util.ItemUtil;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class PastOrderItemsActivity extends AppCompatActivity {

    ViewHolder vh;

    protected NavigationAdapter navigationAdapter;

    protected PastOrderItemsRecyclerViewAdapter pastOrderItemsRecyclerViewAdapter;
    // initialise the DB fetcher class
    protected IPastOrderItemsDataFetcher pastOrderItemsDataFetcher = new PastOrderItemsDataFetcher();

    private class ViewHolder {

        Button backButton;

        MaterialTextView orderNumber;
        RecyclerView pastOrderItemsRecyclerView;
        CircularProgressIndicator pastOrderItemsSpinner;
        TextView orderDate, orderTotalPrice;

        //Bottom Nav Bar
        NavigationBarView bottomNavBar;

        public ViewHolder() {
            backButton = findViewById(R.id.button_back_past_order_items);

            orderNumber = findViewById(R.id.order_number_past_order_items);
            pastOrderItemsRecyclerView = findViewById(R.id.recycler_view_past_order_items);
            pastOrderItemsSpinner = findViewById(R.id.progress_past_order_items);
            orderDate = findViewById(R.id.order_date_past_order_items);
            orderTotalPrice = findViewById(R.id.order_total_price_past_order_items);

            // Bottom Nav Bar
            bottomNavBar = findViewById(R.id.bottom_navigation_past_order_items);
        }
    }


    private class pastOrderItemsFetchHandler implements IFetchHandler<List<Order>> {

        @Override
        public void onFetchComplete(List<Order> data) {
            Order order = data.get(0);
            pastOrderItemsRecyclerViewAdapter.addItems(order.getOrderItems());
            vh.pastOrderItemsSpinner.setVisibility(View.GONE);

            String orderNum = "#" + order.getOrderId();
            vh.orderNumber.setText(orderNum);
            vh.orderDate.setText(order.getPlacedDateAndTime());

            String totalPriceOfOrder = ItemUtil.addDollarSignToPrice(order.getTotalCost());
            vh.orderTotalPrice.setText(totalPriceOfOrder);
        }

        @Override
        public void onFetchFail() {
            System.out.println("Failed to fetch cart items");
            Toast.makeText(getApplicationContext(), "Failed to fetch cart items", Toast.LENGTH_SHORT).show();
        }
    }

    private void initializeBackButton() {
        vh.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private String getItemIdFromIntent(){
        Intent intent = getIntent();
        return intent.getStringExtra(MainActivity.INTENT_KEY_ORDER_ID);
    }


    protected void initializePastOrderItemsRecyclerView() {
        vh.pastOrderItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        pastOrderItemsRecyclerViewAdapter = new PastOrderItemsRecyclerViewAdapter(this);
        vh.pastOrderItemsRecyclerView.setAdapter(pastOrderItemsRecyclerViewAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_order_items);

        vh = new ViewHolder();
        initializePastOrderItemsRecyclerView();

        pastOrderItemsDataFetcher.readData(getItemIdFromIntent(), new pastOrderItemsFetchHandler());

        // Highlight the Selected Navigation ICON
        vh.bottomNavBar.setSelectedItemId(R.id.activity_orders);
        // Initialise the Bottom Bar Navigation Logic
        navigationAdapter = new NavigationAdapter(this);
        vh.bottomNavBar.setOnItemSelectedListener(navigationAdapter.navigationListener);

        initializeBackButton();
    }

}
