package com.epam.esm.veiw.controller;

import com.epam.esm.exception.tag.TagExistException;
import com.epam.esm.exception.tag.TagIdException;
import com.epam.esm.exception.tag.TagNotFoundException;
import com.epam.esm.facade.TagFacade;
import com.epam.esm.veiw.Error;
import com.epam.esm.veiw.dto.TagDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * TagController class is the REST controller which consumes JSON as the request, forwards to relevant
 * method in facade and produces JSON as the result of model's operations
 */
@RestController
@RequestMapping(value = "/tags",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class TagController {
    private final TagFacade tagFacade;

    @Autowired
    public TagController(TagFacade tagFacade) {
        this.tagFacade = tagFacade;
    }

    /**
     * Method consumes request object.
     * Produces response object as the result of create operation.
     *
     * @param tagDTO object for creation
     * @param ucb    UriComponentsBuilder
     * @return response header with uri of created object.
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<TagDTO> create(@RequestBody TagDTO tagDTO, UriComponentsBuilder ucb) {
        long tagId = tagFacade.create(tagDTO);
        HttpHeaders headers = new HttpHeaders();
        URI locationUri = ucb.path("/tags/")
                .path(String.valueOf(tagId))
                .build().toUri();

        headers.setLocation(locationUri);
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    /**
     * Method consumes URL param.
     * Produces response object as the result of find by id operation.
     *
     * @param id URL parameter, which holds gift certificate id value
     * @return found {@link TagDTO}
     **/
    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET)
    public TagDTO findById(@PathVariable long id) {
        return tagFacade.findById(id);
    }

    /**
     * Method produces set of response objects
     *
     * @return list of all {@link TagDTO}
     */
    @RequestMapping(value = "",
            method = RequestMethod.GET)
    public List<TagDTO> findAll() {
        return tagFacade.findAll();
    }

    /**
     * Method consumes URL param.
     * Produces response object as the result of delete operation.
     *
     * @param id URL parameter, which holds tag id value
     * @return Http status
     */
    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE)
    public ResponseEntity<TagDTO> deleteById(@PathVariable long id) {
        tagFacade.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(TagNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error tagNotFound(TagNotFoundException e) {
        long tagId = e.getTagId();
        if (LocaleContextHolder.getLocale().getLanguage().equals("uk")) {
            return new Error(Integer.parseInt(HttpStatus.NOT_FOUND + "04"), "Tag [" + tagId + "] не знайдено.");
        }
        return new Error(Integer.parseInt(HttpStatus.NOT_FOUND + "04"), "Tag [" + tagId + "] not found.");
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error emptyEntity(HttpMessageNotReadableException e) {
        if (LocaleContextHolder.getLocale().getLanguage().equals("uk")) {
            return new Error(Integer.parseInt(HttpStatus.BAD_REQUEST.value() + "04"), "Не вірне тіло Tag.");
        }
        return new Error(Integer.parseInt(HttpStatus.BAD_REQUEST.value() + "04"), "Wrong body of Tag.");
    }

    @ExceptionHandler(TagIdException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error tagIdError(TagIdException e) {
        long tagId = e.getTagId();
        if (LocaleContextHolder.getLocale().getLanguage().equals("uk")) {
            return new Error(Integer.parseInt(HttpStatus.BAD_REQUEST.value() + "06"), "Помилка в айді Tag [" + tagId + "].");
        }
        return new Error(Integer.parseInt(HttpStatus.BAD_REQUEST.value() + "06"), "Error in id Tag [" + tagId + "].");
    }

    @ExceptionHandler(TagExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error tagIdError(TagExistException e) {
        String tagName = e.getTagName();
        if (LocaleContextHolder.getLocale().getLanguage().equals("uk")) {
            return new Error(Integer.parseInt(HttpStatus.BAD_REQUEST.value() + "07"), "Tag з ім'ям  [" + tagName + "] вже існує.");
        }
        return new Error(Integer.parseInt(HttpStatus.BAD_REQUEST.value() + "07"), "Tag with name [" + tagName + "] already exist.");
    }
}

