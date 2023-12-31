package com.example.project_1_java_new_team42.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_1_java_new_team42.models.ItemWithQuantity;
import com.example.project_1_java_new_team42.R;
import com.example.project_1_java_new_team42.util.ItemUtil;
import com.google.android.material.card.MaterialCardView;

// ItemWithQuantity Instead of Order Class
public class PastOrderItemsRecyclerViewAdapter extends GenericRecyclerViewAdapter<ItemWithQuantity, PastOrderItemsRecyclerViewAdapter.ViewHolder> {

    public PastOrderItemsRecyclerViewAdapter(Context context) {
        super(context);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
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

        int drawableId = ItemUtil.getImageDrawableId(context, itemWithQuantity.getImagePaths().get(0));
        holder.orderItemImage.setImageResource(drawableId);

        String itemName = itemWithQuantity.getName();
        holder.orderItemName.setText(itemName);

        String quantity = "Qty: " + itemWithQuantity.getQuantity();
        holder.orderItemQuantity.setText(quantity);

        String totalPrice = ItemUtil.addDollarSignToPrice(itemWithQuantity.getQuantity() * itemWithQuantity.getPrice());
        holder.orderItemTotalPrice.setText(totalPrice);
    }

}
