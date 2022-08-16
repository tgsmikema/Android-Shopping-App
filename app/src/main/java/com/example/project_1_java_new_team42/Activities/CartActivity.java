package com.example.project_1_java_new_team42.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_1_java_new_team42.Adapters.ItemsRecyclerViewAdapter;
import com.example.project_1_java_new_team42.Data.Fetchers.CartDataFetcher;
import com.example.project_1_java_new_team42.R;
import com.google.android.material.progressindicator.CircularProgressIndicator;

public class CartActivity extends AppCompatActivity {

    protected RecyclerView cartRecyclerView;
//    protected ItemsRecyclerViewAdapter topItemsAdapter;
//    protected CircularProgressIndicator topItemsSpinner;
    protected CartDataFetcher cartDataFetcher = new CartDataFetcher();

    protected void initializeCartRecyclerView() {
        cartRecyclerView = findViewById(R.id.recycler_view_cart);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

//        cartAdapter = new ItemsRecyclerViewAdapter(this);

//        cartRecyclerView.setAdapter(cartAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
    }
}
