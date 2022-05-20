package com.example.chat.room;

import com.example.chat.data.MessageRepository;
import com.example.chat.data.model.Message;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("chat")
@RequiredArgsConstructor
public class RoomController {
    private MessageRepository messageRepository;
    private final ConcurrentHashMap<String, Member> members = new ConcurrentHashMap<>();

    private onJoin(String username, String sessionId, WebSocketSession socket){
        if(members.containsKey(username)){
            throw new MemberAlreadyException();
        }
        members.put(username, new Member(
                username,
                sessionId,
                socket
        );
    }

    private void sendMessage(String senderUsername, String message) {
        members.values().forEach(member -> {
            Message messageEntity = new Message(
                    message,
                    senderUsername,
                    System.currentTimeMillis()
            );
            messageRepository.save(messageEntity);

            try {
                member.getSocket().sendMessage(new TextMessage(messageEntity.toString()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @GetMapping
    private List<Message> getAllMessages(){
        return messageRepository.findAllOrderByTimestampDesc();
    }

    private String tryDisconnect(String username) throws IOException {
        members.get(username).getSocket().close();
        if(members.containsKey(username)){
            members.remove(username);
        }
    }
}
