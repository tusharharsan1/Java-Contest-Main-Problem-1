package com.cloudstorage.model;

public class MediaFile extends StorageItem {

    public MediaFile(String fileName, double sizeInMb) {
        super(fileName, sizeInMb);
    }

    @Override
    public double getRatePerMb() {
        return 0.25;
    }
}
