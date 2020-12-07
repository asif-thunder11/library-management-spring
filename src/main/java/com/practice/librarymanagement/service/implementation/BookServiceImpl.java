package com.practice.librarymanagement.service.implementation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.practice.librarymanagement.LibraryContext;
import com.practice.librarymanagement.exception.RestApiException;
import com.practice.librarymanagement.model.Book;
import com.practice.librarymanagement.model.BookRecord;
import com.practice.librarymanagement.model.BookRecord.RECORD_TYPE;
import com.practice.librarymanagement.model.BookRecordDTO;
import com.practice.librarymanagement.model.User;
import com.practice.librarymanagement.repository.BookDao;
import com.practice.librarymanagement.repository.BookRecordDao;
import com.practice.librarymanagement.repository.UserDao;
import com.practice.librarymanagement.service.BookService;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	UserDao userdao;
	@Autowired
	BookDao bookDao;
	@Autowired
	BookRecordDao bookRecordDao;
	@Autowired
	LibraryContext context;

	@Override
	public Book addBook(Book book) {
		Book resultBook = bookDao.findByIsbn(book.getIsbn());
		if (resultBook != null) {
			RestApiException rex = new RestApiException();
			rex.getRestApiError().setStatus(HttpStatus.BAD_REQUEST).setErrorMsg("Book Already Exist")
					.setErrorDescription("Please Verify the ISBN of the book.");
			throw rex;
		} else {
			if ((resultBook = bookDao.save(book)) == null) {
				throw new RestApiException("Book Not Added", HttpStatus.INTERNAL_SERVER_ERROR);
			} else {
				return resultBook;
			}
		}
	}

	@Override
	public List<Book> saveAll(List<Book> books) {
		return bookDao.saveAll(books);
	}

	@Override
	public Optional<Book> findById(long id) {
		return bookDao.findById(id);
	}

	@Override
	public List<Book> findAll() {

		List<Book> books = bookDao.findAll();
		return books;

	}

	@Override
	public List<Book> findAllById(List<Long> ids) {
		return bookDao.findAllById(ids);
	}

	@Override
	public long count() {
		return bookDao.count();
	}

	@Override
	@Transactional
	public void deleteByIsbn(String isbn) {
		bookDao.deleteByIsbn(isbn);
		return;
	}

	@Override
	public void delete(Book book) {
		bookDao.delete(book);
	}

	@Override
	public void deleteAll(List<Book> books) {
		bookDao.deleteAll(books);
	}

	@Override
	public void deleteAll() {
		bookDao.deleteAll();
	}

	@Override
	public List<Book> findByAuthor(String author) {
		return bookDao.findByAuthor(author);
	}

	@Override
	public List<Book> findByPublisher(String publisher) {
		return bookDao.findByPublisher(publisher);
	}

	@Override
	public List<Book> findByCategory(String category) {
		return bookDao.findByCategory(category);
	}

	@Override
	public Book findByIsbn(String isbn) {
		return bookDao.findByIsbn(isbn);
	}

	@Override
	public List<BookRecordDTO> getAllBookRecords() {
		List<BookRecord> bookRecords = bookRecordDao.findAll();
		List<BookRecordDTO> dtos = new ArrayList<BookRecordDTO>();
		bookRecords.forEach((dto) -> dtos.add(mapper_BookRecordDto(dto, dto.getRecordType())));
		return dtos;
	
	}

	@Override
	public List<BookRecordDTO> getBookRecordsByUser(long enrollmentId) {
		List<BookRecord> records = bookRecordDao.findByUser_enrollmentId(enrollmentId);
		
		if(records != null) {
			List<BookRecordDTO> dtos = new ArrayList<BookRecordDTO>();
			records.forEach( bookRecord -> dtos.add(  mapper_BookRecordDto(bookRecord, bookRecord.getRecordType())  ) );
			return dtos;
		}
		else {
			return null;
		}
		
	}

	@Override
	public List<BookRecordDTO> getBookRecordsByIsbn(String isbn) {
		List<BookRecord> records = bookRecordDao.findByBook_isbn(isbn);
		
		if(records != null) {
			List<BookRecordDTO> dtos = new ArrayList<BookRecordDTO>();
			records.forEach( bookRecord -> dtos.add(  mapper_BookRecordDto(bookRecord, bookRecord.getRecordType())  ) );
			return dtos;
		} else {
			return null;
		}	
	}

	@Override
	public List<BookRecordDTO> getIssuedBooks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BookRecordDTO> getReturnedBooks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	// @Query("INSERT INTO book_records(user_id, book_id) VALUES (:enrollmentId,
	// :book_isb);")
	public BookRecordDTO issueBook(BookRecordDTO bookRecordDTO) {
		Book book = bookDao.findByIsbn(bookRecordDTO.getIsbn());
		Optional<User> user = userdao.findByEnrollmentId(bookRecordDTO.getEnrollmentId());
		BookRecord bookRecord = new BookRecord(RECORD_TYPE.ISSUE, book, user.get(), new Date());

		if (book == null || user.isEmpty() ) {
			throw new RestApiException("Invalid Details", HttpStatus.BAD_REQUEST);
		} else {
			BookRecordDTO dto = mapper_BookRecordDto(bookRecordDao.save(bookRecord), RECORD_TYPE.ISSUE);
			return dto;
		}
	}
	
	@Override
	@Transactional
	public BookRecordDTO issueBookByIsbn(String isbn) {
		
		System.out.println("Issuing book : "+isbn+" for "+context.getUsername());
		BookRecord bkrc = bookRecordDao.findByUser_enrollmentIdAndBook_isbn( Long.parseLong(context.getUsername()) , isbn);
		//if null, or if record present should be RETURN
		if(bkrc == null) {	
			
			Book book = bookDao.findByIsbn(isbn);
			Optional<User> user = userdao.findByEnrollmentId( Long.parseLong(context.getUsername()) );
			BookRecord br = new BookRecord(RECORD_TYPE.ISSUE, book, user.get(), new Date());
			if(book!=null && user!=null) {
				System.out.println("Creating Book Record-Issue");
				bookRecordDao.save(br);
				return mapper_BookRecordDto(br, RECORD_TYPE.ISSUE);
			}
//			System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
			
			
		} else if ( bkrc.getRecordType()==RECORD_TYPE.RETURN ) {
			System.out.println("Re Issuing");
			bkrc.setRecordType(RECORD_TYPE.ISSUE);
			bookRecordDao.save(bkrc);
			return mapper_BookRecordDto(bkrc, RECORD_TYPE.ISSUE);
		}
		else if( bkrc.getRecordType()==RECORD_TYPE.ISSUE ){
			System.out.println("Already Issued");
			throw new RestApiException("You have already issued the book.", HttpStatus.BAD_REQUEST);			
		}
		
		return null;
//		System.out.println("Issuing book : "+isbn+" for "+context.getUsername());
//		Book book = bookDao.findByIsbn(isbn);
//		Optional<User> user = userdao.findByEnrollmentId( Long.parseLong(context.getUsername()) );
//		BookRecord br = new BookRecord(RECORD_TYPE.ISSUE, book, user.get(), new Date());
//		if(book!=null && user!=null) {
//			bookRecordDao.save(br);
//		}
////		System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
//		return mapper_BookRecordDto(br, RECORD_TYPE.ISSUE);
	}

	@Override
	@Transactional
	public BookRecordDTO returnBook(BookRecordDTO bookRecordDTO) {
		
		BookRecord record = bookRecordDao.findByUser_enrollmentIdAndBook_isbn(bookRecordDTO.getEnrollmentId(), bookRecordDTO.getIsbn());
		if(record==null) {
			throw new RestApiException("Book Is Not Issued Yet", HttpStatus.BAD_REQUEST);
		}
		
		Book book = bookDao.findByIsbn(bookRecordDTO.getIsbn());
		Optional<User> user = userdao.findByEnrollmentId(bookRecordDTO.getEnrollmentId());
		BookRecord bookRecord = new BookRecord(RECORD_TYPE.RETURN, book, user.get(), new Date());
		
		if(book==null || user.isEmpty()) {
			throw new RestApiException("Invalid Details", HttpStatus.BAD_REQUEST);
		} else {
			//saves and returns BookRecord which will be converted to dto.
			BookRecordDTO dto = mapper_BookRecordDto(bookRecordDao.save(bookRecord), RECORD_TYPE.RETURN);
			return dto;
		}
	}
	
	@Override
	@Transactional
	public BookRecordDTO returnBookByIsbn(String isbn) {
		System.out.println("Returning book : "+isbn+" for "+context.getUsername());
		BookRecord bkrc = bookRecordDao.findByUser_enrollmentIdAndBook_isbn( Long.parseLong(context.getUsername()) , isbn);
		if(bkrc != null && bkrc.getRecordType()==RECORD_TYPE.ISSUE) {
//			BookRecord bk = new BookRecord(RECORD_TYPE.RETURN, bkrc.getBook(), bkrc.getUser(), bkrc.getDate());
			bkrc.setRecordType(RECORD_TYPE.RETURN);
//			bookRecordDao.save(bk);
			return mapper_BookRecordDto(bkrc, RECORD_TYPE.RETURN);
		} else {
			throw new RestApiException("You have not issued the book yet", HttpStatus.BAD_REQUEST);			
		}
//		return mapper_BookRecordDto(bkrc, RECORD_TYPE.RETURN);

	}

	
	
	@Override
	public BookRecordDTO getByIdAndIsbn(long enrollmentId, String isbn) {
		// TODO Auto-generated method stub
		return null;
	}

	public BookRecordDTO mapper_BookRecordDto(BookRecord bookRecord, RECORD_TYPE recordType) {
		return new BookRecordDTO(bookRecord.getUser().getEnrollmentId(), bookRecord.getBook().getTitle(),
				bookRecord.getBook().getIsbn(), recordType, bookRecord.getDate());
	}
}
