package com.example.project_1_java_new_team42.data.Senders;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.project_1_java_new_team42.models.ItemWithQuantity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * This class is used for sending Queries regarding shopping CART
 *
 * Methods:
 * 1) addItemWithQuantityToCart
 * 2) deleteSingleCartItem
 * 3) deleteAllCartItems
 *
 */
public class CartDataSender implements ICartDataSender{

    /**
     * this method adds an `ItemWithQuantity` to the CART database.
     * @param anItemWithQuantity
     * @param dataSendHandler
     */
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

    /**
     * This methods delete a single Item record from the CART in the database GIVEN a `Id` of an Item.
     * @param id
     * @param dataSendHandler
     */
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

    /**
     * This Method DELETES all items in the shopping CART.
     * EFFECTIVELY empty shopping cart, USE WITH CAUTION!!!!
     * @param dataSendHandler
     */
    public void deleteAllCartItems(ISendHandler dataSendHandler){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("cart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                        if (queryDocumentSnapshot.exists()){
                            //get a reference to each document, then delete the selected document,
                            //effectively deleting all documents one by one till CART is empty.
                            queryDocumentSnapshot.getReference().delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d("Delete All from Cart","All cart items DELETED." );
                                    dataSendHandler.onSendSuccess(true);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("Delete All from Cart","All cart items NOT DELETED!!!" );
                                    dataSendHandler.onSendSuccess(false);
                                }
                            });
                        }
                    }
                } else {
                    dataSendHandler.onSendSuccess(false);
                }
            }
        });

    }




}
