package com.example.project_1_java_new_team42.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_1_java_new_team42.Models.Category;
import com.example.project_1_java_new_team42.R;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class CategoriesRecyclerViewAdapter extends RecyclerView.Adapter<CategoriesRecyclerViewAdapter.ViewHolder> {
    private List<Category> categoriesData = new ArrayList<>();
    private final LayoutInflater layoutInflater;
    private final Context context;

    public CategoriesRecyclerViewAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setData(List<Category> categoriesData) {
        this.categoriesData = categoriesData;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView categoryImageView;
        TextView categoryNameTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            categoryImageView = itemView.findViewById(R.id.image_category_card);
            categoryNameTextView = itemView.findViewById(R.id.text_category_card_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // TODO Navigate to the category items page
            Category category = categoriesData.get(getAdapterPosition());
            Toast.makeText(context, "Name: " + category.getCategoryName(), Toast.LENGTH_SHORT).show();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(layoutInflater.inflate(R.layout.category_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = categoriesData.get(position);
        int drawableId = context.getResources().getIdentifier(category.getImageURI(), "drawable", context.getPackageName());
        holder.categoryNameTextView.setText(category.getCategoryName());
        holder.categoryImageView.setImageResource(drawableId);
    }

    @Override
    public int getItemCount() {
        return categoriesData.size();
    }
}
