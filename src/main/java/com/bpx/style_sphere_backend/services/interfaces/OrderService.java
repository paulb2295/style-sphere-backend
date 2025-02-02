package com.bpx.style_sphere_backend.services.interfaces;

import com.bpx.style_sphere_backend.enums.OrderStatus;
import com.bpx.style_sphere_backend.models.dtos.OrderItemInputDTO;
import com.bpx.style_sphere_backend.models.dtos.OrderOutputDTO;
import com.bpx.style_sphere_backend.models.entities.User;

import java.util.List;

public interface OrderService {
    OrderOutputDTO createOrder(User user, List<OrderItemInputDTO> orderItemInputDTOS);

    boolean setOrderAsDelivered(Long orderId);

    boolean setOrderAsCompleted(Long orderId);

    boolean deleteOrder(Long orderId);

    List<OrderOutputDTO> getUsersOrders(User user, OrderStatus orderStatus);
}
