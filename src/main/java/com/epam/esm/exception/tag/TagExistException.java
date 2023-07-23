package com.epam.esm.exception.tag;

/**
 * Exception class.
 * Objects of this class can be thrown during creation, if tag already exist.
 */
public class TagExistException extends RuntimeException {

    public TagExistException(String message) {
        super(message);
    }
}