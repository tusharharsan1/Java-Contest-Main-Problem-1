package com.cloudstorage.model;

public class MediaFile extends StorageItem {

    public MediaFile(String fileName, double sizeInMb) {
        // TODO 29: Call the parent constructor using super().
        super(fileName, 0); // Fix this line
    }

    @Override
    public double getRatePerMb() {
        // TODO 30: Return the media billing rate: 0.25
        return 0.0; // Replace this
    }
}
