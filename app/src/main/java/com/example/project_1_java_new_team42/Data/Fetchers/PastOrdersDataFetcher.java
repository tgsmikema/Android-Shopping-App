package com.example.project_1_java_new_team42.Data.Fetchers;

import androidx.annotation.NonNull;

import com.example.project_1_java_new_team42.Models.Order;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to retrieve ALL -- PAST ORDERS.
 */
public class PastOrdersDataFetcher implements IPastOrdersDataFetcher{

    public void readData(IFetchHandler<List<Order>> dataFetchHandler) {
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
