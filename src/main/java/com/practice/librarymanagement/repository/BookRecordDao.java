package com.practice.librarymanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.practice.librarymanagement.model.BookRecord;
import com.practice.librarymanagement.model.BookRecord.RECORD_TYPE;

@Repository
public interface BookRecordDao extends JpaRepository<BookRecord, Long> {
	
	
	//more
	List<BookRecord> findByRecordType(RECORD_TYPE recordType);
	List<BookRecord> findByBook_isbn(String isbn); 	//_ means child property
	List<BookRecord> findByUser_enrollmentId(long enrollmentId);
	BookRecord findByUser_enrollmentIdAndBook_isbn(long enrollmentId, String isbn);
	
}
