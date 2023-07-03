package com.epam.esm.exception.giftcertificate;

/**
 * Exception class.
 * Objects of this class can be thrown during gift certificate update, if update body empty.
 */
public class GiftCertificateUpdateException extends RuntimeException {

    private final long giftCertificateId;

    public GiftCertificateUpdateException(long giftCertificateId) {
        this.giftCertificateId = giftCertificateId;
    }

    public long getGiftCertificateId() {
        return giftCertificateId;
    }


}


