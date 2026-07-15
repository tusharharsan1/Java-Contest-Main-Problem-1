package com.cloudstorage.model;

import com.cloudstorage.exception.InvalidFileSizeException;
import lombok.Getter;

import java.util.Objects;

@Getter
public abstract class StorageItem implements Archivable {

    // TODO 17: Declare a private static integer counter to track total files.
    // TODO 18: Declare private fields: final String fileId, String fileName, double sizeInMb, boolean active.

    protected StorageItem(String fileName, double sizeInMb) {
        // TODO 19: Implement constructor: validate size >= 0, assign fields, generate unique fileId, increment counter.
    }

    public abstract double getRatePerMb();

    public double calculateStorageCost() {
        // TODO 20: Return storage cost (sizeInMb * rate per MB) using polymorphism.
        return 0.0; // Replace this
    }

    public static int getTotalFiles() {
        // TODO 21: Return the value of the static counter.
        return 0; // Replace this
    }

    public void setSizeInMb(double sizeInMb) {
        // TODO 22: Validate size >= 0 and update sizeInMb. Throw InvalidFileSizeException if negative.
    }

    @Override
    public void archive() {
        // TODO 23: Mark the file as inactive.
    }

    @Override
    public void restore() {
        // TODO 24: Mark the file as active.
    }

    @Override
    public boolean equals(Object o) {
        // TODO 25: Implement equals() comparing only the fileId.
        return false; // Replace this
    }

    @Override
    public int hashCode() {
        // TODO 26: Return the hash code of the fileId.
        return 0; // Replace this
    }
}

