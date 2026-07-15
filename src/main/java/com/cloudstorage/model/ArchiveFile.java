package com.cloudstorage.model;

public class ArchiveFile extends StorageItem {

    public ArchiveFile(String fileName, double sizeInMb) {
        super(fileName, sizeInMb);
    }

    @Override
    public double getRatePerMb() {
        return 0.05;
    }
}
