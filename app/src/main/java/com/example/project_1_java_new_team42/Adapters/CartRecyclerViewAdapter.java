package com.example.project_1_java_new_team42.Adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_1_java_new_team42.Activities.CartActivity;
import com.example.project_1_java_new_team42.Data.Senders.CartDataSender;
import com.example.project_1_java_new_team42.Data.Senders.ISendHandler;
import com.example.project_1_java_new_team42.Models.Cart;
import com.example.project_1_java_new_team42.Models.IItem;
import com.example.project_1_java_new_team42.Models.ItemWithQuantity;
import com.example.project_1_java_new_team42.R;

import java.util.ArrayList;
import java.util.List;

public class CartRecyclerViewAdapter extends RecyclerView.Adapter<CartRecyclerViewAdapter.ViewHolder> {
    private List<ItemWithQuantity> itemsWithQuantity = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private Context context;

    private int itemQuantity;
    private int itemPrice;
    private int itemsTotalPrice;

    protected CartDataSender cartDataSender = new CartDataSender();
    public static boolean isAddedToCart = false;

    public CartRecyclerViewAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setData(Cart cartData) {
        this.itemsWithQuantity = cartData.getItems();
        this.itemsTotalPrice = cartData.getTotalPrice();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView cartItemImageView;
        TextView cartItemNameTextView;
        TextView cartItemPriceTextView;
        TextView cartItemQuantityTextView;
        Button incrementButton;
        Button decrementButton;
        Button deleteButton;
        TextView quantityTextView;
        MyClickListener listener;

        public ViewHolder(View itemView, MyClickListener listener) {
            super(itemView);

            cartItemImageView = itemView.findViewById(R.id.image_cart_itemcard);
            cartItemNameTextView = itemView.findViewById(R.id.text_cart_itemcard_name);
            cartItemPriceTextView = itemView.findViewById(R.id.text_cart_itemcard_price);
            cartItemQuantityTextView = itemView.findViewById(R.id.text_item_quantity);
            incrementButton = itemView.findViewById(R.id.button_plus);
            decrementButton = itemView.findViewById(R.id.button_minus);
            deleteButton = itemView.findViewById(R.id.button_delete);
            quantityTextView = itemView.findViewById(R.id.text_item_quantity);

            this.listener = listener;

            incrementButton.setOnClickListener(this);
            decrementButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);

            quantityTextView.addTextChangedListener(quantityTextWatcher);
        }

        @Override
        public void onClick(View view) {
            String price;
            switch (view.getId()) {
                case R.id.button_plus:
                    // Call onIncrement to update quantity field in database
                    listener.onIncrement(this.getLayoutPosition());

                    // Set text views in RecyclerView
                    quantityTextView.setText(String.valueOf(itemQuantity));
                    price = "$" + (itemQuantity * itemPrice);
                    cartItemPriceTextView.setText(price);
                    break;

                case R.id.button_minus:
                    // Call onIncrement to update quantity field in database
                    listener.onDecrement(this.getLayoutPosition());

                    // Set text views in RecyclerView
                    quantityTextView.setText(String.valueOf(itemQuantity));
                    price = "$" + (itemQuantity * itemPrice);
                    cartItemPriceTextView.setText(price);
                    break;

                case R.id.button_delete:
                    // Call onDelete to remove item from database
                    listener.onDeletion(this.getLayoutPosition());
                    break;

                default:
                    break;
            }

            // Update total price in CartActivity
            ((CartActivity) context).updateTotalPrice(itemsTotalPrice);
        }

        private TextWatcher quantityTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                String quantityNumber = quantityTextView.getText().toString().trim();

                if (quantityNumber.equals("1")) {
                    decrementButton.setEnabled(false);
                } else if (quantityNumber.equals("9")) {
                    incrementButton.setEnabled(false);
                } else {
                    incrementButton.setEnabled(true);
                    decrementButton.setEnabled(true);
                }
            }
        };
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.cart_item_card, parent, false);
        ViewHolder holder = new ViewHolder(view, new MyClickListener() {
            @Override
            public void onIncrement(int p) {
                // Implement functionality for decrement button
                itemQuantity = itemsWithQuantity.get(p).getQuantity();
                itemQuantity++;

                itemPrice = itemsWithQuantity.get(p).getPrice();
                itemsTotalPrice += itemPrice;

                ItemWithQuantity item = new ItemWithQuantity(itemsWithQuantity.get(p), itemQuantity);

                cartDataSender.addItemWithQuantityToCart(item, new CartDataSendHandler());
                itemsWithQuantity.set(p, item);
            }

            @Override
            public void onDecrement(int p) {
                // Implement functionality for decrement button
                itemQuantity = itemsWithQuantity.get(p).getQuantity();
                itemQuantity--;

                itemPrice = itemsWithQuantity.get(p).getPrice();
                itemsTotalPrice -= itemPrice;

                ItemWithQuantity item = new ItemWithQuantity(itemsWithQuantity.get(p), itemQuantity);

                cartDataSender.addItemWithQuantityToCart(item, new CartDataSendHandler());
                itemsWithQuantity.set(p, item);
            }

            @Override
            public void onDeletion(int p) {
                // Implement functionality for delete button
                itemQuantity = itemsWithQuantity.get(p).getQuantity();
                itemPrice = itemsWithQuantity.get(p).getPrice();

                itemsTotalPrice -= (itemQuantity * itemPrice);

                String itemId = itemsWithQuantity.get(p).getId();
                cartDataSender.deleteSingleCartItem(itemId, new CartDataSendHandler());

                itemsWithQuantity.remove(p);
                notifyItemRemoved(p);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemWithQuantity item = itemsWithQuantity.get(position);
        int drawableId = context.getResources().getIdentifier(item.getImagePaths().get(0),"drawable", context.getPackageName());
        holder.cartItemImageView.setImageResource(drawableId);
        holder.cartItemNameTextView.setText(item.getName());
        String price = "$" + item.getPrice() * item.getQuantity();
        holder.cartItemPriceTextView.setText(price);
        String quantity = "" + item.getQuantity();
        holder.cartItemQuantityTextView.setText(quantity);
    }

    @Override
    public int getItemCount() {
        return itemsWithQuantity.size();
    }

    public interface MyClickListener {
        void onIncrement(int p);
        void onDecrement(int p);
        void onDeletion(int p);
    }

    // Implementation of CartDataSendHandler
    private class CartDataSendHandler implements ISendHandler {

        @Override
        public void onSendSuccess(boolean isSuccess) {
            if (!isAddedToCart) {
                isAddedToCart = true;
            }
        }
    }
}