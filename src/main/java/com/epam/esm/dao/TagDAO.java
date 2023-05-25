package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.List;

public interface TagDAO extends BaseDAO<Tag> {

    List<Tag> findAllByGiftCertificateId(long id);

    boolean existByName(String name);

    Tag findByName(String name);
}
