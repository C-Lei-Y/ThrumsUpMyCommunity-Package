package org.goodneigbor.postitserver.exception.functionnal;

import org.goodneigbor.postitserver.exception.AppAbstractException;
import org.springframework.http.HttpStatus;

public class InvalidDataException extends AppAbstractException {

    private static final long serialVersionUID = 6433907447561227975L;

    public InvalidDataException(String message) {
        super(message);
    }

    public InvalidDataException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

}
