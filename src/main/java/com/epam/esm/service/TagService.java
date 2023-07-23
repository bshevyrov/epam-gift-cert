package com.epam.esm.service;

import com.epam.esm.entity.TagEntity;

import java.util.List;

public interface TagService extends BaseService<TagEntity> {
    List<TagEntity> findAllByGiftCertificateId(long id);

}
