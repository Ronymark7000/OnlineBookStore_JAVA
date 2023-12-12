package com.intern.OnlineBookStore.service;

import com.intern.OnlineBookStore.dto.BookDto;
import com.intern.OnlineBookStore.model.Book;
import com.intern.OnlineBookStore.repository.BookRepo;
import com.intern.OnlineBookStore.util.CustomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepo bookRepo;
    int size = 8;

    public Page<BookDto> getAllBooks(int page, String title, String author, String genre) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Book> booksPage = bookRepo.findAllByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrGenreContainingIgnoreCase(title, author, genre, pageable);

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
        return CustomMapper.mapBookDto(book);
    }

    public Book getBookById(Integer bookId) {
//        Optional<Book> optionalBook = bookRepo.findById(bookId);
//        return optionalBook.get();
        return bookRepo.findById(bookId).orElse(null);
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
//            Book savedBook = bookRepo.save(existingBook);
            return bookRepo.save(existingBook);
        } else {
            // Handle the scenario when the user with the given ID is not found
            return null;
        }
    }

    public Book deleteBook(Integer bookId) {
        Optional<Book> optionalBook = bookRepo.findById(bookId);
        if (optionalBook.isPresent()) {
            bookRepo.deleteById(bookId);
        }
        return null;
    }

}
