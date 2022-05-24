package com.example.chat.data.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "message")
public class Message {
    @Id
    @SequenceGenerator(
            name = "message_sequence",
            sequenceName = "message_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "message_sequence"
    )
    private Long id;

    private String message;
    private String createdBy;
    private Long created;
    private String profileName;

    public Message(String message, String createdBy, Long created) {
        this.message = message;
        this.createdBy = createdBy;
        this.created = created;
    }
}
