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

import com.example.project_1_java_new_team42.Adapters.PastOrdersRecyclerViewAdapter;
import com.example.project_1_java_new_team42.Data.Fetchers.IFetchHandler;
import com.example.project_1_java_new_team42.Data.Fetchers.PastOrdersDataFetcher;
import com.example.project_1_java_new_team42.Models.Order;
import com.example.project_1_java_new_team42.R;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.List;

public class PastOrdersActivity extends AppCompatActivity {

    private class ViewHolder {
        RecyclerView pastOrdersRecyclerView;
        CircularProgressIndicator pastOrdersSpinner;
        //Bottom Nav Bar
        NavigationBarView bottomNavBar;

        public ViewHolder() {
            pastOrdersRecyclerView = findViewById(R.id.recycler_view_past_orders);
            pastOrdersSpinner = findViewById(R.id.progress_past_orders);
            // Bottom Nav Bar
            bottomNavBar = findViewById(R.id.bottom_navigation);
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
        vh.pastOrdersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        pastOrdersRecyclerViewAdapter = new PastOrdersRecyclerViewAdapter(this);
        vh.pastOrdersRecyclerView.setAdapter(pastOrdersRecyclerViewAdapter);
    }

    // Logic of Navigation Bar selection.
    private NavigationBarView.OnItemSelectedListener navigationListener =
            new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch(item.getItemId())
                    {
                        case R.id.activity_home:
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            overridePendingTransition(0,0);
                            return true;
                        case R.id.activity_cart:
                            //startActivity(new Intent(getApplicationContext(),CartActivity.class));
                            //overridePendingTransition(0,0);
                            return true;
                        case R.id.activity_orders:
                            return true;
                    }
                    return false;
                }
            };

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
        vh.bottomNavBar.setOnItemSelectedListener(navigationListener);
    }
}