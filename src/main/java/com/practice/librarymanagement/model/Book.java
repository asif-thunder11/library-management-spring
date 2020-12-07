package com.practice.librarymanagement.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.practice.librarymanagement.MyDateDeserializer;

@Entity(name = "book")
@Table(name = "books")
public class Book {

//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	private long id;
	
	@Id
	@JsonProperty("isbn")
	@Column(length = 20)
	private String isbn;
	
	private String title;

	private int noOfCopies;
	private int noOfCopiesAvailable;

	@Temporal(TemporalType.TIMESTAMP)
	//@DateTimeFormat(pattern = "yyyy-mm-dd hh:mm:ss" )
	@JsonFormat(pattern = "yyyy-mm-dd")
	//@JsonDeserialize(using = MyDateDeserializer.class)
	Date publicationDate;
 
	//String publicationDate;
	
	String publisher;

	private String author;

	private String category;

	private int rack;

	public Book() {
		super();
	}

	/**
	 * 
	 * @param title
	 * @param isbn
	 * @param noOfCopies
	 * @param publicationDate
	 * @param publisher
	 * @param category
	 * @param rack
	 */
	public Book(String title, String isbn, int noOfCopies, Date publicationDate, String publisher, String category,
			int rack) {
		this();
		this.title = title;
		this.isbn = isbn;
		this.noOfCopies = noOfCopies;
		this.publicationDate = publicationDate;
		this.publisher = publisher;
		this.category = category;
		this.rack = rack;
	}
	
//	public long getId() {
//		return id;
//	}
//
//	public void setId(long id) {
//		this.id = id;
//	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getRack() {
		return rack;
	}

	public void setRack(int rack) {
		this.rack = rack;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getNoOfCopiesAvailable() {
		return noOfCopiesAvailable;
	}

	public void setNoOfCopiesAvailable(int noOfCopiesAvailable) {
		this.noOfCopiesAvailable = noOfCopiesAvailable;
	}

	public int getNoOfCopies() {
		return noOfCopies;
	}

	public void setNoOfCopies(int noOfCopies) {
		this.noOfCopies = noOfCopies;
	}

}
