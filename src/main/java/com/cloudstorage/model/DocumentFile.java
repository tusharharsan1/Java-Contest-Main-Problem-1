package com.cloudstorage.model;

public class DocumentFile extends StorageItem {

    public DocumentFile(String fileName, double sizeInMb) {
        // TODO 27: Call the parent constructor using super().
        super(fileName, 0); // Fix this line
    }

    @Override
    public double getRatePerMb() {
        // TODO 28: Return the document billing rate: 0.10
        return 0.0; // Replace this
    }
}
