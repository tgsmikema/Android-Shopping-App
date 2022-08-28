package com.example.project_1_java_new_team42.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project_1_java_new_team42.adapters.ItemsRecyclerViewAdapter;
import com.example.project_1_java_new_team42.adapters.NavigationAdapter;
import com.example.project_1_java_new_team42.data.Fetchers.CategoryItemsDataFetcher;
import com.example.project_1_java_new_team42.data.Fetchers.ICategoryItemsDataFetcher;
import com.example.project_1_java_new_team42.data.Fetchers.IFetchHandler;
import com.example.project_1_java_new_team42.models.Category;
import com.example.project_1_java_new_team42.models.IItem;
import com.example.project_1_java_new_team42.R;
import com.example.project_1_java_new_team42.util.ItemUtil;
import com.example.project_1_java_new_team42.widgets.ItemsRecyclerView;
import com.example.project_1_java_new_team42.widgets.RecyclerViewLayoutType;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

public class CategoryItemsActivity extends AppCompatActivity {
    private static final String TAG = "CategoryItemsActivity";

    private final ICategoryItemsDataFetcher itemsFetcher = new CategoryItemsDataFetcher();
    private ItemsRecyclerViewAdapter itemsAdapter;

    private Category category;

    private ViewHolder viewHolder;

    private class ViewHolder {
        TextView headingTextView;
        ImageView headingImageView;
        ShimmerFrameLayout itemsShimmer;
        ItemsRecyclerView itemsRecyclerView;
        Button backButton;
        NavigationBarView bottomNavBar;

        public ViewHolder() {
            initializeHeadingText();
            initializeHeadingImage();
            initializeBackButton();
            itemsShimmer = findViewById(R.id.shimmer_search_items);
            initializeItemsRecyclerView();
            initializeBottomBarViews();
        }


        private void initializeHeadingText() {
            headingTextView = findViewById(R.id.text_category_heading);
            headingTextView.setText(category.getCategoryName());
        }

        private void initializeHeadingImage() {
            headingImageView = findViewById(R.id.image_category_header);
            int drawableId = ItemUtil.getImageDrawableId(CategoryItemsActivity.this, category.getImageURI());
            headingImageView.setImageResource(drawableId);
        }

        private void initializeBackButton() {
            backButton = findViewById(R.id.button_back_category_items);
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    supportFinishAfterTransition();
                }
            });
        }

        private void initializeItemsRecyclerView() {
            itemsRecyclerView = new ItemsRecyclerView(CategoryItemsActivity.this, findViewById(R.id.recycler_view_category_items), RecyclerViewLayoutType.GRID);
            itemsAdapter = itemsRecyclerView.getAdapter();
            itemsAdapter.relayCategory(category);
        }

        private void initializeBottomBarViews() {
            bottomNavBar = findViewById(R.id.bottom_navigation_category_items);
            bottomNavBar.setSelectedItemId(R.id.activity_home);
            bottomNavBar.setOnItemSelectedListener(new NavigationAdapter(CategoryItemsActivity.this).navigationListener);
        }
    }

    private class CategoryItemsFetchHandler implements IFetchHandler<List<IItem>> {
        @Override
        public void onFetchComplete(List<IItem> data) {
           itemsAdapter.addItems(data);
           viewHolder.itemsRecyclerView.getRecyclerView().setVisibility(View.VISIBLE);
           viewHolder.itemsShimmer.setVisibility(View.INVISIBLE);
           Log.i(TAG, "Fetched category  successfully");

           startPostponedEnterTransition();
        }

        @Override
        public void onFetchFail() {
            System.out.println("Failed to fetch items");
            viewHolder.itemsShimmer.hideShimmer();
            Toast.makeText(getApplicationContext(), "Failed to fetch items", Toast.LENGTH_SHORT).show();
        }
    }

    private Category constructCategoryFromIntent() {
        Intent intent = getIntent();
        String categoryName = intent.getStringExtra(MainActivity.INTENT_KEY_CATEGORY_NAME);
        String categoryImageUri = intent.getStringExtra(MainActivity.INTENT_KEY_CATEGORY_IMAGE_URI);
        return new Category(categoryName, categoryImageUri);
    }

    private void startLoadingState() {
        // Handles orientation changes as well
        GridLayout gridShimmer = findViewById(R.id.grid_shimmer_search);
        gridShimmer.setColumnCount(viewHolder.itemsRecyclerView.calculateNoOfColumns(ItemsRecyclerView.ITEM_COLUMN_WIDTH_DP));
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_items);

        postponeEnterTransition();

        category = constructCategoryFromIntent();

        viewHolder = new ViewHolder();

        startLoadingState();
        itemsFetcher.readData(category.getDocId(), new CategoryItemsFetchHandler());
    }
}
