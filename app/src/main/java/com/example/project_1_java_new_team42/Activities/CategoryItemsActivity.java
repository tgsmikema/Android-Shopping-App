package com.example.project_1_java_new_team42.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project_1_java_new_team42.Models.Category;
import com.example.project_1_java_new_team42.R;

public class CategoryItemsActivity extends AppCompatActivity {
    private static final String TAG = "CategoryItemsActivity";

    private Category category;

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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_items);

        category = constructCategoryFromIntent();
        setCategoryViews(category);

        // TODO Fetch using the doc id
        // String docId = category.getDocId();
    }
}
