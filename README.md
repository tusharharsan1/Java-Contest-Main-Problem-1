# COMPLETE ASSIGNMENT SPECIFICATION: Cloud Storage Billing & File Management

### Problem Context for the Student

You are building the backend for a cloud storage provider (like Google Drive or Dropbox). Users upload different kinds of files — documents, media (photos/video), and archives. Each file type is billed at a different rate per megabyte.

This project is set up as a **Spring Boot** application. However, instead of connecting to a real database, you will manage the data in-memory using Java Collections. This will test your ability to use Spring Dependency Injection alongside pure Java Object-Oriented Programming (OOP) and Streams.

Your job is to complete the partially-implemented codebase so the storage and billing system works correctly and safely. There are **30 TODOs** (numbered 4 through 9 and 11 through 34, note: some numbers like 27-32 refer to subclasses) across the project files.

### Constraints & Rules

1. **In-Memory Storage:** The `StorageService` uses a `HashMap`. Do **not** attempt to use JPA, `@Entity`, or SQL.
2. **Polymorphism:** `calculateStorageCost()` must rely on polymorphism (calling the overridden `getRatePerMb()`). Do NOT use `instanceof` or switch statements to check the file type.
3. **Streams:** All collection queries in `StorageService` must use Java Streams.
4. **Generics:** You must correctly write generic methods when prompted (`getActiveFilesByType`), explicitly declaring the generic bounds in the signature.
