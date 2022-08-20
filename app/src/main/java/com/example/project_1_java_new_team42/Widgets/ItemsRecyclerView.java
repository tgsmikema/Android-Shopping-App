package com.example.project_1_java_new_team42.Widgets;

import android.content.Context;
import android.util.DisplayMetrics;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_1_java_new_team42.Adapters.ItemsRecyclerViewAdapter;
import com.example.project_1_java_new_team42.R;

/**
 * Custom Recycler View specifically for displaying items and allow
 * for flexible layouts as different layouts are used throughout the app
 * (i.e. Grid, Horizontal and Vertical)
 */
public class ItemsRecyclerView {
    public static final float ITEM_COLUMN_WIDTH_DP = 200;

    private final Context context;
    private final RecyclerView recyclerView;
    private final ItemsRecyclerViewAdapter adapter;
    private final int columns;

    public ItemsRecyclerView(Context context, RecyclerView recyclerView, RecyclerViewLayoutType layoutType) {
        this.context = context;

        this.columns = calculateNoOfColumns(ITEM_COLUMN_WIDTH_DP);

        this.recyclerView = recyclerView;
        setLayoutManager(layoutType);

        addSpacingDecoration(layoutType);

        this.adapter = new ItemsRecyclerViewAdapter(context);
        this.recyclerView.setAdapter(adapter);
    }

    private int calculateNoOfColumns(float columnWidthDp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (screenWidthDp / columnWidthDp + 0.5); // +0.5 for correct rounding to int.
    }

    private void setLayoutManager(RecyclerViewLayoutType layoutType) {
        switch (layoutType) {
            case HORIZONTAL:
                recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                break;
            case VERTICAL:
                recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                break;
            case GRID:
                recyclerView.setLayoutManager(new GridLayoutManager(context, columns));
                break;
        }
    }

    private void addSpacingDecoration(RecyclerViewLayoutType layoutType) {
        switch (layoutType) {
            case HORIZONTAL:
                recyclerView.addItemDecoration(new ItemOffsetDecoration(context, R.dimen.rv_card_item_hor_offset, layoutType));
                break;
            case VERTICAL:
                recyclerView.addItemDecoration(new ItemOffsetDecoration(context, R.dimen.rv_card_item_ver_offset, layoutType));
                break;
            case GRID:
                recyclerView.addItemDecoration(new ItemOffsetDecoration(context, R.dimen.rv_card_item_hor_offset, R.dimen.rv_card_item_ver_offset, columns));
                break;
        }
    }

    public ItemsRecyclerViewAdapter getAdapter() {
        return adapter;
    }
}
