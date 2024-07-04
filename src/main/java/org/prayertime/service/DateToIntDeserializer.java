package org.prayertime.service;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class DateToIntDeserializer extends JsonDeserializer<Integer> {
    @Override
    public Integer deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        String string = jsonParser.getValueAsString();
        if (!string.contains(".")) {
            return null;
        }
        String[] strings = string.split("\\.");
        if (string.length() < 3) {
            return null;
        }
        
        return Integer.parseInt(strings[0] + strings[1] + strings[2]);
    }
}
