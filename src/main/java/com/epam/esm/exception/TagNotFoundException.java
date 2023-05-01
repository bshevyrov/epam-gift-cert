package com.epam.esm.exception;

public class TagNotFoundException extends RuntimeException {
    private long tagId;

    public TagNotFoundException(long tagId) {
        this.tagId = tagId;
    }

    public long getTagId() {
        return tagId;
    }
}
