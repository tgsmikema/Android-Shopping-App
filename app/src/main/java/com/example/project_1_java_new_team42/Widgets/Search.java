package com.example.project_1_java_new_team42.Widgets;

import android.content.Context;
import android.graphics.Rect;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class Search implements ISearch {
    private final EditText searchEditText;

    private boolean disableSearchIfEmpty = false; // Setting
    private boolean clearQueryOnSearch = false; // Setting

    /**
     * Setting that allows the user to optionally clear the text field
     * upon a search action.
     * @param clearQueryOnSearch setting that clears the query upon searching action.
     */
    public void setClearQueryOnSearch(boolean clearQueryOnSearch) {
        this.clearQueryOnSearch = clearQueryOnSearch;
    }

    private void clearSearch() {
        searchEditText.setText("");
    }

    /**
     * Setting that allows the user to disable the search
     * if it is empty. User may want to allow search of empty fields
     * or handle it themselves.
     * @param disableSearch setting that will disable the search if it is empty.
     */
    public void setDisableSearchIfEmpty(boolean disableSearch) {
        this.disableSearchIfEmpty = disableSearch;
    }

    public Search(EditText searchEditText) {
        this.searchEditText = searchEditText;
        setProperties();
    }

    private void setProperties() {
        setImeOptionsToSearch();
        setSearchFieldToSingleLine();
        setInputTypeAsText();
        setOnFocusChangeListener();
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

    // Hides the keyboard when the user clicks outside of the search bar
    // https://stackoverflow.com/questions/4828636/edittext-clear-focus-on-touch-outside
    public static void handleTouchEventOutsideKeyboard(MotionEvent event, View view) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (view instanceof EditText) {
                Rect rect = new Rect();
                view.getGlobalVisibleRect(rect);

                boolean rectContainsTouch = rect.contains((int) event.getRawX(), (int) event.getRawY());

                if (!rectContainsTouch) {
                    view.clearFocus();
                    hideKeyboard(view);
                }
            }
        }
    }

    private static void hideKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    private void setOnFocusChangeListener() {
        searchEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
    }

    public void setOnSearchActionListener(OnSearchActionListener listener) {
        searchEditText.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String searchText = searchEditText.getText().toString();
                Boolean pressedSearch = actionId == EditorInfo.IME_ACTION_SEARCH;
                Boolean hardKeyboardEnter = event != null && event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER;

                if (disableSearchIfEmpty && searchText.isEmpty()) {
                    return true;
                }

                if (pressedSearch || hardKeyboardEnter) {
                    if (clearQueryOnSearch) {
                        clearSearch();
                    }
                    listener.onSearch(searchEditText, searchText);
                    hideKeyboard(searchEditText);
                    return true;
                }

                return false;
            }
        });
    }
}
