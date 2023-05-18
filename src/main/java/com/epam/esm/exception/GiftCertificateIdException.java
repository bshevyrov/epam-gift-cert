package com.epam.esm.exception;

public class GiftCertificateIdException extends RuntimeException {
    private final long giftCertificateId;
    public GiftCertificateIdException(long giftCertificateId) {
        this.giftCertificateId = giftCertificateId;
    }

    public long getGiftCertificateId() {
        return giftCertificateId;
    }
}

