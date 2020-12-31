package com.practice.librarymanagement;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.practice.librarymanagement.model.Book;
import com.practice.librarymanagement.model.User;
import com.practice.librarymanagement.model.UserRole;
import com.practice.librarymanagement.repository.BookDao;
import com.practice.librarymanagement.repository.UserDao;

@Component
public class StartUpRunner implements ApplicationRunner {

	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	UserDao userDao;
	@Autowired
	BookDao bookDao;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		User admin = new User(16103494, "admin" , passwordEncoder.encode("admin"), UserRole.ADMIN);
		User student = new User(16103495, "student", passwordEncoder.encode("student"), UserRole.NORMAL);
		userDao.save(admin);
		userDao.save(student);
		
		populateBooks();
	}
	
	@Transactional
	private void populateBooks() throws ParseException {
		
		Date d= new SimpleDateFormat("yyyy-mm-dd").parse("2012-10-12");
		List<Book> books = new ArrayList<Book>();
		books.add( new Book("Fellowship Of The Ring", "isbn_lotr1", 3, new java.sql.Date(2020, 12, 21),
				"Allen & Ulwin", "science-fiction", 1)  );
		books.add( new Book("Avengers Assemble", "isb_avengers1", 2, new java.sql.Date(2020, 12, 22),
				"Marvel Studios", "science-fiction", 2) );
		books.add( new Book("Justice League War", "isbn_jleague1", 5, new java.sql.Date(2020, 12, 23), 
				"DC Comics", "science-fiction", 1) );
		
		books.forEach( book->{
			bookDao.save(book);
		});
	}
	

}
