package com.example.project_1_java_new_team42.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_1_java_new_team42.Activities.DetailsActivity;
import com.example.project_1_java_new_team42.Activities.MainActivity;
import com.example.project_1_java_new_team42.Models.IItem;
import com.example.project_1_java_new_team42.R;

import java.util.ArrayList;
import java.util.List;

public class ItemsRecyclerViewAdapter extends RecyclerView.Adapter<ItemsRecyclerViewAdapter.ViewHolder> {
    private List<IItem> items = new ArrayList<>();
    private final LayoutInflater layoutInflater;
    private final Context context;

    public ItemsRecyclerViewAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setData(List<IItem> categoriesData) {
        this.items = categoriesData;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView itemImageView;
        TextView itemNameTextView;
        TextView itemPriceTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            itemImageView = itemView.findViewById(R.id.image_itemcard);
            itemNameTextView = itemView.findViewById(R.id.text_itemcard_name);
            itemPriceTextView = itemView.findViewById(R.id.text_itemcard_price);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // TODO Navigate to the item details page

            IItem item = items.get(getAdapterPosition());
            Toast.makeText(context, "Name: " + item.getName() + " Price: " + item.getPrice(), Toast.LENGTH_SHORT).show();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(layoutInflater.inflate(R.layout.item_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        IItem item = items.get(position);
        int drawableId = context.getResources().getIdentifier(item.getImagePaths().get(0),"drawable", context.getPackageName());
        holder.itemImageView.setImageResource(drawableId);
        holder.itemNameTextView.setText(item.getName());
        String price = "$" + item.getPrice();
        holder.itemPriceTextView.setText(price);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
