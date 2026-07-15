package com.cloudstorage.service;

import com.cloudstorage.exception.FileNotFoundInStorageException;
import com.cloudstorage.model.StorageItem;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StorageService {

    // Simulating a database in-memory to test Java Collections and Streams
    private Map<String, StorageItem> storage = new HashMap<>();

    public void uploadFile(StorageItem file) {
        storage.put(file.getFileId(), file);
    }

    public List<StorageItem> getActiveFiles() {
        return storage.values().stream()
                .filter(StorageItem::isActive)
                .collect(Collectors.toList());
    }

    public double getTotalActiveStorageCost() {
        return storage.values().stream()
                .filter(StorageItem::isActive)
                .mapToDouble(StorageItem::calculateStorageCost)
                .sum();
    }

    public StorageItem findMostExpensiveActiveFile() {
        return storage.values().stream()
                .filter(StorageItem::isActive)
                .max(Comparator.comparingDouble(StorageItem::calculateStorageCost))
                .orElseThrow(() -> new FileNotFoundInStorageException("No active files found in storage"));
    }

    public void archiveFile(String fileId) {
        StorageItem file = storage.get(fileId);
        if (file == null) {
            throw new FileNotFoundInStorageException("File not found: " + fileId);
        }
        file.archive();
    }

    public long countActiveOfType(Class<? extends StorageItem> type) {
        return storage.values().stream()
                .filter(StorageItem::isActive)
                .filter(type::isInstance)
                .count();
    }

    public <T extends StorageItem> List<T> getActiveFilesByType(Class<T> type) {
        return storage.values().stream()
                .filter(StorageItem::isActive)
                .filter(type::isInstance)
                .map(type::cast)
                .collect(Collectors.toList());
    }

    // PROVIDED: Do not modify
    public int getTotalFileCount() { return storage.size(); }
    public Map<String, StorageItem> getAllFiles() { return Collections.unmodifiableMap(storage); }
}
