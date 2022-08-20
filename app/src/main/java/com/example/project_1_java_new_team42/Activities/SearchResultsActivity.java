package com.example.project_1_java_new_team42.Activities;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class SearchResultsActivity extends AppCompatActivity {
    private static final String TAG = "SearchResultsActivity";
    private static final int NUM_COLUMNS = 2;

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
        itemsRecyclerView = findViewById(R.id.recycler_view_search_items);
        itemsRecyclerView.setLayoutManager(new GridLayoutManager(this, NUM_COLUMNS));

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        Intent intent = getIntent();
        searchedText = intent.getStringExtra(MainActivity.INTENT_KEY_SEARCH);
        spinner = findViewById(R.id.progress_search);

        setSearchBarText();
        initializeItemsRecyclerView();

        itemsDataFetcher.readData(searchedText, new SearchItemsFetchHandler());
    }
}
