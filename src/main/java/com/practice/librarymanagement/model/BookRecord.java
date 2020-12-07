package com.practice.librarymanagement.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.practice.librarymanagement.MyDateDeserializer;

@Entity(name = "book_record")
@Table(name = "book_records")
public class BookRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private RECORD_TYPE recordType;

	// private int borrow_duration;

	@ManyToOne(targetEntity = Book.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "book_id")
	private Book book;

	@ManyToOne(targetEntity = User.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private User user;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date date;

//	public int getBorrow_duration() {
//		return borrow_duration;
//	}
//
//	public void setBorrow_duration(int borrow_duration) {
//		this.borrow_duration = borrow_duration;
//	}

	public BookRecord() {
		super();
	}

	public BookRecord(RECORD_TYPE record_type, Book book, User user, Date date) {
		this();
		this.recordType = record_type;
		this.book = book;
		this.user = user;
		this.date = date;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public RECORD_TYPE getRecordType() {
		return recordType;
	}

	public void setRecordType(RECORD_TYPE recordType) {
		this.recordType = recordType;
	}

	public enum RECORD_TYPE {
		ISSUE, RETURN
	}
}
