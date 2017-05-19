package com.jabrouwer82.codetest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import com.googlecode.objectify.ObjectifyService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.Closeable;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BookServletTest {

  private BookServlet bookServlet;
  private Closeable closeable = null;


  private final LocalServiceTestHelper helper =
      new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig())
          .setEnvIsLoggedIn(true)
          .setEnvAuthDomain("localhost")
          .setEnvEmail("test@localhost");

  @Before
  public void setupSignGuestBookServlet() {
    helper.setUp();
    closeable = ObjectifyService.begin();

    bookServlet = new BookServlet();
  }

  @After
  public void tearDownHelper() throws IOException {
    if(closeable != null) {
      closeable.close();
    }
    helper.tearDown();
  }
  
  @Test
  public void testDoPost_CompleteBook() throws IOException, EntityNotFoundException {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);

    String libraryName = "TestLibrary";
    String testTitle = "Test Title";
    String testAuthor = "Test Author";

    when(request.getParameter("libraryName")).thenReturn(libraryName);
    when(request.getParameter("title")).thenReturn(testTitle);
    when(request.getParameter("author")).thenReturn(testAuthor);

    bookServlet.doPost(request, response);

    Entity book = DatastoreServiceFactory.getDatastoreService().prepare(new Query()).asSingleEntity();

    assertEquals(libraryName, book.getKey().getParent().getName());
    assertEquals(testTitle, book.getProperty("title"));
    assertEquals(testAuthor, book.getProperty("author"));
  }
  
  @Test
  public void testDoPost_IncompleteBook() throws IOException, EntityNotFoundException {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);

    when(request.getParameter("libraryName")).thenReturn(null);
    when(request.getParameter("title")).thenReturn(null);
    when(request.getParameter("author")).thenReturn(null);

    bookServlet.doPost(request, response);

    Entity book = DatastoreServiceFactory.getDatastoreService().prepare(new Query()).asSingleEntity();

    assertEquals("default", book.getKey().getParent().getName());
    assertEquals("", book.getProperty("title"));
    assertEquals("", book.getProperty("author"));
  }
}
