package com.epam.esm.service;

import com.epam.esm.entity.Tag;

import java.util.List;

public interface TagService extends BaseService<Tag> {
    List<Tag> findAllByGiftCertificateId(long id);

    boolean existByName(String v);
}
