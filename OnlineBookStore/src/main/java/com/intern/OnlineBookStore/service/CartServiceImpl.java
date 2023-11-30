package com.intern.OnlineBookStore.service;

import com.intern.OnlineBookStore.dto.CartDto;
import com.intern.OnlineBookStore.model.Cart;
import com.intern.OnlineBookStore.repository.CartRepo;
import com.intern.OnlineBookStore.util.CustomException;
import com.intern.OnlineBookStore.util.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    public CartServiceImpl(CartRepo cartRepo) {
        this.cartRepo = cartRepo;
    }

    public List<CartDto> getBooksFromCart(long userId) {
        List<Cart> cartList = cartRepo.findAllBooksByUser(userId);
        List<CartDto> cartResDtoList = new ArrayList<>();
        for (Cart cart : cartList) {
            cartResDtoList.add(new CartDto(cart.getCartId(), cart.getBook(), cart.getQuantity()));
        }
        return cartResDtoList;
    }

    public Cart findSingleCartByUser(long userId, long bookId){
        return  cartRepo.findSingleCartByUser(userId, bookId);
    }

    public CartDto addBookToCart(Cart cart) {
        try {
            if (cart != null) {
                Cart newCart = cartRepo.save(cart);
                return new CartDto(newCart.getCartId(), newCart.getBook(), newCart.getQuantity());
            } else {
                throw new IllegalArgumentException("Invalid Book Id Entered");
            }
        }
        catch(Exception e){
//            e.printStackTrace();
            throw new CustomException("Falied to add book to cart");
        }
    }




    public ResponseWrapper updateCart(long cartId, int quantity, long userId){
        Cart prevCart = cartRepo.findById(cartId).orElse(null);

        if (prevCart != null && prevCart.getUser().getUserId() == userId) {

            prevCart.setQuantity(quantity);
            Cart cart = cartRepo.save(prevCart);
            CartDto cartDto = new CartDto(cart.getCartId(), cart.getBook(), cart.getQuantity());

            return new ResponseWrapper(true, 200, "Cart updated successfully", cartDto);
        } else {
            return new ResponseWrapper(false, 400, "Error updating cart", null);
        }
    }


    public boolean removeBookFromCart(long cartId, long userId) {
        try {
            Cart cart = cartRepo.findById(cartId).orElse(null);
            if (cart != null && cart.getUser().getUserId() == userId) {
                cartRepo.deleteById(cartId);
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            return false;
        }
    }
}
