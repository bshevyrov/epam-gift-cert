package com.epam.esm.veiw.controller;

import com.epam.esm.exception.GiftCertificateNotFound;
import com.epam.esm.facade.GiftCertificateFacade;
import com.epam.esm.veiw.Error;
import com.epam.esm.veiw.dto.GiftCertificateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/gifts")
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
        URI locationUri = ucb.path("/gifts/")
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
    public List<GiftCertificateDTO> findAll(@RequestParam(required = false, value = "name") Optional<String> certName,
                                            @RequestParam(required = false) Optional<String> description,
                                            @RequestParam(required = false,defaultValue = "name") String sortField,
                                            @RequestParam(required = false,defaultValue = "asc") String sortType) {
        //todo check sort field and sort type
        return giftCertificateFacade.findAll(certName,description,sortField,sortType);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE)
    public ResponseEntity<GiftCertificateDTO> deleteById(@PathVariable long id) {
        giftCertificateFacade.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PATCH)
    public void updateGift(@RequestBody Map<String, Object> updates,
                           @PathVariable long id) {
        updates.put("id", id);
        giftCertificateFacade.update(updates);
    }

    @RequestMapping(value = "/tag/{name}",
            method = RequestMethod.GET)
    public List<GiftCertificateDTO> findByTagName(@PathVariable String name) {
        return giftCertificateFacade.findAllByTagName(name);
    }


    @ExceptionHandler(GiftCertificateNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error giftCertificateNotFound(GiftCertificateNotFound giftCertificateNotFound) {
        long certId = giftCertificateNotFound.getGiftId();
        return new Error(4, "Gift Certificate [" + certId + "] not found");
    }
}
