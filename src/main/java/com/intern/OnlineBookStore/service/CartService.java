package com.intern.OnlineBookStore.service;

import com.intern.OnlineBookStore.dto.CartDto;
import com.intern.OnlineBookStore.model.Cart;
import com.intern.OnlineBookStore.util.ResponseWrapper;

import java.util.List;

public interface CartService {
     List<CartDto> getBooksFromCart(long userId);

     Cart findSingleCartByUser(long userId, long bookId);

     CartDto addBookToCart(Cart cart);

     ResponseWrapper updateCart(long cartId, int quantity, long userId);

     boolean removeBookFromCart(long cartId, long userId);


}
