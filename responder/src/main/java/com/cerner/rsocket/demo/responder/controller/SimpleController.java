package com.cerner.rsocket.demo.responder.controller;

import com.cerner.rsocket.demo.model.SimpleObservation;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.InstantType;
import org.hl7.fhir.r4.model.Observation;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

import java.util.Date;
import java.util.Random;

@Controller
public class SimpleController {

    private final Random random = new Random(10);

    @MessageMapping("observation.simple")
    public Flux<SimpleObservation> simple() {
        return Flux.range(1, random.nextInt(10))
                   .map(value -> new SimpleObservation(value, "The patient with the ID " + random.nextInt()));
    }

    @MessageMapping("observation.fhir")
    public Flux<Observation> fhir() {
        return Flux.range(1, random.nextInt(10))
                   .map(this::buildObservation);
    }

    private Observation buildObservation(Integer value) {
        final Observation observation = new Observation();

        observation.setId("id-" + value);
        observation.setIssuedElement(new InstantType(new Date()));
        observation.setCode(new CodeableConcept(new Coding("system-" + random.nextInt(),
                "code-" + random.nextInt(), "display-" + random.nextInt())));

        return observation;
    }
}
