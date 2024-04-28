package org.anonmes.messenger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class AuthenticationResponseDTO {
    private final String jwt;
}
