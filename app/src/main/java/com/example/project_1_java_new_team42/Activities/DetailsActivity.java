package com.example.project_1_java_new_team42.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_1_java_new_team42.Adapters.ImageSliderAdapter;
import com.example.project_1_java_new_team42.Adapters.NavigationAdapter;
import com.example.project_1_java_new_team42.Adapters.ItemsRecyclerViewAdapter;
import com.example.project_1_java_new_team42.Data.Fetchers.IFetchHandler;
import com.example.project_1_java_new_team42.Data.Fetchers.ItemDetailsDataFetcher;
import com.example.project_1_java_new_team42.Data.Senders.CartDataSender;
import com.example.project_1_java_new_team42.Data.Senders.ISendHandler;
import com.example.project_1_java_new_team42.Models.Category;
import com.example.project_1_java_new_team42.Models.IItem;
import com.example.project_1_java_new_team42.Models.ItemWithQuantity;
import com.example.project_1_java_new_team42.R;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    // global variables
    public static int initialQuantity = 1;
    public static boolean isAddedToCart = false;
    public static IItem item;

    // Image Slider Dots
    private int dotsCount;
    private ImageView[] dots;

    //Intent related variables
    private String activityNamePreviousLevel;
    private String categoryNamePreviousLevel;
    private String categoryImageUriPreviousLevel;
    private String searchStringPreviousLevel;

    ViewHolder vh;
    // Adapters
    protected ImageSliderAdapter imageSliderAdapter;
    protected NavigationAdapter navigationAdapter;

    // Database Interaction Classes
    protected ItemDetailsDataFetcher itemDetailsDataFetcher = new ItemDetailsDataFetcher();
    protected CartDataSender cartDataSender = new CartDataSender();

    // On Change Listener Watcher
    protected TextWatcherImpl textWatcherImpl = new TextWatcherImpl();

    // View Holders Class for all Views in the current Activity
    private class ViewHolder {

        ViewPager imageSlider;
        CircularProgressIndicator imageSpinner;
        LinearLayout imageSliderDotPanel;
        RelativeLayout quantityBar, addToCartSection;
        View divider;

        TextView itemName, itemDetail, itemPrice, itemTotalPrice, quantityText, quantity;
        Button decreaseBtn, increaseBtn, addCartButton, backButton;

        NavigationBarView bottomNavBar;


        public ViewHolder() {
            imageSlider = findViewById(R.id.image_slide_details);
            imageSpinner = findViewById(R.id.progress_images);
            imageSliderDotPanel = findViewById(R.id.image_slider_dots);
            quantityBar = findViewById(R.id.quantity_bar_details);
            addToCartSection = findViewById(R.id.add_to_cart_section_details);
            divider = findViewById(R.id.divider_details);
            // TextView
            itemName = findViewById(R.id.item_name_details);
            itemDetail = findViewById(R.id.item_detail_details);
            itemPrice = findViewById(R.id.item_price_details);
            itemTotalPrice = findViewById(R.id.item_total_price_details);
            quantityText = findViewById(R.id.quantity_text_details);
            quantity = findViewById(R.id.quantity_details);
            // Button
            decreaseBtn = findViewById(R.id.decrease_btn_details);
            increaseBtn = findViewById(R.id.increase_btn_details);
            addCartButton = findViewById(R.id.add_cart_button_details);
            backButton = findViewById(R.id.button_back_details);
            // Bottom Nav Bar
            bottomNavBar = findViewById(R.id.bottom_navigation_details);
        }
    }

    // Implementation of CartDataSendHandler.
    private class CartDataSendHandler implements ISendHandler {

        @Override
        public void onSendSuccess(boolean isSuccess) {
            if (!isAddedToCart) {
                isAddedToCart = true;
                Toast.makeText(DetailsActivity.this, "Item(s) has been Added to cart!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(DetailsActivity.this, "Item(s) in the Cart has been Updated!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Implementation of ItemDetailsFetchHandler.
    private class ItemDetailsFetchHandler implements IFetchHandler<List<IItem>> {
        @Override
        public void onFetchComplete(List<IItem> data) {
            item = data.get(0);

            // pass data from DB to image slider adapter to populate.
            imageSliderAdapter.setData(data);
            vh.imageSlider.setAdapter(imageSliderAdapter);

            // remove the display of the waiting icon from the screen
            vh.imageSpinner.setVisibility(View.GONE);

            // display the image slider dots
            initializeImageSliderDots();
            initializeItemDetails();

            vh.quantity.addTextChangedListener(textWatcherImpl);

        }
        @Override
        public void onFetchFail() {
            System.out.println("Failed to fetch top items");
            Toast.makeText(getApplicationContext(), "Failed to fetch item details", Toast.LENGTH_SHORT).show();
        }
    }

    // Implementation of TextWatcher for On Change Listener
    private class TextWatcherImpl implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
        @Override
        public void afterTextChanged(Editable editable) {
            int currentQuantity = Integer.parseInt(vh.quantity.getText().toString());

            // Logic for Gray out button when limit of quantity REACHED.
            if (currentQuantity == 1) {
                vh.decreaseBtn.setBackgroundResource(R.drawable.ic_decrease_btn_not_available);
                vh.increaseBtn.setBackgroundResource(R.drawable.ic_increase_btn_available);
            } else if ((currentQuantity < 9) && (currentQuantity > 1)) {
                vh.decreaseBtn.setBackgroundResource(R.drawable.ic_decrease_btn_available);
                vh.increaseBtn.setBackgroundResource(R.drawable.ic_increase_btn_available);
            } else {
                vh.decreaseBtn.setBackgroundResource(R.drawable.ic_decrease_btn_available);
                vh.increaseBtn.setBackgroundResource(R.drawable.ic_increase_btn_not_available);
            }
            // Update Total Price
            String totalPrice = "Total $ " + item.getPrice() *
                    Integer.parseInt(vh.quantity.getText().toString());
            vh.itemTotalPrice.setText(totalPrice);
        }
    }

    // On Click Listener for All Buttons Logic Compilation
    private View.OnClickListener buttonListener = new View.OnClickListener() {
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.decrease_btn_details:
                    initialQuantity--;
                    if (initialQuantity < 1) {
                        initialQuantity = 1;
                    }
                    vh.quantity.setText(String.valueOf(initialQuantity));
                    break;

                case R.id.increase_btn_details:
                    initialQuantity++;
                    if (initialQuantity > 9) {
                        initialQuantity = 9;
                    }
                    vh.quantity.setText(String.valueOf(initialQuantity));
                    break;

                case R.id.add_cart_button_details:
                    String updateCartText = "Update Cart";
                    vh.addCartButton.setText(updateCartText);
                    ItemWithQuantity itemWithQuantity = new ItemWithQuantity
                            (item, Integer.parseInt(vh.quantity.getText().toString()));
                    // add or update item to DB
                    cartDataSender.addItemWithQuantityToCart(itemWithQuantity, new CartDataSendHandler());
                    break;
                case R.id.button_back_details:

                    navigateBackToPreviousActivity();

                    break;
                default:
                    break;
            }
        }
    };

    protected void initializeImageSlider() {
        imageSliderAdapter = new ImageSliderAdapter(this);
    }

    // Initialise all details of an item to be shown on this activity
    protected void initializeItemDetails() {
        vh.divider.setVisibility(View.VISIBLE);
        vh.quantityBar.setVisibility(View.VISIBLE);
        vh.addToCartSection.setVisibility(View.VISIBLE);

        vh.itemName.setText(item.getName());
        vh.itemDetail.setText(item.getDescription());
        String price = "Price $ " + item.getPrice();
        vh.itemPrice.setText(price);
        String totalPrice = "Total $ " + item.getPrice();
        vh.itemTotalPrice.setText(totalPrice);
        vh.quantity.setText(String.valueOf(initialQuantity));
    }

    // initialise image slider dots configuration
    protected void initializeImageSliderDots() {
        dotsCount = imageSliderAdapter.getCount();
        dots = new ImageView[dotsCount];
        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(DetailsActivity.this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(8, 0, 8, 0);
            vh.imageSliderDotPanel.addView(dots[i], layoutParams);
        }
        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
        vh.imageSlider.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotsCount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable
                            (getApplicationContext(), R.drawable.non_active_dot));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable
                        (getApplicationContext(), R.drawable.active_dot));
            }
            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

    private String constructItemFromIntent(){
        Intent intent = getIntent();
        activityNamePreviousLevel = intent.getStringExtra(MainActivity.INTENT_KEY_ACTIVITY_NAME);

        if (activityNamePreviousLevel.equals(MainActivity.INTENT_VALUE_MAIN_ACTIVITY)){
            //Toast.makeText(this,"From Main",Toast.LENGTH_LONG).show();
            return (intent.getStringExtra(MainActivity.INTENT_KEY_ITEM_ID));

        } else if (activityNamePreviousLevel.equals(MainActivity.INTENT_VALUE_CATEGORY_ITEMS_ACTIVITY)){
            categoryNamePreviousLevel = intent.getStringExtra(MainActivity.INTENT_KEY_CATEGORY_NAME);
            categoryImageUriPreviousLevel = intent.getStringExtra(MainActivity.INTENT_KEY_CATEGORY_IMAGE_URI);
            //Toast.makeText(this,"From Cat: " + categoryNamePreviousLevel,Toast.LENGTH_LONG).show();
            return (intent.getStringExtra(MainActivity.INTENT_KEY_ITEM_ID));

        } else if (activityNamePreviousLevel.equals(MainActivity.INTENT_VALUE_SEARCH_RESULTS_ACTIVITY)){
            searchStringPreviousLevel = intent.getStringExtra(MainActivity.INTENT_KEY_SEARCH);
            //Toast.makeText(this,"From Search: " + searchStringPreviousLevel,Toast.LENGTH_LONG).show();
            return (intent.getStringExtra(MainActivity.INTENT_KEY_ITEM_ID));

        } else {
            return "NoSuchItemID";
        }

    }

    public void navigateBackToPreviousActivity() {

        if (activityNamePreviousLevel.equals(MainActivity.INTENT_VALUE_MAIN_ACTIVITY)) {

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        } else if (activityNamePreviousLevel.equals(MainActivity.INTENT_VALUE_CATEGORY_ITEMS_ACTIVITY)) {

            Intent intent = new Intent(this, CategoryItemsActivity.class);
            intent.putExtra(MainActivity.INTENT_KEY_CATEGORY_NAME, categoryNamePreviousLevel);
            intent.putExtra(MainActivity.INTENT_KEY_CATEGORY_IMAGE_URI, categoryImageUriPreviousLevel);
            startActivity(intent);

        } else if (activityNamePreviousLevel.equals(MainActivity.INTENT_VALUE_SEARCH_RESULTS_ACTIVITY)) {

            Intent intent = new Intent(this, SearchResultsActivity.class);
            intent.putExtra(MainActivity.INTENT_KEY_SEARCH, searchStringPreviousLevel);
            startActivity(intent);
        }
    }


    // ----------------------------------------------------------------------------------------//
    // Details Activity OnCreate Method (Main entry)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        initialQuantity = 1;

        vh = new ViewHolder();
        initializeImageSlider();

        vh.increaseBtn.setOnClickListener(buttonListener);
        vh.decreaseBtn.setOnClickListener(buttonListener);
        vh.addCartButton.setOnClickListener(buttonListener);
        vh.backButton.setOnClickListener(buttonListener);

        // Highlight the Selected Navigation ICON
        vh.bottomNavBar.setSelectedItemId(R.id.activity_home);
        // Initialise the Bottom Bar Navigation Logic
        navigationAdapter = new NavigationAdapter(this);
        vh.bottomNavBar.setOnItemSelectedListener(navigationAdapter.navigationListener);

        String selectedItemId = constructItemFromIntent();
        itemDetailsDataFetcher.readData(selectedItemId, new ItemDetailsFetchHandler());


    }
}