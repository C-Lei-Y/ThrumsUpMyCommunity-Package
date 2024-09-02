package org.goodneigbor.postitserver.util;

import org.goodneigbor.postitserver.exception.functionnal.InvalidDataException;

public class CheckDataUtil {

    private CheckDataUtil() {
    }

    public static void checkNotNull(String message, Object... objectList) throws InvalidDataException {
        for (Object object : objectList) {
            if (object == null) {
                throw new InvalidDataException(message + " is null");
            }
        }
    }

    public static void checkCondition(boolean condition, String message) throws InvalidDataException {
        if (!condition) {
            throw new InvalidDataException(message);
        }
    }

}
