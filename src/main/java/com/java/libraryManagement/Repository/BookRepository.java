package com.java.libraryManagement.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.libraryManagement.Entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
