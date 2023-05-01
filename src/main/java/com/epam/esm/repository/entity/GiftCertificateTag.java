package com.epam.esm.repository.entity;

import java.util.Objects;

public class GiftCertificateTag extends BaseEntity {
    private long giftCertificateId;
    private long tagId;

    public GiftCertificateTag(long giftCertificateId, long tagId) {
        this.giftCertificateId = giftCertificateId;
        this.tagId = tagId;
    }

    public long getGiftCertificateId() {
        return giftCertificateId;
    }

    public void setGiftCertificateId(long giftCertificateId) {
        this.giftCertificateId = giftCertificateId;
    }

    public long getTagId() {
        return tagId;
    }

    public void setTagId(long tagId) {
        this.tagId = tagId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GiftCertificateTag that = (GiftCertificateTag) o;
        return giftCertificateId == that.giftCertificateId && tagId == that.tagId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(giftCertificateId, tagId);
    }

    @Override
    public String toString() {
        return "GiftCertificateTag{" +
                "giftCertificateId=" + giftCertificateId +
                ", tagId=" + tagId +
                '}';
    }

    @Override
    @Deprecated
    public String getName() {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public void setName(String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public long getId() {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public void setId(long id) {
        throw new UnsupportedOperationException();
    }
}
