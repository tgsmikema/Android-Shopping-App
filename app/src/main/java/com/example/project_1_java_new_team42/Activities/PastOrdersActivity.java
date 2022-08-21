package com.example.project_1_java_new_team42.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.project_1_java_new_team42.Adapters.CategoriesRecyclerViewAdapter;
import com.example.project_1_java_new_team42.Adapters.PastOrdersRecyclerViewAdapter;
import com.example.project_1_java_new_team42.Data.Fetchers.IFetchHandler;
import com.example.project_1_java_new_team42.Data.Fetchers.PastOrdersDataFetcher;
import com.example.project_1_java_new_team42.Models.Order;
import com.example.project_1_java_new_team42.R;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.List;

public class PastOrdersActivity extends AppCompatActivity {

    protected RecyclerView pastOrdersRecyclerView;
    protected CircularProgressIndicator pastOrdersSpinner;

    protected PastOrdersDataFetcher pastOrdersDataFetcher = new PastOrdersDataFetcher();
    protected PastOrdersRecyclerViewAdapter pastOrdersRecyclerViewAdapter;

    private class pastOrdersFetchHandler implements IFetchHandler<List<Order>> {

        @Override
        public void onFetchComplete(List<Order> data) {
            pastOrdersRecyclerViewAdapter.addItems(data);
            pastOrdersSpinner.setVisibility(View.GONE);

        }

        @Override
        public void onFetchFail() {
            System.out.println("Failed to fetch past orders");
            Toast.makeText(getApplicationContext(), "Failed to fetch past orders", Toast.LENGTH_SHORT).show();
        }
    }

    protected void initializePastOrdersRecyclerView() {
        pastOrdersRecyclerView = findViewById(R.id.recycler_view_past_orders);
        pastOrdersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        pastOrdersRecyclerViewAdapter = new PastOrdersRecyclerViewAdapter(this);
        pastOrdersRecyclerView.setAdapter(pastOrdersRecyclerViewAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_orders);

        pastOrdersSpinner = findViewById(R.id.progress_past_orders);

        initializePastOrdersRecyclerView();

        pastOrdersDataFetcher.readData(new pastOrdersFetchHandler());
    }
}