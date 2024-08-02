package com.zaprent.controller.order;

import com.zaprent.service.order.IOrderItemService;
import com.zaprnt.beans.dtos.request.order.OrderItemBulkStatusUpdateRequest;
import com.zaprnt.beans.dtos.request.order.OrderItemStatusRequest;
import com.zaprnt.beans.models.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.zaprnt.beans.error.ZResponseEntityBuilder.okCreatedResponseEntity;
import static com.zaprnt.beans.error.ZResponseEntityBuilder.okResponseEntity;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order-items")
public class OrderItemController {

    private final IOrderItemService orderItemService;

    @GetMapping("/ids/{id}")
    public ResponseEntity<Map<String, Object>> getOrderItemById(@PathVariable String id) {
        return okResponseEntity(orderItemService.getOrderItemById(id));
    }

    @GetMapping("/order-ids/{orderId}")
    public ResponseEntity<Map<String, Object>> getOrderItemsByOrderId(@PathVariable String orderId) {
        return okResponseEntity(orderItemService.getOrderItemsByOrderId(orderId));
    }

    //TODO: search feature for various filters

    @GetMapping("/status-details/order-ids/{orderId}")
    public ResponseEntity<Map<String, Object>> getOrderItemStatusDetailsForOrder(@PathVariable String orderId) {
        return okCreatedResponseEntity(orderItemService.getOrderItemStatusDetailsForOrder(orderId));
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createOrderItem(@RequestBody OrderItem request) {
        return okCreatedResponseEntity(orderItemService.createOrderItem(request));
    }

    @PostMapping("/bulk")
    public ResponseEntity<Map<String, Object>> createOrderItems(@RequestBody List<OrderItem> request) {
        orderItemService.createOrderItems(request);
        return okCreatedResponseEntity("Items Created");
    }

    @PutMapping("/statuses")
    public ResponseEntity<Map<String, Object>> updateOrderItemStatus(@RequestBody OrderItemStatusRequest request) {
        return okResponseEntity(orderItemService.updateOrderItemStatus(request));
    }

    @PutMapping("/statuses/bulk")
    public ResponseEntity<Map<String, Object>> updateBulkOrderItemStatus(@RequestBody OrderItemBulkStatusUpdateRequest request) {
        orderItemService.updateBulkOrderItemStatus(request);
        return okResponseEntity("Statuses updated");
    }
}
