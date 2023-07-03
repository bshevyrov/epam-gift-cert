package com.epam.esm.exception.tag;

/**
 * Exception class.
 * Objects of this class can be thrown during id validation, if id is illegal.
 */
public class TagIdException extends RuntimeException {
    private final long tagId;

    public TagIdException(long tagId) {
        this.tagId = tagId;
    }

    public long getTagId() {
        return tagId;
    }
}