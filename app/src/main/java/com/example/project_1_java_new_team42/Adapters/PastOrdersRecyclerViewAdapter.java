package com.example.project_1_java_new_team42.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_1_java_new_team42.Activities.CategoryItemsActivity;
import com.example.project_1_java_new_team42.Activities.MainActivity;
import com.example.project_1_java_new_team42.Activities.PastOrderItemsActivity;
import com.example.project_1_java_new_team42.Models.Category;
import com.example.project_1_java_new_team42.Models.Order;
import com.example.project_1_java_new_team42.R;
import com.google.android.material.card.MaterialCardView;

public class PastOrdersRecyclerViewAdapter extends GenericRecyclerViewAdapter<Order, PastOrdersRecyclerViewAdapter.ViewHolder> {
    public static final String INTENT_KEY_ORDER = "ORDER";

    public PastOrdersRecyclerViewAdapter(Context context) {
        super(context);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        MaterialCardView orderCardView;
        TextView orderNumber, orderDate;

        public ViewHolder(View orderView) {
            super(orderView);
            orderNumber = orderView.findViewById(R.id.text_ordercard_number);
            orderDate = orderView.findViewById(R.id.text_ordercard_date);
            orderCardView = orderView.findViewById(R.id.order_item);

            orderCardView.setClickable(true);
            orderCardView.setFocusable(true);
            orderCardView.setOnClickListener(this);
        }

        private void navigateToPastOrderItemsActivity(Order order) {
            Intent intent = new Intent(context, PastOrderItemsActivity.class);
            intent.putExtra(INTENT_KEY_ORDER, order);
            context.startActivity(intent);
        }

        @Override
        public void onClick(View view) {
            navigateToPastOrderItemsActivity(items.get(getAdapterPosition()));
        }
    }

    @NonNull
    @Override
    public PastOrdersRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(layoutInflater.inflate(R.layout.order_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PastOrdersRecyclerViewAdapter.ViewHolder holder, int position) {
        Order order = items.get(position);

        String orderId = "Order #" + order.getOrderId();
        holder.orderNumber.setText(orderId);

        String orderDateRaw = order.getPlacedDateAndTime();
        String orderDateStr = orderDateRaw.substring(0, orderDateRaw.length());
        holder.orderDate.setText(orderDateStr);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
