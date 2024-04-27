package org.anonmes.messenger.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
@Data
public class MessageResponseDTO {
    private Long id;
    private Long toId;
    private String content;
    @EqualsAndHashCode.Exclude
    private LocalDateTime createdAt;
}
