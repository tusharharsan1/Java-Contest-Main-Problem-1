# Cloud Storage Billing & File Management

### Problem Statement
You are building the core backend for a cloud storage provider (like Google Drive or Dropbox). Users upload different kinds of files — documents, media (photos/video), and archives. Each file type is billed at a different rate per megabyte. The system must track all uploaded files, compute storage bills dynamically using polymorphism, and allow files to be archived (moved to cold storage) or restored.

A file operation touches the in-memory repository and triggers streams to filter and compute metrics, so understanding functional querying and object-oriented principles is critical: calculating the wrong storage cost or allowing a file size to be negative must never occur.

### Tasks

#### Task 1 — Core File Models & Inheritance
Complete `StorageItem` and its subclasses (`DocumentFile`, `MediaFile`, `ArchiveFile`). It must:
- Generate a unique `fileId` using a static counter formatted as `"F-1"`, `"F-2"`, etc.
- Throw an `InvalidFileSizeException` if the `sizeInMb` is negative during construction or when updated via setter.
- Override `getRatePerMb()` in subclasses to return `0.10` for documents, `0.25` for media, and `0.05` for archives.
- Implement `calculateStorageCost()` in the parent class using polymorphic dispatch.
- Ensure equality between two items is determined strictly by their `fileId`.

#### Task 2 — Business Logic & Streams
Implement `StorageService` and annotate it as a Spring `@Service`. It must:
- Add uploaded files to the internal map.
- Use Java Streams to return only active files.
- Use Java Streams to calculate the sum of costs for all active files.
- Use Java Streams to find the most expensive active file. If none exist, throw a `FileNotFoundInStorageException`.
- Archive files by looking up the ID and marking them inactive. If the file doesn't exist, throw `FileNotFoundInStorageException`.
- Use Java Streams to count active files of a specific class type.
- Implement the generic method `getActiveFilesByType(Class<T> type)` using Streams to safely filter and cast files to the requested generic type.

#### Task 3 — Controller Validation & Delegation
Complete `StorageController`. Ensure it is recognized as a Spring REST Controller. It must:
- Accept `StorageService` via Constructor Injection to ensure Spring auto-wires the dependency.
- Delegate all public methods directly to the service layer without containing any business logic.
