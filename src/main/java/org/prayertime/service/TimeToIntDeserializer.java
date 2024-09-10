package org.prayertime.service;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class TimeToIntDeserializer extends JsonDeserializer<Integer> {
    @Override
    public Integer deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        String string = jsonParser.getValueAsString();
        return TimeToIntDeserializer.convertTimeStringToInt(string);
    }

    public static Integer convertTimeStringToInt(String string) {
        if (!string.contains(":")) {
            return null;
        }
        String[] strings = string.split(":");
        if (strings.length == 0) {
            return null;
        }
        return Integer.parseInt(strings[0] + strings[1]);
    }

}
