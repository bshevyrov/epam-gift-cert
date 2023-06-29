package com.epam.esm.dao.Impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.TagNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TagDAOImpl implements TagDAO {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setNamedParameterJDBCTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    private KeyHolder keyHolder;

    @Override
    public Tag findById(long id) {
        try {
            String query = "SELECT * FROM tag where id=:id";
            return (Tag) namedParameterJdbcTemplate.queryForObject(query, new MapSqlParameterSource().addValue("id", id), new BeanPropertyRowMapper(Tag.class));
        } catch (EmptyResultDataAccessException e) {
            throw new TagNotFoundException(id);
        }
    }

    @Override
    public List<Tag> findAll() {
        String query = "SELECT * FROM tag";
        return (List<Tag>) namedParameterJdbcTemplate.query(query, new BeanPropertyRowMapper(Tag.class));
    }

    @Override
    public void deleteById(long id) {
        String query = "delete from tag where id = :id";
        int code = namedParameterJdbcTemplate.update(query, new MapSqlParameterSource().addValue("id", id));
        if (code == 0) {
            throw new TagNotFoundException(id);
        }
    }

    @Override
    public long create(Tag tag) {
        String query = "insert into tag (name) values(:name)";
        keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(query, new MapSqlParameterSource().addValue("name", tag.getName()), keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public void update(Tag tag) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Tag> findAllByGiftCertificateId(long id) {
        String query = "SELECT * FROM tag as t INNER JOIN gift_certificate_has_tag gcht on t.id = gcht.tag_id WHERE gcht.gift_certificate_id=:id";
        return namedParameterJdbcTemplate.query(query, new MapSqlParameterSource().addValue("id", id), new BeanPropertyRowMapper<>(Tag.class));
    }

    @Override
    public boolean existByName(String name) {
        String query = "SELECT COUNT(*) FROM tag WHERE name = :name";
        return 0 < namedParameterJdbcTemplate.queryForObject(query, new MapSqlParameterSource().addValue("name", name), Integer.class);
    }


    @Override
    public Tag findByName(String name) {
        String query = "SELECT * FROM tag where name=:name";
        return (Tag) namedParameterJdbcTemplate.queryForObject(query, new MapSqlParameterSource().addValue("name", name), new BeanPropertyRowMapper(Tag.class));
    }
}
