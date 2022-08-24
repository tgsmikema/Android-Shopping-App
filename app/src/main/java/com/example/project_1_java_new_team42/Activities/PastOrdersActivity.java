package com.example.project_1_java_new_team42.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.project_1_java_new_team42.Adapters.NavigationAdapter;
import com.example.project_1_java_new_team42.Adapters.PastOrdersRecyclerViewAdapter;
import com.example.project_1_java_new_team42.Data.Fetchers.IFetchHandler;
import com.example.project_1_java_new_team42.Data.Fetchers.PastOrdersDataFetcher;
import com.example.project_1_java_new_team42.Models.Order;
import com.example.project_1_java_new_team42.R;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.List;

public class PastOrdersActivity extends AppCompatActivity {

    protected NavigationAdapter navigationAdapter;

    private class ViewHolder {
        RecyclerView pastOrdersRecyclerView;
        CircularProgressIndicator pastOrdersSpinner;
        //Bottom Nav Bar
        NavigationBarView bottomNavBar;

        public ViewHolder() {
            pastOrdersRecyclerView = findViewById(R.id.recycler_view_past_orders);
            pastOrdersSpinner = findViewById(R.id.progress_past_orders);
            // Bottom Nav Bar
            bottomNavBar = findViewById(R.id.bottom_navigation_past_orders);
        }
    }

    ViewHolder vh;

    protected PastOrdersDataFetcher pastOrdersDataFetcher = new PastOrdersDataFetcher();
    protected PastOrdersRecyclerViewAdapter pastOrdersRecyclerViewAdapter;

    private class pastOrdersFetchHandler implements IFetchHandler<List<Order>> {

        @Override
        public void onFetchComplete(List<Order> data) {
            pastOrdersRecyclerViewAdapter.addItems(data);
            vh.pastOrdersSpinner.setVisibility(View.GONE);

        }

        @Override
        public void onFetchFail() {
            System.out.println("Failed to fetch past orders");
            Toast.makeText(getApplicationContext(), "Failed to fetch past orders", Toast.LENGTH_SHORT).show();
        }
    }

    protected void initializePastOrdersRecyclerView() {
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setReverseLayout(true);
        llm.setStackFromEnd(true);
        vh.pastOrdersRecyclerView.setLayoutManager(llm);
        pastOrdersRecyclerViewAdapter = new PastOrdersRecyclerViewAdapter(this);
        vh.pastOrdersRecyclerView.setAdapter(pastOrdersRecyclerViewAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_orders);

        vh = new ViewHolder();
        initializePastOrdersRecyclerView();

        pastOrdersDataFetcher.readData(new pastOrdersFetchHandler());

        // Highlight the Selected Navigation ICON
        vh.bottomNavBar.setSelectedItemId(R.id.activity_orders);
        // Initialise the Bottom Bar Navigation Logic
        navigationAdapter = new NavigationAdapter(this);
        vh.bottomNavBar.setOnItemSelectedListener(navigationAdapter.navigationListener);
    }
}