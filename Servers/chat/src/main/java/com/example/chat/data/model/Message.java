package com.example.chat.data.model;

import com.example.chat.room.MessageResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @Column(name = "profile_name")
    private String profileName;

    public Message(String message, String createdBy, LocalDateTime created) {
        this.message = message;
        this.createdBy = createdBy;
        this.created = created;
    }
}
