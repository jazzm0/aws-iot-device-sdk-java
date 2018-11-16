package com.amazonaws.services.iot.client.sample.pubSub.payload;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class GenericPayload {

    DateTimeFormatter formatter =
            DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL)
                    .withLocale(Locale.GERMAN)
                    .withZone(ZoneId.systemDefault());

    @JsonProperty
    protected final Instant timeStamp;

    public GenericPayload(Instant timeStamp) {
        this.timeStamp = timeStamp;
    }

    @JsonGetter
    public String getTimeStamp() {
        return formatter.format(timeStamp);
    }
}
