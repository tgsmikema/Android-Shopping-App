package com.example.project_1_java_new_team42.Data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.project_1_java_new_team42.Models.ItemWithQuantity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class CartDataSender {

    public void addItemWithQuantityToCart(ItemWithQuantity anItemWithQuantity, ISendHandler dataSendHandler){

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("cart").document(anItemWithQuantity.getId()).set(anItemWithQuantity).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("New Item Added To Cart","Item: " + anItemWithQuantity.getId() + " added.");
                dataSendHandler.onSendSuccess(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("New Item Added To Cart","Item: " + anItemWithQuantity.getId() + " NOT added!!!!");
                dataSendHandler.onSendSuccess(false);
            }
        });

    }

    public void deleteSingleCartItem(String id, ISendHandler dataSendHandler){

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("cart").document(id).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("Item delete from Cart","Item: " + id + " DELETED." );
                        dataSendHandler.onSendSuccess(true);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Item delete from Cart","Item: " + id + "NOT DELETED !!!!" );
                        dataSendHandler.onSendSuccess(false);
                    }
                });

    }

}
