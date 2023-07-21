package com.epam.esm.veiw.controller;

import com.epam.esm.exception.giftcertificate.GiftCertificateNotFoundException;
import com.epam.esm.exception.giftcertificate.GiftCertificateUpdateException;
import com.epam.esm.exception.tag.TagNameException;
import com.epam.esm.facade.GiftCertificateFacade;
import com.epam.esm.veiw.ErrorResponse;
import com.epam.esm.veiw.SearchRequest;
import com.epam.esm.veiw.dto.GiftCertificateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * GiftCertificateController class is the REST controller which consumes JSON as the request, forwards to relevant
 * method in facade and produces JSON as the result of model's operations.
 */
@RestController
@RequestMapping(value = "/gifts",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class GiftController {
    private final GiftCertificateFacade giftCertificateFacade;

    @Autowired
    public GiftController(GiftCertificateFacade giftCertificateFacade) {
        this.giftCertificateFacade = giftCertificateFacade;
    }

    /**
     * Method consumes request object.
     * Produces response object as the result of create operation.
     *
     * @param giftCertificateDTO object for creation
     * @param ucb                UriComponentsBuilder
     * @return response header with uri of created object.
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<GiftCertificateDTO> create(@RequestBody GiftCertificateDTO giftCertificateDTO, UriComponentsBuilder ucb) {
        long id = giftCertificateFacade.create(giftCertificateDTO);

        HttpHeaders headers = new HttpHeaders();
        URI locationUri = ucb.path("/gifts/")
                .path(String.valueOf(id))
                .build().toUri();

        headers.setLocation(locationUri);
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    /**
     * Method consumes URL param.
     * Produces response object as the result of find by id operation.
     *
     * @param id URL parameter, which holds gift certificate id value
     * @return found {@link GiftCertificateDTO}
     **/
    @RequestMapping(method = RequestMethod.GET,
            value = "/{id}")
    public GiftCertificateDTO findById(@PathVariable long id) {
        return giftCertificateFacade.findById(id);
    }

    /**
     * Method consumes URL params from web request
     * Produces set of response objects based on web request params
     *
     * @param searchRequest object, which holds URL request params for search
     * @return {@link  GiftCertificateDTO} as the result of search based on URL params
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "")
    public List<GiftCertificateDTO> findAll(SearchRequest searchRequest) {
        return giftCertificateFacade.findAll(searchRequest);
    }

    /**
     * Method consumes URL param.
     * Produces response object as the result of delete operation.
     *
     * @param id URL parameter, which holds gift certificate id value
     * @return Http status
     */
    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE)
    public ResponseEntity<GiftCertificateDTO> deleteById(@PathVariable long id) {
        giftCertificateFacade.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Method consumes request object and URL param.
     * Produces response object as the result of update operation.
     *
     * @param giftCertificateDTO GiftCertificateDtoRequest request object for update
     * @param id                 URL parameter, which holds gift certificate id value
     */
    @RequestMapping(value = "/{id}",
            method = RequestMethod.PATCH)
    public void update(@RequestBody GiftCertificateDTO giftCertificateDTO,
                       @PathVariable long id) {
        giftCertificateDTO.setId(id);
        giftCertificateFacade.update(giftCertificateDTO);
    }

}
