package com.jabrouwer82.codetest;

import java.net.URI;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Web service for handling GET/PUT/POST/DELETE http requests for /book using Jersey/JAX-RS.
 */
@Path("/book")
public class BookService {

    private final BookDao dao;

    @Inject
    public BookService(BookDao dao) {
        this.dao = dao;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> getAllBooks() {
        return this.dao.getAll();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBook(@PathParam("id") Long id) {
        Book book = this.dao.get(id);
        if (book == null) {
        	return Response.status(404).build();
        }
        return Response.ok(book).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveNewBook(@Context UriInfo info, Book book) {
        Book savedBook = this.dao.save(book);
        
        URI uri = info.getAbsolutePathBuilder()
        		.path(savedBook.getId().toString())
        		.build();
        return Response.created(uri).entity(savedBook).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    /*
     * This PUT implementation does not allow for entity creation, 
     * as our DAO creates ids for new entities, meaning creating would not be idempotent.
     */
    public Response updateBook(@Context UriInfo info, @PathParam("id") Long id, Book book) {
    	Book existingBook = this.dao.get(id);
    	if (existingBook == null) {
    		return Response.status(403)
    				.entity("Please do not use PUT to create entities, use a POST request instead")
    				.build();
    	}
        Book savedBook = this.dao.save(book);
        URI uri = info.getAbsolutePathBuilder()
        		.path(savedBook.getId().toString())
        		.build();
        return Response.ok(uri).entity(savedBook).build();
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteBook(@PathParam("id") Long id) {
    	this.dao.delete(id);
    }

}