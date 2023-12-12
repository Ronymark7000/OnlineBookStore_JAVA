package com.intern.OnlineBookStore.service;

import com.intern.OnlineBookStore.model.Cart;
import com.intern.OnlineBookStore.model.Order;
import com.intern.OnlineBookStore.repository.CartRepo;
import com.intern.OnlineBookStore.repository.OrderRepo;
import com.intern.OnlineBookStore.util.CustomException;
import com.intern.OnlineBookStore.util.ResponseWrapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepo orderRepo;

    private final CartRepo cartRepo;

    @Autowired
    public OrderService(OrderRepo orderRepo, CartRepo cartRepo) {
        this.orderRepo = orderRepo;
        this.cartRepo = cartRepo;
    }

    public List<Order> getAllOrdersByUserId(Integer id) {
        return orderRepo.getOrdersByUser(id);
    }

    public List<Order> getOrdersByUser(long userId) {
        return orderRepo.getOrdersByUser(userId);
    }

    public List<Order> getAllOrders(String sortBy, int orderBy) {
        try {
            if (orderBy == 1) {
                return orderRepo.findAll(Sort.by(sortBy).ascending());
            } else {
                return orderRepo.findAll(Sort.by(sortBy).descending());
            }
        } catch (Exception exception) {
            throw new CustomException("Error fetching order");
        }
    }

    @Transactional
    public ResponseEntity<ResponseWrapper> placeOrder(int userId) {
        List<Cart> cartList = cartRepo.findAllBooksByUser(userId);

        if (cartList == null) {
            ResponseWrapper apiResponse = new ResponseWrapper(false, 400, "No item to place order", null);
            return ResponseEntity.status(400).body(apiResponse);
        } else {
            List<Order> orderList = new ArrayList<>();
            for (Cart cart : cartList) {
                Order order = new Order(cart);
                orderList.add(order);
            }
            try {
                orderRepo.saveAll(orderList);
                cartRepo.deleteAll(cartList);
                ResponseWrapper responseWrapper = new ResponseWrapper(true, 200, "Order Placed successfully", null);
                return ResponseEntity.status(200).body(responseWrapper);
            } catch (DataAccessException exception) {
                ResponseWrapper responseWrapper = new ResponseWrapper(false, 500, "Error placing order", null);
                return ResponseEntity.status(500).body(responseWrapper);
            }
        }
    }

    public ResponseEntity<ResponseWrapper> updateOrderStatus(long orderId, String status) {
        Order order = orderRepo.findById(orderId).orElse(null);

        if (order == null) {
            ResponseWrapper responseWrapper  = new ResponseWrapper(false, 400, "Can not find order at the moment", null);
            return ResponseEntity.status(400).body(responseWrapper);
        } else {
            order.setStatus(status);
            order.setUpdatedOn(LocalDateTime.now());
            orderRepo.save(order);
            ResponseWrapper apiResponse = new ResponseWrapper(true, 200, "Order updated successfully", null);
            return ResponseEntity.status(200).body(apiResponse);
        }
    }


}