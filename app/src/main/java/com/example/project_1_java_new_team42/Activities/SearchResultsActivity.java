package com.example.project_1_java_new_team42.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_1_java_new_team42.Adapters.ItemsRecyclerViewAdapter;
import com.example.project_1_java_new_team42.Data.Fetchers.IFetchHandler;
import com.example.project_1_java_new_team42.Data.Fetchers.SearchItemsDataFetcher;
import com.example.project_1_java_new_team42.Models.IItem;
import com.example.project_1_java_new_team42.R;
import com.example.project_1_java_new_team42.Widgets.ItemOffsetDecoration;
import com.example.project_1_java_new_team42.Widgets.Search;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class SearchResultsActivity extends AppCompatActivity {
    private static final String TAG = "SearchResultsActivity";
    private static final float COLUMN_WIDTH_DP = 200;

    // TODO May need to extract this out as it will most likely be needed in another class
    public int calculateNoOfColumns(float columnWidthDp) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (screenWidthDp / columnWidthDp + 0.5); // +0.5 for correct rounding to int.
    }

    protected SearchItemsDataFetcher itemsDataFetcher = new SearchItemsDataFetcher();
    protected RecyclerView itemsRecyclerView;
    protected ItemsRecyclerViewAdapter itemsAdapter;
    protected CircularProgressIndicator spinner;
    protected String searchedText;

    private class SearchItemsFetchHandler implements IFetchHandler<List<IItem>> {
        @Override
        public void onFetchComplete(List<IItem> data) {
            itemsAdapter.setData(data);
            itemsAdapter.notifyItemRangeInserted(0, data.size());

            spinner.setVisibility(View.GONE);

            // Set text of number results found
            setSearchResultsFoundText(data.size());

            Log.i(TAG, "Fetched items successfully");
        }

        @Override
        public void onFetchFail() {
            System.out.println("Failed to fetch items");
            Toast.makeText(getApplicationContext(), "Failed to fetch items", Toast.LENGTH_SHORT).show();
        }
    }

    protected void initializeItemsRecyclerView() {
        int numColumns = calculateNoOfColumns(COLUMN_WIDTH_DP);
        itemsRecyclerView = findViewById(R.id.recycler_view_search_items);
        itemsRecyclerView.setLayoutManager(new GridLayoutManager(this, numColumns));

        ItemOffsetDecoration decoration = new ItemOffsetDecoration(this, R.dimen.rv_card_item_hor_offset, R.dimen.rv_card_item_ver_offset, numColumns);
        itemsRecyclerView.addItemDecoration(decoration);

        itemsAdapter = new ItemsRecyclerViewAdapter(this);
        itemsRecyclerView.setAdapter(itemsAdapter);
    }

    private void setSearchBarText() {
        TextInputLayout til = findViewById(R.id.text_input_layout_search_results);
        EditText et = til.getEditText();
        if (et != null) {
            et.setText(searchedText);
        }
    }

    private void setSearchResultsFoundText(int numResults) {
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
                itemsAdapter.clearData();
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
    }
}
