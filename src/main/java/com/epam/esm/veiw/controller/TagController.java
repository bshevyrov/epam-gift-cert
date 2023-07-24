package com.epam.esm.veiw.controller;

import com.epam.esm.facade.TagFacade;
import com.epam.esm.veiw.dto.TagDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
@Validated
public class TagController {
    private final TagFacade tagFacade;
    private final MessageSource messageSource;

    @Autowired
    public TagController(TagFacade tagFacade, MessageSource messageSource) {
        this.tagFacade = tagFacade;
        this.messageSource = messageSource;
    }

    /**
     * Method consumes request object.
     * Produces response object as the result of create operation.
     *
     * @param tagDTO object for creation
     * @param ucb    UriComponentsBuilder
     * @return response header with uri of created object.
     */
    @PostMapping
    public ResponseEntity<TagDTO> create(@RequestBody @Valid TagDTO tagDTO, UriComponentsBuilder ucb) {
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
    @GetMapping(value = "/{id}")
    public TagDTO findById(@PathVariable @Min(value = 1)
                           @Max(Long.MAX_VALUE) long id) {
        return tagFacade.findById(id);
    }

    /**
     * Method produces set of response objects
     *
     * @return list of all {@link TagDTO}
     */
    @GetMapping(value = "")
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
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<TagDTO> deleteById(@PathVariable @Min(1) @Max(Long.MAX_VALUE) long id) {
        tagFacade.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

