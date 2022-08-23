package com.example.project_1_java_new_team42.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_1_java_new_team42.Activities.DetailsActivity;
import com.example.project_1_java_new_team42.Activities.SearchResultsActivity;
import com.example.project_1_java_new_team42.Models.IItem;
import com.example.project_1_java_new_team42.R;
import com.example.project_1_java_new_team42.util.CategoryChipUtil;
import com.example.project_1_java_new_team42.util.CategoryChipUtil.CategoryChipData;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;

public class ItemsRecyclerViewAdapter extends GenericRecyclerViewAdapter<IItem, ItemsRecyclerViewAdapter.ViewHolder> {
    public static final String INTENT_KEY_ITEM_ID_TO_FETCH = "ITEM_ID_TO_FETCH";

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
            Intent intent = new Intent(context, DetailsActivity.class);
            IItem item = items.get(getAdapterPosition());
            intent.putExtra(INTENT_KEY_ITEM_ID_TO_FETCH, item.getId());
            context.startActivity(intent);
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
        CategoryChipData chipData = CategoryChipUtil.getChipDataFromCategory(item.getCategory());
        holder.setChipColors(chipData.getFgColor(), chipData.getBgColor());
        holder.categoryChip.setChipIcon(ContextCompat.getDrawable(context, chipData.getIcon()));
        holder.categoryChip.setText(chipData.getText());
    }
}
