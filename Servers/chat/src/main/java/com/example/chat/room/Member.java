package com.example.chat.room;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.web.socket.WebSocketSession;

@ToString
@Getter
@AllArgsConstructor
public class Member {
    private String username;
    private String sessionId;
    private WebSocketSession socket;
}
