package com.epam.esm.dao.Impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.repository.entity.GiftCertificate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class GiftCertificateDAOImpl implements GiftCertificateDAO {
 private JdbcTemplate jdbcTemplate;

    public void setJDBCTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public GiftCertificate findById(long id) {
        return null;
    }

    @Override
    public List<GiftCertificate> findAll() {
        return null;
    }

    @Override
    public void deleteById(long id) {
    }

    @Override
    public int create(GiftCertificate giftCertificate) {
        String query = "insert into gift_certificate values()";

        return 0;
    }

    @Override
    public void update(GiftCertificate giftCertificate) {
    }
}
