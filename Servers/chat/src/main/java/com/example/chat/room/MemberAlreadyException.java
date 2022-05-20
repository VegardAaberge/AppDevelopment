package com.example.chat.room;

public class MemberAlreadyException extends RuntimeException {
    public MemberAlreadyException() {
        super("There is already a member with that username in the room");
    }
}
