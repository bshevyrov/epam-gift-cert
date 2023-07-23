package com.epam.esm.util;

import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.exception.giftcertificate.GiftCertificateUpdateException;
import com.epam.esm.veiw.SearchRequest;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.Map;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * Util class. Helps giftCertificate create update entity.
 */
public final class DAOUtils {

    private DAOUtils() {
    }

    /**
     * Method creates update query from map parameters.
     *
     * @param map of parameters
     * @return query sting
     */
    public static String createUpdateQuery(Map<String, Object> map) {
        StringBuilder sb = new StringBuilder("UPDATE gift_certificate SET ");
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

    /**
     * Method scans entity and creates map.
     * First by reflection gets fields and methods.
     * Then creates field value map
     *
     * @param giftCertificateEntity entity to scan.
     * @return map field,value
     */
    public static Map<String, Object> objectToMap(GiftCertificateEntity giftCertificateEntity) {
        List<String> filledFieldsNames = getAllFilledFieldsNames(giftCertificateEntity);
        List<Method> methods = getAllGetMethods(giftCertificateEntity);
        return createNameValueMap(filledFieldsNames, methods, giftCertificateEntity);
    }

    /**
     * The method creates a map of fields and values.
     * Filter the list of methods by fields that are not null or 0.
     * Then calls these methods to get the returned values.
     * After creates map field, value.
     *
     * @param fieldNameList   list of not null or 0 fields.
     * @param methods         list of all methods in entity.
     * @param giftCertificateEntity entity.
     * @return map
     */
    private static Map<String, Object> createNameValueMap(List<String> fieldNameList, List<Method> methods, GiftCertificateEntity giftCertificateEntity) {
        Map<String, Object> result = new HashMap<>();

        Map<String, Method> fieldMethodMap = fieldNameList.stream()
                .collect(
                        Collectors.toMap(field -> field, field -> methods.stream()
                                .filter(method -> method.getName().toLowerCase().contains(field))
                                .findFirst().orElseThrow(() -> new GiftCertificateUpdateException(giftCertificateEntity.getId()))));

        fieldMethodMap.forEach((key, value) -> {
            try {
                result.put(key, (value).invoke(giftCertificateEntity));
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });
        return result;
    }

    /**
     * Method gets all  methods and after filtering keeps only GET methods.
     *
     * @param giftCertificateEntity entity.
     * @return list of methods
     */
    private static List<Method> getAllGetMethods(GiftCertificateEntity giftCertificateEntity) {
        Method[] childMethods = giftCertificateEntity.getClass().getDeclaredMethods();
        Method[] parentsMethods = giftCertificateEntity.getClass().getSuperclass().getDeclaredMethods();

        return Arrays.stream(ArrayUtils.addAll(childMethods, parentsMethods))
                .filter(method -> method.getName().contains("get"))
                .collect(Collectors.toList());
    }

    /**
     * Method gets all fields then filtering them.
     * If count of not null or 0 fields less than 1 throws GiftCertificateUpdateException
     *
     * @param giftCertificateEntity entity
     * @return list of field names
     */
    private static List<String> getAllFilledFieldsNames(GiftCertificateEntity giftCertificateEntity) {
        List<String> notNullFields = new ArrayList<>();

        Field[] childFields = giftCertificateEntity.getClass().getDeclaredFields();
        Field[] parentFields = giftCertificateEntity.getClass().getSuperclass().getDeclaredFields();
        Field[] fields = ArrayUtils.addAll(childFields, parentFields);

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                if (ClassUtils.isAssignable(field.getType(), Number.class)) {
                    if (((Number) field.get(giftCertificateEntity)).doubleValue() > 0) {
                        notNullFields.add(field.getName());
                    }
                } else {
                    if (field.get(giftCertificateEntity) != null) {
                        notNullFields.add(field.getName());
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if (notNullFields.size() <= 1) {
            throw new GiftCertificateUpdateException(giftCertificateEntity.getId());
        }
        return notNullFields;
    }


    /**
     * Method creates query from searchRequest.
     * Checks if search parameter is present and build query
     *
     * @param searchRequest request parameter
     * @return String query
     */
    public static String createQueryFindAll(SearchRequest searchRequest) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT  gc.* FROM gift_certificate  as gc  INNER JOIN gift_certificate_has_tag gcht on gc.id = gcht.gift_certificate_id INNER JOIN tag t on gcht.tag_id = t.id ");
        if (searchRequest.getTagName().isPresent()
                || searchRequest.getGiftCertificateName().isPresent()
                || searchRequest.getDescription().isPresent()) {
            query.append("WHERE ");
            if (searchRequest.getTagName().isPresent()) {
                query.append("t.name LIKE '%")
                        .append(searchRequest.getTagName().get())
                        .append("%' ");
            }
            if (searchRequest.getGiftCertificateName().isPresent()) {
                query.append(searchRequest.getTagName().isPresent()
                        ? "AND "
                        : "WHERE ");
                query.append("gc.name LIKE '%")
                        .append(searchRequest.getGiftCertificateName().get())
                        .append("%' ");
            }
            if (searchRequest.getDescription().isPresent()) {
                query.append(searchRequest.getTagName().isPresent()
                        || searchRequest.getGiftCertificateName().isPresent()
                        ? "AND "
                        : "WHERE ");
                query.append("gs.description = '%")
                        .append(searchRequest.getDescription().get())
                        .append("%' ");
            }
        }

        query.append("ORDER BY ");
        query.append("gc.")
                .append(searchRequest.getSortField())
                .append(" ");
        query.append(searchRequest.getSortType().toUpperCase());
        return query.toString();
    }
}
