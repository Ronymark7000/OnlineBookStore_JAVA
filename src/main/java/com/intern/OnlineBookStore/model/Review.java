package com.intern.OnlineBookStore.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private long reviewId;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name="book_id",nullable = false)
    private Book book;

    @Column(name="rating")
    private int rating;

    @Column(name = "comment")
    private String comment;

    @Column(name = "date")
    private LocalDateTime date = LocalDateTime.now();


    public Review(User user, Book book, int rating, String comment) {
        this.user = user;
        this.book = book;
        this.rating = rating;
        this.comment = comment;
    }

    public Review() {

    }

    public long getReviewId() {
        return reviewId;
    }

    public void setReviewId(long review_id) {
        this.reviewId = review_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }


}
