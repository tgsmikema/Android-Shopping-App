package com.example.project_1_java_new_team42.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_1_java_new_team42.Adapters.NavigationAdapter;
import com.example.project_1_java_new_team42.Adapters.PastOrderItemsRecyclerViewAdapter;
import com.example.project_1_java_new_team42.Adapters.PastOrdersRecyclerViewAdapter;
import com.example.project_1_java_new_team42.Data.Fetchers.IFetchHandler;
import com.example.project_1_java_new_team42.Data.Fetchers.PastOrderItemsDataFetcher;
import com.example.project_1_java_new_team42.Models.Order;
import com.example.project_1_java_new_team42.R;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class PastOrderItemsActivity extends AppCompatActivity {

    ViewHolder vh;

    protected NavigationAdapter navigationAdapter;

    protected PastOrderItemsRecyclerViewAdapter pastOrderItemsRecyclerViewAdapter;
    // initialise the DB fetcher class
    protected PastOrderItemsDataFetcher pastOrderItemsDataFetcher = new PastOrderItemsDataFetcher();

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
            pastOrderItemsRecyclerViewAdapter.addItems(data.get(0).getOrderItems());
            vh.pastOrderItemsSpinner.setVisibility(View.GONE);

            String orderNum = "#" + data.get(0).getOrderId();
            vh.orderNumber.setText(orderNum);
            vh.orderDate.setText(data.get(0).getPlacedDateAndTime());

            String totalPriceOfOrder = "$" + String.valueOf(data.get(0).getTotalCost());
            vh.orderTotalPrice.setText(totalPriceOfOrder);
        }

        @Override
        public void onFetchFail() {

        }
    }

    private View.OnClickListener buttonListener = new View.OnClickListener() {
        public void onClick(View view) {
            navigateBackToPreviousActivity();
        }
    };

    public void navigateBackToPreviousActivity() {
        Intent intent = new Intent(this, PastOrdersActivity.class);
        startActivity(intent);
    }

    private String constructItemFromIntent(){
        Intent intent = getIntent();
        String orderID = intent.getStringExtra(MainActivity.INTENT_KEY_ORDER_ID);
        return orderID;
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

        pastOrderItemsDataFetcher.readData(constructItemFromIntent(), new pastOrderItemsFetchHandler());

        // Highlight the Selected Navigation ICON
        vh.bottomNavBar.setSelectedItemId(R.id.activity_orders);
        // Initialise the Bottom Bar Navigation Logic
        navigationAdapter = new NavigationAdapter(this);
        vh.bottomNavBar.setOnItemSelectedListener(navigationAdapter.navigationListener);

        vh.backButton.setOnClickListener(buttonListener);


    }

}
