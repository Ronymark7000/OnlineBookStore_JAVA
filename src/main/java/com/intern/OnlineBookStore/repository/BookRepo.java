package com.intern.OnlineBookStore.repository;

import com.intern.OnlineBookStore.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepo extends JpaRepository<Book, Integer> {
    Page<Book> findAllByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrGenreContainingIgnoreCase(String title, String author, String genre,Pageable pageable);
}
