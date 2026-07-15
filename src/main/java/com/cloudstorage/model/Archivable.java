package com.cloudstorage.model;

/**
 * DO NOT MODIFY THIS FILE.
 */
public interface Archivable {

    /**
     * Archives the file — moves it to cold storage (marks it as inactive).
     */
    void archive();

    /**
     * Restores the file — moves it back from cold storage to active storage.
     */
    void restore();
}
