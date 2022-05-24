package com.example.chat.room;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public interface MessageResponse {
     Long getId();

     String getMessage();

     @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
     LocalDateTime getCreated();

     String getCreatedBy();

     String getProfileName();
}