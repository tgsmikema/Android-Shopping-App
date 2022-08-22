package com.example.project_1_java_new_team42.Adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_1_java_new_team42.Models.Category;
import com.example.project_1_java_new_team42.Models.IItem;
import com.example.project_1_java_new_team42.R;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;

public class ItemsRecyclerViewAdapter extends GenericRecyclerViewAdapter<IItem, ItemsRecyclerViewAdapter.ViewHolder> {

    public ItemsRecyclerViewAdapter(Context context) {
        super(context);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        MaterialCardView cardView;
        ImageView imageView;
        TextView nameTextView;
        TextView priceTextView;
        Chip categoryChip;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image_itemcard);
            nameTextView = itemView.findViewById(R.id.text_itemcard_name);
            priceTextView = itemView.findViewById(R.id.text_itemcard_price);
            cardView = itemView.findViewById(R.id.card_item);
            categoryChip = itemView.findViewById(R.id.chip_itemcard_category);

            initializeCardView(cardView);
            initializeChip(categoryChip);
        }

        private void initializeCardView(CardView cardView) {
            cardView.setClickable(true);
            cardView.setFocusable(true);
            cardView.setOnClickListener(this);
        }

        private void initializeChip(Chip chip) {
            chip.setClickable(false);
            chip.setFocusable(false);
        }

        public void setChipColors(@ColorRes int foreground, @ColorRes int background) {
            Resources res = categoryChip.getContext().getResources();
            categoryChip.setChipBackgroundColor(ColorStateList.valueOf(res.getColor(background)));
            categoryChip.setChipIconTint(ColorStateList.valueOf(res.getColor(foreground)));
            categoryChip.setTextColor(ColorStateList.valueOf(res.getColor(foreground)));
        }

        @Override
        public void onClick(View view) {
            // TODO Navigate to the item details page

            IItem item = items.get(getAdapterPosition());
            Toast.makeText(context, "Name: " + item.getName() + " Price: " + item.getPrice(), Toast.LENGTH_SHORT).show();
        }
    }

    // TODO Extract all chip data/logic into a separate util class
    public @DrawableRes int determineChipIcon(String category) {
        switch (category) {
            case Category.DESKTOP:
                return R.drawable.ic_desktop;
            case Category.LAPTOP:
                return R.drawable.ic_laptop;
            case Category.TABLET:
                return R.drawable.ic_tablet;
            default:
                throw new IllegalArgumentException("Unknown category: " + category);
        }
    }

    public @ColorRes int determineChipForegroundColor(String category) {
        switch (category) {
            case Category.DESKTOP:
                return R.color.chip_blue_dark;
            case Category.LAPTOP:
                return R.color.chip_green_dark;
            case Category.TABLET:
                return R.color.chip_tan_dark;
            default:
                throw new IllegalArgumentException("Unknown category: " + category);
        }
    }

    public @ColorRes int determineChipBackgroundColor(String category) {
        switch (category) {
            case Category.DESKTOP:
                return R.color.chip_blue_light;
            case Category.LAPTOP:
                return R.color.chip_green_light;
            case Category.TABLET:
                return R.color.chip_tan_light;
            default:
                throw new IllegalArgumentException("Unknown category: " + category);
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

        // Item image
        int drawableId = context.getResources().getIdentifier(item.getImagePaths().get(0),"drawable", context.getPackageName());
        holder.imageView.setImageResource(drawableId);

        // Item name
        holder.nameTextView.setText(item.getName());

        // Item price
        String price = "$" + item.getPrice();
        holder.priceTextView.setText(price);

        // Dynamic chip icon and data depending on the category
        holder.setChipColors(determineChipForegroundColor(item.getCategory()), determineChipBackgroundColor(item.getCategory()));
        holder.categoryChip.setChipIcon(ContextCompat.getDrawable(context, determineChipIcon(item.getCategory())));
        String capitalizedCategory = item.getCategory().substring(0, 1).toUpperCase() + item.getCategory().substring(1);
        holder.categoryChip.setText(capitalizedCategory);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
