package com.bpx.style_sphere_backend.controllers;

import com.bpx.style_sphere_backend.aspects.CurrentUser;
import com.bpx.style_sphere_backend.enums.OrderStatus;
import com.bpx.style_sphere_backend.models.dtos.OrderItemInputDTO;
import com.bpx.style_sphere_backend.models.dtos.OrderOutputDTO;
import com.bpx.style_sphere_backend.models.entities.User;
import com.bpx.style_sphere_backend.services.interfaces.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {

    public final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/orders")
    @CurrentUser
    public ResponseEntity<OrderOutputDTO> createOrder(User user, @RequestBody List<OrderItemInputDTO> orderItemInputDTOS) {
        return ResponseEntity.status(201).body(
                orderService.createOrder(user, orderItemInputDTOS)
        );
    }

    @GetMapping("/orders")
    @CurrentUser
    public ResponseEntity<List<OrderOutputDTO>> getOrdersByStatus(@RequestParam(required = false) OrderStatus orderStatus, User user) {
        return ResponseEntity.status(200).body(orderService.getUsersOrders(user, orderStatus));
    }

    @PatchMapping("/admin/orders/delivered/{id}")
    public ResponseEntity<Boolean> setOrderAsDelivered(@PathVariable Long id) {
        return ResponseEntity.status(200).body(orderService.setOrderAsDelivered(id));
    }

    @PatchMapping("/admin/orders/completed/{id}")
    public ResponseEntity<Boolean> setOrderAsCompleted(@PathVariable Long id) {
        return ResponseEntity.status(200).body(orderService.setOrderAsCompleted(id));
    }

    @DeleteMapping("/admin/orders/{id}")
    public ResponseEntity<Boolean> deleteOrder(@PathVariable Long id) {
        return ResponseEntity.status(200).body(orderService.deleteOrder(id));
    }

}
