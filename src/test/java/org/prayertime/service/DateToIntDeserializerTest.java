package org.prayertime.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class DateToIntDeserializerTest {
    ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(Integer.class, new DateToIntDeserializer());
        objectMapper.registerModule(simpleModule);
    }

    @Test
    void correctInput() throws JsonProcessingException {
        String json = "\"23.4.2004\"";

        Integer actual = objectMapper.readValue(json, Integer.class);
        assertEquals(2342004, actual);
    }

    @Test
    void noValue() throws JsonProcessingException {
        String json = "\"..\"";

        Integer actual = objectMapper.readValue(json, Integer.class);
        assertNull(actual);
    }

    @Test
    void noDay() throws JsonProcessingException {
        String json = "\".22.2004\"";

        Integer actual = objectMapper.readValue(json, Integer.class);
        assertNull(actual);
    }
}