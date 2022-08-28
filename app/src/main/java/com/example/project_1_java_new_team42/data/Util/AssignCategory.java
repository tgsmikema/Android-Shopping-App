package com.example.project_1_java_new_team42.data.Util;

import android.util.Log;

import com.example.project_1_java_new_team42.models.Category;
import com.example.project_1_java_new_team42.models.Desktop;
import com.example.project_1_java_new_team42.models.IItem;
import com.example.project_1_java_new_team42.models.Laptop;
import com.example.project_1_java_new_team42.models.Tablet;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used for determine what concrete class of each item belongs to when retrieving
 * data from the database, and hence it's a helper class to aid the Java Deserialization process.
 */
public abstract class AssignCategory {

    protected List<IItem> assignCategory(Task<QuerySnapshot> task) {

        List<IItem> itemsList = new ArrayList<IItem>();

        for (QueryDocumentSnapshot queryItem : task.getResult()) {

            IItem anItem;

            if (queryItem.get("category").toString().equalsIgnoreCase(Category.LAPTOP)) {
                anItem = queryItem.toObject(Laptop.class);
                itemsList.add(anItem);
            } else if (queryItem.get("category").toString().equalsIgnoreCase(Category.TABLET)) {
                anItem = queryItem.toObject(Tablet.class);
                itemsList.add(anItem);
            } else if (queryItem.get("category").toString().equalsIgnoreCase(Category.DESKTOP)) {
                anItem = queryItem.toObject(Desktop.class);
                itemsList.add(anItem);
            } else {
                throw new UnsupportedOperationException("Class Unimplemented ERROR!");
            }
            Log.i("Parsing Items", anItem.getId() + " loaded.");
        }
        return itemsList;
    }
}
