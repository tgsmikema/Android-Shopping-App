package com.example.project_1_java_new_team42.Util;

import android.content.Context;

import androidx.annotation.DrawableRes;

public class ItemUtil {
    public static String addDollarSignToPrice(int price) {
        return "$" + price;
    }

    public static @DrawableRes int getImageDrawableId(Context ctx, String path) {
        return getImageDrawableId(ctx, path, 0);
    }

    public static @DrawableRes int getImageDrawableId(Context ctx, String path, int pos) {
        return ctx.getResources().getIdentifier(path, "drawable", ctx.getPackageName());
    }
}
