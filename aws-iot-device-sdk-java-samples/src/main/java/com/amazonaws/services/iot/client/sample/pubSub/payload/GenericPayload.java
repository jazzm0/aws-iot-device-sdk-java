package com.amazonaws.services.iot.client.sample.pubSub.payload;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;

public class GenericPayload {

    @JsonProperty
    protected final Instant timeStamp;

    public GenericPayload(Instant timeStamp) {
        this.timeStamp = timeStamp;
    }

    @JsonGetter
    public String getTimeStamp() {
        return OffsetDateTime.ofInstant(timeStamp, ZoneId.systemDefault()).toString();
    }
}
