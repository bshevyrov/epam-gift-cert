package com.epam.esm.exception.giftcertificate;

/**
 * Exception class.
 * Objects of this class can be thrown during gift certificate update, if update body empty.
 */
public class GiftCertificateUpdateException extends RuntimeException {
    public GiftCertificateUpdateException(String message) {
        super(message);
    }
}


