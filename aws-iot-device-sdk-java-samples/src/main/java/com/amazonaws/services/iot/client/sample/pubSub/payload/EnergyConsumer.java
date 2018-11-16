package com.amazonaws.services.iot.client.sample.pubSub.payload;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public class EnergyConsumer extends GenericPayload {

    @JsonProperty
    private final String id;
    @JsonProperty
    private final Integer capacity;
    @JsonProperty
    private final Integer maxCapacity;
    @JsonProperty
    private final Integer chargingPower;

    public EnergyConsumer(String id, Integer capacity, Integer maxCapacity, Integer chargingPower, Instant timestamp) {
        super(timestamp);
        this.id = id;
        this.capacity = capacity;
        this.maxCapacity = maxCapacity;
        this.chargingPower = chargingPower;
    }
}
