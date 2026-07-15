package com.cloudstorage.service;

import com.cloudstorage.exception.FileNotFoundInStorageException;
import com.cloudstorage.model.StorageItem;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

// TODO 10: Annotate this class so Spring recognizes it as a Service component
public class StorageService {

    // Simulating a database in-memory to test Java Collections and Streams
    private Map<String, StorageItem> storage = new HashMap<>();

    public void uploadFile(StorageItem file) {
        // TODO 11: Add the file to the storage map using its fileId as the key.
    }

    public List<StorageItem> getActiveFiles() {
        // TODO 12: Use Streams to return a List of all active files (isActive() is true).
        return new ArrayList<>(); // Replace this
    }

    public double getTotalActiveStorageCost() {
        // TODO 13: Use Streams to calculate the sum of calculateStorageCost() for all active files.
        return 0.0; // Replace this
    }

    public StorageItem findMostExpensiveActiveFile() {
        // TODO 14: Use Streams to find the active file with highest cost. Throw FileNotFoundInStorageException if none exist.
        return null; // Replace this
    }

    public void archiveFile(String fileId) {
        // TODO 15: Find the file by ID and call archive(). Throw FileNotFoundInStorageException if ID is not found.
    }

    public long countActiveOfType(Class<? extends StorageItem> type) {
        // TODO 16: Use Streams to count how many active files are instances of the specified class type.
        return 0; // Replace this
    }

    // TODO 33: Implement a generic method named getActiveFilesByType that returns a List of active files of the given type.
    // The method signature must be: public <T extends StorageItem> List<T> getActiveFilesByType(Class<T> type)
    // Hint: Use Streams. Filter by isActive, filter by type.isInstance, map using type::cast, and collect to List.
    // Replace the following line with your full method implementation:
    // ...

    // PROVIDED: Do not modify
    public int getTotalFileCount() { return storage.size(); }
    public Map<String, StorageItem> getAllFiles() { return Collections.unmodifiableMap(storage); }
}
