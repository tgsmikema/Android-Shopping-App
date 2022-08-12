package com.example.project_1_java_new_team42.Data;

import androidx.annotation.NonNull;

import com.example.project_1_java_new_team42.Models.IItem;
import com.example.project_1_java_new_team42.Models.Order;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PastOrdersDataFetcher {

    public void readData(IPastOrdersDataFetchHandler dataFetchHandler) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<Order> ordersList = new ArrayList<>();

        db.collection("orders").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot queryItem : task.getResult()) {

                        Order anOrder = queryItem.toObject(Order.class);
                        ordersList.add(anOrder);
                    }
                    dataFetchHandler.onFetchComplete(ordersList);
                } else {
                    dataFetchHandler.onFetchFail();
                }
            }
        });
    }
}
