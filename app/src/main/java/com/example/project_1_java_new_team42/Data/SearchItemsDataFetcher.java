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

public class SearchItemsDataFetcher extends AssignCategory {
    public void readData(ISearchItemsDataFetchHandler dataFetchHandler, String name) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("items").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    final List<IItem> itemsList = assignCategory(task);
                    List<IItem> searchList = new ArrayList<>();
                    // check for substrings
                    for (IItem anItem : itemsList){
                        if (anItem.getName().toLowerCase().contains(name.toLowerCase())){
                            searchList.add(anItem);
                        }
                    }

                    dataFetchHandler.onFetchComplete(searchList);

                } else {
                    dataFetchHandler.onFetchFail();
                }
            }
        });
    }
}
