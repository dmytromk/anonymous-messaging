package org.anonmes.messenger.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users_mapping")
@Data
public class UserMapping {
    @EmbeddedId
    private UserMappingId id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public UserMapping() {
    }

    public UserMapping(UserMappingId id, User user) {
        this.id = id;
        this.user = user;
    }
}
