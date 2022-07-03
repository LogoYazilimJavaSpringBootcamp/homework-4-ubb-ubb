package com.logo.producer;

import com.logo.model.User;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendToQueue(User user) {

        System.out.println("From producer: User name:" + user.getName());
        rabbitTemplate.convertAndSend("isbasi-routingkey",user.getName());


    }
}
