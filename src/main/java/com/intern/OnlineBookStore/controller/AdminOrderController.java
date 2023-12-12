package com.intern.OnlineBookStore.controller;

import com.intern.OnlineBookStore.service.OrderService;
import com.intern.OnlineBookStore.util.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {

    private final OrderService orderService;

    @GetMapping()
    public ResponseEntity<ResponseWrapper> getAllOrders(@RequestParam(name = "sort", defaultValue = "date") String sort,
                                                        @RequestParam(name = "order", defaultValue = "1") int order) {
        ResponseWrapper apiResponse = new ResponseWrapper(true, 200, "All orders fetched", orderService.getAllOrders(sort, order));
        return ResponseEntity.status(200).body(apiResponse);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<ResponseWrapper> updateOrderStatus(@PathVariable long orderId, @RequestBody String status) {
        String statusStr = status.replace("\"", "");
        return orderService.updateOrderStatus(orderId, statusStr);
    }
}
