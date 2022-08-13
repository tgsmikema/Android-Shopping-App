package com.example.project_1_java_new_team42.Data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.project_1_java_new_team42.Models.ItemWithQuantity;
import com.example.project_1_java_new_team42.Models.Order;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class OrderDataSender {

    public void writeCartOrderToFirestore(Order anOrder, ISendHandler dataSendHandler){

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //update 'orders' collection TOTAL SOLD field.
        anOrder.updateItemsTotalSoldField();
        //update 'items' collection TOTAL SOLD field.
        updateTotalSoldItemsCollection(anOrder);

        db.collection("orders").document(anOrder.getOrderId()).set(anOrder).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("New Order Add","order: " + anOrder.getOrderId() + " added.");
                dataSendHandler.onSendSuccess(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("New Order Add", "order: " + anOrder.getOrderId() + "NOT added!!!");
                dataSendHandler.onSendSuccess(false);
            }
        });

    }


    private void updateTotalSoldItemsCollection(Order anOrder){

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        for (ItemWithQuantity itemWithQuantity : anOrder.getOrderItems()){

            int currentItemOrderedQuantity = itemWithQuantity.getQuantity();
            String currentItemId = itemWithQuantity.getId();

            DocumentReference currentItemInFirestore = db.collection("items").document(currentItemId);
            currentItemInFirestore.update("totalSold", FieldValue.increment(currentItemOrderedQuantity));
        }

    }

}
