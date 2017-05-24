package com.jabrouwer82.codetest;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * This represents a book object with id, title, and author properties.
 */
@Entity
public class Book {

	// TODO: Convert away from auto-generated ids, manually use app engine id generator
    @Id
    private Long id;

    private String title;
    private String author;
    
    // Required by objectify.
    private Book() {};
    
    @JsonCreator
    public Book(
        @JsonProperty("id") Long id,
        @JsonProperty("title") String title,
        @JsonProperty("author") String author
    ) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getAuthor() {
        return this.author;
    }
    
    public void setAuthor(String author) {
    	this.author = author;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
        	return true;
        }
        if (other == null || this.getClass() != other.getClass()) {
        	return false;
        }
        Book otherBook = (Book) other;
        return Objects.equals(this.id, otherBook.id) &&
            Objects.equals(this.title, otherBook.title) &&
            Objects.equals(this.author, otherBook.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.title, this.author);
    }
}