package com.example.project_1_java_new_team42.Activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project_1_java_new_team42.Data.CategoriesDataFetcher;
import com.example.project_1_java_new_team42.Data.IFetchHandler;
import com.example.project_1_java_new_team42.Models.Category;
import com.example.project_1_java_new_team42.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    CategoriesDataFetcher categoriesDataFetcher = new CategoriesDataFetcher();

    private static class CategoriesFetchHandler implements IFetchHandler<List<Category>> {
        @Override
        public void onFetchComplete(List<Category> data) {
            for (Category category : data) {
                Log.d("INFO", category.getCategoryName() + ": " + category.getImageUri());
            }
        }

        @Override
        public void onFetchFail() {
            System.out.println("Failed to fetch categories");
        }
    }

    protected void displayCategories() {
        categoriesDataFetcher.fetchItems(new CategoriesFetchHandler());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayCategories();
    }
}
