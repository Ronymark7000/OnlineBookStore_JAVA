package com.intern.OnlineBookStore.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "OnlineBook")
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookId;

    @Column(nullable = false, length = 30)
    private String title;

    @Column(nullable = false, length = 50)
    private String author;

    @Column(nullable = false, length = 15)
    private String genre;

    @Column(nullable = false, length = 10)
    private double price;

    @Column(nullable = false, length = 10)
    private boolean availability;

}
