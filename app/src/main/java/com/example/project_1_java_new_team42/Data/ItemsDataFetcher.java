package com.example.project_1_java_new_team42.Data;

import com.example.project_1_java_new_team42.Models;

public class ItemsDataFetcher {
    IItems fetchItems(IFetchHandler fetchHandler) {
        List<IItems> items = new ArrayList<IItems>(); // Use any list implementation as long consistent

        firestore...addOnCompleteListener(new onCompleteListener()) {
            @Override
            onComplete() {
                if (task.isSuccessful) {
                    QuerySnapshot results = task.getResult();
                    for (IItems item : task.getResult().toObjects(Number.class)) {
                        items.add(aNumber);
                    }
                    fetchHandler.onFetchComplete(items)
                    return items
                } else {
                    fetchHandler.onFetchFail()
                }
            }
        })
    }
}
