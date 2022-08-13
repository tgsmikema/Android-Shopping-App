package com.example.project_1_java_new_team42.Data;

import androidx.annotation.NonNull;

import com.example.project_1_java_new_team42.Models.Cart;
import com.example.project_1_java_new_team42.Models.IItem;
import com.example.project_1_java_new_team42.Models.ItemWithQuantity;
import com.example.project_1_java_new_team42.Models.Order;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CartDataFetcher {

    public void readData(IFetchHandler<Cart> dataFetchHandler) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Cart cart = new Cart();

        db.collection("cart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot queryItem : task.getResult()) {
                        ItemWithQuantity anItemWithQuantity = queryItem.toObject(ItemWithQuantity.class);

                        cart.addItemToCart(anItemWithQuantity);
                    }
                    dataFetchHandler.onFetchComplete(cart);


                } else {
                    dataFetchHandler.onFetchFail();
                }
            }
        });
    }
}
