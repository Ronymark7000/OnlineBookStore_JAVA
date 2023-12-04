package com.intern.OnlineBookStore.service;

import com.intern.OnlineBookStore.dto.BookDto;
import com.intern.OnlineBookStore.model.Book;
import com.intern.OnlineBookStore.repository.BookRepo;
import com.intern.OnlineBookStore.util.CustomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepo bookRepo;
    int size = 8;
    //get all books
         public Page<BookDto> getAllBooks(int page) {
            Pageable pageable = PageRequest.of(page-1, size);
            Page<Book> booksPage = bookRepo.findAll(pageable);

             return booksPage.map(book -> new BookDto(
                     book.getBookId(),
                     book.getTitle(),
                     book.getGenre(),
                     book.getAuthor(),
                     book.getPrice(),
                     book.isAvailability()
             ));
        }

    public List<BookDto> viewAllBooks() {
        List<Book> book = bookRepo.findAll();
        List<BookDto> bookDtos = CustomMapper.mapBookDto(book);
        return bookDtos;
    }

    public BookDto getBookById(Integer bookId)
    {
        Optional<Book> optionalBook = bookRepo.findById(bookId);
        Book book = optionalBook.get();
        BookDto bookDto = new BookDto(book.getBookId(),book.getTitle(),book.getGenre(),book.getAuthor(),book.getPrice(),book.isAvailability());
        return bookDto;
    }


    public Book addBook(Book book) {
        return bookRepo.save(book);
    }

    public Book updateBook(int id, Book updatedBook) {
        Optional<Book> optionalBook = bookRepo.findById(id);
        if (optionalBook.isPresent()) {
            Book existingBook = optionalBook.get();

            // Update properties of the existing user with values from the updated user
            existingBook.setTitle(updatedBook.getTitle());
            existingBook.setAuthor(updatedBook.getAuthor());
            existingBook.setGenre(updatedBook.getGenre());
            existingBook.setPrice(updatedBook.getPrice());
            existingBook.setAvailability(updatedBook.isAvailability());

            // Save the updated user back to the database
            Book savedBook = bookRepo.save(existingBook);
            return savedBook;
        } else {
            // Handle the scenario when the user with the given ID is not found
            return  null;
        }
    }

    public Book deleteBook(Integer bookId) {
        Optional<Book> optionalBook = bookRepo.findById(bookId);
        if (optionalBook.isPresent()) {
            bookRepo.deleteById(bookId);
        }
        return  null;
    }

}
