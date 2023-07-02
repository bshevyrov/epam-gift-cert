package com.epam.esm.util;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.GiftCertificateUpdateException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public final class DAOUtils {

    private DAOUtils() {
    }

    public static String createUpdateQuery(Map<String, Object> map) {
        StringBuilder sb = new StringBuilder("UPDATE gift_certificate SET ");
        return mapToQueryString(map, sb);
    }


    public static Map<String, Object> objectToMap(GiftCertificate giftCertificate) {
        List<String> filledFieldsNames = getAllFilledFieldsNames(giftCertificate);
        List<Method> methods = getAllGetMethods(giftCertificate);
        return createNameValueMap(filledFieldsNames, methods, giftCertificate);
    }

    private static Map<String, Object> createNameValueMap(List<String> fieldNameList, List<Method> methods, GiftCertificate giftCertificate) {
        Map<String, Object> result = new HashMap<>();

        Map<String, Method> fieldMethodMap = fieldNameList.stream()
                .collect(
                        Collectors.toMap(field -> field, field -> methods.stream()
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

    private static List<Method> getAllGetMethods(GiftCertificate giftCertificate) {
        Method[] childMethods = giftCertificate.getClass().getDeclaredMethods();
        Method[] parentsMethods = giftCertificate.getClass().getSuperclass().getDeclaredMethods();

        return Arrays.stream(ArrayUtils.addAll(childMethods, parentsMethods))
                .filter(method -> method.getName().contains("get"))
                .collect(Collectors.toList());
    }

    private static List<String> getAllFilledFieldsNames(GiftCertificate giftCertificate) {
        List<String> notNullFields = new ArrayList<>();

        Field[] childFields = giftCertificate.getClass().getDeclaredFields();
        Field[] parentFields = giftCertificate.getClass().getSuperclass().getDeclaredFields();
        Field[] fields = ArrayUtils.addAll(childFields, parentFields);

        for (Field field : fields) {
            if (field.getName().equals("serialVersionUID")) {
                continue;
            }
            field.setAccessible(true);
            try {
                if (ClassUtils.isAssignable(field.getType(), Number.class)) {
                    if (((Number) field.get(giftCertificate)).doubleValue() > 0) {
                        notNullFields.add(field.getName());
                    }
                } else {
                    if (field.get(giftCertificate) != null) {
                        notNullFields.add(field.getName());
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if (notNullFields.size() <= 1) {
            throw new GiftCertificateUpdateException(giftCertificate.getId());
        }
        return notNullFields;
    }

    private static String mapToQueryString(Map<String, Object> map, StringBuilder sb) {

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
}
