package com.example.project_1_java_new_team42.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
import com.example.project_1_java_new_team42.Widgets.ItemOffsetDecoration;
import com.example.project_1_java_new_team42.Widgets.Search;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static final String INTENT_KEY_SEARCH = "SEARCH";

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
        topItemsRecyclerView = findViewById(R.id.recycler_view_top_items);
        topItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ItemOffsetDecoration decoration = new ItemOffsetDecoration(this, R.dimen.rv_card_item_hor_offset, ItemOffsetDecoration.LayoutType.HORIZONTAL);
        topItemsRecyclerView.addItemDecoration(decoration);

        topItemsAdapter = new ItemsRecyclerViewAdapter(this);
        topItemsRecyclerView.setAdapter(topItemsAdapter);
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
                if (!searchQuery.isEmpty()) {
                    navigateToSearchResults(searchQuery);
                }
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
    }
}

