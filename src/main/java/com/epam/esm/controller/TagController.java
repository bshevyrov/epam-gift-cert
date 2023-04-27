package com.epam.esm.controller;

import com.epam.esm.controller.dto.TagDTO;
import com.epam.esm.exception.TagNotFoundException;
import com.epam.esm.service.TagService;
import com.epam.esm.service.impl.TagServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/tags")
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagServiceImpl tagService) {
        this.tagService = tagService;
    }


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<TagDTO> saveTagTDO(@RequestBody TagDTO tagDTO, UriComponentsBuilder ucb) {
        long tagId = tagService.create(tagDTO);

        HttpHeaders headers = new HttpHeaders();
        URI locationUri = ucb.path("/tags/")
                .path(String.valueOf(tagId))
                .build().toUri();

        headers.setLocation(locationUri);

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET)
    public TagDTO tagById(@PathVariable long id) {
        return tagService.findById(id);
    }
    @RequestMapping(value = "",
            method = RequestMethod.GET)
    public List<TagDTO> findAll() {
        System.out.println("all");
        return tagService.findAll();
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE)
    public ResponseEntity<TagDTO> deleteById(long id) {
        tagService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PUT)
    public void updateTag(TagDTO tagDTO) {
        tagService.update(tagDTO);
    }

    @ExceptionHandler(TagNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error tagNotFound(TagNotFoundException e) {
        long tagId = e.getTagId();
        return new Error(4, "Tag [" + tagId + "] not found");
    }
}
