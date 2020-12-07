package com.practice.librarymanagement.model;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.practice.librarymanagement.model.BookRecord.RECORD_TYPE;

public class BookRecordDTO {

	long enrollmentId;
	String isbn;
	String book_title;
	RECORD_TYPE recordType;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date date;
	
	public BookRecordDTO(long enrollmentId, String book_title, String isbn, RECORD_TYPE recordType, Date date) {
		super();
		this.enrollmentId = enrollmentId;
		this.book_title = book_title;
		this.isbn = isbn;
		this.recordType = recordType;
		this.date = date;
	}

	public long getEnrollmentId() {
		return enrollmentId;
	}

	public void setEnrollmentId(long enrollmentId) {
		this.enrollmentId = enrollmentId;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getBook_title() {
		return book_title;
	}

	public void setBook_title(String book_title) {
		this.book_title = book_title;
	}

	public RECORD_TYPE getRecordType() {
		return recordType;
	}

	public void setRecordType(RECORD_TYPE recordType) {
		this.recordType = recordType;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	
	
	
}
