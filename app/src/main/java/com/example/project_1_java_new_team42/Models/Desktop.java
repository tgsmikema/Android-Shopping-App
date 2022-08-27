package com.example.project_1_java_new_team42.Models;

import java.util.List;

public class Desktop extends Item{

    protected boolean isSSD;

    public boolean getIsSSD() {
        return isSSD;
    }


    public Desktop(){

    }


    public Desktop(String id, String name, List<String> imagePaths, String category, int price, int totalSold, String description, boolean isTouchScreen) {
        super(id, name, imagePaths, category, price, totalSold, description);
        this.isSSD = isTouchScreen;
    }
}
