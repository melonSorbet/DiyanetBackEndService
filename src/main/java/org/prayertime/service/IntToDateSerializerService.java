package org.prayertime.service;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class IntToDateSerializerService extends JsonSerializer<Integer> {
    @Override
    public void serialize(Integer integer, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        String dateString = integer.toString();
        jsonGenerator.writeString(dateString);

    }

}
