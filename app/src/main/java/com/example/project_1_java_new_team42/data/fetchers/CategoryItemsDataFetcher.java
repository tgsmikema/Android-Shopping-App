package com.example.project_1_java_new_team42.data.fetchers;

import androidx.annotation.NonNull;

import com.example.project_1_java_new_team42.data.util.AssignCategory;
import com.example.project_1_java_new_team42.models.IItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

/**
 * This class is used for fetching all items within a GIVEN specific category.
 */
public class CategoryItemsDataFetcher extends AssignCategory implements ICategoryItemsDataFetcher{

    public void readData(String category, IFetchHandler<List<IItem>> dataFetchHandler) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("items")
                .whereEqualTo("category", category)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
