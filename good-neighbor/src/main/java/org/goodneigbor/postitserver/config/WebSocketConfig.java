package org.goodneigbor.postitserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private static final String WEBSOCKET_ENDPOINT = "/websocket";

    @Value("${myapp.websocket.enable-cors:false}")
    private Boolean enableCors;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/listen");
        config.setApplicationDestinationPrefixes("/send");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        if (Boolean.TRUE.equals(enableCors)) {
            registry.addEndpoint(WEBSOCKET_ENDPOINT).setAllowedOriginPatterns("*");
            registry.addEndpoint(WEBSOCKET_ENDPOINT).setAllowedOriginPatterns("*").withSockJS();
        } else {
            registry.addEndpoint(WEBSOCKET_ENDPOINT);
            registry.addEndpoint(WEBSOCKET_ENDPOINT).withSockJS();
        }
    }

}
