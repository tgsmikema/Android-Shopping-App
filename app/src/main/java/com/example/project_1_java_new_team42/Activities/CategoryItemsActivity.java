package com.example.project_1_java_new_team42.Activities;

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

import com.example.project_1_java_new_team42.Adapters.ItemsRecyclerViewAdapter;
import com.example.project_1_java_new_team42.Adapters.NavigationAdapter;
import com.example.project_1_java_new_team42.Data.Fetchers.CategoryItemsDataFetcher;
import com.example.project_1_java_new_team42.Data.Fetchers.IFetchHandler;
import com.example.project_1_java_new_team42.Models.Category;
import com.example.project_1_java_new_team42.Models.IItem;
import com.example.project_1_java_new_team42.R;
import com.example.project_1_java_new_team42.Util.ItemUtil;
import com.example.project_1_java_new_team42.Widgets.ItemsRecyclerView;
import com.example.project_1_java_new_team42.Widgets.RecyclerViewLayoutType;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

public class CategoryItemsActivity extends AppCompatActivity {
    private static final String TAG = "CategoryItemsActivity";

    private final CategoryItemsDataFetcher itemsFetcher = new CategoryItemsDataFetcher();
    private ItemsRecyclerViewAdapter itemsAdapter;
    private ItemsRecyclerView itemsRecyclerView;
    private ShimmerFrameLayout itemsShimmer;

    private Category category;

    private class CategoryItemsFetchHandler implements IFetchHandler<List<IItem>> {
        @Override
        public void onFetchComplete(List<IItem> data) {
           itemsAdapter.addItems(data);
           itemsRecyclerView.getRecyclerView().setVisibility(View.VISIBLE);
           itemsShimmer.setVisibility(View.INVISIBLE);
           Log.i(TAG, "Fetched category  successfully");

           startPostponedEnterTransition();
        }

        @Override
        public void onFetchFail() {
            System.out.println("Failed to fetch items");
            itemsShimmer.hideShimmer();
            Toast.makeText(getApplicationContext(), "Failed to fetch items", Toast.LENGTH_SHORT).show();
        }
    }

    private Category constructCategoryFromIntent() {
        Intent intent = getIntent();
        String categoryName = intent.getStringExtra(MainActivity.INTENT_KEY_CATEGORY_NAME);
        String categoryImageUri = intent.getStringExtra(MainActivity.INTENT_KEY_CATEGORY_IMAGE_URI);
        return new Category(categoryName, categoryImageUri);
    }

    private void setCategoryViews(Category category) {
        TextView headingTextView = findViewById(R.id.text_category_heading);
        headingTextView.setText(category.getCategoryName());

        ImageView headingImageView = findViewById(R.id.image_category_header);
        int drawableId = ItemUtil.getImageDrawableId(this, category.getImageURI());
        headingImageView.setImageResource(drawableId);
    }

    private void initializeBackButton() {
        Button backButton = findViewById(R.id.button_back_category_items);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supportFinishAfterTransition();
            }
        });
    }

    private final View.OnClickListener buttonListener = new View.OnClickListener() {
        public void onClick(View view) {
            supportFinishAfterTransition();
        }
    };

    private void initializeItemsRecyclerView() {
        itemsRecyclerView = new ItemsRecyclerView(this, findViewById(R.id.recycler_view_category_items), RecyclerViewLayoutType.GRID);
        itemsAdapter = itemsRecyclerView.getAdapter();
        itemsAdapter.relayCategory(category);
    }

    private void initializeLoadingState() {
        // Handles orientation changes as well
        itemsShimmer = findViewById(R.id.shimmer_search_items);
        GridLayout gridShimmer = findViewById(R.id.grid_shimmer_search);
        gridShimmer.setColumnCount(itemsRecyclerView.calculateNoOfColumns(ItemsRecyclerView.ITEM_COLUMN_WIDTH_DP));
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_items);

        postponeEnterTransition();

        category = constructCategoryFromIntent();
        setCategoryViews(category);

        initializeBackButton();
        initializeItemsRecyclerView();
        initializeLoadingState();

        String docId = category.getDocId();
        itemsFetcher.readData(docId, new CategoryItemsFetchHandler());

        //TODO(Refactor) put this 2 line inside ViewHolder
        NavigationBarView bottomNavBar = findViewById(R.id.bottom_navigation_category_items);

        // Highlight the Selected Navigation ICON
        bottomNavBar.setSelectedItemId(R.id.activity_home);
        // Add the Bottom Bar Navigation Logic
        NavigationAdapter navigationAdapter = new NavigationAdapter(this);
        bottomNavBar.setOnItemSelectedListener(navigationAdapter.navigationListener);
    }
}
