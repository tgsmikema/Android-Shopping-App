package com.example.project_1_java_new_team42.Data;

import com.example.project_1_java_new_team42.Data.IFetchHandler;
import com.example.project_1_java_new_team42.Models.Category;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CategoriesDataFetcher {


    private List<Category> convertSnapshotsToCategories(QuerySnapshot snapshot) {
        // TODO Create views for these categories (fetch is successful)
        List<Category> categories = new ArrayList<>();
        for (QueryDocumentSnapshot doc : Objects.requireNonNull(snapshot)) {
            Category category = new Category(doc.getId(), doc.getString("imageURI"));
            categories.add(category);
        }
        return categories;
    }

    public void fetchItems(IFetchHandler<List<Category>> iFetchHandler) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("categories").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Category> categories = convertSnapshotsToCategories(task.getResult());
                iFetchHandler.onFetchComplete(categories);
            } else {
                iFetchHandler.onFetchFail();
            }
        });
    }
}
