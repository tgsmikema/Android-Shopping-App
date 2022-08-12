package com.example.project_1_java_new_team42.Data;

public interface IFetchHandler<T> {
    void onFetchComplete(T items);

    void onFetchFail(); // Still not decided what to pass in can leave blank if want.
}
