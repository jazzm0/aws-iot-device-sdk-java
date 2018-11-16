package com.amazonaws.services.iot.client.sample.pubSub.payload;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public class EnergyConsumer {

    @JsonProperty
    private final String id;
    @JsonProperty
    private final Integer capacity;
    @JsonProperty
    private final Integer maxCapacity;
    @JsonProperty
    private final Integer chargingPower;
    @JsonProperty
    private final Instant timestamp;

    public EnergyConsumer(String id, Integer capacity, Integer maxCapacity, Integer chargingPower, Instant timestamp) {
        this.id = id;
        this.capacity = capacity;
        this.maxCapacity = maxCapacity;
        this.chargingPower = chargingPower;
        this.timestamp = timestamp;
    }
}
