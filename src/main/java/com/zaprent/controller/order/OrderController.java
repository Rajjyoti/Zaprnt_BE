package com.zaprent.controller.order;

import com.zaprent.service.order.IOrderService;
import com.zaprnt.beans.dtos.request.order.OrderCreateRequest;
import com.zaprnt.beans.dtos.request.order.OrderUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.zaprnt.beans.error.ZResponseEntityBuilder.okCreatedResponseEntity;
import static com.zaprnt.beans.error.ZResponseEntityBuilder.okResponseEntity;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final IOrderService orderService;

    @GetMapping("/ids/{id}")
    public ResponseEntity<Map<String, Object>> getOrderById(@PathVariable String id) {
        return okResponseEntity(orderService.getOrderById(id));
    }

    @GetMapping("/user-ids/{userId}")
    public ResponseEntity<Map<String, Object>> getOrdersByUserId(@PathVariable String userId) {
        return okResponseEntity(orderService.getOrdersByUserId(userId));
    }

    @PostMapping()
    public ResponseEntity<Map<String, Object>> createOrder(@RequestBody OrderCreateRequest request) {
        return okCreatedResponseEntity(orderService.createOrder(request));
    }

    @PutMapping("/status")
    public ResponseEntity<Map<String, Object>> updateOrderStatus(@RequestBody OrderUpdateRequest request) {
        return okResponseEntity(orderService.updateOrderStatus(request));
    }
}
