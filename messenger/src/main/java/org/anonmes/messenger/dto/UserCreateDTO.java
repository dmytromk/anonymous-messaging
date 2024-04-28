package org.anonmes.messenger.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "A JSON object containing user's name, email and password")
public class UserCreateDTO {
    private String name;
    private String email;
    private String password;
}
