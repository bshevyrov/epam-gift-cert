package com.epam.esm.exception;

public class GiftCertificateNameException extends RuntimeException {
    private final String giftCertificateName;

    public GiftCertificateNameException(String giftCertificateName) {
        this.giftCertificateName = giftCertificateName;
    }

    public String getGiftCertificateName() {
        return giftCertificateName;
    }
}