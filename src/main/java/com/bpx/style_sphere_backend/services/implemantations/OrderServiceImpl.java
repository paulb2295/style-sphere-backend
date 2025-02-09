package com.bpx.style_sphere_backend.services.implemantations;

import com.bpx.style_sphere_backend.enums.OrderStatus;
import com.bpx.style_sphere_backend.exceptions.EmailException;
import com.bpx.style_sphere_backend.exceptions.ItemNotFoundException;
import com.bpx.style_sphere_backend.models.dtos.OrderItemInputDTO;
import com.bpx.style_sphere_backend.models.dtos.OrderItemOutputDTO;
import com.bpx.style_sphere_backend.models.dtos.OrderOutputDTO;
import com.bpx.style_sphere_backend.models.entities.Order;
import com.bpx.style_sphere_backend.models.entities.OrderItem;
import com.bpx.style_sphere_backend.models.entities.User;
import com.bpx.style_sphere_backend.repositories.OrderItemRepository;
import com.bpx.style_sphere_backend.repositories.OrderRepository;
import com.bpx.style_sphere_backend.repositories.StoreItemRepository;
import com.bpx.style_sphere_backend.repositories.UserRepository;
import com.bpx.style_sphere_backend.services.interfaces.EmailService;
import com.bpx.style_sphere_backend.services.interfaces.OrderService;
import com.bpx.style_sphere_backend.utilities.StoreItemConverter;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final StoreItemRepository storeItemRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final EmailService emailService;


    public OrderServiceImpl(StoreItemRepository storeItemRepository, OrderRepository orderRepository, OrderItemRepository orderItemRepository, EmailService emailService) {
        this.storeItemRepository = storeItemRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.emailService = emailService;
    }

    @Override
    public OrderOutputDTO createOrder(User user, List<OrderItemInputDTO> orderItemInputDTOS) {
        Order order = new Order();
        List<OrderItem> orderItems = orderItemInputDTOS.stream()
                .map(item -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrder(order);
                    orderItem.setStoreItem(storeItemRepository.findById(item.getStoreItemId()).orElseThrow(() -> new ItemNotFoundException("Item not found")));
                    orderItem.setQuantity(item.getQuantity());
                    return orderItem;
                })
                .toList();
        order.setOrderItems(orderItems);
        order.setUser(user);
        order.setOrderStatus(OrderStatus.CREATED);

        Order savedOrder = orderRepository.save(order);


        OrderOutputDTO orderOutputDTO = new OrderOutputDTO();
        orderOutputDTO.setId(savedOrder.getId());
        orderOutputDTO.setUserEmail(user.getEmail());
        orderOutputDTO.setOrderItemOutputList(
                orderItems.stream()
                        .map(item -> new OrderItemOutputDTO(
                                StoreItemConverter.toDto(item.getStoreItem()),
                                item.getQuantity()
                        )).toList()
        );
        try{
            emailService.sendEmail(orderOutputDTO.getUserEmail(),"", OrderStatus.CREATED.name(), orderOutputDTO);
        }catch (MessagingException e){
            throw new EmailException("Email could not be sent. Try Again Later");
        }

        return orderOutputDTO;
    }

    @Override
    public boolean setOrderAsDelivered(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ItemNotFoundException("Order Not Found!"));
        order.setDeliveredTime(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.DELIVERED);
        orderRepository.save(order);
        return true;
    }

    @Override
    public boolean setOrderAsCompleted(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ItemNotFoundException("Order Not Found!"));
        order.setCompletedTime(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.COMPLETED);
        orderRepository.save(order);
        return true;
    }

    @Override
    public boolean deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ItemNotFoundException("Order Not Found!"));
        orderRepository.deleteById(order.getId());
        return true;
    }

    @Override
    public List<OrderOutputDTO> getUsersOrders(User user, OrderStatus orderStatus) {
        List<Order> orders;
        if (orderStatus == null) {
            orders = orderRepository.findByUserId(user.getId());
        } else {
            orders = orderRepository.findByUserIdAndOrderStatus(user.getId(), orderStatus);
        }
        return getOrders(orders, user);
    }

    private List<OrderOutputDTO> getOrders(List<Order> orders, User user) {
        List<OrderOutputDTO> result = orders.stream()
                .map(order -> {
                    OrderOutputDTO outputDTO = new OrderOutputDTO();
                    List<OrderItem> orderItems = orderItemRepository.findByOrderId(order.getId());
                    outputDTO.setUserEmail(user.getEmail());
                    outputDTO.setId(order.getId());
                    outputDTO.setOrderItemOutputList(
                            orderItems.stream()
                                    .map(
                                            item -> new OrderItemOutputDTO(
                                                    StoreItemConverter.toDto(storeItemRepository.findById(item.getStoreItem().getId()).orElseThrow(() -> new ItemNotFoundException("Item not found!"))),
                                                    item.getQuantity()
                                            )
                                    ).toList()
                    );
                    return outputDTO;
                }).toList();
        return result;
    }

}