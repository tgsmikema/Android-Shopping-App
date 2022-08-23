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
import com.example.project_1_java_new_team42.Data.Fetchers.CategoryDataFetcher;
import com.example.project_1_java_new_team42.Data.Fetchers.IFetchHandler;
import com.example.project_1_java_new_team42.Data.Fetchers.TopItemsDataFetcher;
import com.example.project_1_java_new_team42.Models.Category;
import com.example.project_1_java_new_team42.Models.IItem;
import com.example.project_1_java_new_team42.R;
import com.example.project_1_java_new_team42.Widgets.ItemsRecyclerView;
import com.example.project_1_java_new_team42.Widgets.RecyclerViewLayoutType;
import com.example.project_1_java_new_team42.Widgets.Search;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static final String INTENT_KEY_SEARCH = "SEARCH";
    public static final String INTENT_KEY_CATEGORY_NAME = "CATEGORY_NAME";
    public static final String INTENT_KEY_CATEGORY_IMAGE_URI = "CATEGORY_IMAGE_URI";

    private RecyclerView categoriesRecyclerView;
    private CategoriesRecyclerViewAdapter categoriesAdapter;
    private CategoryDataFetcher categoriesDataFetcher = new CategoryDataFetcher();

    private ShimmerFrameLayout topItemsShimmerLayout;
    private ItemsRecyclerView topItemsRecyclerView;
    private ItemsRecyclerViewAdapter topItemsAdapter;
    private final TopItemsDataFetcher topItemsDataFetcher = new TopItemsDataFetcher();

    private class CategoriesFetchHandler implements IFetchHandler<List<Category>> {
        @Override
        public void onFetchComplete(List<Category> data) {
            categoriesAdapter.addItems(data);
            // TODO Handle shimmer and recycler view for categories
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
            topItemsShimmerLayout.setVisibility(View.INVISIBLE);
            topItemsRecyclerView.getRecyclerView().setVisibility(View.VISIBLE);
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
        topItemsRecyclerView = new ItemsRecyclerView(this, findViewById(R.id.recycler_view_top_items), RecyclerViewLayoutType.HORIZONTAL);
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
                            startActivity(new Intent(getApplicationContext(),PastOrdersActivity.class));
                            overridePendingTransition(0,0);
                            return true;
                    }
                    return false;
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        topItemsShimmerLayout = findViewById(R.id.shimmer_top_items);
        topItemsShimmerLayout.startShimmer();

        initializeCategoriesRecyclerView();
        initializeTopItemsRecyclerView();
        initializeSearch();

//        categoriesDataFetcher.readData(new CategoriesFetchHandler());
        topItemsDataFetcher.readData(new TopItemsFetchHandler());

        NavigationBarView bottomNavBar = findViewById(R.id.bottom_navigation);

        bottomNavBar.setSelectedItemId(R.id.activity_home);
        bottomNavBar.setOnItemSelectedListener(navigationListener);
    }
}

