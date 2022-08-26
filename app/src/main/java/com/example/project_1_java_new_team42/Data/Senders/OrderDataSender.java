package com.example.project_1_java_new_team42.Data.Senders;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.project_1_java_new_team42.Activities.DetailsActivity;
import com.example.project_1_java_new_team42.Models.ItemWithQuantity;
import com.example.project_1_java_new_team42.Models.Order;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * This class is used for when Submiting an ORDER, and post the order to database.
 * REFER to methods description for more details.
 */
public class OrderDataSender {

    /**
     * This method is used to write the Submitted ORDER to the database.
     * This method performs a several operations:
     *
     * 1) update 'orders' collection `totalSold` field.
     * 2) update 'items' collection `totalSold` field.
     * 3) empty shopping cart.
     * 4) write the ORDER detail to database.
     *
     * @param anOrder
     * @param dataSendHandler
     */
    public void writeCartOrderToFirestore(Order anOrder, ISendHandler dataSendHandler){

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //update 'orders' collection TOTAL SOLD field.
        anOrder.updateItemsTotalSoldField();
        //update 'items' collection TOTAL SOLD field.
        updateTotalSoldItemsCollection(anOrder);
        //empty shopping cart.
        emptyCart();


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

    /**
     * This method updates the database's `items` collection `totalSold` field of each ITEM specified
     * in the order.
     * @param anOrder
     */
    private void updateTotalSoldItemsCollection(Order anOrder){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // for each ItemWithQuantity
        for (ItemWithQuantity itemWithQuantity : anOrder.getOrderItems()){
            // get the quantity of the current iterated item that has been sold.
            int currentItemOrderedQuantity = itemWithQuantity.getQuantity();
            // get the id of the current iterated item that has been sold.
            String currentItemId = itemWithQuantity.getId();

            // get the document reference of the current item sold in the `items` collection from the database
            DocumentReference currentItemInFirestore = db.collection("items").document(currentItemId);
            // increase the database `totalSold` field of the selected item by QUANTITY specified on the order.
            currentItemInFirestore.update("totalSold", FieldValue.increment(currentItemOrderedQuantity));
        }

    }

    private void emptyCart(){
        CartDataSender cartDataSender = new CartDataSender();

        cartDataSender.deleteAllCartItems(new ISendHandler() {
            @Override
            public void onSendSuccess(boolean isSuccess) {
                // Do Nothing here.
            }
        });
    }

}
