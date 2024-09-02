package org.goodneigbor.postitserver.exception.functionnal;

import org.goodneigbor.postitserver.exception.AppAbstractException;
import org.springframework.http.HttpStatus;

public class FunctionnalException extends AppAbstractException {

    private static final long serialVersionUID = -5571169012896600620L;

    public FunctionnalException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.CONFLICT;
    }

}
