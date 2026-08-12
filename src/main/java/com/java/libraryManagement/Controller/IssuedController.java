package com.java.libraryManagement.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.libraryManagement.Entity.IssuedDate;
import com.java.libraryManagement.Service.IssuedService;

@RestController
@RequestMapping("/issuedrecords")
public class IssuedController {
   
	private final IssuedService issuedService;
	
	public IssuedController(IssuedService issuedService) {
		this.issuedService = issuedService;
	}
	
	@PostMapping("/issuethebook/{bookId}")
	public ResponseEntity<IssuedDate> issueTheBook(@PathVariable Long bookId){
		return ResponseEntity.ok(issuedService.issueTheBook(bookId));
	}
	
	@PostMapping("/returnthebook/{issuedRecordId}")
	public ResponseEntity<IssuedDate> returnTheBook(@PathVariable Long issuedRecordId){
		return ResponseEntity.ok(issuedService.returnTheBook(issuedRecordId));
	}
}
