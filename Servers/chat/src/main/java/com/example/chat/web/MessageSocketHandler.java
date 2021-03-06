package com.example.chat.web;

import com.example.chat.data.MessageRequest;
import com.example.chat.room.Member;
import com.example.chat.room.MemberAlreadyExistsException;
import com.example.chat.room.RoomController;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Component
public class MessageSocketHandler extends TextWebSocketHandler {

    @Autowired
    RoomController roomController;

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws InterruptedException, IOException {

        log.info("Received message " + message.getPayload());
        String username = getUsernameFromSession(session);

        Gson gson = new Gson();
        MessageRequest request = gson.fromJson(message.getPayload(), MessageRequest.class);

        roomController.sendMessage(
                username,
                request
        );
    }



    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        log.info("Received connection request " + session.getUri());
        String username = getUsernameFromSession(session);

        roomController.onJoin(
                username,
                session.getId(),
                session
        );
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("Received close request ");
        String username = getUsernameFromSession(session);

        roomController.tryDisconnect(username);
    }

    private String getUsernameFromSession(WebSocketSession session){
        List<NameValuePair> params = URLEncodedUtils.parse(Objects.requireNonNull(session.getUri()), StandardCharsets.UTF_8);
        Map<String, String> stringMap = params.stream().collect(Collectors.toMap(NameValuePair::getName, NameValuePair::getValue));

        return stringMap.get("username");
    }
}
