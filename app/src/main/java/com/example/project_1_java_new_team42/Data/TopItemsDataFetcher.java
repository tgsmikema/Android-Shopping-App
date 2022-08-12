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
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.core.OrderBy;

import java.util.ArrayList;
import java.util.List;

public class TopItemsDataFetcher extends AssignCategory {

    final private int NO_OF_TOP_ITEMS_TO_FETCH = 6;

    public void readData(ITopItemsDataFetchHandler dataFetchHandler) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("items").orderBy("totalSold", Query.Direction.DESCENDING).limit(NO_OF_TOP_ITEMS_TO_FETCH).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
