package com.example.project_1_java_new_team42.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.example.project_1_java_new_team42.activities.CartActivity;
import com.example.project_1_java_new_team42.activities.MainActivity;
import com.example.project_1_java_new_team42.activities.PastOrdersActivity;
import com.example.project_1_java_new_team42.R;
import com.google.android.material.navigation.NavigationBarView;

public class NavigationAdapter {

    private Context context;
    private Activity activity;

    public static final String MAIN_ACTIVITY = "activities.MainActivity";
    public static final String CART_ACTIVITY = "activities.CartActivity";
    public static final String PAST_ORDERS_ACTIVITY = "activities.PastOrdersActivity";

    public NavigationAdapter(Context context){
        this.context = context;
        this.activity = (Activity) context;
    }

    public void applyTransition() {
        activity.overridePendingTransition(R.anim.fade_activity_in, android.R.anim.fade_out);
    }

    // Logic of Navigation Bar selection.
    public NavigationBarView.OnItemSelectedListener navigationListener = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            String className = activity.getLocalClassName();

            switch(item.getItemId()) {
                case R.id.activity_home:
                    if (!className.equalsIgnoreCase(MAIN_ACTIVITY)){
                        context.startActivity(new Intent(context, MainActivity.class));
                        applyTransition();
                    }
                    return true;
                case R.id.activity_cart:
                    if (!className.equalsIgnoreCase(CART_ACTIVITY)){
                        context.startActivity(new Intent(context, CartActivity.class));
                        applyTransition();
                    }
                    return true;
                case R.id.activity_orders:
                    if (!className.equalsIgnoreCase(PAST_ORDERS_ACTIVITY)){
                        context.startActivity(new Intent(context, PastOrdersActivity.class));
                        applyTransition();
                    }
                    return true;
            }
            return false;
        }
    };

}
