package com.example.project_1_java_new_team42.Models;

import java.util.List;

public class Tablet extends Item{
    
    public Tablet(String id, String name, List<String> imagePaths, String category, int price, int totalSold, String description) {
        super(id, name, imagePaths, category, price, totalSold, description);
    }
}
