package com.example.project_1_java_new_team42.adapters;

import android.content.Context;
import android.view.LayoutInflater;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * A generic adapter that can be used for the types of recycler views needed in this application.
 *
 * @param <T> The type of the items in the recycler view which is stored in a list.
 * @param <K> The custom ViewHolder defined inside the adapter to allow for custom logic for each item.
 */
public abstract class GenericRecyclerViewAdapter<T, K extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<K> {

    protected List<T> items = new ArrayList<>();

    protected Context context;
    protected LayoutInflater layoutInflater;

    protected GenericRecyclerViewAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    protected GenericRecyclerViewAdapter(Context context, List<T> items) {
        this(context);
        this.items = items;
    }

    public void addItems(List<T> data) {
        int posStart = this.items.isEmpty() ? 0 : this.items.size() - 1;
        items.addAll(data);
        notifyItemRangeInserted(posStart, items.size());
    }

    public void clearItems() {
        int size = items.size();
        items.clear();
        notifyItemRangeRemoved(0, size);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
