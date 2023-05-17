package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.Tag;
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
        if(!StringUtils.isAlpha(tag.getName())){
            throw  new TagNameException(tag.getName());
        }
        return tagDAO.create(tag);
    }

    @Override
    public Tag findById(long id) {
        return tagDAO.findById(id);
    }

    @Override
    public List<Tag> findAll() {
        return tagDAO.findAll();
    }

    @Override
    public void update(Map<String, Object> updates) {
        if
        tagDAO.update(updates);
    }

    @Override
    public void delete(long id) {
        if(id<=0||id>Long.MAX_VALUE){
            throw new TagIdException(id);
        }
        tagDAO.deleteById(id);
    }

    @Override
    public List<Tag> findAllByGiftCertificateId(long id) {
        return tagDAO.findAllByGiftCertificateId(id);
    }

    @Override
    public boolean existByName(String name) {
        return tagDAO.existByName(name);
    }

    @Override
    public Tag findByName(String name) {
        return tagDAO.findByName(name);
    }
}
