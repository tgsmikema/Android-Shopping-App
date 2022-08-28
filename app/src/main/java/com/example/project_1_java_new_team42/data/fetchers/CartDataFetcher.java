package com.example.project_1_java_new_team42.data.fetchers;

import androidx.annotation.NonNull;

import com.example.project_1_java_new_team42.models.Cart;
import com.example.project_1_java_new_team42.models.ItemWithQuantity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * This class is used for getting all current items inside the shopping cart.
 */
public class CartDataFetcher implements ICartDataFetcher{

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
