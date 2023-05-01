package com.epam.esm.dao.Impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.veiw.exception.GiftCertificateNotFound;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


@Component
public class GiftCertificateDAOImpl implements GiftCertificateDAO {
    private NamedParameterJdbcTemplate npjt;

    public void setNamedParameterJDBCTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.npjt = namedParameterJdbcTemplate;
    }

    @Override
    public GiftCertificate findById(long id) {
        String query = "SELECT * FROM gift_certificate WHERE id=:id";
        try {
            return (GiftCertificate) npjt.queryForObject(query, new MapSqlParameterSource().addValue("id", id), new BeanPropertyRowMapper(GiftCertificate.class));
        } catch (EmptyResultDataAccessException e) {
            throw new GiftCertificateNotFound(id);
        }
    }

    @Override
    public List<GiftCertificate> findAll() {
        String query = "SELECT * FROM gift_certificate";
        return (List<GiftCertificate>) npjt.query(query, new BeanPropertyRowMapper(GiftCertificate.class));
    }

    @Override
    public void deleteById(long id) {
        String query = "DELETE FROM gift_certificate WHERE id=?";
        npjt.update(query, new MapSqlParameterSource().addValue("id", id));
    }

    @Override
    public int create(GiftCertificate giftCertificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String query = "insert into gift_certificate (name,description,duration) values (:name,:description,:duration)";
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("name", giftCertificate.getName())
                .addValue("description", giftCertificate.getDescription())
                .addValue("duration", giftCertificate.getDuration());
        return npjt.update(query, paramSource, keyHolder);
    }

    @Override
    public void update(Map<String, Object> updates) {
        String query = createQuery(updates);
        SqlParameterSource parameterSource = new MapSqlParameterSource().addValues(updates);
        npjt.update(query, parameterSource);
    }

    private String createQuery(Map<String, Object> map) {
        StringBuilder sb = new StringBuilder("UPDATE gift_certificate SET");
        return getString(map, sb);
    }

    private String getString(Map<String, Object> map, StringBuilder sb) {
        map.forEach((s, o) -> {
            if (!s.equals("id")) {
                sb.append(s)
                        .append(" = ")
                        .append(":")
                        .append(s)
                        .append(",");
            }
        });
        sb.deleteCharAt(sb.length() - 1);
        sb.append("WHERE id = ")
                .append(map.get(":id").toString());
        return sb.toString();
    }
}
