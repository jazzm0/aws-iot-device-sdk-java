package com.amazonaws.services.iot.client.sample.pubSub.payload;

import java.time.Instant;
import java.util.Iterator;
import java.util.UUID;

public class EnergyConsumerGenerator implements Iterator<EnergyConsumer> {

    private final Integer[] capacities = {1500, 2000, 2700, 3500};

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public EnergyConsumer next() {
        Integer maxCapacity = capacities[((int) (Math.random() * 1000)) % capacities.length];
        return new EnergyConsumer(UUID.randomUUID().toString(), ((int) ((Math.random() * 1000)) * maxCapacity * 1000) % maxCapacity, maxCapacity, ((int) ((Math.random() * 1000)) % maxCapacity), Instant.now());
    }
}
