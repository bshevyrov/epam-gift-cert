package com.epam.esm.exception;

public class TagIdException extends RuntimeException {
    private final long tagId;
    public TagIdException(long tagId) {
        this.tagId = tagId;
    }

    public long getTagId() {
        return tagId;
    }
}