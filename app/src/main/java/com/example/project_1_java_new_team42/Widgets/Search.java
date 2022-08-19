package com.example.project_1_java_new_team42.Widgets;

import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class Search {
    private final EditText searchEditText;

    public interface OnSearchActionListener {
        void onSearch(String searchQuery);
    }

    public Search(EditText searchEditText) {
        this.searchEditText = searchEditText;
        setProperties();
    }

    private void setProperties() {
        setImeOptionsToSearch();
        setSearchFieldToSingleLine();
        setInputTypeAsText();
        searchEditText.setNextFocusForwardId(searchEditText.getId());
    }

    private void setInputTypeAsText() {
        searchEditText.setInputType(EditorInfo.TYPE_CLASS_TEXT);
    }

    private void setSearchFieldToSingleLine() {
        int SINGLE_LINE = 1;
        searchEditText.setMaxLines(SINGLE_LINE);
    }

    private void setImeOptionsToSearch() {
        searchEditText.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
    }

    public void setOnSearchActionListener(OnSearchActionListener listener) {
        searchEditText.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Boolean pressedSearch = actionId == EditorInfo.IME_ACTION_SEARCH;
                Boolean hardKeyboardEnter = event != null && event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER;

                if (pressedSearch || hardKeyboardEnter) {
                    listener.onSearch(searchEditText.getText().toString());
                    return true;
                }

                return false;
            }
        });
    }
}
