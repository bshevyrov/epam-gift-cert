package com.epam.esm.exception.tag;

/**
 * Exception class.
 * Objects of this class can be thrown during id validation, if id is illegal.
 */
public class TagInvalidIdException extends RuntimeException {

    public TagInvalidIdException(String message) {
        super(message);
    }
}