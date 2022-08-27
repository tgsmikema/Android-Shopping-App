package com.example.project_1_java_new_team42.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
//import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_1_java_new_team42.Activities.CategoryItemsActivity;
import com.example.project_1_java_new_team42.Activities.MainActivity;
import com.example.project_1_java_new_team42.Models.Category;
import com.example.project_1_java_new_team42.R;
import com.example.project_1_java_new_team42.Util.ItemUtil;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class CategoriesRecyclerViewAdapter extends GenericRecyclerViewAdapter<Category, CategoriesRecyclerViewAdapter.ViewHolder> {

    public CategoriesRecyclerViewAdapter(Context context) {
        super(context);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        MaterialCardView categoryCardView;
        ImageView categoryImageView;
        TextView categoryNameTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            categoryImageView = itemView.findViewById(R.id.image_category_card);
            categoryNameTextView = itemView.findViewById(R.id.text_category_card_name);
            categoryCardView = itemView.findViewById(R.id.card_category);

            categoryCardView.setClickable(true);
            categoryCardView.setFocusable(true);
            categoryCardView.setOnClickListener(this);
        }

        private void navigateToCategoryItemsActivity(Category category) {
            Intent intent = new Intent(context, CategoryItemsActivity.class);
            intent.putExtra(MainActivity.INTENT_KEY_CATEGORY_NAME, category.getCategoryName());
            intent.putExtra(MainActivity.INTENT_KEY_CATEGORY_IMAGE_URI, category.getImageURI());

            Pair<View, String> p1 = Pair.create(categoryCardView, ViewCompat.getTransitionName(categoryCardView));
            Pair<View, String> p2 = Pair.create(categoryNameTextView, ViewCompat.getTransitionName(categoryNameTextView));
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity)context, p1, p2);
            context.startActivity(intent, options.toBundle());
        }

        @Override
        public void onClick(View view) {
            navigateToCategoryItemsActivity(items.get(getAdapterPosition()));
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(layoutInflater.inflate(R.layout.category_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = items.get(position);

        holder.categoryNameTextView.setText(category.getCategoryName());

        int drawableId = ItemUtil.getImageDrawableId(context, category.getImageURI());
        holder.categoryImageView.setImageResource(drawableId);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
