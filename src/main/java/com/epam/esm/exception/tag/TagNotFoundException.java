package com.epam.esm.exception.tag;

/**
 * Exception class.
 * Objects of this class can be thrown during find by id operation, if record not present in DB.
 */
public class TagNotFoundException extends RuntimeException {
    private final long tagId;

    public TagNotFoundException(long tagId) {
        this.tagId = tagId;
    }

    public long getTagId() {
        return tagId;
    }
}
