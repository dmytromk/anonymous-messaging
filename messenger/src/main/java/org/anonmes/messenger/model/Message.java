package org.anonmes.messenger.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Entity
@Table(name="messages")
@NoArgsConstructor
public class Message {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "from_user")
    private User from;
    @ManyToOne
    @JoinColumn(name = "to_user")
    private User to;
    private String content;
    @Column(name="created_at")
    @EqualsAndHashCode.Exclude
    private LocalDateTime createdAt;
}
