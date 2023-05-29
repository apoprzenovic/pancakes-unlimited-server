package com.pancakesunlimited.server.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;

/**
 * @author Arnes Poprzenovic
 * Entity class for the users table
 */
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

    /**
     * Constructor for the Users class
     *
     * @param email    The email of the user
     * @param username The username of the user
     * @param lastname The lastname of the user
     * @param password The password of the user which gets hashed before saving
     * @param roles    The role of the user
     */
    public Users(String email, String username, String lastname, String password, Roles roles) {
        this.email = email;
        this.username = username;
        this.lastname = lastname;
        this.password = hashPassword(password);
        this.roles = roles;
    }

    /**
     * Hashes the password using BCrypt
     *
     * @param passwordToBeHashed The password to be hashed
     * @return The hashed password
     */
    public String hashPassword(String passwordToBeHashed) {
        String salt = BCrypt.gensalt(12);
        return BCrypt.hashpw(passwordToBeHashed, salt);
    }

    /**
     * Sets the password to the hashed password
     *
     * @param password The password to be hashed and saved
     */
    public void setPassword(String password) {
        this.password = hashPassword(password);
    }

}
