package com.epam.esm.dao.Impl;

import com.epam.esm.dao.GiftCertificateTagDAO;
import com.epam.esm.entity.GiftCertificateTag;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
@Component
public class GiftCertificateTagDAOImpl implements GiftCertificateTagDAO {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    @Deprecated
    public GiftCertificateTag findById(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public List<GiftCertificateTag> findAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public void deleteById(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long create(GiftCertificateTag gct) {
        String query = "INSERT INTO gift_certificate_has_tag (gift_certificate_id, tag_id) VALUES (:giftId,:tagId)";
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("giftId", gct.getGiftCertificateId())
                .addValue("tagId", gct.getTagId());
        return namedParameterJdbcTemplate.update(query, parameterSource);
    }

    @Override
    @Deprecated
    public void update(GiftCertificateTag giftCertificateTag) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteByGiftCertificateId(long giftCertificateId) {
        String query = "DELETE FROM gift_certificate_has_tag WHERE gift_certificate_id=:id";
        namedParameterJdbcTemplate.update(query, new MapSqlParameterSource().addValue("id", giftCertificateId));
    }
}
