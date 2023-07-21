package com.epam.esm.exception;

import com.epam.esm.exception.giftcertificate.GiftCertificateNotFoundException;
import com.epam.esm.exception.giftcertificate.GiftCertificateUpdateException;
import com.epam.esm.exception.tag.TagExistException;
import com.epam.esm.exception.tag.TagIdException;
import com.epam.esm.exception.tag.TagNameException;
import com.epam.esm.exception.tag.TagNotFoundException;
import com.epam.esm.veiw.ErrorResponse;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Class catch exceptions which might occur during code execution.
 * Contains handlers to produce response if exception occur.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(GiftCertificateNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleGiftCertificateNotFoundException(GiftCertificateNotFoundException giftCertificateNotFoundException) {
        long certId = giftCertificateNotFoundException.getGiftCertificateId();
        if (LocaleContextHolder.getLocale().getLanguage().equals("uk")) {
            return new ErrorResponse(Integer.parseInt(HttpStatus.NOT_FOUND.value() + "04"), "Gift Certificate [" + certId + "] не знайдено.");
        }
        return new ErrorResponse(Integer.parseInt(HttpStatus.NOT_FOUND.value() + "04"), "Gift Certificate [" + certId + "] not found");
    }

    @ExceptionHandler(GiftCertificateUpdateException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleGiftCertificateUpdateException(GiftCertificateUpdateException giftCertificateUpdateException) {
        long certId = giftCertificateUpdateException.getGiftCertificateId();
        if (LocaleContextHolder.getLocale().getLanguage().equals("uk")) {
            return new ErrorResponse(Integer.parseInt(HttpStatus.BAD_REQUEST.value() + "06"), "Помилка в параметрах Gift Certificate [" + certId + "] .");
        }
        return new ErrorResponse(Integer.parseInt(HttpStatus.BAD_REQUEST.value() + "06"), "Error in parameters Gift Certificate [" + certId + "] .");
    }


    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        if (LocaleContextHolder.getLocale().getLanguage().equals("uk")) {
            return new ErrorResponse(Integer.parseInt(HttpStatus.BAD_REQUEST.value() + "04"), "Не вірне тіло Tag.");
        }
        return new ErrorResponse(Integer.parseInt(HttpStatus.BAD_REQUEST.value() + "04"), "Wrong body of Tag.");
    }

    @ExceptionHandler(TagNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleTagNotFoundException(TagNotFoundException e) {
        long tagId = e.getTagId();
        if (LocaleContextHolder.getLocale().getLanguage().equals("uk")) {
            return new ErrorResponse(Integer.parseInt(HttpStatus.NOT_FOUND.value() + "04"), "Tag [" + tagId + "] не знайдено.");
        } else {

            return new ErrorResponse(Integer.parseInt(HttpStatus.NOT_FOUND.value() + "04"), "Tag [" + tagId + "] not found.");
        }
    }

    @ExceptionHandler(TagNameException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleTagNameException(TagNameException e) {
        String tagName = e.getTagName();
        if (LocaleContextHolder.getLocale().getLanguage().equals("uk")) {
            return new ErrorResponse(Integer.parseInt(HttpStatus.BAD_REQUEST.value() + "05"), "Помилка в імені Tag [" + tagName + "].");
        }
        return new ErrorResponse(Integer.parseInt(HttpStatus.BAD_REQUEST.value() + "05"), "Error in name Tag [" + tagName + "].");
    }

    @ExceptionHandler(TagIdException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleTagIdException(TagIdException e) {
        long tagId = e.getTagId();
        if (LocaleContextHolder.getLocale().getLanguage().equals("uk")) {
            return new ErrorResponse(Integer.parseInt(HttpStatus.BAD_REQUEST.value() + "06"), "Помилка в айді Tag [" + tagId + "].");
        }
        return new ErrorResponse(Integer.parseInt(HttpStatus.BAD_REQUEST.value() + "06"), "Error in id Tag [" + tagId + "].");
    }

    @ExceptionHandler(TagExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleTagExistException(TagExistException e) {
        String tagName = e.getTagName();
        if (LocaleContextHolder.getLocale().getLanguage().equals("uk")) {
            return new ErrorResponse(Integer.parseInt(HttpStatus.BAD_REQUEST.value() + "07"), "Tag з ім'ям  [" + tagName + "] вже існує.");
        }
        return new ErrorResponse(Integer.parseInt(HttpStatus.BAD_REQUEST.value() + "07"), "Tag with name [" + tagName + "] already exist.");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception e) {

        if (LocaleContextHolder.getLocale().getLanguage().equals("uk")) {
            return new ErrorResponse(Integer.parseInt(HttpStatus.INTERNAL_SERVER_ERROR.value() + "00"), "Помилка серверу. " + " " + e.getMessage());
        }
        return new ErrorResponse(Integer.parseInt(HttpStatus.INTERNAL_SERVER_ERROR.value() + "00"), "Server error. " + e.getMessage());
    }
}
