package com.example.project_1_java_new_team42.Data.Senders;

import com.example.project_1_java_new_team42.Models.Order;

public interface IOrderDataSender {

    void writeCartOrderToFirestore(Order anOrder, ISendHandler dataSendHandler);


}
