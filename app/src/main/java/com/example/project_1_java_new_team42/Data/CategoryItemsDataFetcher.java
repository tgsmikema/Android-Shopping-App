package com.example.project_1_java_new_team42.Data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.project_1_java_new_team42.Models.Desktop;
import com.example.project_1_java_new_team42.Models.IItem;
import com.example.project_1_java_new_team42.Models.Laptop;
import com.example.project_1_java_new_team42.Models.Tablet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CategoryItemsDataFetcher extends AssignCategory {

    public void readData(String category, ICategoryItemsDataFetchHandler dataFetchHandler) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //convert input category string to capital letter for the first letter
        String camelCaseCategory = category.substring(0, 1).toUpperCase() + category.substring(1);

        db.collection("items").whereEqualTo("category",camelCaseCategory).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    final List<IItem> itemsList = assignCategory(task);
                    dataFetchHandler.onFetchComplete(itemsList);

                } else {
                    dataFetchHandler.onFetchFail();
                }
            }
        });
    }


}
