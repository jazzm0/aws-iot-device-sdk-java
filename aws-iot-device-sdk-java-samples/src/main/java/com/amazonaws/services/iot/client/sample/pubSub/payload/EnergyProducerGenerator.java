package com.amazonaws.services.iot.client.sample.pubSub.payload;

import java.time.Instant;
import java.util.Iterator;
import java.util.UUID;

import static com.amazonaws.services.iot.client.sample.pubSub.payload.EnergyProducer.ProducerType.ATOM;
import static com.amazonaws.services.iot.client.sample.pubSub.payload.EnergyProducer.ProducerType.COAL;
import static com.amazonaws.services.iot.client.sample.pubSub.payload.EnergyProducer.ProducerType.SOLAR;
import static com.amazonaws.services.iot.client.sample.pubSub.payload.EnergyProducer.ProducerType.WIND;


public class EnergyProducerGenerator implements Iterator<EnergyProducer> {

    private final EnergyProducer.ProducerType[] types = {SOLAR, WIND, COAL, ATOM};

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public EnergyProducer next() {
        return new EnergyProducer(UUID.randomUUID().toString(), types[((int) (Math.random() * 1000)) % 4], ((int) (Math.random() * 100)) % 100, 100, Instant.now());
    }
}
