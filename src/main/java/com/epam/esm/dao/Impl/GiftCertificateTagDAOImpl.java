package com.epam.esm.dao.Impl;

import com.epam.esm.dao.GiftCertificateTagDAO;
import com.epam.esm.entity.GiftCertificateTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This class make request to DB to table gift_certificate_has_tag.
 */
@Repository
public class GiftCertificateTagDAOImpl implements GiftCertificateTagDAO {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setNamedParameterJDBCTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    /**
     * Guaranteed to throw an exception and leave.
     *
     * @throws UnsupportedOperationException always
     * @deprecated Unsupported operation.
     */
    @Override
    @Deprecated
    public GiftCertificateTag findById(long id) {
        throw new UnsupportedOperationException();
    }

    /**
     * Guaranteed to throw an exception and leave.
     *
     * @throws UnsupportedOperationException always
     * @deprecated Unsupported operation.
     */
    @Override
    @Deprecated
    public List<GiftCertificateTag> findAll() {
        throw new UnsupportedOperationException();
    }

    /**
     * Guaranteed to throw an exception and leave.
     *
     * @throws UnsupportedOperationException always
     * @deprecated Unsupported operation.
     */
    @Override
    @Deprecated
    public void deleteById(long id) {
        throw new UnsupportedOperationException();
    }

    /**
     * Method save to DB gift certificate tag entity.
     *
     * @param giftCertificateTag entity to save in DB.
     * @return nothing.
     */
    @Override
    public long create(GiftCertificateTag giftCertificateTag) {
        String query = "INSERT INTO gift_certificate_has_tag (gift_certificate_id, tag_id) VALUES (:giftId,:tagId)";
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("giftId", giftCertificateTag.getGiftCertificateId())
                .addValue("tagId", giftCertificateTag.getTagId());
        return namedParameterJdbcTemplate.update(query, parameterSource);
    }

    /**
     * Guaranteed to throw an exception and leave.
     *
     * @throws UnsupportedOperationException always
     * @deprecated Unsupported operation.
     */
    @Override
    @Deprecated
    public void update(GiftCertificateTag giftCertificateTag) {
        throw new UnsupportedOperationException();
    }

    /**
     * Method delete record in DB by gift certificate id.
     *
     * @param giftCertificateId entity to save in DB.
     */
    @Override
    public void deleteByGiftCertificateId(long giftCertificateId) {
        String query = "DELETE FROM gift_certificate_has_tag WHERE gift_certificate_id=:id";
        namedParameterJdbcTemplate.update(query, new MapSqlParameterSource().addValue("id", giftCertificateId));
    }
}
