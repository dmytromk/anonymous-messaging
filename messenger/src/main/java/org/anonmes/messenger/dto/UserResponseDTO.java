package org.anonmes.messenger.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@Schema(description = "A JSON object containing user's id, name, email and timestamp of its creation as string")
public class UserResponseDTO {
    private Long id;
    private String name;
    private String email;
    @EqualsAndHashCode.Exclude
    private LocalDateTime createdAt;
}
