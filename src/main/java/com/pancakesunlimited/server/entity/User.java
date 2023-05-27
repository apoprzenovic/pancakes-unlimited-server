package com.pancakesunlimited.server.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;

@Entity
@Data
@NoArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "email")
    private String email;

    @Column(name = "firstname")
    private String username;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "password")
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public String hashPassword(String passwordToBeHashed) {
        String salt = BCrypt.gensalt(12);
        return BCrypt.hashpw(passwordToBeHashed, salt);
    }

    public void setPassword(String password) {
        this.password = hashPassword(password);
    }

    public User(String email, String username, String lastname, String password, Role role) {
        this.email = email;
        this.username = username;
        this.lastname = lastname;
        this.password = hashPassword(password);
        this.role = role;
    }

}
