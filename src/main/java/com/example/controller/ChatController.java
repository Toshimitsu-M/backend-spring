package com.example.controller;

import java.util.Optional;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.rdb.ChatRequest;
import com.example.service.ChatService;

@RestController
@RequestMapping("/chat")
@CrossOrigin(origins = "*") // CORS設定
public class ChatController {

    private final Optional<ChatService> chatServiceOptional;

    public ChatController(Optional<ChatService> chatServiceOptional) {
        this.chatServiceOptional = chatServiceOptional;
    }

    
    @PostMapping("/chat")
    public String chat(@RequestBody ChatRequest req) {
        String message = req.getMessage();
        
        if (chatServiceOptional.isEmpty()) {
            return "Chat機能は利用できません。OPENAI_API_KEYが設定されていないためです。";
        }
        
        String response = chatServiceOptional.get().call(message);
        return response;
    }
}
