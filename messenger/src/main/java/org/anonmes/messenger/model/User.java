package org.anonmes.messenger.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "users")
public class User {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name= "email")
    private String email;
    @Column(name = "created_at")
    @EqualsAndHashCode.Exclude
    private LocalDateTime createdAt;
}
