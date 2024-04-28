package org.anonmes.messenger.model;

import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "user_credentials")
public class UserCredentials {
    private Long id;
    private String email;
    private String password;
}
