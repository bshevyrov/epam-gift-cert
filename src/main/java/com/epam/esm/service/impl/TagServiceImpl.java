package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.giftcertificate.GiftCertificateIdException;
import com.epam.esm.exception.tag.TagIdException;
import com.epam.esm.exception.tag.TagNameException;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.epam.esm.util.InputVerification.verifyId;
import static com.epam.esm.util.InputVerification.verifyName;


@Component
public class TagServiceImpl implements TagService {
    private final TagDAO tagDAO;

    @Autowired
    public TagServiceImpl(TagDAO tagDAO) {
        this.tagDAO = tagDAO;
    }

    @Override
    public long create(Tag tag) {
        if (!verifyName(tag.getName())) {
            throw new TagNameException(tag.getName());
        }
        return tagDAO.create(tag);
    }

    @Override
    public Tag findById(long id) {
        if (!verifyId(id)) {
            throw new TagIdException(id);
        }
        return tagDAO.findById(id);
    }

    @Override
    public List<Tag> findAll(Optional<String> tagName, Optional<String> giftCertificateName, Optional<String> description, String sortField, String sortType) {
        return tagDAO.findAll();
    }

    @Override
    public void update(Tag tag) {
        tagDAO.update(tag);
    }

    @Override
    public void delete(long id) {
        if (!verifyId(id)) {
            throw new TagIdException(id);
        }
        tagDAO.deleteById(id);
    }

    @Override
    public List<Tag> findAllByGiftCertificateId(long id) {
        if (!verifyId(id)) {
            throw new GiftCertificateIdException(id);
        }
        return tagDAO.findAllByGiftCertificateId(id);
    }
}
