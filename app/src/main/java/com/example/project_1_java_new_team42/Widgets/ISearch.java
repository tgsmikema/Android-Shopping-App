package com.example.project_1_java_new_team42.Widgets;

public interface ISearch {
    interface OnSearchActionListener {
        void onSearch(String searchQuery);
    }

    void setOnSearchActionListener(OnSearchActionListener listener);
}
