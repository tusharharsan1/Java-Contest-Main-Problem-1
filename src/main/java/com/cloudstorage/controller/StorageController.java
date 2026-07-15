package com.cloudstorage.controller;

import com.cloudstorage.model.StorageItem;
import com.cloudstorage.service.StorageService;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class StorageController {

    private final StorageService storageService;

    public StorageController(StorageService storageService) {
        this.storageService = storageService;
    }

    public void uploadFile(StorageItem file) {
        storageService.uploadFile(file);
    }

    public List<StorageItem> getActiveFiles() {
        return storageService.getActiveFiles();
    }

    public double getTotalActiveStorageCost() {
        return storageService.getTotalActiveStorageCost();
    }

    public StorageItem findMostExpensiveActiveFile() {
        return storageService.findMostExpensiveActiveFile();
    }

    public void archiveFile(String fileId) {
        storageService.archiveFile(fileId);
    }

    public long countActiveOfType(Class<? extends StorageItem> type) {
        return storageService.countActiveOfType(type);
    }

    public <T extends StorageItem> List<T> getActiveFilesByType(Class<T> type) {
        return storageService.getActiveFilesByType(type);
    }
}
