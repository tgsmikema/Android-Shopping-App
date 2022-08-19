package com.example.project_1_java_new_team42.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;

    private class ItemDetailsFetchHandler implements IFetchHandler<List<IItem>> {

            @Override
            public void onFetchComplete(List<IItem> data) {

                imageSliderAdapter.setData(data);
                imageSlider.setAdapter(imageSliderAdapter);

                imageSpinner.setVisibility(View.GONE);

                dotscount = imageSliderAdapter.getCount();
                dots = new ImageView[dotscount];

                for(int i = 0; i < dotscount; i++){

                    dots[i] = new ImageView(DetailsActivity.this);
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                    params.setMargins(8, 0, 8, 0);

                    sliderDotspanel.addView(dots[i], params);

                }

                dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

                imageSlider.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {

                        for(int i = 0; i< dotscount; i++){
                            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                        }

                        dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
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

        //---------------------------------------
        sliderDotspanel = (LinearLayout) findViewById(R.id.image_slider_dots);


        //------------------------------------------





        itemDetailsDataFetcher.readData("tablet_1", new ItemDetailsFetchHandler());
    }
}