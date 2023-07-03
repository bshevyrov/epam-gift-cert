package com.epam.esm.exception.giftcertificate;

/**
 * Exception class.
 * Objects of this class can be thrown during find by id operation, if record not present in DB.
 */
public class GiftCertificateNotFoundException extends RuntimeException {
    private final long getGiftCertificateId;

    public GiftCertificateNotFoundException(long id) {
        this.getGiftCertificateId = id;
    }

    public long getGiftCertificateId() {
        return getGiftCertificateId;
    }
}
