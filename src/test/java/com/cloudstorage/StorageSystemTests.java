package com.cloudstorage;

import com.cloudstorage.controller.StorageController;
import com.cloudstorage.exception.FileNotFoundInStorageException;
import com.cloudstorage.exception.InvalidFileSizeException;
import com.cloudstorage.model.*;
import com.cloudstorage.service.StorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StorageSystemTests {

    private StorageService storageService;

    @Mock
    private StorageService mockedService;

    @InjectMocks
    private StorageController storageController;

    @BeforeEach
    void setUp() {
        // Reset the real service for stateful tests
        storageService = new StorageService();
    }

    // ==========================================
    // 1. MODEL TESTS (OOP & Polymorphism)
    // ==========================================

    @Test
    void testFileIdGenerationAndEquality() {
        DocumentFile doc1 = new DocumentFile("doc1.txt", 10.0);
        DocumentFile doc2 = new DocumentFile("doc2.txt", 10.0);

        // Ensure IDs are unique and formatted correctly
        assertNotEquals(doc1.getFileId(), doc2.getFileId());
        assertTrue(doc1.getFileId().startsWith("F-"));

        // Two different files should not be equal
        assertNotEquals(doc1, doc2);
    }

    @Test
    void testStaticFileCounterIncrements() {
        // Because tests run in arbitrary order, the static counter won't be 0.
        // We test the relative difference.
        int initialCount = StorageItem.getTotalFiles();

        new DocumentFile("temp1.txt", 1.0);
        new MediaFile("temp2.mp4", 2.0);

        int newCount = StorageItem.getTotalFiles();
        assertEquals(initialCount + 2, newCount, "Static counter should increment by exactly 2");
    }

    @Test
    void testNegativeSizeValidationThrowsException() {
        assertThrows(InvalidFileSizeException.class, () -> new DocumentFile("doc.txt", -5.0),
                "Constructor should reject negative size");

        DocumentFile validDoc = new DocumentFile("doc.txt", 5.0);
        assertThrows(InvalidFileSizeException.class, () -> validDoc.setSizeInMb(-1.0),
                "Setter should reject negative size");
    }

    @Test
    void testPolymorphicCostCalculation() {
        DocumentFile doc = new DocumentFile("doc.txt", 100);    // rate: 0.10
        MediaFile media = new MediaFile("media.mp4", 100);      // rate: 0.25
        ArchiveFile archive = new ArchiveFile("archive.zip", 100); // rate: 0.05

        assertEquals(10.0, doc.calculateStorageCost(), 0.001);
        assertEquals(25.0, media.calculateStorageCost(), 0.001);
        assertEquals(5.0, archive.calculateStorageCost(), 0.001);
    }

    @Test
    void testArchiveAndRestoreStatus() {
        DocumentFile doc = new DocumentFile("doc.txt", 10.0);
        assertTrue(doc.isActive());

        doc.archive();
        assertFalse(doc.isActive());

        doc.restore();
        assertTrue(doc.isActive());
    }

    // ==========================================
    // 2. SERVICE TESTS (Streams & Logic)
    // ==========================================

    @Test
    void testUploadUsesCorrectMapKey() {
        DocumentFile doc = new DocumentFile("doc.txt", 10.0);
        storageService.uploadFile(doc);

        Map<String, StorageItem> allFiles = storageService.getAllFiles();

        // Anti-bypass: Ensure they used fileId as the key, not fileName or something else
        assertTrue(allFiles.containsKey(doc.getFileId()), "Map must use fileId as the key");
        assertEquals(doc, allFiles.get(doc.getFileId()));
    }

    @Test
    void testUploadAndGetActiveFiles() {
        DocumentFile doc = new DocumentFile("doc.txt", 10.0);
        MediaFile media = new MediaFile("vid.mp4", 100.0);
        media.archive(); // Make inactive

        storageService.uploadFile(doc);
        storageService.uploadFile(media);

        List<StorageItem> activeFiles = storageService.getActiveFiles();
        assertEquals(1, activeFiles.size(), "Should only return active files");
        assertEquals(doc.getFileId(), activeFiles.get(0).getFileId());
    }

    @Test
    void testGetTotalActiveStorageCostPreventsHardcoding() {
        DocumentFile doc = new DocumentFile("doc.txt", 10.0);       // Cost: 1.0
        MediaFile media = new MediaFile("vid.mp4", 100.0);          // Cost: 25.0
        ArchiveFile archive = new ArchiveFile("arch.zip", 200.0);   // Cost: 10.0 (But archived)
        archive.archive();

        storageService.uploadFile(doc);
        storageService.uploadFile(media);
        storageService.uploadFile(archive);

        // First assertion
        assertEquals(26.0, storageService.getTotalActiveStorageCost(), 0.001);

        // Anti-bypass: Add another file and assert again to prevent hardcoded returns
        DocumentFile extraDoc = new DocumentFile("extra.txt", 50.0); // Cost: 5.0
        storageService.uploadFile(extraDoc);

        assertEquals(31.0, storageService.getTotalActiveStorageCost(), 0.001);
    }

    @Test
    void testFindMostExpensiveActiveFile() {
        DocumentFile doc = new DocumentFile("doc.txt", 10.0);       // Cost: 1.0
        MediaFile media = new MediaFile("vid.mp4", 100.0);          // Cost: 25.0

        storageService.uploadFile(doc);
        storageService.uploadFile(media);

        StorageItem mostExpensive = storageService.findMostExpensiveActiveFile();
        assertEquals(media.getFileId(), mostExpensive.getFileId());
    }

    @Test
    void testFindMostExpensiveThrowsWhenNoneActive() {
        // Test empty map
        assertThrows(FileNotFoundInStorageException.class, () -> storageService.findMostExpensiveActiveFile());

        // Test populated map but all are archived
        DocumentFile doc = new DocumentFile("doc.txt", 10.0);
        doc.archive();
        storageService.uploadFile(doc);

        assertThrows(FileNotFoundInStorageException.class, () -> storageService.findMostExpensiveActiveFile(),
                "Should throw exception if files exist but none are active");
    }

    @Test
    void testArchiveFileThrowsWhenNotFound() {
        assertThrows(FileNotFoundInStorageException.class, () -> storageService.archiveFile("INVALID_ID"));
    }

    @Test
    void testArchiveFileCallsArchiveMethod() {
        // Use Mockito Spy to verify that the archive() method is actually called on the item
        DocumentFile doc = Mockito.spy(new DocumentFile("doc.txt", 10.0));
        storageService.uploadFile(doc);

        storageService.archiveFile(doc.getFileId());

        verify(doc, times(1)).archive();
        assertFalse(doc.isActive());
    }

    @Test
    void testCountActiveOfType() {
        storageService.uploadFile(new DocumentFile("d1.txt", 10.0));
        storageService.uploadFile(new DocumentFile("d2.txt", 10.0));

        MediaFile m1 = new MediaFile("m1.mp4", 10.0);
        m1.archive(); // should not be counted
        storageService.uploadFile(m1);

        assertEquals(2, storageService.countActiveOfType(DocumentFile.class));
        assertEquals(0, storageService.countActiveOfType(MediaFile.class));
    }

    @Test
    void testGetActiveFilesByTypeGeneric() {
        DocumentFile doc = new DocumentFile("d1.txt", 10.0);
        MediaFile media = new MediaFile("m1.mp4", 10.0);
        storageService.uploadFile(doc);
        storageService.uploadFile(media);

        List<DocumentFile> docs = storageService.getActiveFilesByType(DocumentFile.class);
        assertEquals(1, docs.size());
        assertEquals(doc.getFileId(), docs.get(0).getFileId());
    }

    // ==========================================
    // 3. CONTROLLER TESTS (Delegation & Mocking)
    // ==========================================

    @Test
    void testControllerDelegatesToService() {
        DocumentFile doc = new DocumentFile("doc.txt", 10.0);

        storageController.uploadFile(doc);
        verify(mockedService, times(1)).uploadFile(doc);

        storageController.getActiveFiles();
        verify(mockedService, times(1)).getActiveFiles();

        storageController.getTotalActiveStorageCost();
        verify(mockedService, times(1)).getTotalActiveStorageCost();

        storageController.findMostExpensiveActiveFile();
        verify(mockedService, times(1)).findMostExpensiveActiveFile();

        storageController.archiveFile(doc.getFileId());
        verify(mockedService, times(1)).archiveFile(doc.getFileId());

        storageController.countActiveOfType(DocumentFile.class);
        verify(mockedService, times(1)).countActiveOfType(DocumentFile.class);

        storageController.getActiveFilesByType(DocumentFile.class);
        verify(mockedService, times(1)).getActiveFilesByType(DocumentFile.class);
    }
}