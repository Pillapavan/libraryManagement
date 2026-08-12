package com.java.libraryManagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.libraryManagement.Entity.IssuedDate;

@Repository
public interface IssuedRecordRepository extends JpaRepository<IssuedDate, Long>{

}
