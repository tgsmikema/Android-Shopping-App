package com.example.project_1_java_new_team42.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_1_java_new_team42.Adapters.CategoriesRecyclerViewAdapter;
import com.example.project_1_java_new_team42.Data.Fetchers.CategoryDataFetcher;
import com.example.project_1_java_new_team42.Data.Fetchers.IFetchHandler;
import com.example.project_1_java_new_team42.Models.Category;
import com.example.project_1_java_new_team42.R;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    protected RecyclerView categoriesRecyclerView;
    protected CategoriesRecyclerViewAdapter categoriesAdapter;
    protected CircularProgressIndicator categoriesSpinner;

    protected CategoryDataFetcher categoriesDataFetcher = new CategoryDataFetcher();

    private class CategoriesFetchHandler implements IFetchHandler<List<Category>> {
        @Override
        public void onFetchComplete(List<Category> data) {
            categoriesAdapter.setData(data);
            categoriesAdapter.notifyItemRangeInserted(0, data.size());

            categoriesSpinner.setVisibility(View.GONE);

            Log.i("MainActivity", "Fetched categories successfully");
        }

        @Override
        public void onFetchFail() {
            System.out.println("Failed to fetch categories");
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        categoriesSpinner = findViewById(R.id.progress_categories);

        initializeCategoriesRecyclerView();

        categoriesDataFetcher.readData(new CategoriesFetchHandler());
    }
}

