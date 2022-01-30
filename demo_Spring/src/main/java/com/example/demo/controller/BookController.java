package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.BookService;
import com.example.demo.datamodel.Book;

@RequestMapping("/bookStore")
@RestController
@CrossOrigin(origins="http://localhost:4200")
public class BookController {

	@Autowired
	BookService bookSer;
	
	@GetMapping
	public ResponseEntity<List<Book>> getAllbooks() {
		
		List<Book> books = new ArrayList<Book> ();
		books = bookSer.findBooks();
		
		return new ResponseEntity<>(books, HttpStatus.OK);	
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Book> getBookById(@PathVariable int id) {
		
		return new ResponseEntity<>(bookSer.fetchBook(id).get(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Book> addBook(@RequestBody Book book) {
		
		bookSer.addBook(book);
		
		return new ResponseEntity<>(book, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Book> removeBookById(@PathVariable int id) {
		
		Book book = bookSer.fetchBook(id).get();
		
		if (bookSer.deleteBook(id)) {
			return new ResponseEntity<>(book, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(book, HttpStatus.BAD_REQUEST);
		}	
	}
	
	@GetMapping("/oldest")
	public ResponseEntity<Book> getOldestBook() {
		
		List<Book> books = new ArrayList<Book> ();
		books = bookSer.findBooks();
		
		int oldest = books.get(0).getYear();
		int oldestID = 0;
		
		for(int i = 1; i < books.size(); i++) {
			if (oldest > books.get(i).getYear()) {
				oldest = books.get(i).getYear();
				oldestID = i;
			}
		}
		
		return new ResponseEntity<>(bookSer.fetchBook(oldestID+1).get(), HttpStatus.OK);
	}
	
	@GetMapping("/latest")
	public ResponseEntity<Book> getLatestBook() {
		
		List<Book> books = new ArrayList<Book> ();
		books = bookSer.findBooks();
		
		int latest = books.get(0).getYear();
		int latestID = 0;
		
		for(int i = 1; i < books.size(); i++) {
			if (latest < books.get(i).getYear()) {
				latest = books.get(i).getYear();
				latestID = i;
			}
		}
		
		return new ResponseEntity<>(bookSer.fetchBook(latestID+1).get(), HttpStatus.OK);
	}
	
}
