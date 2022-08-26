package com.example.project_1_java_new_team42.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project_1_java_new_team42.R;
import com.google.android.material.button.MaterialButton;

public class SuccessfulOrderActivity extends AppCompatActivity {

    private MaterialButton ordersButton;
    private MaterialButton homeButton;

    private void animateActivity() {
        ImageView checkMark = findViewById(R.id.image_check_mark);
        TextView heading = findViewById(R.id.text_order_placed);
        TextView subheading = findViewById(R.id.text_thanks_for_shopping);

        checkMark.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_simple));
        heading.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in_simple));
        subheading.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in_simple));
        ordersButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in_simple));
        homeButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in_simple));
    }

    private void initializeOrdersPageButton() {
        ordersButton = findViewById(R.id.button_orders_page);
        ordersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SuccessfulOrderActivity.this, PastOrdersActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initializeGoHomeButton() {
        homeButton = findViewById(R.id.button_home);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SuccessfulOrderActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful_order);

        initializeOrdersPageButton();
        initializeGoHomeButton();

        animateActivity();
    }
}
