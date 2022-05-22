package com.example.chat.web;

import com.example.chat.room.Member;
import com.example.chat.room.MemberAlreadyExistsException;
import com.example.chat.room.RoomController;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class MessageSocketHandler extends TextWebSocketHandler {

    @Autowired
    RoomController roomController;

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws InterruptedException, IOException {

        String username = getUsernameFromSession(session);

        roomController.sendMessage(
                username,
                message.getPayload()
        );
    }



    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        String username = getUsernameFromSession(session);

        roomController.onJoin(
                username,
                session.getId(),
                session
        );
    }

    private String getUsernameFromSession(WebSocketSession session){
        List<NameValuePair> params = URLEncodedUtils.parse(Objects.requireNonNull(session.getUri()), StandardCharsets.UTF_8);
        Map<String, String> stringMap = params.stream().collect(Collectors.toMap(NameValuePair::getName, NameValuePair::getValue));

        return stringMap.get("username");
    }
}
