package org.anonmes.messenger.model;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class Message {
    private Long id;
    private User from;
    private User to;
    private String content;
    private LocalDateTime createdAt;
}
