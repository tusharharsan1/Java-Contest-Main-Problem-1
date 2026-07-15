package com.cloudstorage.exception;

/**
 * Thrown when a file size is invalid (negative).
 * This is an unchecked exception (extends RuntimeException).
 *
 * DO NOT MODIFY THIS FILE.
 */
public class InvalidFileSizeException extends RuntimeException {

    public InvalidFileSizeException(String message) {
        super(message);
    }
}
