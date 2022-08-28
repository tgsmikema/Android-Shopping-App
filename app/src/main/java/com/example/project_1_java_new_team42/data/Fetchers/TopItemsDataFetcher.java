package com.example.project_1_java_new_team42.data.Fetchers;

import com.example.project_1_java_new_team42.data.Util.AssignCategory;
import com.example.project_1_java_new_team42.models.IItem;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;

/**
 * This class is used for retrieving a List of TOP ITEMS.
 */
public class TopItemsDataFetcher extends AssignCategory implements ITopItemsDataFetcher{

    final private int NO_OF_TOP_ITEMS_TO_FETCH = 6;

    public void readData(IFetchHandler<List<IItem>> dataFetchHandler) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("items")
            .orderBy("totalSold", Query.Direction.DESCENDING)
            .limit(NO_OF_TOP_ITEMS_TO_FETCH)
            .get()
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    final List<IItem> itemsList = assignCategory(task);
                    dataFetchHandler.onFetchComplete(itemsList);
                } else {
                    dataFetchHandler.onFetchFail();
                }
            })
            .addOnFailureListener(task -> dataFetchHandler.onFetchFail());
    }
}
