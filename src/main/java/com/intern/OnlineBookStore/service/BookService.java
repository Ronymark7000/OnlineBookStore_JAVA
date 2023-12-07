package com.intern.OnlineBookStore.service;

import com.intern.OnlineBookStore.dto.BookDto;
import com.intern.OnlineBookStore.model.Book;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BookService {

    Page<BookDto> getAllBooks(int page);
    List<BookDto> viewAllBooks();
    Book getBookById(Integer bookId);
    Book addBook(Book book);
    Book updateBook(int id, Book updatedBook);
    Book deleteBook(Integer bookId);
}
