package com.jabrouwer82.codetest;

import static org.junit.Assert.assertEquals;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.appengine.tools.development.testing.LocalUserServiceTestConfig;
import com.google.common.collect.ImmutableList;

public class BookServiceTest {

  private BookService bookService;

  private final LocalServiceTestHelper helper =
      new LocalServiceTestHelper(new LocalUserServiceTestConfig())
          .setEnvIsLoggedIn(true)
          .setEnvAuthDomain("localhost")
          .setEnvEmail("test@localhost");
  
  @Mock BookDao mockDao;
  
  @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

  @Before
  public void setupBookService() {
    helper.setUp();
    bookService = new BookService(mockDao);
  }

  @After
  public void tearDownHelper() {
    helper.tearDown();
  }

  @Test
  public void testGetBook() {
    Book expectedBook = new Book(1234l, "t", "a");
    
    when(mockDao.get(anyLong())).thenReturn(expectedBook);

    Book actualBook = bookService.getBook(1234L);

    assertEquals(expectedBook, actualBook);
  }

  @Test
  public void tesGetAllBooks() {
	Book expectedBookOne = new Book(1234l, "t", "a");
	Book expectedBookTwo = new Book(5678l, "u", "b");
	ImmutableList<Book> expectedBookList = ImmutableList.of(expectedBookOne, expectedBookTwo);
    
    when(mockDao.getAll()).thenReturn(expectedBookList);

    List<Book> actualBookList = bookService.getAllBooks();

    assertEquals(expectedBookList, actualBookList);
  }
  
  @Test
  public void testSaveNewBook() {
    Book expectedBook = new Book(1234l, "t", "a");
    
    when(mockDao.save(any(Book.class))).thenReturn(expectedBook);

    Book actualBook = bookService.saveNewBook(expectedBook);

    assertEquals(expectedBook, actualBook);
  }
  
  @Test
  public void testUpdateBook() {
    Book expectedBook = new Book(1234l, "t", "a");
    
    when(mockDao.save(any(Book.class))).thenReturn(expectedBook);

    Book actualBook = bookService.updateBook(1234L, expectedBook);

    assertEquals(expectedBook, actualBook);
  }
  
  @Test
  public void testDeleteBook() {
    bookService.deleteBook(1234L);

    verify(mockDao).delete(1234L);
  }
}