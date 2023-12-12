package com.intern.OnlineBookStore.controller;

import com.intern.OnlineBookStore.model.Order;
import com.intern.OnlineBookStore.model.User;
import com.intern.OnlineBookStore.service.OrderService;
import com.intern.OnlineBookStore.util.ResponseWrapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/user-orders")
    public ResponseWrapper getAllOrdersByUserId(HttpServletRequest request){
        int userId = (int) request.getAttribute("userId");
        List<Order> orderList = orderService.getAllOrdersByUserId(userId);
        ResponseWrapper response = new ResponseWrapper();
        response.setStatusCode(200);
        response.setMessage("Order retrieved successfully");
        response.setSuccess(true);
        response.setResponse(orderList);
        return response;
    }

    @PostMapping()
    public ResponseEntity<ResponseWrapper> placeOrder(HttpServletRequest request) {
//        User decodedUser = (User) request.getAttribute("user");
        int userId = (int) request.getAttribute("userId");
        try {
            return orderService.placeOrder(userId);
        } catch (Exception exception) {
            ResponseWrapper responseWrapper = new ResponseWrapper(false, 500, "Error placing orders ", null);
            return ResponseEntity.status(500).body(responseWrapper);
        }
    }

    @GetMapping()
    public ResponseEntity<ResponseWrapper> getOrdersByUser(HttpServletRequest request) {
        int userId = (int) request.getAttribute("userId");
        List<Order> orderList = orderService.getOrdersByUser(userId);
        ResponseWrapper responseWrapper =  new ResponseWrapper(true, 200, "Orders fetched", orderList);
        return ResponseEntity.status(200).body(responseWrapper);
    }
}
