package com.example.project_1_java_new_team42.models;

import java.util.List;

public interface IItem {

    String getId();

    String getName();

    List<String> getImagePaths();

    String getCategory();

    int getPrice();

    int getTotalSold();

    String getDescription();

    boolean getIsKeyboard();

    boolean getIsTouchScreen();

    boolean getIsSSD();

}
