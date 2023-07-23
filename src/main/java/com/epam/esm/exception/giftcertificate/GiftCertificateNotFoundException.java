package com.epam.esm.exception.giftcertificate;

/**
 * Exception class.
 * Objects of this class can be thrown during find by id operation, if record not present in DB.
 */
public class GiftCertificateNotFoundException extends RuntimeException {

    public GiftCertificateNotFoundException(String message) {
        super(message);
    }
}
