package com.jabrouwer82.codetest;

import com.google.inject.servlet.ServletModule;
import com.googlecode.objectify.ObjectifyFilter;

/**
 * Jersey Servlet Configuration.
 */
public class CodetestServletModule extends ServletModule {

    @Override
    protected void configureServlets() {
        filter("/*").through(ObjectifyFilter.class);
    }
}