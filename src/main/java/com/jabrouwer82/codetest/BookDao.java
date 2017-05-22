package com.jabrouwer82.codetest;

import java.util.List;

import javax.inject.Inject;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;

/**
 * DAO/objectify helper class for accessing the book datastore.
 */
public class BookDao {

    private final Objectify objectify;

    @Inject
    public BookDao(Objectify objectify) {
        this.objectify = objectify;
    }

    public List<Book> getAll() {
        return objectify.load().type(Book.class).list();
    }

    public Book get(Long id) {
        return objectify.load().type(Book.class).id(id).now();
    }

    public Book save(Book book) {
        Key<Book> key = objectify.save().entity(book).now();
        return objectify.load().key(key).now();
    }
    
    public void delete(Long id) {
    	objectify.delete().type(Book.class).id(id).now();
    }
}