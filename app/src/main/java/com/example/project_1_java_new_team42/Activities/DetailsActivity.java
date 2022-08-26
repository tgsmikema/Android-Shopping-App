package com.example.project_1_java_new_team42.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_1_java_new_team42.Adapters.ImageSliderAdapter;
import com.example.project_1_java_new_team42.Adapters.ItemsRecyclerViewAdapter;
import com.example.project_1_java_new_team42.Adapters.NavigationAdapter;
import com.example.project_1_java_new_team42.Data.Fetchers.IFetchHandler;
import com.example.project_1_java_new_team42.Data.Fetchers.ItemDetailsDataFetcher;
import com.example.project_1_java_new_team42.Data.Senders.CartDataSender;
import com.example.project_1_java_new_team42.Data.Senders.ISendHandler;
import com.example.project_1_java_new_team42.Models.Cart;
import com.example.project_1_java_new_team42.Models.IItem;
import com.example.project_1_java_new_team42.Models.ItemWithQuantity;
import com.example.project_1_java_new_team42.R;
import com.example.project_1_java_new_team42.Util.ItemUtil;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    private static final int DOT_HOR_MARGIN = 10;
    private static final int DOT_VER_MARGIN = 0;

    // global variables
    public static int quantity = 1;
    public static IItem item;

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

        TextView itemName, itemDetail, itemPrice, itemTotalPrice, quantityText, quantity;
        MaterialButton decreaseBtn, increaseBtn, backButton, addCartButton;

        NavigationBarView bottomNavBar;


        public ViewHolder() {
            imageSlider = findViewById(R.id.image_slide_details);
            imageSpinner = findViewById(R.id.progress_images);
            imageSliderDotPanel = findViewById(R.id.image_slider_dots);

            // TextViews
            itemName = findViewById(R.id.item_name_details);
            itemDetail = findViewById(R.id.item_detail_details);
            itemPrice = findViewById(R.id.item_price_details);
            itemTotalPrice = findViewById(R.id.item_total_price_details);
            quantityText = findViewById(R.id.quantity_text_details);
            quantity = findViewById(R.id.quantity_details);

            // Buttons
            decreaseBtn = findViewById(R.id.decrease_btn_details);
            increaseBtn = findViewById(R.id.increase_btn_details);
            addCartButton = findViewById(R.id.add_cart_button_details);
            backButton = findViewById(R.id.button_back_details);

            bottomNavBar = findViewById(R.id.bottom_navigation_details);
        }
    }


    private class CartDataSendHandler implements ISendHandler {

        @Override
        public void onSendSuccess(boolean isSuccess) {
            showSuccessfulDialog();
        }

        private void showSuccessfulDialog() {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(DetailsActivity.this, R.style.Theme_Team42_Dialog);

            builder.setTitle(R.string.text_added_to_cart);
            builder.setMessage(R.string.text_added_to_cart_body);
            builder.setCancelable(false);
            builder.setPositiveButton(R.string.dialog_btn_checkout, (dialog, which) -> {
                navigateToCartActivity();
            });
            builder.setNegativeButton(R.string.dialog_btn_back, (dialog, which) -> {
                finish();
            });
            builder.show();
        }

        private void navigateToCartActivity(){
            Intent intent = new Intent(DetailsActivity.this, CartActivity.class);
            startActivity(intent);
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
            setItemDetailViews(item);

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

            // Disabling/enabling quantity buttons with respect to order limits.
            if (currentQuantity == ItemWithQuantity.MIN_QUANTITY) {
                vh.decreaseBtn.setEnabled(false);
                vh.increaseBtn.setEnabled(true);
            } else if (currentQuantity > ItemWithQuantity.MIN_QUANTITY && currentQuantity < ItemWithQuantity.MAX_QUANTITY) {
                vh.decreaseBtn.setEnabled(true);
                vh.increaseBtn.setEnabled(true);
            } else {
                vh.decreaseBtn.setEnabled(true);
                vh.increaseBtn.setEnabled(false);
            }

            // Update Total Price
            int calculatedPrice = item.getPrice() * Integer.parseInt(vh.quantity.getText().toString());
            String totalPrice = "Total $" + calculatedPrice;
            vh.itemTotalPrice.setText(totalPrice);
        }
    }

    protected void initializeDecreaseQtyButton() {
        if (quantity == ItemWithQuantity.MIN_QUANTITY) {
            vh.decreaseBtn.setEnabled(false);
        }
        vh.decreaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Decrease Quantity
                if (quantity > ItemWithQuantity.MIN_QUANTITY) {
                    quantity--;
                    vh.quantity.setText(String.valueOf(quantity));
                }

            }
        });
    }

    protected void initializeIncreaseQtyButton() {
        vh.increaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity < ItemWithQuantity.MAX_QUANTITY) {
                    quantity++;
                    vh.quantity.setText(String.valueOf(quantity));
                }
            }
        });
    }

    protected void initializeAddToCartButton() {
        vh.addCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemWithQuantity itemWithQuantity = new ItemWithQuantity(item, Integer.parseInt(vh.quantity.getText().toString()));
                cartDataSender.addItemWithQuantityToCart(itemWithQuantity, new CartDataSendHandler());
            }
        });
    }

    protected void initializeBackButton() {
        vh.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected void initializeImageSlider() {
        imageSliderAdapter = new ImageSliderAdapter(this);
    }

    // Initialise all details of an item to be shown on this activity
    protected void setItemDetailViews(IItem item) {
        vh.itemName.setText(item.getName());
        vh.itemDetail.setText(item.getDescription());

        String price = ItemUtil.addDollarSignToPrice(item.getPrice());
        vh.itemPrice.setText(price);

        String totalPrice = "Total " + ItemUtil.addDollarSignToPrice(item.getPrice());
        vh.itemTotalPrice.setText(totalPrice);

        vh.quantity.setText(String.valueOf(quantity));
    }

    // Initialise image slider dots configuration
    protected void initializeImageSliderDots() {
        int numDots = imageSliderAdapter.getCount();
        ImageView[] dots = new ImageView[numDots];

        Context context = DetailsActivity.this;

        for (int i = 0; i < numDots; i++) {
            dots[i] = new ImageView(DetailsActivity.this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_inactive_dot));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(DOT_HOR_MARGIN, DOT_VER_MARGIN, DOT_HOR_MARGIN, DOT_VER_MARGIN);
            vh.imageSliderDotPanel.addView(dots[i], layoutParams);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_active_dot));

        vh.imageSlider.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageScrollStateChanged(int state) {}

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < numDots; i++) {
                    if (i == position) {
                        dots[i].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_active_dot));
                    } else {
                        dots[i].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_inactive_dot));
                    }
                }
            }
        });
    }

    // ----------------------------------------------------------------------------------------//
    // Details Activity OnCreate Method (Main entry)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        quantity = 1;

        vh = new ViewHolder();
        initializeImageSlider();

        initializeDecreaseQtyButton();
        initializeIncreaseQtyButton();
        initializeAddToCartButton();
        initializeBackButton();

        // Highlight the Selected Navigation ICON
        vh.bottomNavBar.setSelectedItemId(R.id.activity_home);
        // Initialise the Bottom Bar Navigation Logic
        navigationAdapter = new NavigationAdapter(this);
        vh.bottomNavBar.setOnItemSelectedListener(navigationAdapter.navigationListener);

        Intent intent = getIntent();
        String selectedItemId = intent.getStringExtra(ItemsRecyclerViewAdapter.INTENT_KEY_ITEM_ID_TO_FETCH);
        itemDetailsDataFetcher.readData(selectedItemId, new ItemDetailsFetchHandler());
    }
}