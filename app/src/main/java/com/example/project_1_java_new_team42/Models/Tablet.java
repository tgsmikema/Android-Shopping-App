package com.example.project_1_java_new_team42.Models;

import java.util.List;

public class Tablet extends Item{

    protected boolean isKeyboard;

    public boolean getIsKeyboard() {
        return isKeyboard;
    }

    public Tablet(){

    }
    
    public Tablet(String id, String name, List<String> imagePaths, String category, int price, int totalSold, String description, boolean isKeyboard) {
        super(id, name, imagePaths, category, price, totalSold, description);
        this.isKeyboard = isKeyboard;
    }
}
