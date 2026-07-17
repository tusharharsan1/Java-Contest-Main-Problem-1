package com.cloudstorage.controller;

import com.cloudstorage.model.StorageItem;
import com.cloudstorage.service.StorageService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/storage")
public class StorageController {

    private final StorageService storageService;

    public StorageController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/upload")
    public void uploadFile(@RequestBody StorageItem file) {
        storageService.uploadFile(file);
    }

    @GetMapping("/active")
    public List<StorageItem> getActiveFiles() {
        return storageService.getActiveFiles();
    }

    @GetMapping("/active/cost")
    public double getTotalActiveStorageCost() {
        return storageService.getTotalActiveStorageCost();
    }

    @GetMapping("/active/expensive")
    public StorageItem findMostExpensiveActiveFile() {
        return storageService.findMostExpensiveActiveFile();
    }

    @DeleteMapping("/{fileId}")
    public void archiveFile(@PathVariable String fileId) {
        storageService.archiveFile(fileId);
    }

    @PostMapping("/count-type")
    public long countActiveOfType(@RequestBody Class<? extends StorageItem> type) {
        return storageService.countActiveOfType(type);
    }

    @PostMapping("/active-by-type")
    public <T extends StorageItem> List<T> getActiveFilesByType(@RequestBody Class<T> type) {
        return storageService.getActiveFilesByType(type);
    }
}
