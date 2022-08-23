package com.example.project_1_java_new_team42.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_1_java_new_team42.Adapters.ItemsRecyclerViewAdapter;
import com.example.project_1_java_new_team42.Adapters.NavigationAdapter;
import com.example.project_1_java_new_team42.Data.Fetchers.IFetchHandler;
import com.example.project_1_java_new_team42.Data.Fetchers.SearchItemsDataFetcher;
import com.example.project_1_java_new_team42.Models.IItem;
import com.example.project_1_java_new_team42.R;
import com.example.project_1_java_new_team42.Widgets.ItemsRecyclerView;
import com.example.project_1_java_new_team42.Widgets.RecyclerViewLayoutType;
import com.example.project_1_java_new_team42.Widgets.Search;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class SearchResultsActivity extends AppCompatActivity {
    private static final String TAG = "SearchResultsActivity";

    private final SearchItemsDataFetcher itemsDataFetcher = new SearchItemsDataFetcher();
    private ItemsRecyclerViewAdapter itemsAdapter;
    private ItemsRecyclerView itemsRecyclerView;
    private ShimmerFrameLayout itemsShimmer;
    private TextView helperTextView;
    private String searchedText;

    protected NavigationAdapter navigationAdapter;

    private class SearchItemsFetchHandler implements IFetchHandler<List<IItem>> {
        @Override
        public void onFetchComplete(List<IItem> data) {
            itemsAdapter.addItems(data);
            itemsShimmer.setVisibility(View.INVISIBLE);
            itemsRecyclerView.getRecyclerView().setVisibility(View.VISIBLE);

            setNumberResultsFoundText(data.size());
            Log.i(TAG, "Fetched items successfully");
        }

        @Override
        public void onFetchFail() {
            System.out.println("Failed to fetch items");
            itemsShimmer.hideShimmer();
            Toast.makeText(getApplicationContext(), "Failed to fetch items", Toast.LENGTH_SHORT).show();
        }
    }

    protected void initializeItemsRecyclerView() {
        RecyclerView rv = findViewById(R.id.recycler_view_search_items);
        rv.setVisibility(View.INVISIBLE);
        itemsRecyclerView = new ItemsRecyclerView(this, rv, RecyclerViewLayoutType.GRID);
        itemsAdapter = itemsRecyclerView.getAdapter();
        itemsAdapter.relaySearchString(searchedText);
    }

    private void initializeSearchBarText(String text) {
        TextInputLayout til = findViewById(R.id.text_input_layout_search_results);
        EditText et = til.getEditText();
        if (et != null) {
            et.setText(text);
        }
    }

    private void initializeHelperText() {
        String helperText = "Searching for " + "\"" + searchedText + "\"..." ;
        helperTextView = findViewById(R.id.text_helper_search_text);
        helperTextView.setText(helperText);
    }

    private void initializeLoadingState() {
        // Handles orientation changes as well
        itemsShimmer = findViewById(R.id.shimmer_search_items);
        GridLayout gridShimmer = findViewById(R.id.grid_shimmer_search);
        gridShimmer.setColumnCount(itemsRecyclerView.calculateNoOfColumns(ItemsRecyclerView.ITEM_COLUMN_WIDTH_DP));
    }

    private void setNumberResultsFoundText(int numResults) {
        String formatted = "Found " + numResults + " results for " + "\"" + searchedText + "\"";
        helperTextView.setText(formatted);
    }

    protected void initializeSearchFunctionality() {
        TextInputLayout searchTextInputLayout = findViewById(R.id.text_input_layout_search_results);
        Search search = new Search(searchTextInputLayout.getEditText());
        search.setDisableSearchIfEmpty(true);

        search.setOnSearchActionListener(new Search.OnSearchActionListener() {
            @Override
            public void onSearch(EditText view, String searchQuery) {
                searchedText = searchQuery;
                itemsAdapter.clearItems();
                itemsShimmer.setVisibility(View.VISIBLE);
                itemsShimmer.startShimmer();
                itemsDataFetcher.readData(searchedText, new SearchItemsFetchHandler());
            }
        });
    }

    private View.OnClickListener buttonListener = new View.OnClickListener() {
        public void onClick(View view) {
            navigateBackToPreviousActivity();
        }
    };

    public void navigateBackToPreviousActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        Intent intent = getIntent();
        searchedText = intent.getStringExtra(MainActivity.INTENT_KEY_SEARCH);

        initializeSearchBarText(searchedText);
        initializeHelperText();

        initializeItemsRecyclerView();
        initializeLoadingState();

        initializeSearchFunctionality();

        itemsDataFetcher.readData(searchedText, new SearchItemsFetchHandler());

        //TODO(Refactor) put this 2 lines inside ViewHolder
        NavigationBarView bottomNavBar = findViewById(R.id.bottom_navigation_search_results);
        Button backButton = findViewById(R.id.button_back_search_results);

        backButton.setOnClickListener(buttonListener);
        // Highlight the Selected Navigation ICON
        bottomNavBar.setSelectedItemId(R.id.activity_home);
        // Add the Bottom Bar Navigation Logic
        navigationAdapter = new NavigationAdapter(this);
        bottomNavBar.setOnItemSelectedListener(navigationAdapter.navigationListener);
    }
}
