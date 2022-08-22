package com.example.project_1_java_new_team42.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project_1_java_new_team42.Adapters.ItemsRecyclerViewAdapter;
import com.example.project_1_java_new_team42.Adapters.NavigationAdapter;
import com.example.project_1_java_new_team42.Data.Fetchers.IFetchHandler;
import com.example.project_1_java_new_team42.Data.Fetchers.SearchItemsDataFetcher;
import com.example.project_1_java_new_team42.Models.IItem;
import com.example.project_1_java_new_team42.R;
import com.example.project_1_java_new_team42.Widgets.ItemsRecyclerView;
import com.example.project_1_java_new_team42.Widgets.RecyclerViewLayoutType;
import com.example.project_1_java_new_team42.Widgets.Search;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class SearchResultsActivity extends AppCompatActivity {
    private static final String TAG = "SearchResultsActivity";

    protected SearchItemsDataFetcher itemsDataFetcher = new SearchItemsDataFetcher();
    protected ItemsRecyclerViewAdapter itemsAdapter;
    protected CircularProgressIndicator spinner;
    protected String searchedText;

    protected NavigationAdapter navigationAdapter;

    private class SearchItemsFetchHandler implements IFetchHandler<List<IItem>> {
        @Override
        public void onFetchComplete(List<IItem> data) {
            itemsAdapter.addItems(data);
            spinner.setVisibility(View.GONE);
            setNumberResultsFoundText(data.size());
            Log.i(TAG, "Fetched items successfully");
        }

        @Override
        public void onFetchFail() {
            System.out.println("Failed to fetch items");
            Toast.makeText(getApplicationContext(), "Failed to fetch items", Toast.LENGTH_SHORT).show();
        }
    }

    protected void initializeItemsRecyclerView() {
        ItemsRecyclerView searchedItemsRV = new ItemsRecyclerView(this, findViewById(R.id.recycler_view_search_items), RecyclerViewLayoutType.GRID);
        itemsAdapter = searchedItemsRV.getAdapter();
    }

    private void setSearchBarText() {
        TextInputLayout til = findViewById(R.id.text_input_layout_search_results);
        EditText et = til.getEditText();
        if (et != null) {
            et.setText(searchedText);
        }
    }

    private void setNumberResultsFoundText(int numResults) {
        TextView textView = findViewById(R.id.text_search_results_found);
        if (textView != null) {
            String formatted = "Found " + numResults + " results for " + "\"" + searchedText + "\"";
            textView.setText(formatted);
        }
    }

    protected void initializeSearch() {
        TextInputLayout searchTextInputLayout = findViewById(R.id.text_input_layout_search_results);
        Search search = new Search(searchTextInputLayout.getEditText());
        search.setDisableSearchIfEmpty(true);

        search.setOnSearchActionListener(new Search.OnSearchActionListener() {
            @Override
            public void onSearch(EditText view, String searchQuery) {
                searchedText = searchQuery;
                itemsAdapter.clearItems();
                spinner.setVisibility(View.VISIBLE);
                itemsDataFetcher.readData(searchedText, new SearchItemsFetchHandler());
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        Intent intent = getIntent();
        searchedText = intent.getStringExtra(MainActivity.INTENT_KEY_SEARCH);
        spinner = findViewById(R.id.progress_search);

        setSearchBarText();
        initializeItemsRecyclerView();
        initializeSearch();

        itemsDataFetcher.readData(searchedText, new SearchItemsFetchHandler());

        //TODO(Refactor) put this inside ViewHolder
        NavigationBarView bottomNavBar = findViewById(R.id.bottom_navigation_search_results);

        // Highlight the Selected Navigation ICON
        bottomNavBar.setSelectedItemId(R.id.activity_home);
        // Add the Bottom Bar Navigation Logic
        navigationAdapter = new NavigationAdapter(this);
        bottomNavBar.setOnItemSelectedListener(navigationAdapter.navigationListener);
    }
}
