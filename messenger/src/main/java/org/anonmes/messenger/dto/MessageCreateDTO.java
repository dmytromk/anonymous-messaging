package org.anonmes.messenger.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "A JSON object containing message's content as string " +
        "and receiving user's id")
public class MessageCreateDTO {
    private Long toId;
    private String content;
}
