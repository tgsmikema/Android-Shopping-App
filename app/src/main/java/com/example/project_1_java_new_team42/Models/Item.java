package com.example.project_1_java_new_team42.Models;

import java.util.List;

public abstract class Item implements IItem{

    protected String id;
    protected String name;
    protected List<String> imagePaths;
    protected String category;
    protected int price;
    protected String description;

    public Item(String id, String name, List<String> imagePaths,
                String category, int price, String description){
        this.id = id;
        this.name = name;
        this.imagePaths = imagePaths;
        this.category = category;
        this.price = price;
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

    public String getDescription(){
        return description;
    }

}
