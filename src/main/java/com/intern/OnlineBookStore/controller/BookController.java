package com.intern.OnlineBookStore.controller;

import com.intern.OnlineBookStore.dto.BookDto;
import com.intern.OnlineBookStore.util.ResponseWrapper;
import com.intern.OnlineBookStore.model.Book;
import com.intern.OnlineBookStore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;
    //Auto Wired Constructor
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    private ResponseEntity<ResponseWrapper> getAllBooks() {
        List<BookDto> book = bookService.getAllBooks();
        ResponseWrapper response = new ResponseWrapper();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Book Collected successfully");
        response.setResponse(book);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/book/{bookId}")
    private ResponseEntity<ResponseWrapper> getBookById(@PathVariable("bookId") int bookId) {

        BookDto book = bookService.getBookById(bookId);
        if (book != null){
            ResponseWrapper response = new ResponseWrapper();
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Book Collected successfully by ID");
            response.setResponse(bookService.getBookById(bookId));
            return ResponseEntity.ok(response);
        }else {
            ResponseWrapper response = new ResponseWrapper();
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("Book not found...Please Check Again");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping("/books")
    private ResponseEntity<ResponseWrapper> addBook(@RequestBody Book book){
        //bookService.addBook(book);
        ResponseWrapper response = new ResponseWrapper();
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMessage("Successfully Added where Book Id: " + book.getBookId() );
        response.setResponse(bookService.addBook(book));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/book/{bookId}")
    private ResponseEntity<ResponseWrapper> updateBook(@PathVariable ("bookId") int bookId, @RequestBody Book book){
        Book updatedBook = bookService.updateBook(bookId,book);
        if (updatedBook != null){
            ResponseWrapper response = new ResponseWrapper();
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Book updated successfully");
            response.setResponse(book);
            return ResponseEntity.ok(response);
        } else {
            ResponseWrapper response = new ResponseWrapper();
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("Book not found...Check Again");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/book/{bookId}")
    private ResponseEntity<ResponseWrapper> deleteBook(@PathVariable("bookId")int bookId){
        bookService.deleteBook(bookId);
        ResponseWrapper response = new ResponseWrapper();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("User deleted successfully");
        return ResponseEntity.ok(response);
    }
}

