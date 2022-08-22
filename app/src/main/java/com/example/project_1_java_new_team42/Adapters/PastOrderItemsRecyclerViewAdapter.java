package com.example.project_1_java_new_team42.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_1_java_new_team42.Models.ItemWithQuantity;
import com.example.project_1_java_new_team42.Models.Order;
import com.example.project_1_java_new_team42.R;
import com.google.android.material.card.MaterialCardView;

// ItemWithQuantity Instead of Order Class
public class PastOrderItemsRecyclerViewAdapter extends GenericRecyclerViewAdapter<ItemWithQuantity, PastOrderItemsRecyclerViewAdapter.ViewHolder> {

    public PastOrderItemsRecyclerViewAdapter(Context context) {
        super(context);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        MaterialCardView orderItemCardView;
        ImageView orderItemImage;
        TextView orderItemName, orderItemQuantity, orderItemTotalPrice;

        public ViewHolder(View orderView) {
            super(orderView);
            orderItemCardView = orderView.findViewById(R.id.material_past_order_item_card);

            orderItemImage = orderView.findViewById(R.id.image_past_order_item_card);

            orderItemName = orderView.findViewById(R.id.name_past_order_item_card);
            orderItemQuantity = orderView.findViewById(R.id.quantity_past_order_item_card);
            orderItemTotalPrice = orderView.findViewById(R.id.price_past_order_item_card);

            orderItemCardView.setClickable(true);
            orderItemCardView.setFocusable(true);
            orderItemCardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //Do nothing.
        }
    }

    @NonNull
    @Override
    public PastOrderItemsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(layoutInflater.inflate(R.layout.past_order_item_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PastOrderItemsRecyclerViewAdapter.ViewHolder holder, int position) {
        ItemWithQuantity itemWithQuantity = items.get(position);

        int drawableId = context.getResources().getIdentifier(itemWithQuantity.getImagePaths().get(0),"drawable", context.getPackageName());
        holder.orderItemImage.setImageResource(drawableId);

        String itemName = itemWithQuantity.getName();
        holder.orderItemName.setText(itemName);

        String quantity = "Qty: " + itemWithQuantity.getQuantity();
        holder.orderItemQuantity.setText(quantity);

        String totalPrice = "Total $" + String.valueOf(itemWithQuantity.getQuantity() * itemWithQuantity.getPrice());
        holder.orderItemTotalPrice.setText(totalPrice);
    }

}
