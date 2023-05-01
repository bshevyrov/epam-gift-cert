package com.epam.esm.dao.Impl;

import com.epam.esm.dao.GiftCertificateTagDAO;
import com.epam.esm.repository.entity.GiftCertificateTag;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;
import java.util.Map;

public class GiftCertificateTagDAOImpl implements GiftCertificateTagDAO {
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void setJDBCTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public GiftCertificateTag findById(long id) {
        return null;
    }

    @Override
    public List<GiftCertificateTag> findAll() {
        return null;
    }

    @Override
    public void deleteById(long id) {

    }

    @Override
    public int create(GiftCertificateTag gct) {
        String query = "INSERT INTO gift_certificate_has_tag (gift_certificate_id, tag_id) VALUES (?,?)";
        return jdbcTemplate.update(query, gct.getGiftCertificateId(), gct.getTagId());
    }

    @Override
    public void update(Map<String, Object> updates) {

    }
}
