package com.intern.OnlineBookStore.controller;

import com.intern.OnlineBookStore.dto.CartDto;
import com.intern.OnlineBookStore.model.Book;
import com.intern.OnlineBookStore.model.Cart;
import com.intern.OnlineBookStore.model.User;
import com.intern.OnlineBookStore.service.CartService;
import com.intern.OnlineBookStore.util.CustomException;
import com.intern.OnlineBookStore.util.ResponseWrapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping()
    public ResponseWrapper getAllFromCart(HttpServletRequest request) {
        int userId = (int) request.getAttribute("userId");
        return new ResponseWrapper(true, 200, "Cart fetched successfully", cartService.getBooksFromCart(userId));
    }

    @PostMapping("/add")
    public ResponseWrapper addToCart(@RequestBody int bookId, HttpServletRequest request) {

        int decodedUserId = (int) request.getAttribute("userId");

        Cart existCart = cartService.findSingleCartByUser(decodedUserId, bookId);
        if(existCart == null){
            Cart newCart = new Cart();
            Book book = new Book();
            User user = new User();

            int userId = (int) request.getAttribute("userId");
            book.setBookId(bookId);
            user.setUserId(userId);

            newCart.setQuantity(1);
            newCart.setBook(book);
            newCart.setUser(user);
            return new ResponseWrapper(true, 200, "Book added to cart", cartService.addBookToCart(newCart));
        }else  {
            return new ResponseWrapper(false, 400, "Book is already in the cart", null);
        }
    }

    @PutMapping("/update/{cartId}")
    public ResponseWrapper updateCart(@PathVariable long cartId, @RequestBody int quantity, HttpServletRequest request) {
        try {
            Integer userId = (Integer) request.getAttribute("userId");
            ResponseWrapper newCartDto =cartService.updateCart(cartId, quantity, userId);
            ResponseWrapper response = new ResponseWrapper();
            response.setStatusCode(200);
            response.setMessage("Cart updated successfully");
            response.setResponse(newCartDto);
            return response;

        }
        catch(CustomException e){
            throw new CustomException(e.getMessage());
        }
    }


    @DeleteMapping("/{cartId}")
    public ResponseWrapper deleteBookFromCart(@PathVariable long cartId, HttpServletRequest request)  {
        int userId = (Integer) request.getAttribute("userId");
        boolean success = cartService.removeBookFromCart(cartId, userId);

        if(success){
            return new ResponseWrapper(true, 200, "Book removed from cart", null);
        }else{
            return new ResponseWrapper(false, 400, "Error removing book from cart", null);
        }
    }
}
