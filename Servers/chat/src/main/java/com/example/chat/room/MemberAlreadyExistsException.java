package com.example.chat.room;

public class MemberAlreadyExistsException extends RuntimeException{
    public MemberAlreadyExistsException() {
        super("There is already a member with that username in the room.");
    }
}
