package com.example.project_1_java_new_team42.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project_1_java_new_team42.adapters.ItemsRecyclerViewAdapter;
import com.example.project_1_java_new_team42.adapters.NavigationAdapter;
import com.example.project_1_java_new_team42.data.Fetchers.IFetchHandler;
import com.example.project_1_java_new_team42.data.Fetchers.ISearchItemsDataFetcher;
import com.example.project_1_java_new_team42.data.Fetchers.SearchItemsDataFetcher;
import com.example.project_1_java_new_team42.models.IItem;
import com.example.project_1_java_new_team42.R;
import com.example.project_1_java_new_team42.widgets.ItemsRecyclerView;
import com.example.project_1_java_new_team42.widgets.RecyclerViewLayoutType;
import com.example.project_1_java_new_team42.widgets.Search;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class SearchResultsActivity extends AppCompatActivity {
    private static final String TAG = "SearchResultsActivity";

    private final ISearchItemsDataFetcher itemsDataFetcher = new SearchItemsDataFetcher();

    private String searchedText;

    private ItemsRecyclerViewAdapter itemsAdapter;

    private ViewHolder viewHolder;

    private class ViewHolder {
        TextInputLayout searchBar;
        TextView helperTextView;
        ItemsRecyclerView itemsRecyclerView;
        ShimmerFrameLayout itemsShimmer;
        Button backButton;
        NavigationBarView bottomNavBar;

        public ViewHolder() {
            initializeBackButton();
            initializeSearchBar();
            initializeHelperTextView();
            itemsShimmer = findViewById(R.id.shimmer_search_items);
            initializeItemsRecyclerView();
            initializeBottomBarViews();
        }

        private void initializeBackButton() {
            backButton = findViewById(R.id.button_back_search_results);
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

        private void initializeSearchBar() {
            searchBar = findViewById(R.id.text_input_layout_search_results);
            EditText et = searchBar.getEditText();
            if (et != null) {
                et.setText(searchedText);

                Search search = new Search(et);
                search.setDisableSearchIfEmpty(true);

                search.setOnSearchActionListener(new Search.OnSearchActionListener() {
                    @Override
                    public void onSearch(EditText view, String searchQuery) {
                        if (searchedText.equals(searchQuery)) {
                            return;
                        }

                        searchedText = searchQuery;
                        itemsAdapter.clearItems();
                        viewHolder.itemsShimmer.setVisibility(View.VISIBLE);
                        viewHolder.itemsShimmer.startShimmer();
                        itemsDataFetcher.readData(searchedText, new SearchItemsFetchHandler());
                    }
                });
            }
        }

        private void initializeHelperTextView() {
            String helperText = "Searching for " + "\"" + searchedText + "\"..." ;
            helperTextView = findViewById(R.id.text_helper_search_text);
            helperTextView.setText(helperText);
        }

        private void initializeItemsRecyclerView() {
            itemsRecyclerView = new ItemsRecyclerView(SearchResultsActivity.this, findViewById(R.id.recycler_view_search_items), RecyclerViewLayoutType.GRID);
            itemsAdapter = itemsRecyclerView.getAdapter();
        }

        private void initializeBottomBarViews() {
            bottomNavBar = findViewById(R.id.bottom_navigation_search_results);
            bottomNavBar.setOnItemSelectedListener(new NavigationAdapter(SearchResultsActivity.this).navigationListener);
        }
    }

    private class SearchItemsFetchHandler implements IFetchHandler<List<IItem>> {
        @Override
        public void onFetchComplete(List<IItem> data) {
            itemsAdapter.addItems(data);
            viewHolder.itemsShimmer.setVisibility(View.INVISIBLE);
            viewHolder.itemsRecyclerView.getRecyclerView().setVisibility(View.VISIBLE);

            setNumberResultsFoundText(data.size());
            Log.i(TAG, "Fetched items successfully");
        }

        @Override
        public void onFetchFail() {
            System.out.println("Failed to fetch items");
            viewHolder.itemsShimmer.hideShimmer();
            Toast.makeText(getApplicationContext(), "Failed to fetch items", Toast.LENGTH_SHORT).show();
        }
    }

    private void initializeLoadingState() {
        // Handles orientation changes as well
        GridLayout gridShimmer = findViewById(R.id.grid_shimmer_search);
        gridShimmer.setColumnCount(viewHolder.itemsRecyclerView.calculateNoOfColumns(ItemsRecyclerView.ITEM_COLUMN_WIDTH_DP));
    }

    private void setNumberResultsFoundText(int numResults) {
        String formatted = "Found " + numResults + " results for " + "\"" + searchedText + "\"";
        viewHolder.helperTextView.setText(formatted);
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
        setContentView(R.layout.activity_search_results);

        Intent intent = getIntent();
        searchedText = intent.getStringExtra(MainActivity.INTENT_KEY_SEARCH);

        viewHolder = new ViewHolder();

        initializeLoadingState();

        itemsDataFetcher.readData(searchedText, new SearchItemsFetchHandler());
    }
}
