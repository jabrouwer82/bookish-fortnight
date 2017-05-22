package com.jabrouwer82.codetest;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;

/**
 * Handles resolving object mappers.
 */
@Provider
public class ObjectMapperResolver implements ContextResolver<ObjectMapper> {
    private final ObjectMapper objectMapper;

    public ObjectMapperResolver() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new GuavaModule());
    }


    @Override
    public ObjectMapper getContext(Class<?> type) {
        return objectMapper;
    }
}