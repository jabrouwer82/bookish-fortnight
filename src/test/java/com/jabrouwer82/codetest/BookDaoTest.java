package com.jabrouwer82.codetest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import com.google.appengine.repackaged.com.google.common.collect.ImmutableList;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.LoadResult;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.Result;
import com.googlecode.objectify.cmd.DeleteType;
import com.googlecode.objectify.cmd.Deleter;
import com.googlecode.objectify.cmd.LoadType;
import com.googlecode.objectify.cmd.Loader;
import com.googlecode.objectify.cmd.Saver;


public class BookDaoTest {

	private BookDao dao;
	
	private static final Book staticBook = new Book(1234L, "t", "a");
	
	@Mock Objectify mockObjectify;
	@Mock Loader mockLoader;
	@Mock LoadType<Book> mockLoadType;
	@Mock LoadResult<Book> mockLoadResult;
	@Mock Saver mockSaver;
	@Mock Result<Key<Book>> mockBookKeyResult;
	@Mock Key<Book> mockKey;
	@Mock Deleter mockDeleter;
	@Mock DeleteType mockDeleteType;
	@Mock Result<Void> mockVoidResult;
	
	@Rule public MockitoRule mockitoRule = MockitoJUnit.rule();
	
	@Before
	public void setup() {
		dao = new BookDao(mockObjectify);
		
		when(mockObjectify.load()).thenReturn(mockLoader);
		when(mockLoader.type(eq(Book.class))).thenReturn(mockLoadType);
		when(mockLoadType.list()).thenReturn(ImmutableList.of(staticBook));
		when(mockLoadType.id(anyLong())).thenReturn(mockLoadResult);
		when(mockLoadResult.now()).thenReturn(staticBook);
		when(mockObjectify.save()).thenReturn(mockSaver);
		when(mockSaver.entity(any(Book.class))).thenReturn(mockBookKeyResult);
		when(mockBookKeyResult.now()).thenReturn(mockKey);
		when(mockLoader.key(eq(mockKey))).thenReturn(mockLoadResult);
		when(mockObjectify.delete()).thenReturn(mockDeleter);
		when(mockDeleter.type(eq(Book.class))).thenReturn(mockDeleteType);
		when(mockDeleteType.id(anyLong())).thenReturn(mockVoidResult);
	}
	
	@Test
	public void testGetAll() {
		List<Book> books = dao.getAll();
		
		verify(mockObjectify).load();
		assertEquals(ImmutableList.of(staticBook), books);
	}
	
	@Test
	public void testGet() {
		Book book = dao.get(1234L);
		
		verify(mockObjectify).load();
		assertEquals(staticBook, book);
	}

	@Test
	public void testSave() {
		Book book = dao.save(staticBook);
		
		verify(mockObjectify).save();
		verify(mockObjectify).load();
		assertEquals(staticBook, book);
	}
	
	@Test
	public void testDelete() {
		dao.delete(1234L);
		
		verify(mockObjectify).delete();
	}
}
