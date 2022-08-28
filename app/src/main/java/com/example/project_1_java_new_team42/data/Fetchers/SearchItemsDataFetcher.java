package com.example.project_1_java_new_team42.data.Fetchers;

import androidx.annotation.NonNull;

import com.example.project_1_java_new_team42.data.Util.AssignCategory;
import com.example.project_1_java_new_team42.models.IItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


/**
 * This class is to retrieve a List of ITEMS given a search string is part of the ITEM's name.
 */
public class SearchItemsDataFetcher extends AssignCategory implements ISearchItemsDataFetcher{
    private boolean itemNameContainsSubstring(IItem item, String searchString) {
        return item.getName().toLowerCase().contains(searchString.toLowerCase());
    }

    public void readData(String name , IFetchHandler<List<IItem>> dataFetchHandler) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("items").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    final List<IItem> itemsList = assignCategory(task);
                    List<IItem> searchList = new ArrayList<>();

                    // Firestore does not contain any methods of querying for substrings
                    // Recommended for small datasets to process on client side (for our case OK)
                    for (IItem anItem : itemsList){
                        if (itemNameContainsSubstring(anItem, name)){
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
