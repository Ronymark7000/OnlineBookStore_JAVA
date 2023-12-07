package com.intern.OnlineBookStore.controller;

import com.intern.OnlineBookStore.service.OrderService;
import com.intern.OnlineBookStore.util.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/admin/orders")
public class AdminOrderController {

    private final OrderService orderService;

    @Autowired
    public AdminOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping()
    public ResponseEntity<ResponseWrapper> getAllOrders(@RequestParam(name = "sort", defaultValue = "date") String sort,
                                                        @RequestParam(name = "order", defaultValue = "1") int order) {
        ResponseWrapper apiResponse = new ResponseWrapper(true, 200, "All orders fetched", orderService.getAllOrders(sort, order));
        return ResponseEntity.status(200).body(apiResponse);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<ResponseWrapper> updateOrderStatus(@PathVariable long orderId, @RequestBody String status) {
        String statusStr = status.replaceAll("\"", "");
        return orderService.updateOrderStatus(orderId, statusStr);
    }
}
