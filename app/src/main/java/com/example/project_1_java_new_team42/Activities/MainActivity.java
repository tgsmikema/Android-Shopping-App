package com.example.project_1_java_new_team42.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_1_java_new_team42.Adapters.CategoriesRecyclerViewAdapter;
import com.example.project_1_java_new_team42.Adapters.ItemsRecyclerViewAdapter;
import com.example.project_1_java_new_team42.Adapters.NavigationAdapter;
import com.example.project_1_java_new_team42.Data.Fetchers.CategoryDataFetcher;
import com.example.project_1_java_new_team42.Data.Fetchers.IFetchHandler;
import com.example.project_1_java_new_team42.Data.Fetchers.TopItemsDataFetcher;
import com.example.project_1_java_new_team42.Models.Category;
import com.example.project_1_java_new_team42.Models.IItem;
import com.example.project_1_java_new_team42.R;
import com.example.project_1_java_new_team42.Widgets.ItemsRecyclerView;
import com.example.project_1_java_new_team42.Widgets.RecyclerViewLayoutType;
import com.example.project_1_java_new_team42.Widgets.Search;

import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static final String INTENT_KEY_SEARCH = "SEARCH";
    public static final String INTENT_KEY_CATEGORY_NAME = "CATEGORY_NAME";
    public static final String INTENT_KEY_CATEGORY_IMAGE_URI = "CATEGORY_IMAGE_URI";
    public static final String INTENT_KEY_ACTIVITY_NAME = "ACTIVITY_NAME";
    public static final String INTENT_VALUE_MAIN_ACTIVITY = "Activities.MainActivity";
    public static final String INTENT_VALUE_SEARCH_RESULTS_ACTIVITY = "Activities.SearchResultsActivity";
    public static final String INTENT_VALUE_CATEGORY_ITEMS_ACTIVITY = "Activities.CategoryItemsActivity";
    public static final String INTENT_KEY_ITEM_ID = "ITEM_ID";
    public static final String INTENT_KEY_ORDER_ID = "ORDER_ID";

    protected RecyclerView categoriesRecyclerView;
    protected CategoriesRecyclerViewAdapter categoriesAdapter;
    protected CircularProgressIndicator categoriesSpinner;
    protected CategoryDataFetcher categoriesDataFetcher = new CategoryDataFetcher();

    protected ItemsRecyclerViewAdapter topItemsAdapter;
    protected CircularProgressIndicator topItemsSpinner;
    protected TopItemsDataFetcher topItemsDataFetcher = new TopItemsDataFetcher();

    protected NavigationAdapter navigationAdapter;

    private class CategoriesFetchHandler implements IFetchHandler<List<Category>> {
        @Override
        public void onFetchComplete(List<Category> data) {
            categoriesAdapter.addItems(data);
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
            topItemsAdapter.addItems(data);
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
     * Initialize the recycler views which will be called when the activity is created in `onCreate`.
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
        ItemsRecyclerView topItemsRecyclerView = new ItemsRecyclerView(this, findViewById(R.id.recycler_view_top_items), RecyclerViewLayoutType.HORIZONTAL);
        topItemsAdapter = topItemsRecyclerView.getAdapter();
    }

    private void navigateToSearchResults(String searchQuery) {
        Intent intent = new Intent(this, SearchResultsActivity.class);
        intent.putExtra(INTENT_KEY_SEARCH, searchQuery);
        startActivity(intent);
    }

    /* Search functionality */
    protected void initializeSearch() {
        TextInputLayout searchTextInputLayout = findViewById(R.id.text_input_layout_search_results);
        Search search = new Search(searchTextInputLayout.getEditText());
        search.setDisableSearchIfEmpty(true);

        search.setOnSearchActionListener(new Search.OnSearchActionListener() {
            @Override
            public void onSearch(EditText view, String searchQuery) {
                navigateToSearchResults(searchQuery);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        categoriesSpinner = findViewById(R.id.progress_categories);
        topItemsSpinner = findViewById(R.id.progress_top_items);

        initializeCategoriesRecyclerView();
        initializeTopItemsRecyclerView();
        initializeSearch();

        categoriesDataFetcher.readData(new CategoriesFetchHandler());
        topItemsDataFetcher.readData(new TopItemsFetchHandler());

        //TODO(Refactor) put this inside ViewHolder
        NavigationBarView bottomNavBar = findViewById(R.id.bottom_navigation);

        // Highlight the Selected Navigation ICON
        bottomNavBar.setSelectedItemId(R.id.activity_home);
        // Add the Bottom Bar Navigation Logic
        navigationAdapter = new NavigationAdapter(this);
        bottomNavBar.setOnItemSelectedListener(navigationAdapter.navigationListener);

    }
}

