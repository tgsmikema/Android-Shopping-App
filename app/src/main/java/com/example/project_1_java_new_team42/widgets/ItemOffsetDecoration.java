package com.example.project_1_java_new_team42.widgets;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemOffsetDecoration extends RecyclerView.ItemDecoration {

    // Offset is defined to be half of the spacing between items
    private int horOffset;
    private int verOffset;
    private int gridCols;
    private RecyclerViewLayoutType layoutType = RecyclerViewLayoutType.VERTICAL;

    // Linear layouts
    private ItemOffsetDecoration(int offset, RecyclerViewLayoutType layoutType) {
        this.horOffset = offset;
        this.verOffset = offset;
        this.layoutType = layoutType;
    }

    // Grid layouts
    private ItemOffsetDecoration(int horOffset, int verOffset) {
        this.horOffset = horOffset;
        this.verOffset = verOffset;
    }

    /**
     * Use this constructor for a 1D linear layout type (e.g. LINEAR or HORIZONTAL)
     * If you use grid then you must use the other constructor, otherwise it throws an exception.
     * @param context
     * @param itemOffsetId
     * @param layoutType
     */
    public ItemOffsetDecoration(@NonNull Context context, @DimenRes int itemOffsetId, RecyclerViewLayoutType layoutType) {
        this(context.getResources().getDimensionPixelSize(itemOffsetId), layoutType);
        if (layoutType == RecyclerViewLayoutType.GRID) {
            throw new IllegalArgumentException("You must use the other constructor for grid layouts");
        }
    }

    /**
     * Use this constructor for a 2D grid layout type (e.g. GRID)
     * @param context
     * @param horOffset
     * @param verOffset
     */
    public ItemOffsetDecoration(@NonNull Context context, @DimenRes int horOffset, @DimenRes int verOffset, int gridCols) {
        this(context.getResources().getDimensionPixelSize(horOffset), context.getResources().getDimensionPixelSize(verOffset));
        this.layoutType = RecyclerViewLayoutType.GRID;
        this.gridCols = gridCols;
    }

    private void setGridOffsets(Rect rect, int pos) {
        int column = pos % gridCols; // item column

        boolean isFirstColumn = column == 0;
        boolean isLastColumn = column == gridCols - 1;

        rect.left = isFirstColumn ? 0: horOffset;
        rect.right = isLastColumn ? 0: horOffset;

        // Add spacing to the top if beyond first row
        if (pos >= gridCols) {
            rect.top = verOffset;
        }

    }

    private void setItemOffsets(Rect rect, View view, RecyclerView parent, RecyclerView.State state) {
        int pos = parent.getChildAdapterPosition(view);

        boolean isFirstItem = pos == 0;
        boolean isLastItem = pos == state.getItemCount() - 1;

        // These are only used for the 1D layouts so horOffset = verOffset.
        int startOffset = isFirstItem ? 0 : horOffset;
        int endOffset = isLastItem ? 0 : horOffset;

        switch (layoutType) {
            case HORIZONTAL:
                rect.set(startOffset, 0, endOffset, 0);
                break;
            case VERTICAL:
                rect.set(0, startOffset, 0, endOffset);
                break;
            case GRID:
                setGridOffsets(rect, pos);
                break;
            default:
                rect.set(0, 0, 0, 0);
        }
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        setItemOffsets(outRect, view, parent, state);
    }

    public void setGridCols(int gridCols) {
        if (layoutType == RecyclerViewLayoutType.GRID) {
            this.gridCols = gridCols;
        }
    }
}
