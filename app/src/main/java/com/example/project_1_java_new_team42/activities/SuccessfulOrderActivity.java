package com.example.project_1_java_new_team42.activities;

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

    private ViewHolder vh;

    private class ViewHolder {
        ImageView checkMark;
        TextView heading;
        TextView subheading;
        MaterialButton ordersButton;
        MaterialButton homeButton;

        public ViewHolder() {
            checkMark = findViewById(R.id.image_check_mark);
            heading = findViewById(R.id.text_order_placed);
            subheading = findViewById(R.id.text_thanks_for_shopping);
            ordersButton = findViewById(R.id.button_orders_page);
            homeButton = findViewById(R.id.button_home);
        }
    }

    private void animateActivity() {
        vh.checkMark.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_simple));
        vh.heading.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in_simple));
        vh.subheading.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in_simple));
        vh.ordersButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in_simple));
        vh.homeButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in_simple));
    }

    private void initializeOrdersPageButton() {
        vh.ordersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SuccessfulOrderActivity.this, PastOrdersActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initializeGoHomeButton() {
        vh.homeButton.setOnClickListener(new View.OnClickListener() {
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

        vh = new ViewHolder();

        initializeOrdersPageButton();
        initializeGoHomeButton();

        animateActivity();
    }
}
