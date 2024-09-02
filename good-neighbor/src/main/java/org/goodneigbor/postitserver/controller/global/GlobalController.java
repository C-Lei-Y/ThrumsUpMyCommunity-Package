package org.goodneigbor.postitserver.controller.global;

import javax.servlet.http.HttpServletRequest;

import org.goodneigbor.postitserver.dto.IdEntityDto;
import org.goodneigbor.postitserver.dto.global.CountLikesDto;
import org.goodneigbor.postitserver.dto.global.GlobalStatusDto;
import org.goodneigbor.postitserver.exception.functionnal.ForbiddenException;
import org.goodneigbor.postitserver.exception.functionnal.NotFoundException;
import org.goodneigbor.postitserver.service.global.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/global")
public class GlobalController {

    @Autowired
    private GlobalService globalService;

    @GetMapping("/status")
    public GlobalStatusDto getStatus() {
        return globalService.getStatus();
    }

    @GetMapping("/parameters/{name}")
    public String getParameterValue(@PathVariable("name") String parameterName)
            throws NotFoundException, ForbiddenException {
        return globalService.getParameterValueForClient(parameterName);
    }

    @PostMapping(":clearCache")
    public void clearCache() {
        globalService.clearCache();
    }

    @GetMapping("/likes:count")
    public CountLikesDto countLikes() {
        return globalService.countLikes();
    }

    @PostMapping("/likes")
    public IdEntityDto addLike(HttpServletRequest request) {
        String clientIp = null;
        if (request != null) {
            clientIp = request.getRemoteAddr();
        }
        return globalService.addLike(clientIp);
    }

    @MessageMapping("/likes")
    @SendTo("/listen/likes")
    public IdEntityDto wsAddLike() {
        return globalService.addLike("websocket");
    }

}
