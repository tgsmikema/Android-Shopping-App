package com.example.project_1_java_new_team42.Models;

public class Category {

    public static final String LAPTOP = "laptop";
    public static final String DESKTOP = "desktop";
    public static final String TABLET = "tablet";

    private String categoryName;
    private String imageURI;

    public Category(){

    }

    public Category(String categoryName, String imageURI) {
        this.categoryName = categoryName;
        this.imageURI = imageURI;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getImageURI() {
        return imageURI;
    }
}