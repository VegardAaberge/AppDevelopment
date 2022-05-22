package com.example.chat.data;

import com.example.chat.chat.Status;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MessageRequest {
    private String username;
    private String message;
    private Long timestamp;
}