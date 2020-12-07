package com.practice.librarymanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.practice.librarymanagement.model.Book;

@Repository
public interface BookDao extends JpaRepository<Book, Long> {

	Book findByIsbn(String isbn);
	List<Book> findByAuthor(String author);
	List<Book> findByPublisher(String publisher);
	List<Book> findByCategory(String category);
	void deleteByIsbn(String isbn);
//	List<Book> findByPublication_date(Date publication_date);
}
