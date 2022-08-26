package com.example.project_1_java_new_team42.Models;

import com.google.firebase.firestore.Exclude;

import java.util.List;

public abstract class Item implements IItem{

    protected String id;
    protected String name;
    protected List<String> imagePaths;
    protected String category;
    protected int price;
    protected int totalSold;
    protected String description;

    public Item(){

    }

    public Item(String id, String name, List<String> imagePaths,
                String category, int price, int totalSold, String description){
        this.id = id;
        this.name = name;
        this.imagePaths = imagePaths;
        this.category = category;
        this.price = price;
        this.totalSold = totalSold;
        this.description = description;

    }

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public List<String> getImagePaths(){
        return imagePaths;
    }

    public String getCategory(){
        return category;
    }

    public int getPrice(){
        return price;
    }

    public int getTotalSold() {
        return totalSold;
    }

    public String getDescription(){
        return description;
    }

    @Exclude
    public boolean getIsKeyboard(){
        throw new RuntimeException();
    }

    @Exclude
    public boolean getIsSSD(){
        throw new RuntimeException();
    }

    @Exclude
    public boolean getIsTouchScreen(){
        throw new RuntimeException();
    }


}
