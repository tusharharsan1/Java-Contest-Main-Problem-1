package com.cloudstorage.model;

import com.cloudstorage.exception.InvalidFileSizeException;
import lombok.Getter;

import java.util.Objects;

@Getter
public abstract class StorageItem implements Archivable {

    private static int fileCounter = 0;

    private final String fileId;
    private String fileName;
    private double sizeInMb;
    private boolean active;

    protected StorageItem(String fileName, double sizeInMb) {
        if (sizeInMb < 0) {
            throw new InvalidFileSizeException("File size cannot be negative: " + sizeInMb);
        }
        fileCounter++;
        this.fileId = "F-" + fileCounter;
        this.fileName = fileName;
        this.sizeInMb = sizeInMb;
        this.active = true;
    }

    public abstract double getRatePerMb();

    public double calculateStorageCost() {
        return sizeInMb * getRatePerMb();
    }

    public static int getTotalFiles() {
        return fileCounter;
    }

    public void setSizeInMb(double sizeInMb) {
        if (sizeInMb < 0) {
            throw new InvalidFileSizeException("File size cannot be negative: " + sizeInMb);
        }
        this.sizeInMb = sizeInMb;
    }

    @Override
    public void archive() {
        this.active = false;
    }

    @Override
    public void restore() {
        this.active = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StorageItem that = (StorageItem) o;
        return Objects.equals(fileId, that.fileId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileId);
    }
}

