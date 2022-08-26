package com.example.project_1_java_new_team42.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project_1_java_new_team42.R;
import com.google.android.material.button.MaterialButton;

public class SuccessfulOrderActivity extends AppCompatActivity {

    private void initializeOrdersPageButton() {
        MaterialButton button = findViewById(R.id.button_orders_page);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SuccessfulOrderActivity.this, PastOrdersActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initializeGoHomeButton() {
        MaterialButton button = findViewById(R.id.button_home);
        button.setOnClickListener(new View.OnClickListener() {
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
    }
}
