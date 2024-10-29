package com.messagin.app.rabbitmq.services;

import com.messagin.app.rabbitmq.model.ChatMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {
    private final RabbitTemplate rabbitTemplate;

    public MessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(ChatMessage message) {
        rabbitTemplate.convertAndSend("chatExchange", "chatKey",  message);
    }
}
