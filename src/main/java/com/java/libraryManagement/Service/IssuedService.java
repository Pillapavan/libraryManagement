package com.java.libraryManagement.Service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.java.libraryManagement.Entity.Book;
import com.java.libraryManagement.Entity.IssuedDate;
import com.java.libraryManagement.Entity.User;
import com.java.libraryManagement.Repository.BookRepository;
import com.java.libraryManagement.Repository.IssuedRecordRepository;
import com.java.libraryManagement.Repository.UserRepository;

@Service
public class IssuedService {
	
	private final IssuedRecordRepository issuedRecordRepository;
	private final BookRepository bookRepository;
	private final UserRepository userRepository;
	
	@Autowired
	public IssuedService(IssuedRecordRepository issuedRecordRepository,BookRepository bookRepository,UserRepository userRepository) {
		this.bookRepository = bookRepository;
		this.issuedRecordRepository = issuedRecordRepository;
		this.userRepository = userRepository;
	}

	public IssuedDate issueTheBook(Long bookId) {
		Book book = bookRepository.findById(bookId)
								  .orElseThrow(() -> new  RuntimeException("Book not found"));
		
		if(book.getQuantity()<=0 || !book.getIsAvailable()) {
			throw new RuntimeException("book is not available");
		}
		
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		
		User user = userRepository.findByUserName(name)
								  .orElseThrow(() -> new RuntimeException("User is not found"));
		IssuedDate issuedDate = new IssuedDate();
		issuedDate.setIssueDate(LocalDate.now());
		issuedDate.setDueDate(LocalDate.now().plusDays(14));
		issuedDate.setIsReturned(false);
		issuedDate.setBook(book);
		issuedDate.setUser(user);
		
		book.setQuantity(book.getQuantity()-1);
		
		if(book.getQuantity()==0) {
			book.setIsAvailable(false);
		}
		
		bookRepository.save(book);
		return issuedRecordRepository.save(issuedDate);
	}

	
	
	public IssuedDate returnTheBook(Long issuedRecordId) {
		IssuedDate issuedDate = issuedRecordRepository.findById(issuedRecordId)
				                                      .orElseThrow(() -> new RuntimeException("No issued Record"));
		if(issuedDate.getIsReturned()) {
			throw new RuntimeException("book already Returned");
		}
		
		Book book = issuedDate.getBook();
		
		book.setQuantity(book.getQuantity()+1);
		book.setIsAvailable(true);
		
		
		issuedDate.setReturnDate(LocalDate.now());
		issuedDate.setIsReturned(true);
		
		bookRepository.save(book);
		return issuedRecordRepository.save(issuedDate);
		
	}
	
	
	

}
