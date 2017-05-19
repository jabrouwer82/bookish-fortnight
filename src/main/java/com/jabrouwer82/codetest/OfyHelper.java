package com.jabrouwer82.codetest;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

/**
 * This service exists to ensure that datastore objects get properly registered
 * before objectify attempts to write them to the datastore.
 */
public class OfyHelper{
	/**
	 * Register Objectify datastore objects here.
	 */
    static {
        ObjectifyService.register(Library.class);
        ObjectifyService.register(Book.class);
    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }

}