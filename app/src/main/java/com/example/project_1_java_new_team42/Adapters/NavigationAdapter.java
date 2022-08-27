package com.example.project_1_java_new_team42.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.transition.Slide;
import android.transition.Transition;
import android.view.Gravity;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.example.project_1_java_new_team42.Activities.CartActivity;
import com.example.project_1_java_new_team42.Activities.MainActivity;
import com.example.project_1_java_new_team42.Activities.PastOrdersActivity;
import com.example.project_1_java_new_team42.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class NavigationAdapter {

    private Context context;
    private Activity activity;

    public static final String MAIN_ACTIVITY = "Activities.MainActivity";
    public static final String CART_ACTIVITY = "Activities.CartActivity";
    public static final String CATEGORY_ITEMS_ACTIVITY = "Activities.CategoryItemsActivity";
    public static final String DETAILS_ACTIVITY = "Activities.DetailsActivity";
    public static final String PAST_ORDER_ITEMS_ACTIVITY = "Activities.PastOrderItemsActivity";
    public static final String PAST_ORDERS_ACTIVITY = "Activities.PastOrdersActivity";
    public static final String SEARCH_RESULT_ACTIVITY = "Activities.SearchResultsActivity";

    public NavigationAdapter(Context context){
        this.context = context;
        this.activity = (Activity) context;
    }

    public void applyTransition() {
        activity.overridePendingTransition(R.anim.fade_activity_in, android.R.anim.fade_out);
    }

    // Logic of Navigation Bar selection.
    public NavigationBarView.OnItemSelectedListener navigationListener =
            new NavigationBarView.OnItemSelectedListener() {
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
