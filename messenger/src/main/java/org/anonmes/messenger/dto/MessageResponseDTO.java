package org.anonmes.messenger.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
@Data
@Schema(description = "A JSON object containing sending and receiving users' ids, " +
        "message content as string and timestamp of its creation as string\\")
public class MessageResponseDTO {
    private Long id;
    private Long toId;
    private String content;
    @EqualsAndHashCode.Exclude
    private LocalDateTime createdAt;
}
