package com.example.project_1_java_new_team42.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_1_java_new_team42.Adapters.CategoriesRecyclerViewAdapter;
import com.example.project_1_java_new_team42.Data.CategoryDataFetcher;
import com.example.project_1_java_new_team42.Data.IFetchHandler;
import com.example.project_1_java_new_team42.Models.Category;
import com.example.project_1_java_new_team42.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CategoriesRecyclerViewAdapter.CategoryClickListener {

    protected RecyclerView categoriesRecyclerView;
    protected CategoriesRecyclerViewAdapter categoriesAdapter;
    protected CategoryDataFetcher categoriesDataFetcher = new CategoryDataFetcher();

    private class CategoriesFetchHandler implements IFetchHandler<List<Category>> {
        @Override
        public void onFetchComplete(List<Category> data) {
            categoriesAdapter.setData(data);
            categoriesAdapter.notifyItemRangeInserted(0, data.size());
        }

        @Override
        public void onFetchFail() {
            System.out.println("Failed to fetch categories");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* We have to initialize the recycler view and its adapter bindings in the main UI thread to
        *  prevent warnings. The adapter has a setter method to set the data so can do this when fetch success.
        * */
        categoriesRecyclerView = findViewById(R.id.recycler_view_category_cards);
        categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        categoriesAdapter = new CategoriesRecyclerViewAdapter(this);
        categoriesAdapter.setClickListener(MainActivity.this);

        categoriesRecyclerView.setAdapter(categoriesAdapter);

        categoriesDataFetcher.readData(new CategoriesFetchHandler());
    }

    @Override
    public void onCategoryCardClick(View view, int position) {
        Category category = categoriesAdapter.getItem(position);
        Log.i("Category", "Name: " + category.getCategoryName() + " URI: " + category.getImageURI());
    }
}

