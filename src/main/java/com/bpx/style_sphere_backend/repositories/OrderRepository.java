package com.bpx.style_sphere_backend.repositories;

import com.bpx.style_sphere_backend.enums.OrderStatus;
import com.bpx.style_sphere_backend.models.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserId(Long userId);

    List<Order> findByUserIdAndOrderStatus(Long userId, OrderStatus orderStatus);
}
