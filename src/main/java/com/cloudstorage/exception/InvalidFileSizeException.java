package com.cloudstorage.exception;

// Unchecked exception: thrown when a file size is negative.
public class InvalidFileSizeException extends RuntimeException {
    public InvalidFileSizeException(String message) {
        super(message);
    }
}
