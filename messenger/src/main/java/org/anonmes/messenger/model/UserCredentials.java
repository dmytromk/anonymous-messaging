package org.anonmes.messenger.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@Table(name = "user_credentials")
@AllArgsConstructor
@NoArgsConstructor
public class UserCredentials {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name= "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "active")
    private boolean active;
    @Column(name = "role")
    private String role;
    @Column(name = "provider")
    private String provider;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false, cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public void addUser(@NotNull User user) {
        this.user = user;
        user.setUserCredentials(this);
    }
}