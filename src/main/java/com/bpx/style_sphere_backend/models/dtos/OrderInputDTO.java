package com.bpx.style_sphere_backend.models.dtos;

import java.util.List;

public class OrderInputDTO {

    private Long id;
    private String userEmail;
    private List <OrderItemInputDTO> orderItemInputDTOS;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public List<OrderItemInputDTO> getOrderItemDTOS() {
        return orderItemInputDTOS;
    }

    public void setOrderItemDTOS(List<OrderItemInputDTO> orderItemInputDTOS) {
        this.orderItemInputDTOS = orderItemInputDTOS;
    }
}



