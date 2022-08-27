package com.example.project_1_java_new_team42.Data.Fetchers;

import androidx.annotation.NonNull;

import com.example.project_1_java_new_team42.Data.Util.AssignCategory;
import com.example.project_1_java_new_team42.Models.IItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

/**
 * This class is used for retrieving specific single ITEM info GIVEN its ID.
 */
public class ItemDetailsDataFetcher extends AssignCategory implements IItemDetailsDataFetcher{
    public void readData(String id , IFetchHandler<List<IItem>> dataFetchHandler) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("items").whereEqualTo("id",id).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
