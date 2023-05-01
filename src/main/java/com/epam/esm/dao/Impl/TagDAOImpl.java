package com.epam.esm.dao.Impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.exception.TagNotFoundException;
import com.epam.esm.repository.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

@Repository
public class TagDAOImpl implements TagDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJDBCTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Tag findById(long id) {
        try {
            String query = "SELECT * FROM tag where id=?";
            return (Tag) jdbcTemplate.queryForObject(query, new Object[]{id}, new BeanPropertyRowMapper(Tag.class));
        } catch (EmptyResultDataAccessException e) {
            throw new TagNotFoundException(id);
        }
    }

    @Override
    public List<Tag> findAll() {
        String query = "SELECT * FROM tag";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper(Tag.class));
    }

    @Override
    public void deleteById(long id) {
        String query = "delete from tag where id = ?";
        int code = jdbcTemplate.update(query, id);
        if (code == 0) {
            throw new TagNotFoundException(id);
        }
    }

    @Override
    public int create(Tag tag) {
        String query = "insert into tag (name) values(?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement pst = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, tag.getName());
            return pst;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public void update(Map<String, Object> updates) {
        jdbcTemplate.update(createQuery(updates));
    }

    @Override
    public List<Tag> findAllByGiftCertificateId(long id) {
        String query = "SELECT * FROM tag as t INNER JOIN gift_certificate_has_tag gcht on t.id = gcht.tag_id WHERE gcht.gift_certificate_id=?";
        return jdbcTemplate.query(query, new Object[]{id}, new BeanPropertyRowMapper<>(Tag.class));
    }

    @Override
    public boolean existByName(String name) {
        String query = "SELECT COUNT(*) FROM tag WHERE name = ?";
        return jdbcTemplate.queryForObject(query,Integer.class,name)>0;
    }

    private String createQuery(Map<String, Object> map) {
        StringBuilder sb = new StringBuilder("UPDATE tags SET");
        return getString(map, sb);
    }

    static String getString(Map<String, Object> map, StringBuilder sb) {
        map.forEach((s, o) -> {
            if (!s.equals("id")) {
                sb.append(s)
                        .append(" = ")
                        .append(o.toString())
                        .append(",");
            }
        });
        sb.deleteCharAt(sb.length() - 1);
        sb.append("WHERE id = ")
                .append(map.get("id").toString());
        return sb.toString();
    }
}
