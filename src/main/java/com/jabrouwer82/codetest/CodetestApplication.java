package com.jabrouwer82.codetest;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.server.ResourceConfig;
import org.jvnet.hk2.guice.bridge.api.GuiceBridge;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

/**
 * Handles Guice and Jersey configuration for handling request conversion using Jackson.
 */
public class CodetestApplication extends ResourceConfig {

    @Inject
    public CodetestApplication(@Context ServletContext servletContext, ServiceLocator serviceLocator) {

        //registers the Jackson stuff
        register(JacksonJaxbJsonProvider.class);
        register(ObjectMapperResolver.class);

        // Then, enable the bridge between jersey and guice.
        GuiceBridge.getGuiceBridge().initializeGuiceBridge(serviceLocator);
        GuiceIntoHK2Bridge guiceBridge = serviceLocator.getService(GuiceIntoHK2Bridge.class);
        guiceBridge.bridgeGuiceInjector(GuiceServletConfig.getOrCreateInjector());
    }
}