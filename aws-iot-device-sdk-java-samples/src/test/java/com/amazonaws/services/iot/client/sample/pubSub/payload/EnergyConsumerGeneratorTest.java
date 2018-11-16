package com.amazonaws.services.iot.client.sample.pubSub.payload;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

public class EnergyConsumerGeneratorTest {

    private EnergyConsumerGenerator generator = new EnergyConsumerGenerator();
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testGenerator() throws JsonProcessingException {
        for (int i = 0; i < 100; i++)
            System.out.println(mapper.writeValueAsString(generator.next()));
    }
}