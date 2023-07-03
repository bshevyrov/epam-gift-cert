package com.epam.esm.dao.Impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.util.DAOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * This class make request to DB to table gift_certificate
 */
@Repository
public class GiftCertificateDAOImpl implements GiftCertificateDAO {
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setNamedParameterJDBCTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    /**
     * Method save to DB gift certificate.
     *
     * @param giftCertificate entity to save in DB.
     * @return id of created gift certificate in DB.
     */
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
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    /**
     * Method search gift certificate in DB by id.
     *
     * @param id Id of gift certificate that we want to find.
     * @return GiftCertificate that was found.
     */
    @Override
    public GiftCertificate findById(long id) {
        String query = "SELECT * FROM gift_certificate WHERE id=:id";
        return namedParameterJdbcTemplate.queryForObject(query, new MapSqlParameterSource().addValue("id", id), new BeanPropertyRowMapper<>(GiftCertificate.class));
    }


    /**
     * Guaranteed to throw an exception and leave.
     *
     * @throws UnsupportedOperationException always
     * @deprecated Unsupported operation.
     */
    @Override
    @Deprecated
    public List<GiftCertificate> findAll() {
        throw new UnsupportedOperationException();
    }

    /**
     * Method gets all records from table gift_certificate sorted and searched by parameters.
     *
     * @param tagName             tag name to search all gift certificate with this tag.
     * @param giftCertificateName part of gift certificate name to search.
     * @param description         part of gift certificate description to search.
     * @param sortField           sort by date or description.
     * @param sortType            sort type asc or desc.
     * @return List of all GiftCertificate that was found by parameter and sorted in DB.
     */
    @Override
    public List<GiftCertificate> findAll(Optional<String> tagName, Optional<String> giftCertificateName, Optional<String> description, String sortField, String sortType) {
        String query = DAOUtils.createQueryFindAll(tagName, giftCertificateName, description, sortField, sortType);
        return namedParameterJdbcTemplate.query(query, new BeanPropertyRowMapper<>(GiftCertificate.class));
    }

    /**
     * Method delete record from DB by id.
     *
     * @param id of the record to be deleted.
     */
    @Override
    public void deleteById(long id) {
        String query = "DELETE FROM gift_certificate WHERE id=:id";
        namedParameterJdbcTemplate.update(query, new MapSqlParameterSource().addValue("id", id));
    }

    /**
     * Method update record in DB.
     *
     * @param giftCertificate parameters that wil be updated. Only not NULL or 0 values will be saved other values from entity will be ignored.
     */
    @Override
    public void update(GiftCertificate giftCertificate) {
        Map<String, Object> map = DAOUtils.objectToMap(giftCertificate);
        String query = DAOUtils.createUpdateQuery(map);
        SqlParameterSource parameterSource = new MapSqlParameterSource().addValues(map);
        namedParameterJdbcTemplate.update(query, parameterSource);
    }

    /**
     * Checks if record with id exists
     *
     * @param id gift certificate id value
     * @return {@code true} if record exists
     */
    @Override
    public boolean existById(long id) {
        String query = "SELECT COUNT(*) FROM gift_certificate WHERE id = :id";
        return !Objects.equals(namedParameterJdbcTemplate.queryForObject(query, new MapSqlParameterSource().addValue("id", id), Integer.class), 0);
    }
}
