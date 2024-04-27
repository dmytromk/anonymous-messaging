package org.anonmes.messenger.dto;


import lombok.Data;

@Data
public class MessageCreateDTO {
    private Long toId;
    private String content;
}
