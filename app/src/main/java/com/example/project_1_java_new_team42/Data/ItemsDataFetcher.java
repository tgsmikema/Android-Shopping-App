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

public class ItemsDataFetcher {
    List<IItem> fetchItems(IFetchHandler fetchHandler) {
        List<IItem> itemsList = new ArrayList<IItem>(); // Use any list implementation as long consistent

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("items").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot queryItem : task.getResult()) {
                        IItem anItem;
                        if (queryItem.get("category").toString().equalsIgnoreCase("laptop")) {
                            anItem = queryItem.toObject(Laptop.class);
                            itemsList.add(anItem);
                        } else if (queryItem.get("category").toString().equalsIgnoreCase("tablet")) {
                            anItem = queryItem.toObject(Tablet.class);
                            itemsList.add(anItem);
                        } else if (queryItem.get("category").toString().equalsIgnoreCase("desktop")) {
                            anItem = queryItem.toObject(Desktop.class);
                            itemsList.add(anItem);
                        } else {
                            throw new UnsupportedOperationException("Class Unimplemented ERROR!");
                        }
                        fetchHandler.onFetchComplete(itemsList);
                        Log.i("Parsing Items", anItem.getId() + " loaded.");
                    }

                } else {
                    fetchHandler.onFetchFail();
                }
            }
        });

        return itemsList;
    }
}

