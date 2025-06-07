package com.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);
    
    @Autowired
    private ChatClient chatClient;

    public String call(String message) {
        logger.info("Received message: {}", message);
        String response = chatClient.prompt()
                .user(message)
                .call()
                .content();
        return response;
    }
}
