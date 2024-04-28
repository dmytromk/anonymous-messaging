package org.anonmes.messenger.dto;


import lombok.Data;

@Data
public class MessageCreateDTO {
    private String toEmail;
    private String content;
}
