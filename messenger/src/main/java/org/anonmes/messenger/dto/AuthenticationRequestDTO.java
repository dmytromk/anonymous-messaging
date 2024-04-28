package org.anonmes.messenger.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "A JSON object containing email and password under which a user is attempting to authorize")
public class AuthenticationRequestDTO {
    private String email;
    private String password;
}
