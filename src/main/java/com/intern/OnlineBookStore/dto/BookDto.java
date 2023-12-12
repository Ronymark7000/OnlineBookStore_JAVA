package com.intern.OnlineBookStore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private Integer bookId;
    private String title;
    private String genre;
    private String author;
    private double price;
    private boolean availability;


}
