package com.jabrouwer82.codetest;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BookServlet extends HttpServlet {
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Book book;

    String libraryName = request.getParameter("libraryName");
    if (libraryName == null) {
    	libraryName = "default";
    }
    String title = request.getParameter("title");
    if (title == null) {
    	title = "";
    }
    String author = request.getParameter("author");
    if (author == null) {
    	author = "";
    }
    
    book = new Book(libraryName, title, author);

    OfyHelper.ofy().save().entity(book);
  }
}
