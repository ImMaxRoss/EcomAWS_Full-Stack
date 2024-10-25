package com.cognixia.stagestream.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    @JsonProperty("userId")
    private Long userId;


    @NotNull(message = "Name cannot be blank.")
    @Column(name = "first_name", nullable = false, length = 30)
    private String firstName;

    @NotNull(message = "Last Name cannot be blank.")
    @Column(name = "last_name", nullable = false, length = 30)
    private String lastName;

    @Column(name = "username", nullable = false, unique = true, length = 255)
    private String username;

    @Email
    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "role", nullable = false, length = 255)
    private String role;

    @Column(name = "password", length = 255)
    @JsonProperty("password")
    private String password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Cart cart;
}