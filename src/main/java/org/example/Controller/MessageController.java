package org.example.Controller;

import org.example.Model.Message;
import org.example.Repository.MessageRepository;
import org.example.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class MessageController {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserService userService;

    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat")
    public void sendMessage(Message message){
        message.setTimestamp(LocalDateTime.now());
        messageRepository.save(message);
        // send message both send to reciver

        messagingTemplate.convertAndSend("topic/message"+ message.getReceiver(),message);
        messagingTemplate.convertAndSend("/topic/messages/" + message.getSender(), message);
    }

    @GetMapping("/api/messages")
    public List<Message> getMessages(
            @RequestParam String sender,
            @RequestParam String receiver) {
        return messageRepository.findMessages(sender, receiver);
    }

}
