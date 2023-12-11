package com.intern.OnlineBookStore.controller;

import com.intern.OnlineBookStore.dto.BookDto;
import com.intern.OnlineBookStore.dto.BookResDto;
import com.intern.OnlineBookStore.dto.ReviewDto;
import com.intern.OnlineBookStore.service.ReviewService;
import com.intern.OnlineBookStore.util.ResponseWrapper;
import com.intern.OnlineBookStore.model.Book;
import com.intern.OnlineBookStore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class BookController {

    @Autowired
    private BookService bookService;

    private final ReviewService reviewService;
    //Auto Wired Constructor


    public BookController(BookService bookService, ReviewService reviewService) {
        this.bookService = bookService;
        this.reviewService = reviewService;
    }

    @GetMapping("/books")
    private ResponseEntity<ResponseWrapper> getAllBooks(@RequestParam(name = "page" ,defaultValue = "1") int page,
                                                        @RequestParam(name = "query", defaultValue = "", required = false) String title,
                                                        @RequestParam(name = "query", defaultValue = "", required = false) String author,
                                                        @RequestParam(name = "query", defaultValue = "", required = false) String genre

                                                        ) {
        Page<BookDto> booksPage = bookService.getAllBooks(page,  title,  author,  genre);

        ResponseWrapper response = new ResponseWrapper();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Book Collected successfully");
        response.setSuccess(true);
        response.setTotalPage(booksPage.getTotalPages());
        response.setResponse(booksPage.getContent());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/view/books")
    private ResponseEntity<ResponseWrapper> viewAllBooks() {
        List<BookDto> book = bookService.viewAllBooks();
        ResponseWrapper response = new ResponseWrapper();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Book Collected successfully");
        response.setResponse(book);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/book/{bookId}")
    private ResponseEntity<ResponseWrapper> getBookById(@PathVariable("bookId") int bookId) {

        Book book = bookService.getBookById(bookId);
        if (book != null){
            ResponseWrapper response = new ResponseWrapper();
            Map<String, Object> rating = reviewService.getSingleBookRating(book.getBookId());
            List<ReviewDto> reviews = reviewService.getAllReviewsByBook(book.getBookId());
            float overallRating = Float.parseFloat(rating.get("overall_rating").toString());
            long numRatings = Long.parseLong(rating.get("num_reviews").toString());
            BookResDto bookResDto = new BookResDto(book, overallRating, numRatings);
            bookResDto.setReviews(reviews);
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Book Collected successfully by ID");
            response.setResponse(bookResDto);
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

