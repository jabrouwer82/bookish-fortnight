package com.jabrouwer82.codetest;

import static com.googlecode.objectify.ObjectifyService.ofy;

import javax.inject.Singleton;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFilter;

/**
 * Guice configuration module.
 */
public class GuiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ObjectifyFilter.class).in(Singleton.class);
    }

    /**
     * Providing the Objectify object.
     */
    @Provides
    @Singleton
    Objectify provideObjectify() {
        return ofy();
    }
}
