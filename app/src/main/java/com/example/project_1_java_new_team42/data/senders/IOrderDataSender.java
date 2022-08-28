package com.example.project_1_java_new_team42.data.senders;

import com.example.project_1_java_new_team42.models.Order;

public interface IOrderDataSender {

    void writeCartOrderToFirestore(Order anOrder, ISendHandler dataSendHandler);


}
