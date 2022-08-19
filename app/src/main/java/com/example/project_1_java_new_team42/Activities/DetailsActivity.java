package com.example.project_1_java_new_team42.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.project_1_java_new_team42.Adapters.ImageSliderAdapter;
import com.example.project_1_java_new_team42.Data.Fetchers.IFetchHandler;
import com.example.project_1_java_new_team42.Data.Fetchers.ItemDetailsDataFetcher;
import com.example.project_1_java_new_team42.Models.IItem;
import com.example.project_1_java_new_team42.R;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    private class ViewHolder {

        ViewPager imageSlider;
        CircularProgressIndicator imageSpinner;
        LinearLayout imageSliderDotPanel;

        public ViewHolder() {
            imageSlider = findViewById(R.id.image_slide_details);
            imageSpinner = findViewById(R.id.progress_images);
            imageSliderDotPanel = findViewById(R.id.image_slider_dots);
        }
    }

    ViewHolder vh;

    protected ImageSliderAdapter imageSliderAdapter;
    protected ItemDetailsDataFetcher itemDetailsDataFetcher = new ItemDetailsDataFetcher();

    private int dotsCount;
    private ImageView[] dots;

    private class ItemDetailsFetchHandler implements IFetchHandler<List<IItem>> {

            @Override
            public void onFetchComplete(List<IItem> data) {
                // pass data from DB to image slider adapter to populate.
                imageSliderAdapter.setData(data);
                vh.imageSlider.setAdapter(imageSliderAdapter);
                // remove the display of the waiting icon from the screen
                vh.imageSpinner.setVisibility(View.GONE);
                // display the image slider dots
                initializeImageSliderDots();
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

        protected void initializeImageSliderDots(){
            dotsCount = imageSliderAdapter.getCount();
            dots = new ImageView[dotsCount];
            for(int i = 0; i < dotsCount; i++){
                dots[i] = new ImageView(DetailsActivity.this);
                dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(8, 0, 8, 0);
                vh.imageSliderDotPanel.addView(dots[i], layoutParams);
            }
            dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
            vh.imageSlider.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
                @Override
                public void onPageSelected(int position) {
                    for(int i = 0; i< dotsCount; i++){
                        dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                    }
                    dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
                }
                @Override
                public void onPageScrollStateChanged(int state) {}
            });
        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        vh = new ViewHolder();

        initializeImageSlider();
        // ----------TESTING PURPOSE, REPLACE Once Developed Navigation-------(API)--------
        // Intent intent = getIntent();
        // String str = intent.getStringExtra("message_key");
        itemDetailsDataFetcher.readData("laptop_1", new ItemDetailsFetchHandler());
        // pass in id(string) of the selected Item.
        // SENDER SIDE (API) CODE
        // Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
        // intent.putExtra("message_key", "string");
        // startActivity(intent);
        // -----------------------------TESTING ENDS----------------------------------------


    }
}