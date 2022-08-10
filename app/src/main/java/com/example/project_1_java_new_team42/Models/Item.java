package com.example.project_1_java_new_team42.Models;

public abstract class Item implements IItem{

    protected String id;
    protected String name;
    protected String imagePath;
    protected String category;
    protected int price;
    protected String description;

    public Item(String id, String name, String imagePath,
                String category, int price, String description){
        this.id = id;
        this.name = name;
        this.imagePath = imagePath;
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

    public String getImagePath(){
        return imagePath;
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
