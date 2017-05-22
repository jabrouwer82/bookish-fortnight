package com.jabrouwer82.codetest;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

/**
 * Configures the guice injector.
 */
public class GuiceServletConfig extends GuiceServletContextListener {

    private static Injector injector;

    @Override
    protected Injector getInjector() {
        return injector;
    }

    public static Injector getOrCreateInjector() {

        if (injector == null) {
            injector = Guice.createInjector(
                new CodetestServletModule(),
                new GuiceModule()
            );
        }
        return injector;
    }
}
