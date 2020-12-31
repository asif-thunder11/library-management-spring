package com.practice.librarymanagement.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.practice.librarymanagement.LibraryContext;
import com.practice.librarymanagement.exception.RestApiException;
import com.practice.librarymanagement.model.Book;
import com.practice.librarymanagement.model.BookRecord;
import com.practice.librarymanagement.model.BookRecordDTO;
import com.practice.librarymanagement.model.RestApiError;
import com.practice.librarymanagement.repository.BookDao;
import com.practice.librarymanagement.service.BookService;

@RestController
public class BooksController {

	@Autowired
	LibraryContext context;
	
	@Autowired
	BookService bookService;
	
	@GetMapping(value = "/books")
	@PreAuthorize("hasAuthority('book:read')")
	public List<Book> getBooks(HttpServletResponse response) {
//		return ResponseEntity.status(HttpStatus.OK).body(bookService.findAll())
		
		return bookService.findAll();	
	}
	
	
	@PostMapping(value = "/books")
	@PreAuthorize("hasAuthority('book:write')")
	public Book addBook(@RequestBody Book book) {
		System.out.println("Adding Book");
		System.out.println("Pub Date: "+book.getPublicationDate());
		return bookService.addBook(book);
	}
	
	@DeleteMapping(value = "/books/{isbn}")
	@PreAuthorize("hasAuthority('book:write')")
	public void deleteBookByIsbn(@PathVariable String isbn) {
		bookService.deleteByIsbn(isbn);
		return;
	}
	
	@GetMapping(value = "/books/records/all")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<BookRecordDTO> getAllbookRecords(){
		return bookService.getAllBookRecords();
	}
	
	@GetMapping(value = "/books/records")//(value = "/books/records/currentuser")
	@PreAuthorize("hasAuthority('book:read')")
	public List<BookRecordDTO> getBookRecordsByUser(){
		System.out.println("bookrecords "+ SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
		return bookService.getBookRecordsByUser( Long.parseLong( context.getUsername() ) );
	}
	
	@PostMapping(value = "/books/issues")
	@PreAuthorize("hasAnyRole('ROLE_NORMAL', 'ROLE_ADMIN')")
	public BookRecordDTO issueBook(@RequestBody String isbn) {
		return bookService.issueBookByIsbn(isbn);
	}
	
	@PostMapping(value = "/books/returns")
	@PreAuthorize("hasAnyRole('ROLE_NORMAL', 'ROLE_ADMIN')")
	public BookRecordDTO returnBook(@RequestBody String isbn) {
		return bookService.returnBookByIsbn(isbn);
	}
	
	
	//sample method for angular app
	@GetMapping(value = "/forAngular/heroes")
	public String getHeroes(){
		System.out.println("Gave Heroes to angular");
		String heroesString="[";
		heroesString+= "{\"id\":1,\"name\":\"Bruce Wayne\"},";
		heroesString+= "{\"id\":2,\"name\":\"Clark Kent\"},";
		heroesString+= "{\"id\":3,\"name\":\"Diana Prince\"},";
		heroesString+= "{\"id\":4,\"name\":\"Barry Allen\"}";
		heroesString+="]";
		
		return heroesString;
	}
}
