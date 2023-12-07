package com.intern.OnlineBookStore.dto;

import com.intern.OnlineBookStore.model.Book;

import java.util.List;

public class BookResDto {

    private long bookId;
    private String title;

    private String author;
    private String genre;
    private double price;
    private boolean availability;
    private double overallRating;
    private long numberOfReviews;

    private List<ReviewDto> reviews;
    public BookResDto() {
    }

    public BookResDto(Book book, double overallRating, long numberOfReviews) {
        this.bookId = book.getBookId();
        this.title = book.getTitle();

        this.author = book.getAuthor();
        this.genre = book.getGenre();
        this.price = book.getPrice();
        this.availability = book.isAvailability();
        this.overallRating = overallRating;
        this.numberOfReviews = numberOfReviews;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public double getOverallRating() {
        return overallRating;
    }

    public void setOverallRating(double overallRating) {
        this.overallRating = overallRating;
    }

    public long getNumberOfReviews() {
        return numberOfReviews;
    }

    public void setNumberOfReviews(long numberOfReviews) {
        this.numberOfReviews = numberOfReviews;
    }

    public List<ReviewDto> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewDto> reviews) {
        this.reviews = reviews;
    }
}