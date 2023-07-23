package com.epam.esm.exception.giftcertificate;

/**
 * Exception class.
 * Objects of this class can be thrown during id validation, if id is illegal.
 */
public class GiftCertificateInvalidIdException extends RuntimeException {
    public GiftCertificateInvalidIdException(String message) {
        super(message);
    }
}

