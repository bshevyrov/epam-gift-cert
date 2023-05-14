package com.epam.esm.exception;

public class TagNameException extends RuntimeException {
    private final String tagName;

    public TagNameException(String tagName) {
        this.tagName = tagName;
    }

    public String getTagName() {
        return tagName;
    }
}