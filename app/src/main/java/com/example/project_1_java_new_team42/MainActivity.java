package com.example.project_1_java_new_team42;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project_1_java_new_team42.Data.IFetchHandler;
import com.example.project_1_java_new_team42.Models.IItem;

import java.util.List;

public class MainActivity extends AppCompatActivity {

//    IFetchHandler myFetchHandler = new FetchHandler<IItem>() {
//        void onFetchComplete(IItem items) {
//
//        }
//    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        myFetchHandler.onFetchComplete(itemsList);
    }

//    class FetchHandler implements IFetchHandler{
//
//        @Override
//        public List<IItem> onFetchComplete() {
//            return null;
//        }
//
//        @Override
//        public void onFetchFail() {
//
//        }
//    }
}