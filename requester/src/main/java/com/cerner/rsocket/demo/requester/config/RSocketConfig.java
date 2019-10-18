package com.cerner.rsocket.demo.requester.config;

import io.rsocket.frame.decoder.PayloadDecoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import reactor.core.publisher.Mono;

@Configuration
public class RSocketConfig {

    @Value("${responder.host}")
    private String responderHost;

    @Value("${responder.port}")
    private int responderPort;

    @Bean
    public Mono<RSocketRequester> rSocketRequester(RSocketStrategies strategies) {
        return RSocketRequester.builder()
                               .dataMimeType(MediaType.APPLICATION_CBOR)
                               .rsocketStrategies(strategies)
                               .connectTcp(responderHost, responderPort);
    }
}
