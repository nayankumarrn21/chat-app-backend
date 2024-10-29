package com.messagin.app.rabbitmq.services;

import com.messagin.app.rabbitmq.model.ChatMessage;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumer {

    private final RabbitTemplate rabbitTemplate;

    public MessageConsumer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "chatQueue") // Your chat queue
    public void receiveMessage(ChatMessage message, MessageProperties messageProperties) {
        System.out.println("--------- Received Msg ---------");
        System.out.println(message);

        // Check if there is a replyTo property
        String replyTo = messageProperties.getReplyTo();
        if (replyTo != null) {
            // Create a response message
            ChatMessage responseMessage = new ChatMessage(); // Populate your response message
            responseMessage.setContent("Received: " + message.getContent());
            responseMessage.setSender(message.getSender());

            // Send the response back to the replyTo queue
            rabbitTemplate.convertAndSend(replyTo, responseMessage);
        }

        System.out.println("--------- End ---------");
    }
}
