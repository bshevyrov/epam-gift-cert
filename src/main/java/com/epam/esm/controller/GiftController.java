package com.epam.esm.controller;

import com.epam.esm.controller.dto.GiftCertificateDTO;
import com.epam.esm.exception.GiftCertificateNotFound;
import com.epam.esm.facade.GiftCertificateFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/gift")
public class GiftController {
    private final GiftCertificateFacade giftCertificateFacade;

    @Autowired
    public GiftController(GiftCertificateFacade giftCertificateFacade) {
        this.giftCertificateFacade = giftCertificateFacade;
    }

    @RequestMapping(method = RequestMethod.POST,
            consumes = "application/json")
    public ResponseEntity<GiftCertificateDTO> saveTagTDO(@RequestBody GiftCertificateDTO giftCertificateDTO, UriComponentsBuilder ucb) {
        long id = giftCertificateFacade.create(giftCertificateDTO);

        HttpHeaders headers = new HttpHeaders();
        URI locationUri = ucb.path("/tags/")
                .path(String.valueOf(id))
                .build().toUri();

        headers.setLocation(locationUri);
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET,
            value = "/{id}")
    public GiftCertificateDTO findById(@PathVariable long id) {
        return giftCertificateFacade.findById(id);
    }

    @RequestMapping(method = RequestMethod.GET,
            value = "")
    public List<GiftCertificateDTO> findAll() {
        return giftCertificateFacade.findAll();
    }

    @RequestMapping(value = "{/id}",
            method = RequestMethod.DELETE)
    public ResponseEntity<GiftCertificateDTO> deleteById(@PathVariable long id) {
        giftCertificateFacade.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PUT)
    public void updateGift(GiftCertificateDTO giftCertificateDTO) {
        giftCertificateFacade.update(giftCertificateDTO);
    }

    @ExceptionHandler(GiftCertificateNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error giftCertificateNotFound(GiftCertificateNotFound giftCertificateNotFound) {
        long certId = giftCertificateNotFound.getGiftId();
        return new Error(4, "Gift Certificate [" + certId + "] not found");
    }

}
