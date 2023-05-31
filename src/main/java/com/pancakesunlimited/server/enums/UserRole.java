package com.pancakesunlimited.server.enums;

/**
 * @author Arnes Poprzenovic
 * Enum class for the user roles
 */
public enum UserRole {
    CUSTOMER(1),
    EMPLOYEE(2),
    STORE_OWNER(3);

    private final Integer id;

    /**
     * Constructor for the UserRole enum
     * @param id - the id of the user role
     */
    UserRole(int id) {
        this.id = id;
    }

    /**
     * Getter for the id of the user role
     * @return - the id of the user role
     */
    public int getId() {
        return id;
    }

    /**
     * Method to get the user role from the id
     * @param id - the id of the user role
     * @return - the user role with the specified id
     */
    public static UserRole fromId(int id) {
        for (UserRole role : values()) {
            if (role.getId() == id) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid id for user role: " + id);
    }
}

