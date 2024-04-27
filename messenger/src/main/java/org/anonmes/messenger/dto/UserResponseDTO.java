package org.anonmes.messenger.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
public class UserResponseDTO {
    private Long id;
    private String name;
    private String email;
    @EqualsAndHashCode.Exclude
    private LocalDateTime createdAt;
}
