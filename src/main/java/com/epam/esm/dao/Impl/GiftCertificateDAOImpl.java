package com.epam.esm.dao.Impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.GiftCertificateNotFoundException;
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
import java.util.Optional;


@Component
public class GiftCertificateDAOImpl implements GiftCertificateDAO {
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void setNamedParameterJDBCTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public GiftCertificate findById(long id) {
        String query = "SELECT * FROM gift_certificate WHERE id=:id";
        try {
            return (GiftCertificate) namedParameterJdbcTemplate.queryForObject(query, new MapSqlParameterSource().addValue("id", id), new BeanPropertyRowMapper(GiftCertificate.class));
        } catch (EmptyResultDataAccessException e) {
            throw new GiftCertificateNotFoundException(id);
        }
    }

    @Override
    public List<GiftCertificate> findAll() {
        String query = "SELECT * FROM gift_certificate";
        return namedParameterJdbcTemplate.query(query, new BeanPropertyRowMapper<>(GiftCertificate.class));
    }

    @Override
    public List<GiftCertificate> findAll(Optional<String> certName, Optional<String> description, String sortField, String sortType) {
        String query = createQueryFindAll(certName, description, sortField, sortType);
        return namedParameterJdbcTemplate.query(query, new BeanPropertyRowMapper<>(GiftCertificate.class));
    }

    @Override
    public void deleteById(long id) {
        String query = "DELETE FROM gift_certificate WHERE id=:id";
        namedParameterJdbcTemplate.update(query, new MapSqlParameterSource().addValue("id", id));
    }

    @Override
    public List<GiftCertificate> findByTagName(String name) {
        String query = "SELECT gs.id,gs.name, gs.description,gs.price,gs.duration,gs.price,gs.create_date,gs.last_update_date FROM gift_certificate as gs INNER JOIN gift_certificate_has_tag gcht on gs.id = gcht.gift_certificate_id INNER JOIN tag t on gcht.tag_id = t.id WHERE t.name = :name";
        return namedParameterJdbcTemplate.query(query, new MapSqlParameterSource().addValue("name", name), new BeanPropertyRowMapper<>(GiftCertificate.class));
    }

    @Override
    public boolean existById(long id) {
        String query = "SELECT COUNT(*) FROM gift_certificate WHERE id = :id";
        return 0 < namedParameterJdbcTemplate.queryForObject(query, new MapSqlParameterSource().addValue("id", id), Integer.class);
    }

    @Override
    public long create(GiftCertificate giftCertificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String query = "insert into gift_certificate (name,description,duration,price) values (:name,:description,:duration,:price)";
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("name", giftCertificate.getName())
                .addValue("description", giftCertificate.getDescription())
                .addValue("price", giftCertificate.getPrice())
                .addValue("duration", giftCertificate.getDuration());
        namedParameterJdbcTemplate.update(query, paramSource, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public void update(Map<String, Object> updates) {
        String query = createQuery(updates);
        SqlParameterSource parameterSource = new MapSqlParameterSource().addValues(updates);
        namedParameterJdbcTemplate.update(query, parameterSource);
    }

    private String createQuery(Map<String, Object> map) {
        StringBuilder sb = new StringBuilder("UPDATE gift_certificate SET ");
        return mapToString(map, sb);
    }

    private String mapToString(Map<String, Object> map, StringBuilder sb) {
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
        sb.append(" WHERE id = :id");
        return sb.toString();
    }

    private String createQueryFindAll(Optional<String> certName, Optional<String> description, String sortField, String sortType) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM gift_certificate gs ");
        //todo escape symbols
        if (certName.isPresent()) {
            query.append("WHERE gs.name = :certName ");
        }
        if (certName.isPresent() && description.isPresent()) {
            query.append("AND ");
            query.append("gs.description = :description ");
        }
        if (description.isPresent() && !certName.isPresent()) {
            query.append("WHERE gs.description = :description ");
        }
        query.append("ORDER BY ");
        query.append(sortField).append(" ");
        query.append(sortType.toUpperCase());
        return query.toString();
    }
}
