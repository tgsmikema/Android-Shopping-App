package com.example.project_1_java_new_team42.Models;

import com.google.firebase.firestore.Exclude;

public class Category {
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

    @Exclude
    public String getDocId() {
        String docId = categoryName.substring(0, 1).toLowerCase() + categoryName.substring(1);
        if (docId.endsWith("s")) {
            docId = docId.substring(0, docId.length() - 1);
        }
        return docId;
    }
}