package com.epam.esm.exception.tag;

/**
 * Exception class.
 * Objects of this class can be thrown during creation, if tag already exist.
 */
public class TagExistException extends RuntimeException {
    private final String tagName;

    public TagExistException(String tagName) {
        this.tagName = tagName;
    }

    public String getTagName() {
        return tagName;
    }
}