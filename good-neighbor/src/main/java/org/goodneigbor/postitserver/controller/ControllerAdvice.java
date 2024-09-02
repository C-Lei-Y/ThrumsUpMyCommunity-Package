package org.goodneigbor.postitserver.controller;

import java.util.Date;

import javax.validation.ConstraintViolationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.goodneigbor.postitserver.exception.functionnal.InvalidDataException;
import org.goodneigbor.postitserver.service.global.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import org.goodneigbor.postitserver.dto.global.ErrorDto;
import org.goodneigbor.postitserver.exception.AppAbstractException;
import org.goodneigbor.postitserver.util.ExceptionUtil;

@RestControllerAdvice
public class ControllerAdvice {

    private static final Logger LOGGER = LogManager.getLogger(ControllerAdvice.class);

    @Autowired
    private UserService userService;

    @ExceptionHandler(AppAbstractException.class)
    public ResponseEntity<ErrorDto> handleAppException(final AppAbstractException exception) {
        return handleServerError(exception.getHttpStatus(), exception);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorDto> handleBadRequest(final Exception exception) {
        return handleServerError(HttpStatus.BAD_REQUEST, exception);
    }

    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<ErrorDto> handleConstraintViolation(TransactionSystemException exception) {
        Throwable cause = exception.getRootCause();
        if (cause instanceof ConstraintViolationException) {
            return handleServerError(HttpStatus.BAD_REQUEST,
                    ExceptionUtil.convert((ConstraintViolationException) cause));
        } else {
            return handleServerError(HttpStatus.INTERNAL_SERVER_ERROR, exception);
        }
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorDto> handleConstraintViolation(MaxUploadSizeExceededException exception) {
        return handleServerError(HttpStatus.BAD_REQUEST,
                new InvalidDataException("Maximum upload size exceeded", exception));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDto> handleAccessDenied(final AccessDeniedException exception) {
        if (userService.getCurrentUser() == null) {
            return handleServerError(HttpStatus.UNAUTHORIZED, exception);
        } else {
            return handleServerError(HttpStatus.FORBIDDEN, exception);
        }
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorDto> handleServerError(final Exception exception) {
        return handleServerError(HttpStatus.INTERNAL_SERVER_ERROR, exception);
    }

    private ResponseEntity<ErrorDto> handleServerError(HttpStatus httpStatus, Throwable throwable) {
        if (httpStatus.is5xxServerError()) {
            LOGGER.error("Server error occured", throwable);
        } else {
            LOGGER.warn("Error returned on client", throwable);
        }

        ErrorDto errorDto = new ErrorDto();
        errorDto.setTimestamp(new Date());
        errorDto.setStatus(httpStatus.value());
        errorDto.setError(throwable.getClass().getSimpleName());
        errorDto.setMessage(throwable.getMessage());
        return new ResponseEntity<>(errorDto, httpStatus);
    }

}
