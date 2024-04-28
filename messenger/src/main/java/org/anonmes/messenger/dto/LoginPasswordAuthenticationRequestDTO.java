package org.anonmes.messenger.dto;

import lombok.Data;

@Data
public class LoginPasswordAuthenticationRequestDTO {
    private String email;
    private String password;
}
