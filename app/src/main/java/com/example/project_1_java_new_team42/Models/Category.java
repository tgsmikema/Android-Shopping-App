package com.example.project_1_java_new_team42.Models;

public class Category {
    private final String categoryName;
    private final String imageUri;

    public Category(String categoryName, String imageUri) {
        this.categoryName = categoryName;
        this.imageUri = imageUri;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getImageUri() {
        return imageUri;
    }
}
