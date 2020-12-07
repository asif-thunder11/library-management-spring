package com.practice.librarymanagement.service;

import java.util.List;
import java.util.Optional;
import com.practice.librarymanagement.model.Book;
import com.practice.librarymanagement.model.BookRecord;
import com.practice.librarymanagement.model.BookRecord.RECORD_TYPE;
import com.practice.librarymanagement.model.BookRecordDTO;


public interface BookService {
	
	Book addBook(Book book);
	List<Book> saveAll(List<Book> books);
	Optional<Book> findById(long id);
	List<Book> findAll();
	List<Book> findAllById(List<Long> ids);
	long count();
	void deleteByIsbn(String isbn);
	void delete(Book book);
	void deleteAll(List<Book> books);
	void deleteAll();
	
	//custom
	List<Book> findByAuthor(String author);
	List<Book> findByPublisher(String publisher);
	List<Book> findByCategory(String category);
	Book findByIsbn(String isbn);
	
	
	//BookRecord service methods
	List<BookRecordDTO> getAllBookRecords();
	List<BookRecordDTO> getBookRecordsByUser(long enrollmentId);
	List<BookRecordDTO> getBookRecordsByIsbn(String isbn);
	List<BookRecordDTO> getIssuedBooks();
	List<BookRecordDTO> getReturnedBooks();
	BookRecordDTO getByIdAndIsbn(long enrollmentId, String isbn);
	BookRecordDTO issueBook(BookRecordDTO bookRecordDTO);
	BookRecordDTO issueBookByIsbn(String isbn);
	BookRecordDTO returnBook(BookRecordDTO bookRecordDTO);
	BookRecordDTO returnBookByIsbn(String isbn);
}
