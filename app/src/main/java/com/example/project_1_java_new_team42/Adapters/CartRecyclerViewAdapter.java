package com.example.project_1_java_new_team42.Adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_1_java_new_team42.Activities.CartActivity;
import com.example.project_1_java_new_team42.Activities.DetailsActivity;
import com.example.project_1_java_new_team42.Data.Senders.CartDataSender;
import com.example.project_1_java_new_team42.Data.Senders.ICartDataSender;
import com.example.project_1_java_new_team42.Data.Senders.ISendHandler;
import com.example.project_1_java_new_team42.Models.ItemWithQuantity;
import com.example.project_1_java_new_team42.R;
import com.example.project_1_java_new_team42.Util.ItemUtil;

import java.util.List;

public class CartRecyclerViewAdapter extends GenericRecyclerViewAdapter<ItemWithQuantity, CartRecyclerViewAdapter.ViewHolder> {

    private static class CartDataSendHandler implements ISendHandler {

        @Override
        public void onSendSuccess(boolean isSuccess) {
            if (!isAddedToCart) {
                isAddedToCart = true;
            }
        }
    }

    private int itemQuantity;
    private int itemPrice;
    private int itemsTotalPrice;

    protected ICartDataSender cartDataSender = new CartDataSender();
    private final CartDataSendHandler cartDataSenderHandler = new CartDataSendHandler();

    public static boolean isAddedToCart = false;

    public CartRecyclerViewAdapter(Context context) {
        super(context);
    }

    public void setItemsTotalPrice(int itemsTotalPrice) {
        this.itemsTotalPrice = itemsTotalPrice;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView cartItemImageView;
        TextView cartItemNameTextView;
        TextView cartItemPriceTextView;
        TextView cartItemQuantityTextView;
        Button incrementButton;
        Button decrementButton;
        ImageButton deleteButton;
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

            addQuantityButtonWatcher(quantityTextView);
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
                    price = ItemUtil.addDollarSignToPrice(itemPrice * itemQuantity);
                    cartItemPriceTextView.setText(price);
                    break;

                case R.id.button_minus:
                    // Call onIncrement to update quantity field in database
                    listener.onDecrement(this.getLayoutPosition());

                    // Set text views in RecyclerView
                    quantityTextView.setText(String.valueOf(itemQuantity));
                    price = ItemUtil.addDollarSignToPrice(itemPrice * itemQuantity);
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

        private void addQuantityButtonWatcher(TextView textView) {
            textView.addTextChangedListener( new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void afterTextChanged(Editable editable) {
                    String quantityNumber = quantityTextView.getText().toString().trim();

                    if (quantityNumber.equals(Integer.toString(ItemWithQuantity.MIN_QUANTITY))) {
                        decrementButton.setEnabled(false);
                    } else if (quantityNumber.equals(Integer.toString(ItemWithQuantity.MAX_QUANTITY))) {
                        incrementButton.setEnabled(false);
                    } else {
                        incrementButton.setEnabled(true);
                        decrementButton.setEnabled(true);
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.cart_item_card, parent, false);

        return new ViewHolder(view, new MyClickListener() {
            @Override
            public void onIncrement(int pos) {
                ItemWithQuantity item = items.get(pos);

                itemQuantity = item.getQuantity();
                itemQuantity++;

                itemPrice = item.getPrice();
                itemsTotalPrice += itemPrice;

                ItemWithQuantity updatedItem = new ItemWithQuantity(item, itemQuantity);

                cartDataSender.addItemWithQuantityToCart(updatedItem, cartDataSenderHandler);
                items.set(pos, updatedItem);
            }

            @Override
            public void onDecrement(int pos) {
                ItemWithQuantity item = items.get(pos);

                itemQuantity = items.get(pos).getQuantity();
                itemQuantity--;

                itemPrice = items.get(pos).getPrice();
                itemsTotalPrice -= itemPrice;

                ItemWithQuantity updatedItem = new ItemWithQuantity(item, itemQuantity);

                cartDataSender.addItemWithQuantityToCart(updatedItem, cartDataSenderHandler);
                items.set(pos, updatedItem);
            }

            @Override
            public void onDeletion(int pos) {
                ItemWithQuantity item = items.get(pos);

                itemQuantity = item.getQuantity();
                itemPrice = item.getPrice();

                itemsTotalPrice -= (itemQuantity * itemPrice);

                cartDataSender.deleteSingleCartItem(item.getId(), cartDataSenderHandler);

                items.remove(pos);
                notifyItemRemoved(pos);
            }
        });
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemWithQuantity item = items.get(position);

        int drawableId = ItemUtil.getImageDrawableId(context, item.getImagePaths().get(0));
        holder.cartItemImageView.setImageResource(drawableId);
        holder.cartItemNameTextView.setText(item.getName());

        String price = ItemUtil.addDollarSignToPrice(item.getPrice() * item.getQuantity());
        holder.cartItemPriceTextView.setText(price);

        String quantity = Integer.toString(item.getQuantity());
        holder.cartItemQuantityTextView.setText(quantity);
    }

    public interface MyClickListener {
        void onIncrement(int pos);
        void onDecrement(int pos);
        void onDeletion(int pos);
    }
}
