package org.goodneigbor.postitserver.exception;

import org.springframework.http.HttpStatus;

public abstract class AppAbstractException extends Exception {

    private static final long serialVersionUID = 5847072563164444146L;

    public AppAbstractException(String message) {
        super(message);
    }

    public AppAbstractException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract HttpStatus getHttpStatus();

}
