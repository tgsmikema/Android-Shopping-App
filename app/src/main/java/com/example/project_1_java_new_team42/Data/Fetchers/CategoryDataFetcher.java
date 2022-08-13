package com.example.project_1_java_new_team42.Data.Fetchers;

import androidx.annotation.NonNull;

import com.example.project_1_java_new_team42.Models.Category;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for Retrieving the name of Categories and image path.
 */
public class CategoryDataFetcher {

    public void readData(IFetchHandler<List<Category>> iFetchHandler) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<Category> categoriesList = new ArrayList<>();

        db.collection("categories").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot queryItem : task.getResult()) {

                        Category aCategory = queryItem.toObject(Category.class);
                        categoriesList.add(aCategory);
                    }
                    iFetchHandler.onFetchComplete(categoriesList);
                } else {
                    iFetchHandler.onFetchFail();
                }
            }
        });
    }
}
