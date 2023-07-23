package com.epam.esm.dao;

import com.epam.esm.entity.TagEntity;

import java.util.List;

public interface TagDAO extends BaseDAO<TagEntity> {

    List<TagEntity> findAllByGiftCertificateId(long id);

    boolean existByName(String name);

    TagEntity findByName(String name);
}
