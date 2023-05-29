package com.pancakesunlimited.server.exception;

/**
 * @author Arnes Poprzenovic
 * Exception class for when a resource is not found
 */
public class ResourceNotFoundException extends RuntimeException {
    /**
     * Constructor
     * An exception thrown when a resource is not found
     *
     * @param message the message to be displayed
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
