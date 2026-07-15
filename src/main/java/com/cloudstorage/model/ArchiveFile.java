package com.cloudstorage.model;

public class ArchiveFile extends StorageItem {

    public ArchiveFile(String fileName, double sizeInMb) {
        // TODO 31: Call the parent constructor using super().
        super(fileName, 0); // Fix this line
    }

    @Override
    public double getRatePerMb() {
        // TODO 32: Return the archive billing rate: 0.05
        return 0.0; // Replace this
    }
}
