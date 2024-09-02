package org.goodneigbor.postitserver.service.global.impl;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.goodneigbor.postitserver.dao.global.LikeDao;
import org.goodneigbor.postitserver.dao.global.ParameterDao;
import org.goodneigbor.postitserver.dto.IdEntityDto;
import org.goodneigbor.postitserver.dto.global.CountLikesDto;
import org.goodneigbor.postitserver.dto.global.GlobalStatusDto;
import org.goodneigbor.postitserver.entity.global.Like;
import org.goodneigbor.postitserver.entity.global.Parameter;
import org.goodneigbor.postitserver.exception.functionnal.ForbiddenException;
import org.goodneigbor.postitserver.exception.functionnal.NotFoundException;
import org.goodneigbor.postitserver.service.global.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import org.goodneigbor.postitserver.util.parameter.ParameterConst;
import org.goodneigbor.postitserver.util.parameter.ParameterUtil;

@Service
@Transactional
public class GlobalServiceImpl implements GlobalService {

    private static final Logger LOGGER = LogManager.getLogger(GlobalServiceImpl.class);

    private static final Date UPDATE = new Date();
    private static final String WS_LIKE_PATH = "/listen/likes:count";

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private ParameterDao parameterDao;

    @Autowired
    private LikeDao likeDao;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Value("${info.app.version:}")
    private String infoAppVersion;

    @Override
    public GlobalStatusDto getStatus() {
        GlobalStatusDto status = new GlobalStatusDto();
        status.setUpDate(UPDATE);
        status.setCurrentDate(new Date());
        status.setVersion(infoAppVersion);

        // We don't use the method getParameterValue to avoid using cache
        // (we test the database access)
        Optional<Parameter> parameterStatusOpt = parameterDao.findById(ParameterConst.GENERAL_STATUS);
        parameterStatusOpt.map(Parameter::getValue).ifPresent(status::setStatus);

        LOGGER.info("Global Status asked");
        return status;
    }

    @Override
    public void clearCache() {
        for (String name : cacheManager.getCacheNames()) {
            cacheManager.getCache(name).clear();
        }
        LOGGER.warn("Cache cleared");
    }

    @Override
    @Cacheable("parameter")
    public Optional<String> getParameterValue(String parameterName) {
        Optional<Parameter> parameter = parameterDao.findById(parameterName);
        LOGGER.info("Get ParameterValue for : {}", parameterName);
        return parameter.map(Parameter::getValue);
    }

    @Override
    @Cacheable("parameter_forclient")
    public String getParameterValueForClient(String parameterName) throws NotFoundException, ForbiddenException {
        Optional<Parameter> parameterOpt = parameterDao.findById(parameterName);
        Parameter parameter = parameterOpt.orElseThrow(() -> new NotFoundException("Parameter"));
        if (Boolean.FALSE.equals(parameter.getClientView())) {
            throw new ForbiddenException("parameter.getClientView == false");
        }
        return parameter.getValue();
    }

    @Override
    public CountLikesDto countLikes() {
        CountLikesDto countLikesDto = new CountLikesDto();
        countLikesDto.setCount(likeDao.count());
        LOGGER.info("CountLikes return the value : {}", countLikesDto.getCount());
        return countLikesDto;
    }

    @Override
    public IdEntityDto addLike(String clientIp) {
        IdEntityDto result = new IdEntityDto();

        Optional<String> maxLikeParameter = getParameterValue(ParameterConst.LIKE_MAX);
        Long maxLike = ParameterUtil.getLong(maxLikeParameter, 0l);

        if (countLikes().getCount() > maxLike) {
            LOGGER.warn("AddLike : the maximum of likes is reached");
            return result;
        }

        Like like = new Like();
        like.setClientIp(clientIp);
        like.setInsertDate(new Date());
        like = likeDao.save(like);

        result.setId(like.getId());

        // Send Result to WebSocket
        simpMessagingTemplate.convertAndSend(WS_LIKE_PATH, countLikes());
        LOGGER.info("AddLike successful");
        return result;
    }

}
