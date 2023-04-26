package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.mapper.TagListMapper;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.repository.entity.Tag;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class TagServiceImpl implements TagService {
    private TagMapper tagMapper;
    private TagDAO tagDAO;

    private TagListMapper tagListMapper;

    @Autowired

    public TagServiceImpl(TagMapper tagMapper, TagDAO tagDAO, TagListMapper tagListMapper) {
        this.tagMapper = tagMapper;
        this.tagListMapper = tagListMapper;
        this.tagDAO = tagDAO;
    }


    @Override
    public long create(Tag tag) {
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
    public void update(Tag tag) {
        tagDAO.update(tag);
    }

    @Override
    public void delete(long id) {
        tagDAO.deleteById(id);
    }
}
