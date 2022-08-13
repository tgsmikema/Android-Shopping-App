package com.example.project_1_java_new_team42.Data;

public interface IFetchHandler<T> {

    void onFetchComplete(T data);

    void onFetchFail();

}
