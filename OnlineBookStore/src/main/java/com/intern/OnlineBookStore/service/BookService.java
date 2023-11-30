package com.intern.OnlineBookStore.service;

import com.intern.OnlineBookStore.dto.BookDto;
import com.intern.OnlineBookStore.model.Book;

import java.util.List;

public interface BookService {

    List<BookDto> getAllBooks();
    BookDto getBookById(Integer bookId);
    Book addBook(Book book);
    Book updateBook(int id, Book updatedBook);
    Book deleteBook(Integer bookId);
}
