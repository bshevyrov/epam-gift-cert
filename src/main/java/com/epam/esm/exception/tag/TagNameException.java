package com.epam.esm.exception.tag;

/**
 * Exception class.
 * Objects of this class can be thrown during name validation, if id is illegal.
 */
public class TagNameException extends RuntimeException {
    private final String tagName;

    public TagNameException(String tagName) {
        this.tagName = tagName;
    }

    public String getTagName() {
        return tagName;
    }
}