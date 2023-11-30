package com.intern.OnlineBookStore.dto;

import com.intern.OnlineBookStore.model.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SecondaryRow;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
    private long cartId;

    private Book book;

    private int quantity;

}
