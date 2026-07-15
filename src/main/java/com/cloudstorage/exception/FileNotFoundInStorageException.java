package com.cloudstorage.exception;

/**
 * Thrown when a requested file ID does not exist in the storage system,
 * or when no active files are available for a query.
 * This is an unchecked exception (extends RuntimeException).
 *
 * DO NOT MODIFY THIS FILE.
 */
public class FileNotFoundInStorageException extends RuntimeException {

    public FileNotFoundInStorageException(String message) {
        super(message);
    }
}
