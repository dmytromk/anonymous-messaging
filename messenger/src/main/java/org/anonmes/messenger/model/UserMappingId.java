package org.anonmes.messenger.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor
public class UserMappingId implements Serializable {
    @Serial
    private static final long serialVersionUID = -449241830375690116L;
    @Column(name = "oauth_provider")
    private String providerId;
    @Column(name = "subject")
    private String subject;
    public UserMappingId(String providerId, String subject) {
        this.providerId = providerId;
        this.subject = subject;
    }
}
