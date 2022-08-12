package com.example.project_1_java_new_team42.Data;

import android.util.Log;


import com.example.project_1_java_new_team42.Models.Desktop;
import com.example.project_1_java_new_team42.Models.IItem;
import com.example.project_1_java_new_team42.Models.Laptop;
import com.example.project_1_java_new_team42.Models.Tablet;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class CategoriesDataFetcher {
    public void readItems(IFetchHandler iFetchHandler) {
        List<IItem> itemsList = new ArrayList<IItem>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("items").get().addOnCompleteListener(task -> {
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

                    Log.i("Parsing Items", anItem.getId() + " loaded.");
                }

                iFetchHandler.onFetchComplete(itemsList);

            } else {
                iFetchHandler.onFetchFail();
            }
        });
    }
}
