package com.cloudstorage.model;

public class DocumentFile extends StorageItem {

    public DocumentFile(String fileName, double sizeInMb) {
        super(fileName, sizeInMb);
    }

    @Override
    public double getRatePerMb() {
        return 0.10;
    }
}
