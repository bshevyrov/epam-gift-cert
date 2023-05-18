package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.GiftCertificateIdException;
import com.epam.esm.exception.TagIdException;
import com.epam.esm.exception.TagNameException;
import com.epam.esm.service.TagService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


@Component
public class TagServiceImpl implements TagService {
    private final TagDAO tagDAO;

    @Autowired
    public TagServiceImpl(TagDAO tagDAO) {
        this.tagDAO = tagDAO;
    }

    @Override
    public long create(Tag tag) {
        verifyName(tag.getName());
        return tagDAO.create(tag);
    }

    @Override
    public Tag findById(long id) {
        verifyId(id);
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
        verifyId(id);
        tagDAO.deleteById(id);
    }

    @Override
    public List<Tag> findAllByGiftCertificateId(long id) {
        //TODO WHERE HANDLER
        verifyGiftCertificateId(id);
        return tagDAO.findAllByGiftCertificateId(id);
    }


    @Override
    public boolean existByName(String name) {
        verifyName(name);
        return tagDAO.existByName(name);
    }

    @Override
    public Tag findByName(String name) {
        verifyName(name);
        return tagDAO.findByName(name);
    }
    private void verifyId(long id) {
        if(id <=0){
            throw new TagIdException(id);
        }
    }
    private void verifyGiftCertificateId(long id) {
        if(id <=0){
            throw new GiftCertificateIdException(id);
        }
    }

    private void verifyName(String tagName) {
        if(!StringUtils.isAlpha(tagName)){
            throw  new TagNameException(tagName);
        }
    }
}
