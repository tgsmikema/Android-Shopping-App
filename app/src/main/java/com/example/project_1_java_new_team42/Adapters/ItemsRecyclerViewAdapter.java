package com.example.project_1_java_new_team42.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_1_java_new_team42.Activities.CategoryItemsActivity;
import com.example.project_1_java_new_team42.Activities.DetailsActivity;
import com.example.project_1_java_new_team42.Activities.MainActivity;
import com.example.project_1_java_new_team42.Models.Category;
import com.example.project_1_java_new_team42.Models.IItem;
import com.example.project_1_java_new_team42.R;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class ItemsRecyclerViewAdapter extends GenericRecyclerViewAdapter<IItem, ItemsRecyclerViewAdapter.ViewHolder> {

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
        MaterialCardView itemCardView;
        ImageView itemImageView;
        TextView itemNameTextView;
        TextView itemPriceTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            itemImageView = itemView.findViewById(R.id.image_itemcard);
            itemNameTextView = itemView.findViewById(R.id.text_itemcard_name);
            itemPriceTextView = itemView.findViewById(R.id.text_itemcard_price);
            itemCardView = itemView.findViewById(R.id.card_item);

            itemCardView.setClickable(true);
            itemCardView.setFocusable(true);
            itemCardView.setOnClickListener(this);
        }

        private void navigateToDetailsActivity(IItem selectedItem){
            Intent intent = new Intent(context, DetailsActivity.class);

            Activity activity = (Activity) context;
            if (activity.getLocalClassName().equalsIgnoreCase(NavigationAdapter.MAIN_ACTIVITY)){

                intent.putExtra(MainActivity.INTENT_KEY_ACTIVITY_NAME,MainActivity.INTENT_VALUE_MAIN_ACTIVITY);
                intent.putExtra(MainActivity.INTENT_KEY_ITEM_ID,selectedItem.getId());
                context.startActivity(intent);

            } else if (activity.getLocalClassName().equalsIgnoreCase(NavigationAdapter.CATEGORY_ITEMS_ACTIVITY)){

                intent.putExtra(MainActivity.INTENT_KEY_ACTIVITY_NAME,MainActivity.INTENT_VALUE_CATEGORY_ITEMS_ACTIVITY);
                intent.putExtra(MainActivity.INTENT_KEY_ITEM_ID,selectedItem.getId());
                intent.putExtra(MainActivity.INTENT_KEY_CATEGORY_NAME,category.getCategoryName());
                intent.putExtra(MainActivity.INTENT_KEY_CATEGORY_IMAGE_URI,category.getImageURI());
                context.startActivity(intent);


            } else if (activity.getLocalClassName().equalsIgnoreCase(NavigationAdapter.SEARCH_RESULT_ACTIVITY)){

                intent.putExtra(MainActivity.INTENT_KEY_ACTIVITY_NAME,MainActivity.INTENT_VALUE_SEARCH_RESULTS_ACTIVITY);
                intent.putExtra(MainActivity.INTENT_KEY_ITEM_ID,selectedItem.getId());
                intent.putExtra(MainActivity.INTENT_KEY_SEARCH,searchString);
                context.startActivity(intent);

            } else {
                Toast.makeText(context,"Sorry, but an ERROR occurred!",Toast.LENGTH_LONG).show();
            }

        }

        @Override
        public void onClick(View view) {
            navigateToDetailsActivity(items.get(getAdapterPosition()));
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
