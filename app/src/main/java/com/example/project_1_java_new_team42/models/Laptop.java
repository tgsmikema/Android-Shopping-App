package com.example.project_1_java_new_team42.models;

import java.util.List;

public class Laptop extends Item{

    protected boolean isTouchScreen;

    public boolean getIsTouchScreen() {
        return isTouchScreen;
    }

    public Laptop(){

    }
    public Laptop(String id, String name, List<String> imagePaths, String category, int price, int totalSold, String description, boolean isSSD) {
        super(id, name, imagePaths, category, price, totalSold, description);
        this.isTouchScreen = isSSD;
    }
}
