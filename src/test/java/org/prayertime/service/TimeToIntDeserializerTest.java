package org.prayertime.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TimeToIntDeserializerTest {
    ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(Integer.class, new TimeToIntDeserializer());
        objectMapper.registerModule(simpleModule);
    }

    @Test
    void normalFormat() throws JsonProcessingException {
        String json = "\"14:12\"";

        Integer actual = objectMapper.readValue(json, Integer.class);
        assertEquals(1412, actual);
    }

    @Test
    void noColon() throws JsonProcessingException {
        String json = "\"1412\"";

        Integer actual = objectMapper.readValue(json, Integer.class);
        assertNull(actual);
    }

    @Test
    void longerFormat() throws JsonProcessingException {
        String json = "\"14:12:05\"";

        Integer actual = objectMapper.readValue(json, Integer.class);
        assertEquals(1412, actual);
    }

    @Test
    void onlyColon() throws JsonProcessingException {
        String json = "\":\"";

        Integer actual = objectMapper.readValue(json, Integer.class);
        assertNull(actual);
    }
}