package com.pancakesunlimited.server.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "email")
    private String email;

    @Column(name = "firstname")
    private String username;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "password")
    private String password;

    @ManyToOne
    @JoinColumn(name = "roles_id")
    private Roles roles;

    public String hashPassword(String passwordToBeHashed) {
        String salt = BCrypt.gensalt(12);
        return BCrypt.hashpw(passwordToBeHashed, salt);
    }

    public void setPassword(String password) {
        this.password = hashPassword(password);
    }

    public Users(String email, String username, String lastname, String password, Roles roles) {
        this.email = email;
        this.username = username;
        this.lastname = lastname;
        this.password = hashPassword(password);
        this.roles = roles;
    }

}
