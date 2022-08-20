package com.example.project_1_java_new_team42.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_1_java_new_team42.Models.Cart;
import com.example.project_1_java_new_team42.Models.IItem;
import com.example.project_1_java_new_team42.Models.Item;
import com.example.project_1_java_new_team42.Models.ItemWithQuantity;
import com.example.project_1_java_new_team42.R;

import java.util.ArrayList;
import java.util.List;

public class CartRecyclerViewAdapter extends RecyclerView.Adapter<CartRecyclerViewAdapter.ViewHolder> {
//    private List<IItem> items = new ArrayList<>();
    private List<ItemWithQuantity> itemsWithQuantity;
    private Cart cartItems = new Cart();
    private LayoutInflater layoutInflater;
    private Context context;

    public CartRecyclerViewAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setData(Cart cartData) {
        this.cartItems = cartData;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView cartItemImageView;
        TextView cartItemNameTextView;
        TextView cartItemPriceTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            cartItemImageView = itemView.findViewById(R.id.image_cart_itemcard);
            cartItemNameTextView = itemView.findViewById(R.id.text_cart_itemcard_name);
            cartItemPriceTextView = itemView.findViewById(R.id.text_cart_itemcard_price);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // TODO Navigate to the item details page
//            Cart item = cartItem.get(getAdapterPosition());
//            Toast.makeText(context, "Name: " + item.getName() + " Price: " + item.getPrice(), Toast.LENGTH_SHORT).show();
        }
    }

    @NonNull
    @Override
    public CartRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartRecyclerViewAdapter.ViewHolder(layoutInflater.inflate(R.layout.cart_item_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartRecyclerViewAdapter.ViewHolder holder, int position) {
        itemsWithQuantity = cartItems.getItems();
        IItem item = itemsWithQuantity.get(position);
        int drawableId = context.getResources().getIdentifier(item.getImagePaths().get(0),"drawable", context.getPackageName());
        holder.cartItemImageView.setImageResource(drawableId);
        holder.cartItemNameTextView.setText(item.getName());
        String price = "$" + cartItems.getTotalPrice();
        holder.cartItemPriceTextView.setText(price);
    }

    @Override
    public int getItemCount() { return itemsWithQuantity.size(); }
}
