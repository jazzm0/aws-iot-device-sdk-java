package com.amazonaws.services.iot.client.sample.pubSub.payload;

import java.time.Instant;
import java.util.Iterator;
import java.util.UUID;

public class EnergyProducerGenerator implements Iterator<EnergyProducer> {

    private final EnergyProducer.ProducerType[] types = EnergyProducer.ProducerType.values();

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public EnergyProducer next() {
        return new EnergyProducer(UUID.randomUUID().toString(), types[((int) (Math.random() * 1000)) % types.length], ((int) (Math.random() * 100)) % 100, 100, Instant.now());
    }
}
