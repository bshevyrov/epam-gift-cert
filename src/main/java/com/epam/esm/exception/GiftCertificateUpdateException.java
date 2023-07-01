package com.epam.esm.exception;

public class GiftCertificateUpdateException extends RuntimeException {

    private final long giftCertificateId;

    public GiftCertificateUpdateException(long giftCertificateId) {
        this.giftCertificateId = giftCertificateId;
    }

    public long getGiftCertificateId() {
        return giftCertificateId;
    }


}


