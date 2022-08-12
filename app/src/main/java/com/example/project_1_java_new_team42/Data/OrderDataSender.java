package com.example.project_1_java_new_team42.Data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.project_1_java_new_team42.Models.Order;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class OrderDataSender {

    public void writeCartOrderToFirestore(Order anOrder, IOrderDataSendHandler dataSendHandler){

        FirebaseFirestore db = FirebaseFirestore.getInstance();

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

}
