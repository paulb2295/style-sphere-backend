package com.bpx.style_sphere_backend.repositories;

import com.bpx.style_sphere_backend.models.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByOrderId(Long orderId);

}
