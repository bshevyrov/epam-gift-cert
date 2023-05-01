package com.epam.esm.dao.Impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.exception.GiftCertificateNotFound;
import com.epam.esm.repository.entity.GiftCertificate;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import static com.epam.esm.dao.Impl.TagDAOImpl.getString;

@Component
public class GiftCertificateDAOImpl implements GiftCertificateDAO {
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void setJDBCTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public GiftCertificate findById(long id) {
        String query = "SELECT * FROM gift_certificate WHERE id=?";
        try {
            return (GiftCertificate) jdbcTemplate.queryForObject(query, new Object[]{id}, new BeanPropertyRowMapper(GiftCertificate.class));
        } catch (EmptyResultDataAccessException e) {
            throw new GiftCertificateNotFound(id);
        }
    }

    @Override
    public List<GiftCertificate> findAll() {
        String query = "SELECT * FROM gift_certificate";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper(GiftCertificate.class));
    }

    @Override
    public void deleteById(long id) {
        String query = "DELETE FROM gift_certificate WHERE id=?";
        jdbcTemplate.update(query, id);
    }

    @Override
    public int create(GiftCertificate giftCertificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String query = "insert into gift_certificate (name,description,duration) values (?,?,?)";

        jdbcTemplate.update(con -> {
            PreparedStatement pst = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int index = 0;
            pst.setString(++index, giftCertificate.getDescription());
            pst.setString(++index, giftCertificate.getName());
            pst.setInt(++index, giftCertificate.getDuration());
            return pst;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public void update(Map<String, Object> updates) {
        jdbcTemplate.update(createQuery(updates));
    }

    private String createQuery(Map<String, Object> map) {
        StringBuilder sb = new StringBuilder("UPDATE gift_certificate SET");
        return getString(map, sb);
    }
}
