package com.cerner.rsocket.demo.requester.service;

import com.cerner.rsocket.demo.model.SimpleObservation;
import org.hl7.fhir.r4.model.Observation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RequesterService {

    private final Mono<RSocketRequester> requester;

    @Autowired
    public RequesterService(final Mono<RSocketRequester> requester) {
        this.requester = requester;
    }

    @Scheduled(fixedRate = 3000)
    public void consumeSimpleObservations() {
        requester.flatMapMany(this::retrieveSimpleObservations)
                 .map(SimpleObservation::toString)
                 .doOnComplete(() -> System.out.println("----------------------------------------------------------"))
                 .subscribe(System.out::println);
    }

    private Flux<SimpleObservation> retrieveSimpleObservations(final RSocketRequester rSocketRequester) {
        return rSocketRequester.route("observation.simple")
                               .retrieveFlux(SimpleObservation.class);
    }

    //@Scheduled(fixedRate = 3000) FIXME - throws a 'No decoder for org.hl7.fhir.r4.model.Observation' exception
    public void consumeFHIRObservations() {
        requester.flatMapMany(this::retrieveFHIRObservations)
                 .map(Observation::toString)
                 .doOnComplete(() -> System.out.println("----------------------------------------------------------"))
                 .subscribe(System.out::println);
    }

    private Flux<Observation> retrieveFHIRObservations(final RSocketRequester rSocketRequester) {
        return rSocketRequester.route("observation.fhir")
                               .retrieveFlux(Observation.class);
    }
}
