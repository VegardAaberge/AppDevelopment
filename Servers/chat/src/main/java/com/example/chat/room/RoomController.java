package com.example.chat.room;

import com.example.chat.data.MessageRepository;
import com.example.chat.data.model.Message;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.webjars.NotFoundException;

import java.io.IOException;
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

    public void sendMessage(String senderUsername, String message) {
        members.values().forEach( member -> {
            Message messageEntity = new Message(
                    message,
                    senderUsername,
                    System.currentTimeMillis()
            );
            messageRepository.save(messageEntity);

            Gson gson = new Gson();
            String parsedMessage = gson.toJson(messageEntity);
            try {
                member.getSocket().sendMessage(new TextMessage(parsedMessage));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @GetMapping
    private List<Message> getAllMessages(){
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
