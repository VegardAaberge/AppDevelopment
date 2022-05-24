package com.example.chat.room;

import com.example.chat.data.MessageRepository;
import com.example.chat.data.MessageRequest;
import com.example.chat.data.model.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.webjars.NotFoundException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
@RequestMapping(path = "messages")
public class RoomController {

    @Autowired
    private MessageRepository messageRepository;

    private final ConcurrentHashMap<String, Member> members = new ConcurrentHashMap<>();

    public void onJoin(String username, String sessionId, WebSocketSession socket){

        if(members.containsKey(username)) {
            throw new MemberAlreadyExistsException();
        }
        members.put(username, new Member(
                username,
                sessionId,
                socket
        ));
    }

    public void sendMessage(String senderUsername, MessageRequest message) {

        Message messageEntity = new Message(
                message.getMessage(),
                senderUsername,
                LocalDateTime.now()
        );
        messageRepository.save(messageEntity);

        MessageResponse messageResponse = messageRepository.findResponseById(messageEntity.getId())
                .orElseThrow(() -> new NotFoundException("Message not found"));

        members.values().forEach( member -> {
            try {
                ObjectWriter ow = new ObjectMapper().findAndRegisterModules().writer().withDefaultPrettyPrinter();
                String json = ow.writeValueAsString(messageResponse);
                member.getSocket().sendMessage(new TextMessage(json));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @GetMapping
    private List<MessageResponse> getAllMessages(){
        log.info("Get All Messages");
        return messageRepository.findAllOrderByTimestampDesc();
    }

    public void tryDisconnect(String username){
        log.info("Disconnect " + username);
        Member member = members.get(username);
        if(member != null && members.containsKey(username) && member.getSocket() != null){
            members.remove(username);
        }
    }
}
