package com.epam.esm.exception.tag;

/**
 * Exception class.
 * Objects of this class can be thrown during name validation, if id is illegal.
 */
public class TagInvalidNameException extends RuntimeException {

    public TagInvalidNameException(String message) {
        super(message);
    }
}