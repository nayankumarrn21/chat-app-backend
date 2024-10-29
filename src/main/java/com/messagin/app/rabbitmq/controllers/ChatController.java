package com.messagin.app.rabbitmq.controllers;

import com.messagin.app.rabbitmq.model.ChatMessage;
import com.messagin.app.rabbitmq.services.MessageProducer;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class ChatController {

    private final MessageProducer messageProducer;

    @GetMapping("/test")
    public String test(){
        return "Success";
    }

    public ChatController(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    @PostMapping("/send")
    public void sendMessage(@RequestBody ChatMessage message) {
        message.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        messageProducer.sendMessage(message);
    }


    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public ChatMessage sendMessage_1(ChatMessage message) {
        System.out.println(message);
        message.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        messageProducer.sendMessage(message);
        return message;
    }
}
