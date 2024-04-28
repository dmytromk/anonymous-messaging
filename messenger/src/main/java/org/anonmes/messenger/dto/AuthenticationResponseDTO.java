package org.anonmes.messenger.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "A JSON object containing a JSON web token as string sent as response to successful authorization")
public class AuthenticationResponseDTO {
    private final String jwt;
}
