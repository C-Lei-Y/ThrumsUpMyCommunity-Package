package org.goodneigbor.postitserver.exception;

import org.springframework.http.HttpStatus;

public class TechnicalException extends AppAbstractException {

    private static final long serialVersionUID = 4396771597899900508L;

    public TechnicalException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

}
