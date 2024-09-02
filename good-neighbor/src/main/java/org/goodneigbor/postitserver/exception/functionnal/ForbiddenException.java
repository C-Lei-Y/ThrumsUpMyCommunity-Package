package org.goodneigbor.postitserver.exception.functionnal;

import org.goodneigbor.postitserver.exception.AppAbstractException;
import org.springframework.http.HttpStatus;

public class ForbiddenException extends AppAbstractException {

    private static final long serialVersionUID = 2093090778191001557L;

    public ForbiddenException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.FORBIDDEN;
    }

}
