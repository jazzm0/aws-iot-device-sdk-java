package com.amazonaws.services.iot.client.sample.pubSub.payload;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public class EnergyProducer extends GenericPayload {

    public enum ProducerType {
        SOLAR,
        WIND,
        COAL,
        ATOM
    }

    @JsonProperty
    private final String id;
    @JsonProperty
    private final ProducerType type;
    @JsonProperty
    private final Integer currentPower;
    @JsonProperty
    private final Integer maxPower;


    public EnergyProducer(String id, ProducerType type, Integer currentPower, Integer maxPower, Instant timeStamp) {
        super(timeStamp);
        this.id = id;
        this.type = type;
        this.currentPower = currentPower;
        this.maxPower = maxPower;
    }
}
