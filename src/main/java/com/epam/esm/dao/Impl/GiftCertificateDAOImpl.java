package com.epam.esm.dao.Impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.GiftCertificateNotFoundException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;


@Repository
public class GiftCertificateDAOImpl implements GiftCertificateDAO {
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setNamedParameterJDBCTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    KeyHolder keyHolder;

    @Override
    public GiftCertificate findById(long id) {
        String query = "SELECT * FROM gift_certificate WHERE id=:id";
        try {
            return namedParameterJdbcTemplate.queryForObject(query, new MapSqlParameterSource().addValue("id", id), new BeanPropertyRowMapper<>(GiftCertificate.class));
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
    public List<GiftCertificate> findAllByTagName(String name) {
        String query = "SELECT gs.id,gs.name, gs.description,gs.price,gs.duration,gs.price,gs.create_date,gs.last_update_date FROM gift_certificate as gs INNER JOIN gift_certificate_has_tag gcht on gs.id = gcht.gift_certificate_id INNER JOIN tag t on gcht.tag_id = t.id WHERE t.name = :name";
        return namedParameterJdbcTemplate.query(query, new MapSqlParameterSource().addValue("name", name), new BeanPropertyRowMapper<>(GiftCertificate.class));
    }

    @Override
    public long create(GiftCertificate giftCertificate) {
        keyHolder = new GeneratedKeyHolder();
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
    public void update(GiftCertificate giftCertificate) {
        Map<String, Object> map = objectToMap(giftCertificate);
        String query = createQuery(map);
        SqlParameterSource parameterSource = new MapSqlParameterSource().addValues(map);
        namedParameterJdbcTemplate.update(query, parameterSource);
    }

    private String createQuery(Map<String, Object> map) {
        StringBuilder sb = new StringBuilder("UPDATE gift_certificate SET ");
        return mapToQueryString(map, sb);
    }


    private Map<String, Object> objectToMap(GiftCertificate giftCertificate) {
        List<String> filledFieldsNames = getAllFilledFieldsNames(giftCertificate);
        List<Method> methods = getAllGetMethods(giftCertificate);
        return createNameValueMap(filledFieldsNames, methods, giftCertificate);
    }

    private Map<String, Object> createNameValueMap(List<String> fieldNameList, List<Method> methods, GiftCertificate giftCertificate) {
        Map<String, Object> result = new HashMap<>();

        Map<String, Method> fieldMethodMap = fieldNameList.stream().collect(Collectors.toMap(
                field -> field, // or Function.identity()
                field -> methods.stream()
                        .filter(method -> method.getName().toLowerCase().contains(field))
                        .findFirst().get()));

        fieldMethodMap.forEach((key, value) -> {
            try {
                result.put(key, (value).invoke(giftCertificate));
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });
        return result;
    }

    private List<Method> getAllGetMethods(GiftCertificate giftCertificate) {
        Method[] childMethods = giftCertificate.getClass().getDeclaredMethods();
        Method[] parentsMethods = giftCertificate.getClass().getSuperclass().getDeclaredMethods();

        List<Method> methods = Arrays.stream(ArrayUtils.addAll(childMethods, parentsMethods))
                .filter(method -> method.getName().contains("get"))
                .collect(Collectors.toList());
        return methods;
    }

    private List<String> getAllFilledFieldsNames(GiftCertificate giftCertificate) {
        List<String> notNullFields = new ArrayList<>();

        Field[] childFields = giftCertificate.getClass().getDeclaredFields();
        Field[] parentFields = giftCertificate.getClass().getSuperclass().getDeclaredFields();
        Field[] fields = ArrayUtils.addAll(childFields, parentFields);

        for (Field field : fields) {
            field.setAccessible(true);
            if (ClassUtils.isAssignable(field.getType(), Number.class)) {
                if (field.getName().equals("serialVersionUID")) {
                    continue;
                }
                try {
                    if (((Number) field.get(giftCertificate)).doubleValue() > 0) {
                        notNullFields.add(field.getName());
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    if (field.get(giftCertificate) != null) {
                        notNullFields.add(field.getName());
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return notNullFields;
    }

    private String mapToQueryString(Map<String, Object> map, StringBuilder sb) {

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String s = entry.getKey();
            if (!s.equals("id")) {
                sb.append(s)
                        .append(" = ")
                        .append(":")
                        .append(s)
                        .append(",");
            }
        }
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
