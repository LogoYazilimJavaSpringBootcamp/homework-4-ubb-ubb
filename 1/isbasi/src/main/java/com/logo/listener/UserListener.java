/**package com.logo.listener;

import com.logo.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserListener {

    @RabbitListener(queues = "isbasi-queue")
    public void handleMessage(String email) {
        System.out.println("From listener: name" + email);
        log.info("User", email);
    }
}

*/