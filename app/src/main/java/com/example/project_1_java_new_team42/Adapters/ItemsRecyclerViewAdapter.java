package com.example.project_1_java_new_team42.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_1_java_new_team42.Activities.DetailsActivity;
import com.example.project_1_java_new_team42.Models.Category;
import com.example.project_1_java_new_team42.Models.IItem;
import com.example.project_1_java_new_team42.Models.Item;
import com.example.project_1_java_new_team42.R;
import com.example.project_1_java_new_team42.Util.CategoryChipUtil;
import com.example.project_1_java_new_team42.Util.CategoryChipUtil.CategoryChipData;
import com.example.project_1_java_new_team42.Util.ItemUtil;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;

public class ItemsRecyclerViewAdapter extends GenericRecyclerViewAdapter<IItem, ItemsRecyclerViewAdapter.ViewHolder> {
    public static final String INTENT_KEY_ITEM = "ITEM";
    public static final String DESKTOP_ACCESSORY_ICON_PATH = "ic_ssd";
    public static final String LAPTOP_ACCESSORY_ICON_PATH = "ic_touch";
    public static final String TABLET_ACCESSORY_ICON_PATH = "ic_keyboard";

    protected String searchString;
    protected Category category;

    public ItemsRecyclerViewAdapter(Context context) {
        super(context);
    }

    public void relaySearchString(String searchString){
        this.searchString = searchString;
    }

    public void relayCategory(Category category){
        this.category = new Category(category.getCategoryName(), category.getImageURI());
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        MaterialCardView cardView;
        ImageView imageView;
        TextView nameTextView;
        TextView priceTextView;
        Chip categoryChip;

        ImageView accessoryView;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image_itemcard);
            nameTextView = itemView.findViewById(R.id.text_itemcard_name);
            priceTextView = itemView.findViewById(R.id.text_itemcard_price);
            cardView = itemView.findViewById(R.id.card_item);
            categoryChip = itemView.findViewById(R.id.chip_itemcard_category);

            accessoryView = itemView.findViewById(R.id.image_itemcard_accessory);

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

        private void navigateToDetailsActivity(Item selectedItem){
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra(INTENT_KEY_ITEM, selectedItem);
            context.startActivity(intent);
        }

        @Override
        public void onClick(View view) {
            navigateToDetailsActivity((Item) items.get(getAdapterPosition()));
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
        int drawableId = ItemUtil.getImageDrawableId(context, item.getImagePaths().get(0));
        holder.imageView.setImageResource(drawableId);

        // Item name
        holder.nameTextView.setText(item.getName());

        // Item price
        String price = ItemUtil.addDollarSignToPrice(item.getPrice());;
        holder.priceTextView.setText(price);

        // Dynamic chip icon and data depending on the category
        CategoryChipData chipData = CategoryChipUtil.getChipDataFromCategory(item.getCategory());
        holder.setChipColors(chipData.getFgColor(), chipData.getBgColor());
        holder.categoryChip.setChipIcon(ContextCompat.getDrawable(context, chipData.getIcon()));
        holder.categoryChip.setText(chipData.getText());

        // Set Item accessory icon via Dependency injection.
        populateAccessory(holder, item);
    }

    // Dependency Injection
    private void populateAccessory(ViewHolder holder, IItem item) {
        switch (item.getCategory()) {
            case Category.DESKTOP:
                populateDesktopAccessory(holder, item);
                break;
            case Category.LAPTOP:
                populateLaptopAccessory(holder, item);
                break;
            case Category.TABLET:
                populateTabletAccessory(holder, item);
                break;
        }
    }

    private void populateDesktopAccessory (ViewHolder holder, IItem item){
        if (item.getIsSSD()){
            int imageResourceId = ItemUtil.getImageDrawableId(context, DESKTOP_ACCESSORY_ICON_PATH);
            holder.accessoryView.setImageResource(imageResourceId);
        }
    }

    private void populateLaptopAccessory (ViewHolder holder, IItem item){
        if (item.getIsTouchScreen()){
            int imageResourceId = ItemUtil.getImageDrawableId(context, LAPTOP_ACCESSORY_ICON_PATH);
            holder.accessoryView.setImageResource(imageResourceId);
        }
    }

    private void populateTabletAccessory (ViewHolder holder, IItem item){
        if (item.getIsKeyboard()){
            int imageResourceId = ItemUtil.getImageDrawableId(context, TABLET_ACCESSORY_ICON_PATH);
            holder.accessoryView.setImageResource(imageResourceId);
        }
    }
}
