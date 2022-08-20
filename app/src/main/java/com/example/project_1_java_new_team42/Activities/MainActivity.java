package com.example.project_1_java_new_team42.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_1_java_new_team42.Adapters.CategoriesRecyclerViewAdapter;
import com.example.project_1_java_new_team42.Adapters.ItemsRecyclerViewAdapter;
import com.example.project_1_java_new_team42.Data.Fetchers.CategoryDataFetcher;
import com.example.project_1_java_new_team42.Data.Fetchers.IFetchHandler;
import com.example.project_1_java_new_team42.Data.Fetchers.TopItemsDataFetcher;
import com.example.project_1_java_new_team42.Models.Category;
import com.example.project_1_java_new_team42.Models.IItem;
import com.example.project_1_java_new_team42.R;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    protected RecyclerView categoriesRecyclerView;
    protected CategoriesRecyclerViewAdapter categoriesAdapter;
    protected CircularProgressIndicator categoriesSpinner;
    protected CategoryDataFetcher categoriesDataFetcher = new CategoryDataFetcher();

    protected RecyclerView topItemsRecyclerView;
    protected ItemsRecyclerViewAdapter topItemsAdapter;
    protected CircularProgressIndicator topItemsSpinner;
    protected TopItemsDataFetcher topItemsDataFetcher = new TopItemsDataFetcher();

    private class CategoriesFetchHandler implements IFetchHandler<List<Category>> {
        @Override
        public void onFetchComplete(List<Category> data) {
            categoriesAdapter.setData(data);
            categoriesAdapter.notifyItemRangeInserted(0, data.size());

            categoriesSpinner.setVisibility(View.GONE);

            Log.i(TAG, "Fetched categories successfully");
        }

        @Override
        public void onFetchFail() {
            System.out.println("Failed to fetch categories");
            Toast.makeText(getApplicationContext(), "Failed to fetch categories", Toast.LENGTH_SHORT).show();
        }
    }

    private class TopItemsFetchHandler implements IFetchHandler<List<IItem>> {
        @Override
        public void onFetchComplete(List<IItem> data) {
            topItemsAdapter.setData(data);
            topItemsAdapter.notifyItemRangeInserted(0, data.size());

            topItemsSpinner.setVisibility(View.GONE);

            Log.i(TAG, "Fetched top items successfully");
        }

        @Override
        public void onFetchFail() {
            System.out.println("Failed to fetch top items");
            Toast.makeText(getApplicationContext(), "Failed to fetch top items", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Initialize the recycler view which will be called when the activity is created in `onCreate`.
     * This has to be done in the main UI thread otherwise will get warning that no adapter is
     * attached to the recycler view.
     */
    protected void initializeCategoriesRecyclerView() {
        categoriesRecyclerView = findViewById(R.id.recycler_view_category_cards);
        categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        categoriesAdapter = new CategoriesRecyclerViewAdapter(this);

        categoriesRecyclerView.setAdapter(categoriesAdapter);
    }

    protected void initializeTopItemsRecyclerView() {
        topItemsRecyclerView = findViewById(R.id.recycler_view_top_items);
        topItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        topItemsAdapter = new ItemsRecyclerViewAdapter(this);

        topItemsRecyclerView.setAdapter(topItemsAdapter);
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
                            startActivity(new Intent(getApplicationContext(),DetailsActivity.class));
                            overridePendingTransition(0,0);
                            return true;
                        case R.id.activity_orders:
                            //startActivity(new Intent(getApplicationContext(),PastOrdersActivity.class));
                            //overridePendingTransition(0,0);
                            return true;
                    }
                    return false;
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        categoriesSpinner = findViewById(R.id.progress_categories);
        topItemsSpinner = findViewById(R.id.progress_top_items);

        initializeCategoriesRecyclerView();
        initializeTopItemsRecyclerView();

        categoriesDataFetcher.readData(new CategoriesFetchHandler());
        topItemsDataFetcher.readData(new TopItemsFetchHandler());

        NavigationBarView bottomNavBar = findViewById(R.id.bottom_navigation);

        // Highlight the Selected Navigation ICON
        bottomNavBar.setSelectedItemId(R.id.activity_home);
        // Initialise the Bottom Bar Navigation Logic
        // -----------------------NEED TO CHANGE NOTE: ---------------------------//
        // Line 112 -
        //                           1) Change DetailsActivity to CartActivity
        // Uncomment Line 112 - 263 after implemented:
        //                           1) CartActivity
        //                           2) PastOrderActivity
        // -----------------------------------------------------------------------//
        bottomNavBar.setOnItemSelectedListener(navigationListener);

    }
}

