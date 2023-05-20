package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.GiftCertificateIdException;
import com.epam.esm.exception.TagIdException;
import com.epam.esm.exception.TagNameException;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

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
    public List<Tag> findAll() {
        return tagDAO.findAll();
    }

    @Override
    public void update(Map<String, Object> updates) {
        tagDAO.update(updates);
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
        //TODO WHERE HANDLER
        if (!verifyId(id)) {
            throw new GiftCertificateIdException(id);
        }
        return tagDAO.findAllByGiftCertificateId(id);
    }


    @Override
    public boolean existByName(String name) {
        if (!verifyName(name)) {
            throw new TagNameException(name);
        }
        return tagDAO.existByName(name);
    }

    @Override
    public Tag findByName(String name) {
        if (!verifyName(name)) {
            throw new TagNameException(name);
        }
        return tagDAO.findByName(name);
    }
}
