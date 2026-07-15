package com.cloudstorage.controller;

import com.cloudstorage.model.StorageItem;
import com.cloudstorage.service.StorageService;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

// TODO 1: Annotate this class so Spring recognizes it as a REST Controller
public class StorageController {

    // TODO 2: Declare a private final StorageService instance variable.

    // TODO 3: Implement constructor injection for StorageService (Spring will auto-wire this).
    public StorageController(StorageService storageService) {
        
    }

    public void uploadFile(StorageItem file) {
        // TODO 4: Delegate this call to the storageService.
    }

    public List<StorageItem> getActiveFiles() {
        // TODO 5: Delegate this call to the storageService.
        return null; // Replace this
    }

    public double getTotalActiveStorageCost() {
        // TODO 6: Delegate this call to the storageService.
        return 0.0; // Replace this
    }

    public StorageItem findMostExpensiveActiveFile() {
        // TODO 7: Delegate this call to the storageService.
        return null; // Replace this
    }

    public void archiveFile(String fileId) {
        // TODO 8: Delegate this call to the storageService.
    }

    public long countActiveOfType(Class<? extends StorageItem> type) {
        // TODO 9: Delegate this call to the storageService.
        return 0; // Replace this
    }

    // TODO 34: Declare a generic method named getActiveFilesByType with the exact same signature as the one in StorageService.
    // Delegate the call to the storageService and return the result.
    // Replace the following line with your full method implementation:
    // ...
}
