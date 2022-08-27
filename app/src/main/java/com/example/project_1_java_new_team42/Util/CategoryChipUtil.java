package com.example.project_1_java_new_team42.Util;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;

import com.example.project_1_java_new_team42.Models.Category;
import com.example.project_1_java_new_team42.R;

/**
 * Utility class to help with the UI of the category chips.
 */
public class CategoryChipUtil {

    public static class CategoryChipData {
        private @ColorRes final int fgColor;
        private @ColorRes final int bgColor;
        private @DrawableRes final int icon;
        private final String text;

        public CategoryChipData(@ColorRes int fgColor, @ColorRes int bgColor, @DrawableRes int icon, String text) {
            this.fgColor = fgColor;
            this.bgColor = bgColor;
            this.icon = icon;
            this.text = text;
        }

        public @ColorRes int getFgColor() {
            return fgColor;
        }

        public @ColorRes int getBgColor() {
            return bgColor;
        }

        public @DrawableRes int getIcon() {
            return icon;
        }

        public String getText() {
            return text;
        }
    }

    public static CategoryChipData getChipDataFromCategory(String category) {
        switch (category) {
            case Category.DESKTOP:
                return new CategoryChipData(R.color.chip_blue_dark, R.color.chip_blue_light, R.drawable.ic_desktop, "Desktop");
            case Category.LAPTOP:
                return new CategoryChipData(R.color.chip_green_dark, R.color.chip_green_light, R.drawable.ic_laptop, "Laptop");
            case Category.TABLET:
                return new CategoryChipData(R.color.chip_tan_dark, R.color.chip_tan_light, R.drawable.ic_tablet, "Tablet");
            default:
                throw new IllegalArgumentException("Unknown category: " + category);
        }
    }
}
