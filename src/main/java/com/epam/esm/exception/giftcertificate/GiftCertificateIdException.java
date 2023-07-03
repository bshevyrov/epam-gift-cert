package com.epam.esm.exception.giftcertificate;

/**
 * Exception class.
 * Objects of this class can be thrown during id validation, if id is illegal.
 */
public class GiftCertificateIdException extends RuntimeException {
    private final long giftCertificateId;

    public GiftCertificateIdException(long giftCertificateId) {
        this.giftCertificateId = giftCertificateId;
    }

    public long getGiftCertificateId() {
        return giftCertificateId;
    }
}

