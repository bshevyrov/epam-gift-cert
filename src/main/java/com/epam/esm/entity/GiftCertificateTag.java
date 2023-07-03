package com.epam.esm.entity;

import java.util.Objects;
/**
 * Entity that represent gift_certificate_has_tag table.
 */
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GiftCertificateTag that = (GiftCertificateTag) o;
        return giftCertificateId == that.giftCertificateId && tagId == that.tagId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), giftCertificateId, tagId);
    }

    @Override
    public String toString() {
        return "GiftCertificateTag{" +
                "giftCertificateId=" + giftCertificateId +
                ", tagId=" + tagId +
                "} " + super.toString();
    }
}
