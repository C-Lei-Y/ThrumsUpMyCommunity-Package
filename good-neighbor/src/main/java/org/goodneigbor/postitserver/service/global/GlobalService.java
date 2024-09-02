package org.goodneigbor.postitserver.service.global;

import java.util.Optional;

import org.goodneigbor.postitserver.dto.IdEntityDto;
import org.goodneigbor.postitserver.dto.global.CountLikesDto;
import org.goodneigbor.postitserver.dto.global.GlobalStatusDto;
import org.goodneigbor.postitserver.exception.functionnal.ForbiddenException;
import org.goodneigbor.postitserver.exception.functionnal.NotFoundException;

public interface GlobalService {

    GlobalStatusDto getStatus();

    void clearCache();

    Optional<String> getParameterValue(String parameterName);

    String getParameterValueForClient(String parameterName) throws NotFoundException, ForbiddenException;

    CountLikesDto countLikes();

    IdEntityDto addLike(String clientIp);

}
