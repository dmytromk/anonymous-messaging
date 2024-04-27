package org.anonmes.messenger.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Entity
@Table(name="messages")
public class Message {
    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "from_user")
    private User from;
    @ManyToOne
    @JoinColumn(name = "to_user")
    private User to;
    private String content;
    @Column(name="created_at")
    private LocalDateTime createdAt;
}
