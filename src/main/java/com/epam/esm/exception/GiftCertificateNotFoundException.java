package com.epam.esm.exception;

public class GiftCertificateNotFoundException extends RuntimeException {
    private long giftId;

    public GiftCertificateNotFoundException(long id) {
        this.giftId = id;
    }

    public long getGiftId() {
        return giftId;
    }
}
