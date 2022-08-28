package com.example.project_1_java_new_team42.widgets;

import android.widget.EditText;

public interface ISearch {
    interface OnSearchActionListener {
        void onSearch(EditText view, String searchQuery);
    }

    void setOnSearchActionListener(OnSearchActionListener listener);
}
