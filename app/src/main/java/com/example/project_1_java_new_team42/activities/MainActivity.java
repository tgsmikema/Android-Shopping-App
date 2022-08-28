package com.example.project_1_java_new_team42.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_1_java_new_team42.adapters.CategoriesRecyclerViewAdapter;
import com.example.project_1_java_new_team42.adapters.ItemsRecyclerViewAdapter;
import com.example.project_1_java_new_team42.adapters.NavigationAdapter;
import com.example.project_1_java_new_team42.data.fetchers.CategoryDataFetcher;
import com.example.project_1_java_new_team42.data.fetchers.ICategoryDataFetcher;
import com.example.project_1_java_new_team42.data.fetchers.IFetchHandler;
import com.example.project_1_java_new_team42.data.fetchers.ITopItemsDataFetcher;
import com.example.project_1_java_new_team42.data.fetchers.TopItemsDataFetcher;
import com.example.project_1_java_new_team42.models.Category;
import com.example.project_1_java_new_team42.models.IItem;
import com.example.project_1_java_new_team42.R;
import com.example.project_1_java_new_team42.widgets.ItemsRecyclerView;
import com.example.project_1_java_new_team42.widgets.RecyclerViewLayoutType;
import com.example.project_1_java_new_team42.widgets.Search;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static final String INTENT_KEY_SEARCH = "SEARCH";
    public static final String INTENT_KEY_CATEGORY_NAME = "CATEGORY_NAME";
    public static final String INTENT_KEY_CATEGORY_IMAGE_URI = "CATEGORY_IMAGE_URI";

    private ViewHolder viewHolder;

    private CategoriesRecyclerViewAdapter categoriesAdapter;
    private ItemsRecyclerViewAdapter topItemsAdapter;

    private class ViewHolder {
        ShimmerFrameLayout categoriesShimmerLayout;
        ShimmerFrameLayout topItemsShimmerLayout;
        RecyclerView categoriesRecyclerView;
        ItemsRecyclerView topItemsRecyclerView;
        TextInputLayout searchTextInputLayout;
        NavigationBarView bottomNavBar;

        public ViewHolder() {
            initializeSearchBar();
            initializeTopItemViews();
            initializeCategoryCardViews();
            initializeBottomBarViews();
        }

        private void initializeSearchBar() {
            searchTextInputLayout = findViewById(R.id.text_input_layout_search_results);
            Search search = new Search(searchTextInputLayout.getEditText());
            search.setDisableSearchIfEmpty(true);
            search.setClearQueryOnSearch(true);

            search.setOnSearchActionListener(new Search.OnSearchActionListener() {
                @Override
                public void onSearch(EditText view, String searchQuery) {
                    navigateToSearchResults(searchQuery);
                }
            });
        }

        private void initializeTopItemViews() {
            topItemsShimmerLayout = findViewById(R.id.shimmer_top_items);
            topItemsRecyclerView = new ItemsRecyclerView(MainActivity.this, findViewById(R.id.recycler_view_top_items), RecyclerViewLayoutType.HORIZONTAL);
            topItemsAdapter = topItemsRecyclerView.getAdapter();
        }

        private void initializeCategoryCardViews() {
            categoriesShimmerLayout = findViewById(R.id.shimmer_categories);
            categoriesAdapter = new CategoriesRecyclerViewAdapter(MainActivity.this);
            categoriesRecyclerView = findViewById(R.id.recycler_view_category_cards);
            categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            categoriesRecyclerView.setAdapter(categoriesAdapter);
        }

        private void initializeBottomBarViews() {
            bottomNavBar = findViewById(R.id.bottom_navigation);
            bottomNavBar.setSelectedItemId(R.id.activity_home);
            bottomNavBar.setOnItemSelectedListener(new NavigationAdapter(MainActivity.this).navigationListener);
        }
    }

    private class CategoriesFetchHandler implements IFetchHandler<List<Category>> {
        @Override
        public void onFetchComplete(List<Category> data) {
            categoriesAdapter.addItems(data);
            viewHolder.categoriesShimmerLayout.setVisibility(View.INVISIBLE);
            viewHolder.categoriesRecyclerView.setVisibility(View.VISIBLE);
            Log.i(TAG, "Fetched categories successfully");
        }

        @Override
        public void onFetchFail() {
            System.out.println("Failed to fetch categories");
            viewHolder.categoriesShimmerLayout.hideShimmer();
            Toast.makeText(getApplicationContext(), "Failed to fetch categories", Toast.LENGTH_SHORT).show();
        }
    }

    private class TopItemsFetchHandler implements IFetchHandler<List<IItem>> {
        @Override
        public void onFetchComplete(List<IItem> data) {
            topItemsAdapter.addItems(data);
            viewHolder.topItemsShimmerLayout.setVisibility(View.INVISIBLE);
            viewHolder.topItemsRecyclerView.getRecyclerView().setVisibility(View.VISIBLE);
            Log.i(TAG, "Fetched top items successfully");
        }

        @Override
        public void onFetchFail() {
            System.out.println("Failed to fetch top items");
            viewHolder.topItemsShimmerLayout.hideShimmer();
            Toast.makeText(getApplicationContext(), "Failed to fetch top items", Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateToSearchResults(String searchQuery) {
        Intent intent = new Intent(this, SearchResultsActivity.class);
        intent.putExtra(INTENT_KEY_SEARCH, searchQuery);
        startActivity(intent);
    }

    private void showLoadingStates() {
        viewHolder.topItemsShimmerLayout.startShimmer();
        viewHolder.categoriesShimmerLayout.startShimmer();
    }

    private void fetchHomePageData() {
        ICategoryDataFetcher categoriesDataFetcher = new CategoryDataFetcher();
        ITopItemsDataFetcher topItemsDataFetcher = new TopItemsDataFetcher();
        categoriesDataFetcher.readData(new CategoriesFetchHandler());
        topItemsDataFetcher.readData(new TopItemsFetchHandler());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        View view = getCurrentFocus();
        Search.handleTouchEventOutsideKeyboard(event, view);
        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewHolder = new ViewHolder();

        showLoadingStates();
        fetchHomePageData();
    }
}

