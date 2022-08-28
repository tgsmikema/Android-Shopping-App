package com.example.project_1_java_new_team42.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.project_1_java_new_team42.adapters.NavigationAdapter;
import com.example.project_1_java_new_team42.adapters.PastOrdersRecyclerViewAdapter;
import com.example.project_1_java_new_team42.data.Fetchers.IFetchHandler;
import com.example.project_1_java_new_team42.data.Fetchers.IPastOrdersDataFetcher;
import com.example.project_1_java_new_team42.data.Fetchers.PastOrdersDataFetcher;
import com.example.project_1_java_new_team42.models.Order;
import com.example.project_1_java_new_team42.R;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.List;

public class PastOrdersActivity extends AppCompatActivity {

    private ViewHolder viewHolder;

    private PastOrdersRecyclerViewAdapter pastOrdersRecyclerViewAdapter;

    private final IPastOrdersDataFetcher pastOrdersDataFetcher = new PastOrdersDataFetcher();

    private class ViewHolder {
        RecyclerView pastOrdersRecyclerView;
        CircularProgressIndicator pastOrdersSpinner;
        NavigationBarView bottomNavBar;

        public ViewHolder() {
            pastOrdersSpinner = findViewById(R.id.progress_past_orders);
            initializePastOrdersRecyclerView();
            initializeBottomBarViews();
        }

        private LinearLayoutManager createLinearLayoutManager() {
            LinearLayoutManager llm = new LinearLayoutManager(PastOrdersActivity.this);
            llm.setReverseLayout(true);
            llm.setStackFromEnd(true);
            return llm;
        }

        private void initializePastOrdersRecyclerView() {
            pastOrdersRecyclerView = findViewById(R.id.recycler_view_past_orders);
            pastOrdersRecyclerViewAdapter = new PastOrdersRecyclerViewAdapter(PastOrdersActivity.this);
            pastOrdersRecyclerView.setLayoutManager(createLinearLayoutManager());
            pastOrdersRecyclerView.setAdapter(pastOrdersRecyclerViewAdapter);
        }

        private void initializeBottomBarViews() {
            bottomNavBar = findViewById(R.id.bottom_navigation_past_orders);
            bottomNavBar.setSelectedItemId(R.id.activity_orders);
            bottomNavBar.setOnItemSelectedListener(new NavigationAdapter(PastOrdersActivity.this).navigationListener);
        }
    }

    private class pastOrdersFetchHandler implements IFetchHandler<List<Order>> {
        @Override
        public void onFetchComplete(List<Order> data) {
            pastOrdersRecyclerViewAdapter.addItems(data);
            viewHolder.pastOrdersSpinner.setVisibility(View.GONE);

        }

        @Override
        public void onFetchFail() {
            System.out.println("Failed to fetch past orders");
            Toast.makeText(getApplicationContext(), "Failed to fetch past orders", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_orders);

        viewHolder = new ViewHolder();

        pastOrdersDataFetcher.readData(new pastOrdersFetchHandler());
    }
}