package com.example.project_1_java_new_team42.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_1_java_new_team42.adapters.NavigationAdapter;
import com.example.project_1_java_new_team42.adapters.PastOrderItemsRecyclerViewAdapter;
import com.example.project_1_java_new_team42.adapters.PastOrdersRecyclerViewAdapter;
import com.example.project_1_java_new_team42.models.Order;
import com.example.project_1_java_new_team42.R;
import com.example.project_1_java_new_team42.util.ItemUtil;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textview.MaterialTextView;

public class PastOrderItemsActivity extends AppCompatActivity {

    ViewHolder vh;

    protected NavigationAdapter navigationAdapter;

    protected PastOrderItemsRecyclerViewAdapter pastOrderItemsRecyclerViewAdapter;

    private class ViewHolder {

        Button backButton;

        MaterialTextView orderNumber;
        RecyclerView pastOrderItemsRecyclerView;
        TextView orderDate, orderTotalPrice;

        //Bottom Nav Bar
        NavigationBarView bottomNavBar;

        public ViewHolder() {
            backButton = findViewById(R.id.button_back_past_order_items);

            orderNumber = findViewById(R.id.order_number_past_order_items);
            pastOrderItemsRecyclerView = findViewById(R.id.recycler_view_past_order_items);
            orderDate = findViewById(R.id.order_date_past_order_items);
            orderTotalPrice = findViewById(R.id.order_total_price_past_order_items);

            // Bottom Nav Bar
            bottomNavBar = findViewById(R.id.bottom_navigation_past_order_items);
        }

        public void setOrderViews(Order order) {
            String orderNum = "#" + order.getOrderId();
            vh.orderNumber.setText(orderNum);
            vh.orderDate.setText(order.getPlacedDateAndTime());

            String totalPriceOfOrder = ItemUtil.addDollarSignToPrice(order.getTotalCost());
            vh.orderTotalPrice.setText(totalPriceOfOrder);
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

        Intent intent = getIntent();
        Order order = (Order) intent.getSerializableExtra(PastOrdersRecyclerViewAdapter.INTENT_KEY_ORDER);

        pastOrderItemsRecyclerViewAdapter.addItems(order.getOrderItems());

        vh.setOrderViews(order);

        // Highlight the Selected Navigation ICON
        vh.bottomNavBar.setSelectedItemId(R.id.activity_orders);
        // Initialise the Bottom Bar Navigation Logic
        navigationAdapter = new NavigationAdapter(this);
        vh.bottomNavBar.setOnItemSelectedListener(navigationAdapter.navigationListener);

        initializeBackButton();
    }

}
