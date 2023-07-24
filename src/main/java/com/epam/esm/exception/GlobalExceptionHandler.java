package com.epam.esm.exception;

import com.epam.esm.exception.giftcertificate.GiftCertificateNotFoundException;
import com.epam.esm.exception.giftcertificate.GiftCertificateUpdateException;
import com.epam.esm.exception.tag.TagExistException;
import com.epam.esm.exception.tag.TagNotFoundException;
import com.epam.esm.veiw.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

/**
 * Class catch exceptions which might occur during code execution.
 * Contains handlers to produce response if exception occur.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(GiftCertificateNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleGiftCertificateNotFoundException(GiftCertificateNotFoundException e) {
        return new ErrorResponse(Integer.parseInt(HttpStatus.NOT_FOUND.value() + "04"), e.getMessage());
    }

    @ExceptionHandler(GiftCertificateUpdateException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleGiftCertificateUpdateException(GiftCertificateUpdateException e) {
        return new ErrorResponse(Integer.parseInt(HttpStatus.BAD_REQUEST.value() + "06"), e.getMessage());
    }


    @ExceptionHandler(TagNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleTagNotFoundException(TagNotFoundException e) {
        return new ErrorResponse(Integer.parseInt(HttpStatus.NOT_FOUND.value() + "04"), e.getMessage());
    }


    @ExceptionHandler(TagExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleTagExistException(TagExistException e) {
        return new ErrorResponse(Integer.parseInt(HttpStatus.BAD_REQUEST.value() + "07"), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception e) {
        return new ErrorResponse(Integer.parseInt(HttpStatus.INTERNAL_SERVER_ERROR.value() + "00"), e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponse handleException(ConstraintViolationException e) {
        return new ErrorResponse(Integer.parseInt(HttpStatus.INTERNAL_SERVER_ERROR.value() + "00"), e.getConstraintViolations().iterator().next().getPropertyPath().toString());

    }
}