package com.example.project_1_java_new_team42.Activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

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

    ViewHolder vh;

    protected PastOrdersRecyclerViewAdapter pastOrdersRecyclerViewAdapter;
    // initialise the DB fetcher class
    protected PastOrderItemsDataFetcher pastOrderItemsDataFetcher = new PastOrderItemsDataFetcher();

    private class pastOrderItemsFetchHandler implements IFetchHandler<List<Order>> {

        @Override
        public void onFetchComplete(List<Order> data) {

        }

        @Override
        public void onFetchFail() {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_orders);

        vh = new ViewHolder();
    }

}
