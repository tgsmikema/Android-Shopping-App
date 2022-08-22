package com.example.project_1_java_new_team42.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.example.project_1_java_new_team42.Widgets.ItemsRecyclerView;
import com.example.project_1_java_new_team42.Widgets.RecyclerViewLayoutType;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.List;

public class CategoryItemsActivity extends AppCompatActivity {
    private static final String TAG = "CategoryItemsActivity";

    private final CategoryItemsDataFetcher itemsFetcher = new CategoryItemsDataFetcher();
    private ItemsRecyclerViewAdapter itemsAdapter;
    private CircularProgressIndicator spinner;

    private Category category;

    private NavigationAdapter navigationAdapter;

    private class CategoryItemsFetchHandler implements IFetchHandler<List<IItem>> {
        @Override
        public void onFetchComplete(List<IItem> data) {
           itemsAdapter.addItems(data);
           spinner.setVisibility(View.GONE);
           Log.i(TAG, "Fetched category  successfully");
        }

        @Override
        public void onFetchFail() {
            System.out.println("Failed to fetch items");
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
        int drawableId = getResources().getIdentifier(category.getImageURI(), "drawable", getPackageName());
        headingImageView.setImageResource(drawableId);
    }

    private View.OnClickListener buttonListener = new View.OnClickListener() {
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.button_back_category_items:
                    navigateBackToPreviousActivity();
                    break;

                default:
                    break;
            }
        }
    };

    public void navigateBackToPreviousActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void initializeItemsRecyclerView() {
        ItemsRecyclerView searchedItemsRV = new ItemsRecyclerView(this, findViewById(R.id.recycler_view_category_items), RecyclerViewLayoutType.GRID);
        itemsAdapter = searchedItemsRV.getAdapter();
        itemsAdapter.relayCategory(category);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_items);

        category = constructCategoryFromIntent();
        setCategoryViews(category);

        spinner = findViewById(R.id.progress_category_items);

        initializeItemsRecyclerView();

        String docId = category.getDocId();
        itemsFetcher.readData(docId, new CategoryItemsFetchHandler());

        //TODO(Refactor) put this 2 line inside ViewHolder
        NavigationBarView bottomNavBar = findViewById(R.id.bottom_navigation_category_items);
        Button backButton = findViewById(R.id.button_back_category_items);

        backButton.setOnClickListener(buttonListener);

        // Highlight the Selected Navigation ICON
        bottomNavBar.setSelectedItemId(R.id.activity_home);
        // Add the Bottom Bar Navigation Logic
        navigationAdapter = new NavigationAdapter(this);
        bottomNavBar.setOnItemSelectedListener(navigationAdapter.navigationListener);
    }
}
