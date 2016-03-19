package com.vitalsport.profile.web;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.hateoas.VndErrors;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.zip.DataFormatException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
@ResponseBody
public class ExceptionControllerAdvice {

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public VndErrors.VndError illegalArgumentException(IllegalArgumentException exception) {
        return prepareVndError(exception);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(DateTimeParseException.class)
    public VndErrors.VndError dateTimeParseException(DateTimeParseException exception) {
        return prepareVndError(exception);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public VndErrors.VndError numberFormatException(NumberFormatException exception) {
        return prepareVndError(exception);
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public VndErrors.VndError emptyResultDataAccessException(EmptyResultDataAccessException exception) {
        return prepareVndError(exception);
    }

    private VndErrors.VndError prepareVndError(Throwable exception) {

        Optional<Throwable> cause = Optional.of(Optional.of(exception.getCause()).orElse());


        String message = Optional.of(exception.getCause().getMessage())
                .orElse(exception.getClass().getSimpleName());
        return new VndErrors.VndError(exception.getLocalizedMessage(), message);
    }
}
