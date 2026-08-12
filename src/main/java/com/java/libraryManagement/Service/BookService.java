package com.java.libraryManagement.Service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.libraryManagement.DTOS.BookDTO;
import com.java.libraryManagement.Entity.Book;
import com.java.libraryManagement.Repository.BookRepository;

@Service
public class BookService {
	
	private final BookRepository bookRepository;
	
	@Autowired
	public BookService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	public List<Book> getAll() {
		return bookRepository.findAll();
	}

	public Book getBookById(Long id) {
		// TODO Auto-generated method stub
		Book book = bookRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Book with this id "+ id + "is not found"));
		return book;
	}

	public Book addBook(BookDTO bookDTO) {
		// TODO Auto-generated method stub
		Book book = new Book();
		book.setAuthor(bookDTO.getAuthor());
		book.setTitle(bookDTO.getTitle());
		book.setIsbn(bookDTO.getIsbn());
		book.setQuantity(bookDTO.getQuantity());
		book.setIsAvailable(bookDTO.getIsAvailable());
		return bookRepository.save(book);
	}

	public Book updateBook(Long id, BookDTO bookDTO) {
		Book book = bookRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Book with this id "+ id + "is not found"));
		book.setAuthor(bookDTO.getAuthor());
		book.setTitle(bookDTO.getTitle());
		book.setIsbn(bookDTO.getIsbn());
		book.setQuantity(bookDTO.getQuantity());
		book.setIsAvailable(bookDTO.getIsAvailable());
		return bookRepository.save(book);
	}

	public void deleteBook(Long id) {
		// TODO Auto-generated method stub
		Book book = bookRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Book with this id "+ id + "is not found"));
		bookRepository.delete(book);
	}
	
	
	
	
	
	
	

}
