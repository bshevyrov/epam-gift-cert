package com.epam.esm.exception;

public class GiftCertificateNotFoundException extends RuntimeException {
    private final long getGiftCertificateId;

    public GiftCertificateNotFoundException(long id) {
        this.getGiftCertificateId = id;
    }

    public long getGiftCertificateId() {
        return getGiftCertificateId;
    }
}
