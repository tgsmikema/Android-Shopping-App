package com.example.project_1_java_new_team42.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.project_1_java_new_team42.Adapters.CategoriesRecyclerViewAdapter;
import com.example.project_1_java_new_team42.Adapters.ImageSliderAdapter;
import com.example.project_1_java_new_team42.Data.Fetchers.IFetchHandler;
import com.example.project_1_java_new_team42.Data.Fetchers.ItemDetailsDataFetcher;
import com.example.project_1_java_new_team42.Models.IItem;
import com.example.project_1_java_new_team42.R;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    protected ImageSliderAdapter imageSliderAdapter;
    protected ViewPager imageSlider;
    protected CircularProgressIndicator imageSpinner;
    protected ItemDetailsDataFetcher itemDetailsDataFetcher = new ItemDetailsDataFetcher();

    private class ItemDetailsFetchHandler implements IFetchHandler<List<IItem>> {

            @Override
            public void onFetchComplete(List<IItem> data) {

                imageSliderAdapter.setData(data);
                imageSlider.setAdapter(imageSliderAdapter);

                imageSpinner.setVisibility(View.GONE);
            }

            @Override
            public void onFetchFail() {
                System.out.println("Failed to fetch top items");
                Toast.makeText(getApplicationContext(), "Failed to fetch item details", Toast.LENGTH_SHORT).show();
            }
        }

        protected void initializeImageSlider() {
            imageSliderAdapter = new ImageSliderAdapter(this);
        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        imageSlider = findViewById(R.id.image_slide_details);
        imageSpinner = findViewById(R.id.progress_images);

        initializeImageSlider();

        itemDetailsDataFetcher.readData("laptop_6", new ItemDetailsFetchHandler());
    }
}