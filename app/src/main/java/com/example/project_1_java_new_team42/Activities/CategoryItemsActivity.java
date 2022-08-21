package com.example.project_1_java_new_team42.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project_1_java_new_team42.R;

public class CategoryItemsActivity extends AppCompatActivity {
    private static final String TAG = "CategoryItemsActivity";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_items);

        Intent intent = getIntent();
        String category = intent.getStringExtra(MainActivity.INTENT_KEY_CATEGORY);
        TextView headingTextView = findViewById(R.id.text_category_heading);
        headingTextView.setText(category);

        // TODO Make dynamic
        ImageView headerImageView = findViewById(R.id.image_category_header);
        headerImageView.setImageResource(R.drawable.category_laptop);
    }
}
