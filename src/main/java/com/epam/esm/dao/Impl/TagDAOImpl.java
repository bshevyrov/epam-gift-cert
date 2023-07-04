package com.epam.esm.dao.Impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

/**
 * This class make request to DB to table tag.
 */
@Repository
public class TagDAOImpl implements TagDAO {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setNamedParameterJDBCTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    /**
     * Method search tag in DB by id.
     *
     * @param id Id of tag that we want to find.
     * @return Tag that was found or throw TagNotFoundException.
     */
    @Override
    public Tag findById(long id) {
        String query = "SELECT * FROM tag where id=:id";
        return namedParameterJdbcTemplate.queryForObject(query, new MapSqlParameterSource().addValue("id", id), new BeanPropertyRowMapper<>(Tag.class));
    }

    /**
     * Method gets all records from table tag.
     *
     * @return List of all Tag that was in DB.
     */
    @Override
    public List<Tag> findAll() {
        String query = "SELECT * FROM tag";
        return namedParameterJdbcTemplate.query(query, new BeanPropertyRowMapper<>(Tag.class));
    }

    /**
     * Method delete record from DB by id.
     *
     * @param id of the record to be deleted.
     */
    @Override
    public void deleteById(long id) {
        String query = "delete from tag where id = :id";
        namedParameterJdbcTemplate.update(query, new MapSqlParameterSource().addValue("id", id));
    }

    /**
     * Method save to DB.
     *
     * @param tag entity to save in DB.
     * @return id of created tag in DB.
     */
    @Override
    public long create(Tag tag) {
        String query = "insert into tag (name) values(:name)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(query, new MapSqlParameterSource().addValue("name", tag.getName()), keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    /**
     * Guaranteed to throw an exception and leave.
     *
     * @throws UnsupportedOperationException always
     * @deprecated Unsupported operation.
     */
    @Override
    @Deprecated
    public void update(Tag tag) {
        throw new UnsupportedOperationException();
    }

    /**
     * Method gets all records from table tag associated with gift certificate.
     *
     * @return List of all Tag that was in DB.
     */
    @Override
    public List<Tag> findAllByGiftCertificateId(long id) {
        String query = "SELECT * FROM tag as t INNER JOIN gift_certificate_has_tag gcht on t.id = gcht.tag_id WHERE gcht.gift_certificate_id=:id";
        return namedParameterJdbcTemplate.query(query, new MapSqlParameterSource().addValue("id", id), new BeanPropertyRowMapper<>(Tag.class));
    }

    /**
     * Method check if record with this name exist in DB
     *
     * @param name of searched record.
     * @return true if record with this name already exist or false.
     */
    @Override
    public boolean existByName(String name) {
        String query = "SELECT COUNT(*) FROM tag WHERE name = :name";
        return !Objects.equals(namedParameterJdbcTemplate.queryForObject(query, new MapSqlParameterSource().addValue("name", name), Integer.class), 0);
    }

    /**
     * Method search tag in DB by tag name.
     * Uses ONLY after existByName or behavior unpredictable.
     *
     * @param name name of tag that we want to find.
     * @return Tag that was found or throw TagNotFoundException.
     */
    @Override
    public Tag findByName(String name) {
        String query = "SELECT * FROM tag where name=:name";
        return namedParameterJdbcTemplate.queryForObject(query, new MapSqlParameterSource().addValue("name", name), new BeanPropertyRowMapper<>(Tag.class));
    }

    /**
     * Checks if record with id exists
     *
     * @param id tag id value
     * @return  true if record exists
     */
    @Override
    public boolean existById(long id) {
        String query = "SELECT COUNT(*) FROM tag WHERE id = :id";
        return !Objects.equals(namedParameterJdbcTemplate.queryForObject(query, new MapSqlParameterSource().addValue("id", id), Integer.class), 0);
    }
}
